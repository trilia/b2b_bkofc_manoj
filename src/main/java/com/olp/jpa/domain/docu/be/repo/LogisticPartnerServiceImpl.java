package com.olp.jpa.domain.docu.be.repo;

import java.util.ArrayList;
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
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.be.model.LgstPtnrWfStatusEntity;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerLocEntity;

@Service("logisticPartnerService")
public class LogisticPartnerServiceImpl extends AbstractServiceImpl<LogisticPartnerEntity, Long>
		implements LogisticPartnerService {

	@Autowired
	@Qualifier("logisticPartnerRepository")
	private LogisticPartnerRepository logisticPartnerRepository;
	
	@Autowired
	@Qualifier("logisticPartnerLocService")
	private LogisticPartnerLocService logisticPartnerLocService;
	
	@Override
	protected JpaRepository<LogisticPartnerEntity, Long> getRepository() {
		return logisticPartnerRepository;
	}

	@Override
	protected ITextRepository<LogisticPartnerEntity, Long> getTextRepository() {
		return logisticPartnerRepository;
	}

	@Override
	public LogisticPartnerEntity findByPartnerCode(String partnerCode) {
		return logisticPartnerRepository.findByPartnerCode(partnerCode);
	}
	
	@Override
	protected String getAlternateKeyAsString(LogisticPartnerEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"LogisticPartner\" : \"").append(entity.getPartnerCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LogisticPartnerEntity entity, EntityVdationType type) throws EntityValidationException {
		List<LogisticPartnerLocEntity> logPartnerLocList = entity.getPartnerLocations();
		if (logPartnerLocList != null && !logPartnerLocList.isEmpty()) {
			for (LogisticPartnerLocEntity logPartnerLoc : logPartnerLocList) {
				logPartnerLoc.setPartnerRef(entity);
				logPartnerLoc.setPartnerCode(entity.getPartnerCode());
				logisticPartnerLocService.validate(logPartnerLoc, false, type);
			}
		}

		Set<LgstPtnrWfStatusEntity> logPartnerWfStatusList = entity.getWfStatus();
		if (logPartnerWfStatusList != null && !logPartnerWfStatusList.isEmpty()) {
			for (LgstPtnrWfStatusEntity logPartnerWfStatus : logPartnerWfStatusList) {
				logPartnerWfStatus.setPartnerRef(entity);
				logPartnerWfStatus.setPartnerCode(entity.getPartnerCode());
				//TODO - No service class for logPartnerWfSvc to check for validation
			}
		}
		if (EntityVdationType.PRE_UPDATE == type) {
			if(!isPrivilegedContext() && entity.getLifeCycleStatus() != LifeCycleStatus.ACTIVE  ) {//TODO - Change status
				throw new EntityValidationException("Update is not allowed. when state is " + entity.getLifeCycleStatus());
			}
		}
	}

	@Override
	protected AbstractServiceImpl<LogisticPartnerEntity, Long>.Outcome postProcess(int opCode, LogisticPartnerEntity entity)
			throws EntityValidationException {
		Outcome result = new Outcome();
		result.setResult(true);

		switch (opCode) {
		case ADD:
		case ADD_BULK:
		case UPDATE:
		case UPDATE_BULK:
		case DELETE:
		case DELETE_BULK:
		default:
			break;
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<LogisticPartnerEntity, Long>.Outcome preProcess(int opCode, LogisticPartnerEntity entity)
			throws EntityValidationException {
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
	protected LogisticPartnerEntity doUpdate(LogisticPartnerEntity neu, LogisticPartnerEntity old)
			throws EntityValidationException {

		if (!old.getPartnerCode().equals(neu.getPartnerCode())) {
			throw new EntityValidationException(
					"LogisticPartner cannot be updated ! Existing - " + old.getPartnerCode() + ", new - " + neu.getPartnerCode());
		}

		// validate(neu, EntityVdationType.PRE_UPDATE);

		List<Long> deletedPartnerLocList = new ArrayList<Long>();
		List<Long> deletedWorkflowStatusList = new ArrayList<Long>();

		if (old.getPartnerLocations() != null && !old.getPartnerLocations().isEmpty()) {
			for (LogisticPartnerLocEntity oldPartnerLocValues : old.getPartnerLocations()) {
				boolean found = false;
				if (neu.getPartnerLocations() != null && !neu.getPartnerLocations().isEmpty()) {
					for (LogisticPartnerLocEntity newPartnerLocValues : neu.getPartnerLocations()) {
						if (Objects.equals(newPartnerLocValues.getId(), oldPartnerLocValues.getId())) {
							found = true;
							break;
						}
					} // end for new LogisticPartnerLocEntity
				}
				if (!found) {
					// lovValues deleted
					deletedPartnerLocList.add(oldPartnerLocValues.getId());

				}
			}
		}

		if (old.getWfStatus() != null && !old.getWfStatus().isEmpty()) {
			for (LgstPtnrWfStatusEntity oldWfStatusValues : old.getWfStatus()) {
				boolean found = false;
				if (neu.getPartnerLocations() != null && !neu.getWfStatus().isEmpty()) {
					for (LgstPtnrWfStatusEntity newWfStatusValues : neu.getWfStatus()) {
						if (Objects.equals(newWfStatusValues.getId(), oldWfStatusValues.getId())) {
							found = true;
							break;
						}
					} // end for new WorkflowStatusList
				}
				if (!found) {
					// lovValues deleted
					deletedWorkflowStatusList.add(oldWfStatusValues.getId());

				}
			}
		}
		
		boolean updated = false;
		if (!deletedPartnerLocList.isEmpty()  ||
				!deletedWorkflowStatusList.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			if (neu.getPartnerLocations() != null && !neu.getPartnerLocations().isEmpty()) {
				updatePartnerLoc(neu, old); // end for
											// neu.getLovValues
			} // end if neu.getPartnerLocations != null

			if (!deletedPartnerLocList.isEmpty()) {
				// dissociate partnetLoc
				deletePartnerLoc(old, deletedPartnerLocList);
			} // end if deletePartnerLocations not empty

			/*if (neu.getPartnerLocations() != null && !neu.getPartnerLocations().isEmpty()) {
				//updateWfStatus(neu, old); // end for
											// neu.getLovValues
			} // end if neu.getPartnerLocations != null

			if (!deletedPartnerLocList.isEmpty()) {
				// dissociate partnetLoc
				//deleteWorkflowStatus(old, deletedWorkflowStatusList);
			} // end if deletePartnerLocations not empty
*/
			
			old.setLegalInfo(neu.getLegalInfo());
			old.setPartnerLocations(neu.getPartnerLocations());
			old.setPartnerName(neu.getPartnerName());
			old.setPrimaryContactRef(neu.getPrimaryContactRef());
			old.setWfStatus(neu.getWfStatus());
			old.setPartnerLocations(neu.getPartnerLocations());
			old.setPartnerCode(neu.getPartnerCode());
			// }
		}
		return (old);
	}

	private boolean checkForUpdate(LogisticPartnerEntity neu, LogisticPartnerEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getPartnerCode(), old.getPartnerCode()) ||
				!Objects.equals(neu.getPartnerName(), old.getPartnerName())
				|| !Objects.equals(neu.getPrimaryContactRef(), old.getPrimaryContactRef())) {

			result = true;
		}

		if (neu.getPartnerLocations() == null || neu.getPartnerLocations().isEmpty()) {
			if (old.getPartnerLocations() != null && !old.getPartnerLocations().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getPartnerLocations() == null || old.getPartnerLocations().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getPartnerLocations().size(), old.getPartnerLocations().size())) {
					result = true;
					return (result);
				} else {
					result = checkForPartnerLocValues(neu.getPartnerLocations(), old.getPartnerLocations());
					return (result);
				}
			}
		}
		return (result);
	}

	private void updatePartnerLoc(LogisticPartnerEntity neu, LogisticPartnerEntity old) throws EntityValidationException {
		for (LogisticPartnerLocEntity newLogisticPartnerLoc : neu.getPartnerLocations()) {
			LogisticPartnerLocEntity oldLogisticPartnerLoc2 = null;
			boolean found = false;
			if (old.getPartnerLocations() != null && !old.getPartnerLocations().isEmpty()) {
				for (LogisticPartnerLocEntity oldLogisticPartnerLoc : old.getPartnerLocations()) {
					if (Objects.equals(newLogisticPartnerLoc.getId(), oldLogisticPartnerLoc.getId())) {
						oldLogisticPartnerLoc2 = oldLogisticPartnerLoc;
						found = true;
						break;
					}
				} // end for old.getPartnerLocations
			} // end if old.getPartnerLocations

			if (!found) {
				// new LogisticPartner being added
				newLogisticPartnerLoc.setPartnerRef(old);
				newLogisticPartnerLoc.setPartnerCode(old.getPartnerCode());
				newLogisticPartnerLoc.setRevisionControl(getRevisionControl());
				
				logisticPartnerLocService.validate(newLogisticPartnerLoc, false, EntityVdationType.PRE_INSERT);
				old.getPartnerLocations().add(newLogisticPartnerLoc);
			} else {
				boolean logisticPartnerLocUpdated = logisticPartnerLocService.checkForUpdate(newLogisticPartnerLoc, oldLogisticPartnerLoc2);
				if (logisticPartnerLocUpdated) {
					logisticPartnerLocService.update(newLogisticPartnerLoc);
				}
			}

		}
	}

	private void deletePartnerLoc(LogisticPartnerEntity old, List<Long> deletedPartnerLoc) {
		Iterator<LogisticPartnerLocEntity> partnerLocEntityIter = old.getPartnerLocations().iterator();
		for (Long id : deletedPartnerLoc) {
			boolean found = false;
			LogisticPartnerLocEntity oldPartnerLoc = null;
			while (partnerLocEntityIter.hasNext()) {
				oldPartnerLoc = partnerLocEntityIter.next();
				if (Objects.equals(id, oldPartnerLoc.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				old.getPartnerLocations().remove(oldPartnerLoc);
				logisticPartnerLocService.delete(id);
			}
		}
	}

	private boolean checkForPartnerLocValues(List<LogisticPartnerLocEntity> newPartnerLocValues, List<LogisticPartnerLocEntity> oldPartnerLocValues) {

		boolean result = false;
		Iterator<LogisticPartnerLocEntity> oldPartnerLocValuesEntityIter = oldPartnerLocValues.iterator();
		Iterator<LogisticPartnerLocEntity> newPartnerLocValuesEntityIter = newPartnerLocValues.iterator();
		LogisticPartnerLocEntity oldPartnerLocValue = null;
		LogisticPartnerLocEntity newPartnerLocValue = null;

		while (newPartnerLocValuesEntityIter.hasNext()) {
			newPartnerLocValue = newPartnerLocValuesEntityIter.next();
			Long newEntityId = newPartnerLocValue.getId();
			if (newEntityId != null) {
				while (oldPartnerLocValuesEntityIter.hasNext()) {
					oldPartnerLocValue = oldPartnerLocValuesEntityIter.next();
					Long oldEntityId = oldPartnerLocValue.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = logisticPartnerLocService.checkForUpdate(newPartnerLocValue, oldPartnerLocValue);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = logisticPartnerLocService.checkForUpdate(newPartnerLocValue, oldPartnerLocValue);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}
	
	private void preProcessAdd(LogisticPartnerEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
	}

	private void preDelete(LogisticPartnerEntity entity) throws EntityValidationException {
		if(!isPrivilegedContext() && entity.getLifeCycleStatus() != LifeCycleStatus.ACTIVE  ) {//TO do change NEW as New is not there in enum
			throw new EntityValidationException("Cannot delete LogisticPartner. when state is " + entity.getLifeCycleStatus() + ".Only TERMINATION possible");
		}
	}

}
