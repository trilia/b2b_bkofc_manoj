/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.CommonEnums.WorkflowStatus;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.comm.repo.WorkflowStatusRepository;
import com.olp.jpa.domain.docu.org.model.LocationEntity;
import com.olp.jpa.domain.docu.org.model.OrganizationEntity;
import com.olp.jpa.domain.docu.org.repo.OrganizationRepository;
import com.olp.jpa.domain.docu.wm.model.WarehouseEntity;
import com.olp.jpa.domain.docu.wm.model.WarehouseLocatorEntity;
import com.olp.jpa.domain.docu.wm.model.WarehouseWorkflowStatusEntity;
import com.olp.jpa.domain.docu.wm.model.WarehouseZoneEntity;
import com.olp.jpa.util.JpaUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raghosh
 */
@Service("warehouseService")
public class WarehouseServiceImpl extends AbstractServiceImpl<WarehouseEntity, Long> implements WarehouseService {

  @Autowired
  @Qualifier("warehouseRepository")
  private WarehouseRepository warehouseRepo;
  
  @Autowired
  @Qualifier("warehouseZoneService")
  private WarehouseZoneService zoneSvc;
  
  @Autowired
  @Qualifier("workflowStatusRepository")
  private WorkflowStatusRepository wfRepo;
  
  @Autowired
  @Qualifier("organizationRepository")
  private OrganizationRepository orgRepo;

  
  @Override
  protected String getAlternateKeyAsString(WarehouseEntity entity) {
        StringBuilder buff = new StringBuilder();
        buff.append("{ \"warehouse\" : \"").append(entity.getId()).append("\" }");
        
        return(buff.toString());
    }

  @Override
  protected JpaRepository<WarehouseEntity, Long> getRepository() {
      return(warehouseRepo);
  }

  @Override
  protected ITextRepository<WarehouseEntity, Long> getTextRepository() {
      return(warehouseRepo);
  }
  
  @Override
  @Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
  public WarehouseEntity findByWarehouseCode(String warehouseCode) {
      WarehouseEntity wh = warehouseRepo.findByWarehouseCode(warehouseCode);
      
      return(wh);
  }
  
  @Override
  @Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
  public void validate(WarehouseEntity entity, EntityVdationType type) throws EntityValidationException {
      
      List<WarehouseZoneEntity> zones = entity.getZones();
      if (zones != null && !zones.isEmpty()) {
          for (WarehouseZoneEntity zone : zones) {
              zone.setWarehouseRef(entity);
              zone.setWarehouseCode(entity.getWarehouseCode());
              zoneSvc.validate(zone, false, type);
          }
      } // end if zones != null
      
      if (entity.getOrganizationRef() != null) {
          OrganizationEntity org = entity.getOrganizationRef(), org2 = null;
          if (org.getId() == null) {
              try {
                  org2 = orgRepo.findByOrgCode(org.getOrgCode());
              } catch (javax.persistence.NoResultException ex) {
                  throw new EntityValidationException("Could not find organization with code - " + org.getOrgCode());
              }
          } else {
              try {
                  org2 = orgRepo.findOne(org.getId());
              } catch (javax.persistence.NoResultException ex) {
                  throw new EntityValidationException("Could not find organization with id - " + org.getId());
              }
          }
          
          entity.setOrganizationRef(org2);
      }
      
      if (EntityVdationType.PRE_INSERT == type) {
          if (entity.getWhLocation() != null) {
              LocationEntity loc = entity.getWhLocation();
              loc.setTenantId(getTenantId());
              loc.setRevisionControl(getRevisionControl());
          }
          entity.setLifecycleStatus(LifeCycleStatus.INACTIVE);
          this.updateTenantWithRevision(entity);
      } else if (EntityVdationType.PRE_UPDATE == type) {
          
            // this is a dummy update of lifecycle status, we do not use this field for actual
          // update. This is needed only to get past the required attribute checker.
          entity.setLifecycleStatus(LifeCycleStatus.INACTIVE); 
          
          JpaUtil.updateRevisionControl(entity, true);
      }
    
  }
    
    @Override
    @Transactional
    public Long requestStatusChange(String warehouseCode, LifeCycleStatus status) throws EntityValidationException {

        Long result = -1L;
        
        WarehouseEntity wh = null;
        boolean submitWorkflow = false;

        try {
            wh = warehouseRepo.findByWarehouseCode(warehouseCode);
        } catch (javax.persistence.NoResultException ex) {
            //throw new EntityValidationException("Could not find warehouse with code - " + warehouseCode);
            // no-op
        }
        
        if (wh == null)
            throw new EntityValidationException("Could not find warehouse with code - " + warehouseCode);
        
        if (wh.getLifecycleStatus() == LifeCycleStatus.INACTIVE) {
            if (status != LifeCycleStatus.INACTIVE) {
                if (isPrivilegedContext()) {
                    wh.setLifecycleStatus(status);
                    JpaUtil.updateRevisionControl(wh, true);
                    result = 0L;
                } else {
                    submitWorkflow = true;
                }
            }
        } else if (wh.getLifecycleStatus() == LifeCycleStatus.ACTIVE) {
            if (status == LifeCycleStatus.INACTIVE) {
                if (isPrivilegedContext()) {
                    wh.setLifecycleStatus(status);
                    JpaUtil.updateRevisionControl(wh, true);
                    result = 0L;
                } else {
                    throw new EntityValidationException("Cannot set status to INACTIVE when current status is ACTIVE !");
                }
            } else if (status == LifeCycleStatus.SUSPENDED || status == LifeCycleStatus.TERMINATED) {
                if (isPrivilegedContext()) {
                    wh.setLifecycleStatus(status);
                    JpaUtil.updateRevisionControl(wh, true);
                    result = 0L;
                } else {
                    submitWorkflow = true;
                }
            }
        } else if (wh.getLifecycleStatus() == LifeCycleStatus.SUSPENDED) {
            if (status == LifeCycleStatus.ACTIVE || status == LifeCycleStatus.TERMINATED) {
                if (isPrivilegedContext()) {
                    wh.setLifecycleStatus(status);
                    JpaUtil.updateRevisionControl(wh, true);
                    result = 0L;
                } else {
                    submitWorkflow = true;
                }
            } else if (status == LifeCycleStatus.INACTIVE) {
                if (isPrivilegedContext()) {
                    wh.setLifecycleStatus(status);
                    JpaUtil.updateRevisionControl(wh, true);
                    result = 0L;
                } else {
                    throw new EntityValidationException("Cannot change status to INACTIVE when current status is SUSPENDED !");
                }
            }
        } else if (wh.getLifecycleStatus() == LifeCycleStatus.TERMINATED) {
            if (isPrivilegedContext()) {
                wh.setLifecycleStatus(status);
                JpaUtil.updateRevisionControl(wh, true);
                result = 0L;
            } else {
                throw new EntityValidationException("Cannot change status to " + status + " when current status is TERMINATED !");
            }
        }
        
        if (submitWorkflow) {
            
            WarehouseWorkflowStatusEntity wfStatus = new WarehouseWorkflowStatusEntity();
            
            wfStatus.setEntityId(wh.getId());
            wfStatus.setEntityType("WAREHOUSE_DEF");
            wfStatus.setNewDefinition(status.toString());
            wfStatus.setOldDefinition(wh.getLifecycleStatus().toString());
            wfStatus.setWarehouseCode(wh.getWarehouseCode());
            wfStatus.setWarehouseRef(wh);
            wfStatus.setWorkflowName("WAREHOUSE_STATUS_CHANGE");
            wfStatus.setWorkflowStatus(WorkflowStatus.NEW);
            wfStatus.setTenantId(getTenantId());
            wfStatus.setRevisionControl(getRevisionControl());
            
            wfRepo.save(wfStatus);
            
            result = wfStatus.getId();
        }

        return(result);
    }


  @Override
  protected Outcome postProcess(int opCode, WarehouseEntity entity)
      throws EntityValidationException {
    
    Outcome result = new Outcome();
    result.setResult(true);
    
    switch(opCode) {
        case ADD:
        case ADD_BULK:
        case UPDATE:
        case UPDATE_BULK:
        case DELETE:
        case DELETE_BULK:
        default:
            break;
    }
    
    return(result);
}

  @Override
  protected Outcome preProcess(int opCode, WarehouseEntity entity)
      throws EntityValidationException {
    
    Outcome result = new Outcome();
    result.setResult(true);
    
    switch(opCode) {
        case ADD:
        case ADD_BULK:
            preProcessAdd(entity);
            break;
        case UPDATE:
        case UPDATE_BULK:
            validate(entity, EntityVdationType.PRE_UPDATE);
        case DELETE:
        case DELETE_BULK:
            preDelete(entity);
            break;
        default:
            break;
    }
    
    return(result);
  }
  
    @Override
    protected WarehouseEntity doUpdate(WarehouseEntity neu, WarehouseEntity old) throws EntityValidationException {

        if (!old.getWarehouseCode().equals(neu.getWarehouseCode())) {
            throw new EntityValidationException("Warehouse cannot be updated ! Existing - " + old.getWarehouseCode() + ", new - " + neu.getWarehouseCode());
        }
        
        //validate(neu, EntityVdationType.PRE_UPDATE);
        
        if (!Objects.equal(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
            if (!isPrivilegedContext())
                throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
        }
        
        
        // first check for zone / locator deletes when warehouse is active
        ArrayList<Long> deletedZones = new ArrayList<>();
        ArrayList<Long> deletedLocators = new ArrayList<>();
        
        if (old.getZones() != null && !old.getZones().isEmpty()) {
            for (WarehouseZoneEntity oldZone: old.getZones()) {
                boolean found = false;
                WarehouseZoneEntity newZone2 = null;
                if (neu.getZones() != null && !neu.getZones().isEmpty()) {
                    for (WarehouseZoneEntity newZone : neu.getZones()) {
                        if (Objects.equal(newZone.getId(), oldZone.getId())) {
                            found = true;
                            newZone2 = newZone;
                            break;
                        }
                    } // end for new zone
                }
                if (!found) {
                    // zone deleted
                    if (old.getLifecycleStatus() != LifeCycleStatus.INACTIVE) {
                        if (isPrivilegedContext()) {
                            deletedZones.add(oldZone.getId());
                        } else {
                            throw new EntityValidationException("Cannot delete zone " + oldZone.getZoneCode() + " when warehouse is " + old.getLifecycleStatus());
                        }
                    } else {
                        deletedZones.add(oldZone.getId());
                    }

                } else {
                    // check for locator delete
                    if (oldZone.getLocators() != null && !oldZone.getLocators().isEmpty()) {
                        for (WarehouseLocatorEntity oldLoc : oldZone.getLocators()) {
                            boolean found2 = false;
                            if (newZone2.getLocators() != null && !newZone2.getLocators().isEmpty()) {
                                for (WarehouseLocatorEntity newLoc : newZone2.getLocators()) {
                                    if (Objects.equal(newLoc.getId(), oldLoc.getId())) {
                                        found2 = true;
                                        break;
                                    }
                                } // end for newLoc2
                            }
                            if (!found2) {
                                // locator deleted
                                if (old.getLifecycleStatus() != LifeCycleStatus.INACTIVE) {
                                    if (isPrivilegedContext()) {
                                        deletedLocators.add(oldLoc.getId());
                                    } else {
                                        throw new EntityValidationException("Cannot delete locator with code -  " + 
                                            oldLoc.getLocatorTypeCode() + 
                                            " of zone " + oldZone.getZoneCode() + " when warehouse status is " + old.getLifecycleStatus());
                                    }
                                } else {
                                    deletedLocators.add(oldLoc.getId());
                                }

                            }
                        } // end for oldLoc
                    }
                }
            } // end for old zone
        }
        
        boolean updated = false;
        
        if (!deletedZones.isEmpty() || !deletedLocators.isEmpty()) {
            updated = true;
        } else {
            updated = checkForUpdate(neu, old);
        }
        
        if (updated) {
            if (isPrivilegedContext()) {
                // carry out the update
                if (neu.getZones() != null && !neu.getZones().isEmpty()) {
                    for (WarehouseZoneEntity newZone : neu.getZones()) {
                        WarehouseZoneEntity oldZone2 = null;
                        boolean found = false;
                        if (old.getZones() != null && !old.getZones().isEmpty()) {
                            for (WarehouseZoneEntity oldZone : old.getZones()) {
                                if (Objects.equal(newZone.getId(), oldZone.getId())) {
                                    oldZone2 = oldZone;
                                    found = true;
                                    break;
                                }
                            } // end for old.getZones
                        } // end if old.getZones
                        
                        if (!found) {
                            // new zone being added
                            newZone.setWarehouseRef(old);
                            newZone.setWarehouseCode(old.getWarehouseCode());
                            newZone.setRevisionControl(getRevisionControl());
                            newZone.setTenantId(getTenantId());
                            
                            zoneSvc.validate(newZone, false, EntityVdationType.PRE_INSERT);
                            old.getZones().add(newZone);
                        } else {
                            boolean zoneUpdated = zoneSvc.checkForUpdate(newZone, oldZone2);
                            if (zoneUpdated) {
                                zoneSvc.update(newZone);
                            }
                        }
                        
                    } //end for neu.getZones
                } // end if neu.getZones != null
                
                
                if (!deletedZones.isEmpty()) {
                    // dissociate zones
                    for (Long id : deletedZones) {
                        int pos = -1;
                        for (int i=0; i < old.getZones().size(); i++) {
                            WarehouseZoneEntity oldZone = old.getZones().get(i);
                            
                            if (Objects.equal(id, oldZone.getId())) {
                                pos = i;
                                break;
                            }
                        }
                        if (pos > -1) {
                            old.getZones().remove(pos);
                            zoneSvc.delete(id);
                        }
                    }
                } //end if deletedZones not empty
                
                /*
                
                // Zone service takes care of locator deletion. Hence not needed here.
                
                if (!deletedLocators.isEmpty()) {
                    // dissociate locator
                    // NOTE: we cannot combine this code with zone deletion. If no zone is deleted, only locator
                    // deleted, the code won't pass the check "if !deletedZones.isEmpty() "
                    
                    for (WarehouseZoneEntity oldZone : old.getZones()) {
                        
                        for (Long id : deletedLocators) {
                            int pos = -1;
                            for (int i=0; oldZone.getLocators() != null && i < oldZone.getLocators().size(); i++) {
                                WarehouseLocatorEntity oldLoc = oldZone.getLocators().get(i);
                                if (Objects.equal(id, oldLoc.getId())) {
                                    pos = i;
                                    break;
                                }
                            }
                            if (pos > -1)
                                oldZone.getLocators().remove(pos);
                        } // end for deletedLocators
                        
                    } //end for oldZone
                } // if deletedLocaors is not empty
                
                */
                
                old.setOrganizationRef(neu.getOrganizationRef());
                old.setWarehouseName(neu.getWarehouseName());
                if (neu.getWhLocation() != null) {
                    old.getWhLocation().setAddressLine1(neu.getWhLocation().getAddressLine1());
                    old.getWhLocation().setAddressLine2(neu.getWhLocation().getAddressLine2());
                    old.getWhLocation().setAddressLine3(neu.getWhLocation().getAddressLine3());
                    old.getWhLocation().setAddressLine4(neu.getWhLocation().getAddressLine4());
                    old.getWhLocation().setCity(neu.getWhLocation().getCity());
                    old.getWhLocation().setCountry(neu.getWhLocation().getCountry());
                    old.getWhLocation().setEndDate(neu.getWhLocation().getEndDate());
                    old.getWhLocation().setLocationSource(neu.getWhLocation().getLocationSource());
                    old.getWhLocation().setLocationType(neu.getWhLocation().getLocationType());
                    old.getWhLocation().setStartDate(neu.getWhLocation().getStartDate());
                    old.getWhLocation().setStateOrProvince(neu.getWhLocation().getStateOrProvince());
                    old.getWhLocation().setZipCode(neu.getWhLocation().getZipCode());
                }
                old.setWmControlEnabled(neu.getWmControlEnabled());
                
                JpaUtil.updateRevisionControl(old, true);
                
            } else {
                
                // submit the workflow
                WarehouseWorkflowStatusEntity wfStatus = new WarehouseWorkflowStatusEntity();
            
                wfStatus.setEntityId(neu.getId());
                wfStatus.setEntityType("WAREHOUSE_DEF");
                //wfStatus.setNewDefinition(status.toString());
                //wfStatus.setOldDefinition(neu.getLifecycleStatus().toString());
                wfStatus.setWarehouseCode(neu.getWarehouseCode());
                wfStatus.setWarehouseRef(neu);
                wfStatus.setWorkflowName("WAREHOUSE_UPDATE");
                wfStatus.setWorkflowStatus(WorkflowStatus.NEW);
                wfStatus.setTenantId(getTenantId());
                wfStatus.setRevisionControl(getRevisionControl());

                wfRepo.save(wfStatus);

                //result = wfStatus.getId();
            }
        }

        
        return(old);
    }
    
  
    private void preProcessAdd(WarehouseEntity entity) throws EntityValidationException {
      validate(entity, EntityVdationType.PRE_INSERT);
      this.updateTenantWithRevision(entity);
    }
  
    private void preDelete(WarehouseEntity entity) throws EntityValidationException {
        if (!isPrivilegedContext() && entity.getLifecycleStatus() != LifeCycleStatus.INACTIVE)
            throw new EntityValidationException("Cannot delete warehouse when state is " + entity.getLifecycleStatus());
    }

    //private void preProcessUpdate(WarehouseEntity entity) throws EntityValidationException {
    //      validate(entity);
    //      this.updateRevisionControl(entity);
    //}
    
    private boolean checkForUpdate(WarehouseEntity neu, WarehouseEntity old) {
        
        boolean result = false;
        
        if (!Objects.equal(neu.getWarehouseCode(), old.getWarehouseCode()) ||
                !Objects.equal(neu.getWarehouseName(), old.getWarehouseName()) ||
                !Objects.equal(neu.getWmControlEnabled(), old.getWmControlEnabled())
                ) {
            
            result = true;
            return(result);
        }
        
        if (neu.getOrganizationRef() == null) {
            if (old.getOrganizationRef() != null) {
                result = true;
                return(result);
            }
        } else {
            if (old.getOrganizationRef() == null) {
                result = true;
                return(result);
            } else {
                // both not null
                if (neu.getOrganizationRef().getId() == null) {
                    if (!Objects.equal(neu.getOrganizationRef().getOrgCode(), old.getOrganizationRef().getOrgCode())) {
                        result = true;
                        return(result);
                    }
                } else {
                    if (!Objects.equal(neu.getOrganizationRef().getId(), old.getOrganizationRef().getId())) {
                        result = true;
                        return(result);
                    }
                }
            }
        }
        
        if (neu.getWhLocation() == null) {
            if (old.getWhLocation() != null) {
                result = true;
                return(result);
            }
        } else {
            if (old.getWhLocation() == null) {
                result = true;
                return(result);
            } else {
                // both not null
                
                if (neu.getWhLocation().getId() != null) {
                    if (!Objects.equal(neu.getWhLocation().getId(), old.getWhLocation().getId())) {
                        result = true;
                        return(result);
                    }
                }
                
                // this location is tied to a specific warehouse. Hence updates to location is done as part of 
                // warehouse update. Therefore checking individual fields of location for update 
                
                if (!Objects.equal(neu.getWhLocation().getLocationAlias(), old.getWhLocation().getLocationAlias()) ||
                        !Objects.equal(neu.getWhLocation().getLocationSource(), old.getWhLocation().getLocationSource()) ||
                        !Objects.equal(neu.getWhLocation().getLocationType(), old.getWhLocation().getLocationType()) ||
                        !Objects.equal(neu.getWhLocation().getStartDate(), old.getWhLocation().getStartDate()) ||
                        !Objects.equal(neu.getWhLocation().getEndDate(), old.getWhLocation().getEndDate()) ||
                        !Objects.equal(neu.getWhLocation().getStateOrProvince(), old.getWhLocation().getStateOrProvince()) ||
                        !Objects.equal(neu.getWhLocation().getAddressLine1(), old.getWhLocation().getAddressLine1()) ||
                        !Objects.equal(neu.getWhLocation().getAddressLine2(), old.getWhLocation().getAddressLine2()) ||
                        !Objects.equal(neu.getWhLocation().getAddressLine3(), old.getWhLocation().getAddressLine3()) ||
                        !Objects.equal(neu.getWhLocation().getAddressLine4(), old.getWhLocation().getAddressLine4()) ||
                        !Objects.equal(neu.getWhLocation().getCity(), old.getWhLocation().getCity()) ||
                        !Objects.equal(neu.getWhLocation().getZipCode(), old.getWhLocation().getZipCode()) ||
                        !Objects.equal(neu.getWhLocation().getCountry(), old.getWhLocation().getCountry())

                        ) {
                    result = true;
                    return(result);
                }
                
            }
        }
        
        if (neu.getZones() == null || neu.getZones().isEmpty()) {
            if (old.getZones() != null && !old.getZones().isEmpty()) {
                result = true;
                return(result);
            }
        } else {
            if (old.getZones() == null || old.getZones().isEmpty()) {
                result = true;
                return(result);
            } else {
                // both are not null 
                if (!Objects.equal(neu.getZones().size(), old.getZones().size())) {
                    result = true;
                    return(result);
                } else {
                    for (int i=0; i < neu.getZones().size(); i++) {
                        WarehouseZoneEntity newZone = neu.getZones().get(i), oldZone = old.getZones().get(i);
                        if (newZone.getId() != null) {
                            if (!Objects.equal(newZone.getId(), oldZone.getId())) {
                                result = true;
                                return(result);
                            }
                        } else {
                            boolean outcome = zoneSvc.checkForUpdate(newZone, oldZone);
                            if (!outcome) {
                                result = true;
                                return(result);
                            }
                        }
                    } // end for
                }
            }
        }
        
        return(result);
    }
  
}
