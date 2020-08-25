package com.olp.jpa.domain.docu.llty.repo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyEntity;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTierEntity;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTxnEntity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.ParticipationStatus;
import com.olp.jpa.domain.docu.llty.model.LoyaltyProgramEntity;
import com.olp.jpa.domain.docu.om.repo.CustomerRepository;
import com.olp.jpa.util.JpaUtil;

@Service("customerLoyaltyService")
public class CustomerLoyaltyServiceImpl extends AbstractServiceImpl<CustomerLoyaltyEntity, Long>
		implements CustomerLoyaltyService {

	@Autowired
	@Qualifier("customerLoyaltyRepository")
	private CustomerLoyaltyRepository customerLoyaltyRepository;

	@Autowired
	@Qualifier("customerRepository")
	private CustomerRepository customerRepository;

	@Autowired
	@Qualifier("loyaltyProgramRepository")
	private LoyaltyProgramRepository loyaltyProgramRepository;

	@Autowired
	@Qualifier("customerLoyaltyTierService")
	private CustomerLoyaltyTierService customerLoyaltyTierService;

	@Autowired
	@Qualifier("customerLoyaltyTxnService")
	private CustomerLoyaltyTxnService customerLoyaltyTxnService;

	@Override
	protected JpaRepository<CustomerLoyaltyEntity, Long> getRepository() {
		return customerLoyaltyRepository;
	}

	@Override
	protected ITextRepository<CustomerLoyaltyEntity, Long> getTextRepository() {
		return customerLoyaltyRepository;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CustomerLoyaltyEntity> findByCustProgCode(String customerCode, String programCode) {
		return customerLoyaltyRepository.findByCustProgCode(customerCode, programCode);
	}
	
	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CustomerLoyaltyEntity> findByCustomerCode(String customerCode){
		return customerLoyaltyRepository.findByCustomerCode(customerCode);
	}

	@Override
	protected String getAlternateKeyAsString(CustomerLoyaltyEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"customerLoyalty\" : \"").append(entity.getCsLoyaltyCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(CustomerLoyaltyEntity entity, EntityVdationType type) throws EntityValidationException {
		List<CustomerLoyaltyTierEntity> customerLoyaltyTiers = entity.getCsLoyaltyTiers();
		if (customerLoyaltyTiers != null && !customerLoyaltyTiers.isEmpty()) {
			for (CustomerLoyaltyTierEntity customerLoyaltyTier : customerLoyaltyTiers) {
				customerLoyaltyTier.setCsLoyaltyRef(entity);
				customerLoyaltyTier.setCsLoyaltyTierCode(entity.getCsLoyaltyCode());
				customerLoyaltyTierService.validate(customerLoyaltyTier, false, type);
			}
		}

		Set<CustomerLoyaltyTxnEntity> customerLoyaltyTxns = entity.getCsLoyaltyTxns();
		if (customerLoyaltyTxns != null && !customerLoyaltyTxns.isEmpty()) {
			for (CustomerLoyaltyTxnEntity customerLoyaltyTxn : customerLoyaltyTxns) {
				customerLoyaltyTxn.setCsLoyaltyRef(entity);
				customerLoyaltyTxn.setCsLoyaltyCode(entity.getCsLoyaltyCode());
				customerLoyaltyTxnService.validate(customerLoyaltyTxn, false, type);
			}
		}

		if (entity.getCustomerRef() != null) {
			// it is possible to have destination ce null
			CustomerEntity ce = entity.getCustomerRef(), ce2 = null;

			if (ce.getId() == null) {
				try {
					ce2 = customerRepository.findByCustomerCode(ce.getCustomerCode());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find Customer with code - " + ce.getCustomerCode());
				}
			} else {
				try {
					ce2 = customerRepository.findOne(ce.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find Customer with id - " + ce.getId());
				}
			}

			if (ce2 == null)
				throw new EntityValidationException("Could not find Customer using either code or id !");

			entity.setCustomerRef(ce2);
			entity.setCustomerCode(ce2.getCustomerCode());
		}

		if (entity.getProgramRef() != null) {
			// it is possible to have lp null
			LoyaltyProgramEntity lp = entity.getProgramRef(), lp2 = null;

			if (lp.getId() == null) {
				try {
					lp2 = loyaltyProgramRepository.findByProgramCode(lp.getProgramCode());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find loyaltyProgram with programcode - " + lp.getProgramCode());
				}
			} else {
				try {
					lp2 = loyaltyProgramRepository.findOne(lp.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find loyaltyProgram with id - " + lp.getId());
				}
			}

			if (lp2 == null)
				throw new EntityValidationException("Could not find loyaltyProgram using either code or id !");

			entity.setProgramRef(lp2);
			entity.setProgramCode(lp2.getProgramCode());
		}
		
		List<CustomerLoyaltyEntity> customerLoyaltyEntityList = findByCustomerCode(entity.getCustomerCode());
		if(!customerLoyaltyEntityList.isEmpty()){
			CustomerLoyaltyEntity previousRecord = customerLoyaltyEntityList.get(0);
			Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(previousRecord.getEndDate());
			calendar.add(Calendar.DATE, -1);
			
			if(calendar.getTime().compareTo(entity.getStartDate()) !=0 ){
				throw new EntityValidationException("should have an endDate 1 day less than the current record’s startDate");
			}
		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {

			// this is a dummy update of lifecycle status, we do not use this
			// field for actual
			// update. This is needed only to get past the required attribute
			// checker.
			entity.setStatus(ParticipationStatus.INACTIVE);
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	protected AbstractServiceImpl<CustomerLoyaltyEntity, Long>.Outcome postProcess(int opCode,
			CustomerLoyaltyEntity entity) throws EntityValidationException {
		Outcome result = new Outcome();
		result.setResult(true);

		switch (opCode) {
		case ADD:
		case ADD_BULK:
		case UPDATE:
		case UPDATE_BULK:
		case DELETE:
		case DELETE_BULK:
			postProcess(entity);
		default:
			break;
		}

		return (result);
	}

	@Override
	protected AbstractServiceImpl<CustomerLoyaltyEntity, Long>.Outcome preProcess(int opCode,
			CustomerLoyaltyEntity entity) throws EntityValidationException {
		Outcome result = new Outcome();
		result.setResult(true);

		switch (opCode) {
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

		return (result);
	}

	@Override
	protected CustomerLoyaltyEntity doUpdate(CustomerLoyaltyEntity neu, CustomerLoyaltyEntity old)
			throws EntityValidationException {

		if (!old.getProgramCode().equals(neu.getProgramCode())) {
			throw new EntityValidationException("CustomerLoyalty cannot be updated ! Existing - " + old.getProgramCode()
					+ ", new - " + neu.getProgramCode());
		}

		// validate(neu, EntityVdationType.PRE_UPDATE);

		/*
		 * if (!Objects.equals(neu.getLifecycleStatus(),
		 * old.getLifecycleStatus())) { if (!isPrivilegedContext()) throw new
		 * EntityValidationException(
		 * "Cannot update status. Use requestStatusChange api instead !!"); }
		 */

		List<Long> deletedCustomerLoyaltyTiers = new ArrayList<Long>();
		Set<Long> deletedCustomerLoyaltyTxns = new HashSet<Long>();

		if (old.getCsLoyaltyTiers() != null && !old.getCsLoyaltyTiers().isEmpty()) {
			for (CustomerLoyaltyTierEntity oldCustomerLoyaltyTier : old.getCsLoyaltyTiers()) {
				boolean found = false;
				if (neu.getCsLoyaltyTiers() != null && !neu.getCsLoyaltyTiers().isEmpty()) {
					for (CustomerLoyaltyTierEntity newCustomerLoyaltyTier : neu.getCsLoyaltyTiers()) {
						if (Objects.equals(newCustomerLoyaltyTier.getId(), oldCustomerLoyaltyTier.getId())) {
							found = true;
							break;
						}
					} // end for new CustomerLoyaltyTier
				}
				if (!found) {
					// CustomerLoyaltyTier deleted
					// if (old.getLifecycleStatus() != LifecycleStatus.INACTIVE)
					// {
					if (isPrivilegedContext()) {
						deletedCustomerLoyaltyTiers.add(oldCustomerLoyaltyTier.getId());
					} else {
						throw new EntityValidationException(
								"Cannot delete CustomerLoyaltyTiers  " + oldCustomerLoyaltyTier.getTierCode());
					}

				}
			}
		}

		if (old.getCsLoyaltyTxns() != null && !old.getCsLoyaltyTxns().isEmpty()) {
			for (CustomerLoyaltyTxnEntity oldCustomerLoyaltyTxn : old.getCsLoyaltyTxns()) {
				boolean found = false;
				if (neu.getCsLoyaltyTxns() != null && !neu.getCsLoyaltyTxns().isEmpty()) {
					for (CustomerLoyaltyTxnEntity newCustomerLoyaltyTxns : neu.getCsLoyaltyTxns()) {
						if (Objects.equals(newCustomerLoyaltyTxns.getId(), oldCustomerLoyaltyTxn.getId())) {
							found = true;
							break;
						}
					} // end for new CustomerLoyaltyTier
				}
				if (!found) {
					// CustomerLoyaltyTier deleted
					// if (old.getLifecycleStatus() != LifecycleStatus.INACTIVE)
					// {
					if (isPrivilegedContext()) {
						deletedCustomerLoyaltyTxns.add(oldCustomerLoyaltyTxn.getId());
					} else {
						throw new EntityValidationException(
								"Cannot delete CustomerLoyaltyTxns  " + oldCustomerLoyaltyTxn.getTxnCode());
					}

				}
			}
		}

		boolean updated = false;
		if (!deletedCustomerLoyaltyTiers.isEmpty() || !deletedCustomerLoyaltyTxns.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			if (neu.getCsLoyaltyTiers() != null && !neu.getCsLoyaltyTiers().isEmpty()) {
				updateCustomerLoyaltyTiers(neu, old); // end for
				// neu.getCsLoyaltyTiers
			} // end if neu.getCsLoyaltyTiers != null

			if (neu.getCsLoyaltyTxns() != null && !neu.getCsLoyaltyTxns().isEmpty()) {
				updateCustomerLoyaltyTxns(neu, old); // end for
				// neu.getCsLoyaltyTiers
			} // end if neu.getCsLoyaltyTiers != null

			if (!deletedCustomerLoyaltyTiers.isEmpty()) {
				// dissociate customerloyaltytiers
				deleteCustomerLoyaltyTiers(old, deletedCustomerLoyaltyTiers);
			} // end if getCsLoyaltyTiers not empty

			if (!deletedCustomerLoyaltyTxns.isEmpty()) {
				// dissociate customerloyaltytxns
				deleteCustomerLoyaltyTxns(old, deletedCustomerLoyaltyTxns);
			} // end if getCsLoyaltyTxns not empty

			old.setActiveCredit(neu.getActiveCredit());
			old.setCsLoyaltyCode(neu.getCsLoyaltyCode());
			old.setCustomerCode(neu.getCustomerCode());
			old.setCustomerRef(neu.getCustomerRef());
			old.setProgramRef(neu.getProgramRef());
			old.setEndDate(neu.getEndDate());
			old.setExpiredCredit(neu.getExpiredCredit());
			old.setRedeemedCredit(neu.getRedeemedCredit());
			old.setStartDate(neu.getStartDate());
			old.setStatus(neu.getStatus());
			old.setTotalCredit(neu.getTotalCredit());
			JpaUtil.updateRevisionControl(old, true);
		}
		return (old);
	}

	private void updateCustomerLoyaltyTiers(CustomerLoyaltyEntity neu, CustomerLoyaltyEntity old)
			throws EntityValidationException {
		for (CustomerLoyaltyTierEntity newCustomerLoyaltyTier : neu.getCsLoyaltyTiers()) {
			CustomerLoyaltyTierEntity oldCustomerLoyaltyTier2 = null;
			boolean found = false;
			if (old.getCsLoyaltyTiers() != null && !old.getCsLoyaltyTiers().isEmpty()) {
				for (CustomerLoyaltyTierEntity oldCustomerLoyaltyTier : old.getCsLoyaltyTiers()) {
					if (Objects.equals(newCustomerLoyaltyTier.getId(), oldCustomerLoyaltyTier.getId())) {
						oldCustomerLoyaltyTier2 = oldCustomerLoyaltyTier;
						found = true;
						break;
					}
				} // end for old.getCsLoyaltyTiers
			} // end if old.getCsLoyaltyTiers

			if (!found) {
				// new LoyaltyTier being added
				newCustomerLoyaltyTier.setCsLoyaltyRef(old);
				newCustomerLoyaltyTier.setCsLoyaltyCode(old.getCsLoyaltyCode());
				newCustomerLoyaltyTier.setRevisionControl(getRevisionControl());
				newCustomerLoyaltyTier.setTenantId(getTenantId());

				customerLoyaltyTierService.validate(newCustomerLoyaltyTier, false, EntityVdationType.PRE_INSERT);
				old.getCsLoyaltyTiers().add(newCustomerLoyaltyTier);
			} else {
				boolean customerLoyaltyTierUpdated = customerLoyaltyTierService.checkForUpdate(newCustomerLoyaltyTier,
						oldCustomerLoyaltyTier2);
				if (customerLoyaltyTierUpdated) {
					customerLoyaltyTierService.update(newCustomerLoyaltyTier);
				}
			}

		}
	}

	private void updateCustomerLoyaltyTxns(CustomerLoyaltyEntity neu, CustomerLoyaltyEntity old)
			throws EntityValidationException {
		for (CustomerLoyaltyTxnEntity newCustomerLoyltyTxn : neu.getCsLoyaltyTxns()) {
			CustomerLoyaltyTxnEntity oldCustomerLoyaltyTxn2 = null;
			boolean found = false;
			if (old.getCsLoyaltyTiers() != null && !old.getCsLoyaltyTxns().isEmpty()) {
				for (CustomerLoyaltyTxnEntity oldCustomerLoyaltyTxn : old.getCsLoyaltyTxns()) {
					if (Objects.equals(newCustomerLoyltyTxn.getId(), oldCustomerLoyaltyTxn.getId())) {
						oldCustomerLoyaltyTxn2 = oldCustomerLoyaltyTxn;
						found = true;
						break;
					}
				} // end for old.getCsLoyaltyTxn
			} // end if old.getCsLoyaltyTxn

			if (!found) {
				// new LoyaltyTier being added
				newCustomerLoyltyTxn.setCsLoyaltyRef(old);
				newCustomerLoyltyTxn.setCsLoyaltyCode(old.getCsLoyaltyCode());
				newCustomerLoyltyTxn.setRevisionControl(getRevisionControl());
				newCustomerLoyltyTxn.setTenantId(getTenantId());

				customerLoyaltyTxnService.validate(newCustomerLoyltyTxn, false, EntityVdationType.PRE_INSERT);
				old.getCsLoyaltyTxns().add(newCustomerLoyltyTxn);
			} else {
				boolean customerLoyaltyTxnUpdated = customerLoyaltyTxnService.checkForUpdate(newCustomerLoyltyTxn,
						oldCustomerLoyaltyTxn2);
				if (customerLoyaltyTxnUpdated) {
					customerLoyaltyTxnService.update(newCustomerLoyltyTxn);
				}
			}
		}
	}

	private void deleteCustomerLoyaltyTiers(CustomerLoyaltyEntity old, List<Long> deletedCustomerLoyaltyTiers) {
		Iterator<CustomerLoyaltyTierEntity> customerLoyaltyTierEntityIter = old.getCsLoyaltyTiers().iterator();
		for (Long id : deletedCustomerLoyaltyTiers) {
			boolean found = false;
			CustomerLoyaltyTierEntity oldProgramTier = null;
			while (customerLoyaltyTierEntityIter.hasNext()) {
				oldProgramTier = customerLoyaltyTierEntityIter.next();
				if (Objects.equals(id, oldProgramTier.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				old.getCsLoyaltyTiers().remove(oldProgramTier);
				customerLoyaltyTierService.delete(id);
			}
		}
	}

	private void deleteCustomerLoyaltyTxns(CustomerLoyaltyEntity old, Set<Long> deletedCustomerLoyaltyTxns) {
		Iterator<CustomerLoyaltyTxnEntity> customerLoyaltyTxnEntityIter = old.getCsLoyaltyTxns().iterator();
		for (Long id : deletedCustomerLoyaltyTxns) {
			boolean found = false;
			CustomerLoyaltyTxnEntity oldProgramTier = null;
			while (customerLoyaltyTxnEntityIter.hasNext()) {
				oldProgramTier = customerLoyaltyTxnEntityIter.next();
				if (Objects.equals(id, oldProgramTier.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				old.getCsLoyaltyTxns().remove(oldProgramTier);
				customerLoyaltyTxnService.delete(id);
			}
		}
	}

	private boolean checkForUpdate(CustomerLoyaltyEntity neu, CustomerLoyaltyEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getActiveCredit(), old.getActiveCredit())
				|| !Objects.equals(neu.getCsLoyaltyCode(), old.getCsLoyaltyCode())
				|| !Objects.equals(neu.getCustomerCode(), old.getCustomerCode())
				|| !Objects.equals(neu.getProgramCode(), old.getProgramCode())
				|| !Objects.equals(neu.getEndDate(), old.getEndDate())
				|| !Objects.equals(neu.getExpiredCredit(), old.getExpiredCredit())
				|| !Objects.equals(neu.getRedeemedCredit(), old.getRedeemedCredit())
				|| !Objects.equals(neu.getStartDate(), old.getStartDate())
				|| !Objects.equals(neu.getStatus(), old.getStatus())
				|| !Objects.equals(neu.getTotalCredit(), old.getTotalCredit())) {

			result = true;
			return (result);
		}
		
		if (neu.getCustomerRef() == null) {
            if (old.getCustomerRef() != null) {
                result = true;
                return(result);
            }
        } else {
            if (old.getCustomerRef() == null) {
                result = true;
                return(result);
            } else {
                // both not null
                if (neu.getCustomerRef().getId() == null) {
                    if (!Objects.equals(neu.getCustomerRef().getCustomerCode(), old.getCustomerRef().getCustomerCode())) {
                        result = true;
                        return(result);
                    }
                } else {
                    if (!Objects.equals(neu.getCustomerRef().getId(), old.getCustomerRef().getId())) {
                        result = true;
                        return(result);
                    }
                }
            }
        }
		
		if (neu.getProgramRef() == null) {
            if (old.getProgramRef() != null) {
                result = true;
                return(result);
            }
        } else {
            if (old.getProgramRef() == null) {
                result = true;
                return(result);
            } else {
                // both not null
                if (neu.getProgramRef().getId() == null) {
                    if (!Objects.equals(neu.getProgramRef().getProgramCode(), old.getProgramRef().getProgramCode())) {
                        result = true;
                        return(result);
                    }
                } else {
                    if (!Objects.equals(neu.getProgramRef().getId(), old.getProgramRef().getId())) {
                        result = true;
                        return(result);
                    }
                }
            }
        }

		if (neu.getCsLoyaltyTiers() == null || neu.getCsLoyaltyTiers().isEmpty()) {
			if (old.getCsLoyaltyTiers() != null && !old.getCsLoyaltyTiers().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getCsLoyaltyTiers() == null || old.getCsLoyaltyTiers().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getCsLoyaltyTiers().size(), old.getCsLoyaltyTiers().size())) {
					result = true;
					return (result);
				} else {
					result = checkForCustomerLoyaltyTiers(neu.getCsLoyaltyTiers(), old.getCsLoyaltyTiers());
					return (result);
				}
			}
		}

		if (neu.getCsLoyaltyTxns() == null || neu.getCsLoyaltyTxns().isEmpty()) {
			if (old.getCsLoyaltyTxns() != null && !old.getCsLoyaltyTxns().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getCsLoyaltyTxns() == null || old.getCsLoyaltyTxns().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getCsLoyaltyTxns().size(), old.getCsLoyaltyTxns().size())) {
					result = true;
					return (result);
				} else {
					result = checkForCustomerLoyaltyTxns(neu.getCsLoyaltyTxns(), old.getCsLoyaltyTxns());
					return (result);
				}
			}
		}

		return result;
	}

	private boolean checkForCustomerLoyaltyTiers(List<CustomerLoyaltyTierEntity> newCustomerLoyaltyTiers,
			List<CustomerLoyaltyTierEntity> oldCustomerLoyaltyTiers) {

		boolean result = false;
		Iterator<CustomerLoyaltyTierEntity> oldCustomerLoyaltyTierEntityIter = oldCustomerLoyaltyTiers.iterator();
		Iterator<CustomerLoyaltyTierEntity> newCustomerLoyaltyTierEntityIter = newCustomerLoyaltyTiers.iterator();
		CustomerLoyaltyTierEntity oldCustomerLoyatyTier = null;
		CustomerLoyaltyTierEntity newCustomerLoyaltyTier = null;

		while (newCustomerLoyaltyTierEntityIter.hasNext()) {
			newCustomerLoyaltyTier = newCustomerLoyaltyTierEntityIter.next();
			Long newEntityId = newCustomerLoyaltyTier.getId();
			if (newEntityId != null) {
				while (oldCustomerLoyaltyTierEntityIter.hasNext()) {
					oldCustomerLoyatyTier = oldCustomerLoyaltyTierEntityIter.next();
					Long oldEntityId = oldCustomerLoyatyTier.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = customerLoyaltyTierService.checkForUpdate(newCustomerLoyaltyTier,
								oldCustomerLoyatyTier);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = customerLoyaltyTierService.checkForUpdate(newCustomerLoyaltyTier,
						oldCustomerLoyatyTier);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

	private boolean checkForCustomerLoyaltyTxns(Set<CustomerLoyaltyTxnEntity> newCustomerLoyaltyTxns,
			Set<CustomerLoyaltyTxnEntity> oldCustomerLoyaltyTxns) {

		boolean result = false;
		Iterator<CustomerLoyaltyTxnEntity> oldCustomerLoyaltyTxnEntityIter = oldCustomerLoyaltyTxns.iterator();
		Iterator<CustomerLoyaltyTxnEntity> newCustomerLoyaltyTxnEntityIter = newCustomerLoyaltyTxns.iterator();
		CustomerLoyaltyTxnEntity oldCustomerLoyatyTxn = null;
		CustomerLoyaltyTxnEntity newCustomerLoyaltyTxn = null;

		while (newCustomerLoyaltyTxnEntityIter.hasNext()) {
			newCustomerLoyaltyTxn = newCustomerLoyaltyTxnEntityIter.next();
			Long newEntityId = newCustomerLoyaltyTxn.getId();
			if (newEntityId != null) {
				while (oldCustomerLoyaltyTxnEntityIter.hasNext()) {
					oldCustomerLoyatyTxn = oldCustomerLoyaltyTxnEntityIter.next();
					Long oldEntityId = oldCustomerLoyatyTxn.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = customerLoyaltyTxnService.checkForUpdate(newCustomerLoyaltyTxn,
								oldCustomerLoyatyTxn);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = customerLoyaltyTxnService.checkForUpdate(newCustomerLoyaltyTxn, oldCustomerLoyatyTxn);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

	private void preProcessAdd(CustomerLoyaltyEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		entity.setStatus(ParticipationStatus.ACTIVE);
		entity.setEndDate(null);
		this.updateTenantWithRevision(entity);
	}
	
	private void postProcess(CustomerLoyaltyEntity entity) throws EntityValidationException {
		Calendar calendar = java.util.Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		entity.setEndDate(calendar.getTime());
		entity.setStatus(ParticipationStatus.INACTIVE);
		
		List<CustomerLoyaltyTierEntity> listOfLoyaltyTiers = entity.getCsLoyaltyTiers();
		if(!listOfLoyaltyTiers.isEmpty()){
			for(CustomerLoyaltyTierEntity customerLoyaltyTierEntity:listOfLoyaltyTiers){
				customerLoyaltyTierEntity.setEndDate(calendar.getTime());
			}
		}		
	}

	private void preDelete(CustomerLoyaltyEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext())
			throw new EntityValidationException("Cannot delete customerLoyal when state is " + entity.getStatus());
	}

}
