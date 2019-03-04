/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.wm.model.WarehouseEntity;
import com.olp.jpa.domain.docu.wm.model.WarehouseLocatorEntity;
import com.olp.jpa.domain.docu.wm.model.WarehouseZoneEntity;
import com.olp.jpa.domain.docu.wm.model.WmEnums.ZoneType;
import com.olp.jpa.util.JpaUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author raghosh
 */
@Service("warehouseZoneService")
public class WarehouseZoneServiceImpl extends AbstractServiceImpl<WarehouseZoneEntity, Long> implements WarehouseZoneService {

  @Autowired
  @Qualifier("warehouseZoneRepository")
  private WarehouseZoneRepository zoneRepo;
  
  @Autowired
  @Qualifier("warehouseRepository")
  private WarehouseRepository whRepo;
  
  @Autowired
  @Qualifier("warehouseLocatorService")
  private WarehouseLocatorService locSvc;

  @Override
  @Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
  public void validate(WarehouseZoneEntity entity, boolean valParent, CommonEnums.EntityVdationType type) throws EntityValidationException {
    
      if (valParent) {
          WarehouseEntity wh = entity.getWarehouseRef(), wh2 = null;
          if (wh == null)
              throw new EntityValidationException("Warehouse reference cannot be null !!");
          
          if (wh.getId() == null) {
              try {
                  wh2 = whRepo.findByWarehouseCode(wh.getWarehouseCode());
              } catch (javax.persistence.NoResultException ex) {
                  throw new EntityValidationException("Could not find warehouse with code - " + wh.getWarehouseCode());
              }
          } else {
              try {
                  wh2 = whRepo.findOne(wh.getId());
              } catch (javax.persistence.NoResultException ex) {
                  throw new EntityValidationException("Could not find warehouse with id - " + wh.getId());
              }
          }
          
          if (wh2 == null)
              throw new EntityValidationException("Could not find warehouse using either code or id !");
          
          entity.setWarehouseRef(wh2);
          entity.setWarehouseCode(wh2.getWarehouseCode());
          
      } // end if valParent
      
      if (entity.getLocators() != null && !entity.getLocators().isEmpty()) {
          if (entity.getZoneType() != ZoneType.STORAGE)
              throw new EntityValidationException("Locators can only be defined for Storage type zones !");
          
          if (!entity.getIslocatorEnabled())
              throw new EntityValidationException("Zone cannot have locators unless locator control is enabled !");
      }
      
      List<WarehouseLocatorEntity> locators = entity.getLocators();
      if (locators != null && !locators.isEmpty()) {
          
          for (WarehouseLocatorEntity locator : locators) {
              
              if (EntityVdationType.PRE_INSERT == type) {
                  locator.setZoneRef(entity);
                  locator.setZoneCode(entity.getZoneCode());
                  if (entity.getWarehouseRef() != null) {
                      locator.setWarehouseCode(entity.getWarehouseRef().getWarehouseCode());
                  }
                  //locator.setTenantId(getTenantId());
                  //locator.setRevisionControl(getRevisionControl());
              }
              
              locSvc.validate(locator, false, type);
              
          }
          
      } // end if locators != null .. 
      
      if (EntityVdationType.PRE_INSERT == type) {
          this.updateTenantWithRevision(entity);
      } else if (EntityVdationType.PRE_UPDATE == type) {
          JpaUtil.updateRevisionControl(entity, true);
      }
    
  }

  
    @Override
    protected String getAlternateKeyAsString(WarehouseZoneEntity entity) {
        StringBuilder buff = new StringBuilder();
        buff.append("{ \"warehouse-zone\" : \"").append(entity.getId()).append("\" }");
        
        return(buff.toString());
    }
    

  @Override
  protected JpaRepository<WarehouseZoneEntity, Long> getRepository() {
      return(zoneRepo);
  }

  @Override
  protected ITextRepository<WarehouseZoneEntity, Long> getTextRepository() {
      return(zoneRepo);
  }
  
    @Override
    @Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
    public WarehouseZoneEntity findByZoneCode(String whCode, String zoneCode) {
        
        WarehouseZoneEntity zone = zoneRepo.findByZoneCode(whCode, zoneCode);
        
        return(zone);
    }
    
    @Override
    @Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
    public List<WarehouseZoneEntity> findByWarehouseCode(String whCode) {
        
        List<WarehouseZoneEntity> list = zoneRepo.findByWarehouseCode(whCode);
        
        return(list);
    }
  
  @Override
  protected Outcome postProcess(int opCode, WarehouseZoneEntity entity)
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
  protected Outcome preProcess(int opCode, WarehouseZoneEntity entity)
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
            validate(entity, true, EntityVdationType.PRE_UPDATE);
        case DELETE:
        case DELETE_BULK:
            preDelete(entity);
        default:
            break;
    }
    
    return(result);
  }
  
    @Override
    protected WarehouseZoneEntity doUpdate(WarehouseZoneEntity neu, WarehouseZoneEntity old) throws EntityValidationException {

        if (!old.getZoneCode().equals(neu.getZoneCode()))
            throw new EntityValidationException("Warehouse Zonecode cannot be updated ! Existing - " + old.getZoneCode() + " , new - " + neu.getZoneCode());

        if (!old.getWarehouseCode().equals(neu.getWarehouseCode())) {
            throw new EntityValidationException("Warehouse cannot be updated ! Existing - " + old.getWarehouseCode() + ", new - " + neu.getWarehouseCode());
        }

        if (!old.getZoneSubType().equals(neu.getZoneSubType())) {
            throw new EntityValidationException("Warehouse Zone subtype cannot be updated ! Existing - " + old.getZoneSubType() + ", new - " + neu.getZoneSubType());
        }

        if (old.getWarehouseRef().getWarehouseCode() != null &&
            !old.getWarehouseRef().getWarehouseCode().equals(neu.getWarehouseRef().getWarehouseCode())) {
            throw new EntityValidationException("Warehouse Ref Code cannot be updated ! Existing - " + old.getWarehouseRef().getWarehouseCode() + ", new - " + neu.getWarehouseRef().getWarehouseCode());
        }

        List<WarehouseLocatorEntity> newLocators = neu.getLocators(), oldLocators = old.getLocators();
        List<Long> deleteList = new ArrayList<>();
        if (oldLocators != null && !oldLocators.isEmpty()) {
            for (WarehouseLocatorEntity oldLoc : oldLocators) {
                boolean found = false;
                if (newLocators != null && !newLocators.isEmpty()) {
                    for (WarehouseLocatorEntity newLoc : newLocators) {
                        if (newLoc.getId() == null) {
                            // new locator being created, which is fine
                            // the actual locator addition will be taken care in the subsequent code block
                            continue; 
                        }

                        if (Objects.equals(newLoc.getId(), oldLoc.getId())) {
                            found = true;
                            break;
                        }
                    } // end for newLocators
                } // end if newLocators != null

                if (!found) {
                    // i.e. locator is deleted
                    // TODO: Check if any invenory stock lying with the locator
                    WarehouseEntity wh = old.getWarehouseRef();
                    if (wh != null && wh.getLifecycleStatus() != LifeCycleStatus.INACTIVE && !isPrivilegedContext())
                        throw new EntityValidationException("Cannot delete locator with id " + oldLoc.getId() + " when warehouse status is " + wh.getLifecycleStatus());

                    deleteList.add(oldLoc.getId());
                }

            } // end for oldLocators
        } // end if oldLocators != null

        if (newLocators != null && !newLocators.isEmpty()) {
            for (WarehouseLocatorEntity newLoc : newLocators) {
                WarehouseLocatorEntity oldLoc2 = null;
                boolean found = false;
                if (oldLocators != null && !oldLocators.isEmpty()) {
                    for (WarehouseLocatorEntity oldLoc : oldLocators) {
                        if (Objects.equals(newLoc.getId(), oldLoc.getId())) {
                            // found the locator
                            oldLoc2 = oldLoc;
                            found = true;
                            break;
                        }
                    }
                } // end if oldLocators != null
                if (!found) {
                    // new locator being created 
                    newLoc.setZoneRef(old);
                    newLoc.setZoneCode(old.getZoneCode());
                    newLoc.setWarehouseCode(old.getWarehouseCode());

                    locSvc.validate(newLoc, false, EntityVdationType.PRE_INSERT);
                    
                    old.getLocators().add(newLoc);
                } else {
                    // existing locator
                    boolean locUpdated = locSvc.checkForUpdate(newLoc, oldLoc2);
                    if (locUpdated) {
                        locSvc.update(newLoc);
                    }
                }
            } // end for newLocators
        } // end if newLocators != null

        if (!deleteList.isEmpty()) {
            for (Long id : deleteList) {
                int pos = -1;
                for (int i = 0; oldLocators != null && !oldLocators.isEmpty() && i < oldLocators.size(); i++) {
                    if (Objects.equals(id, oldLocators.get(i).getId())) {
                        pos = i;
                        break;
                    }
                }
                if (pos > -1) {
                    old.getLocators().remove(pos);
                    locSvc.delete(id);
                }
            } // end for 
        }

        old.setAllowDynamicLocator(neu.getAllowDynamicLocator());
        old.setIslocatorEnabled(neu.getIslocatorEnabled());
        old.setMaintStorageHistory(neu.isMaintStorageHistory());

        this.updateRevisionControl(old);
        return(old);
    }
    
    @Override
    public boolean checkForUpdate(WarehouseZoneEntity neu, WarehouseZoneEntity old) {
        
        boolean result = false;
        
        if (!Objects.equals(neu.getIslocatorEnabled(), old.getIslocatorEnabled()) ||
                !Objects.equals(neu.isMaintStorageHistory(), old.isMaintStorageHistory()) ||
                !Objects.equals(neu.getSubInventory(), old.getSubInventory()) ||
                !Objects.equals(neu.getWarehouseCode(), old.getWarehouseCode()) ||
                !Objects.equals(neu.getZoneCode(), old.getZoneCode()) ||
                !Objects.equals(neu.getZoneName(), old.getZoneName()) ||
                !Objects.equals(neu.getZoneSubType(), old.getZoneSubType()) ||
                !Objects.equals(neu.getZoneType(), old.getZoneType())
                ) {
            
            result = true;
            return(result);
        }
        
        if (neu.getLocators() == null || neu.getLocators().isEmpty()) {
            if (old.getLocators() != null && !old.getLocators().isEmpty()) {
                result = true;
                return(result);
            }
        } else {
            if (old.getLocators() != null && !old.getLocators().isEmpty()) {
                result = true;
                return(result);
            } else {
                // both are not null
                if (!Objects.equals(neu.getLocators().size(), old.getLocators().size())) {
                    result = true;
                    return(result);
                } else {
                    for (int i=0; i < neu.getLocators().size(); i++) {
                        WarehouseLocatorEntity newLoc = neu.getLocators().get(i);
                        WarehouseLocatorEntity oldLoc = old.getLocators().get(i);
                        if (locSvc.checkForUpdate(newLoc, oldLoc)) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        } // end if newLocators == null
        
        return(result);
    }
  
    private void preProcessAdd(WarehouseZoneEntity entity) throws EntityValidationException {
      validate(entity, true, EntityVdationType.PRE_INSERT);
      this.updateTenantWithRevision(entity);
    }
    
    private void preDelete(WarehouseZoneEntity entity) throws EntityValidationException {
        if (entity.getWarehouseRef() != null) {
            WarehouseEntity wh = entity.getWarehouseRef();
            if (!isPrivilegedContext() && wh.getLifecycleStatus() != LifeCycleStatus.INACTIVE)
                throw new EntityValidationException("Cannot delete zone when warehouse status is " + wh.getLifecycleStatus());
        }
    }

  //private void preProcessUpdate(WarehouseZoneEntity entity) throws EntityValidationException {
  //      validate(entity);
  //      this.updateRevisionControl(entity);
  //}
  
}
