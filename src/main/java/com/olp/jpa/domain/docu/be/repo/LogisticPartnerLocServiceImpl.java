package com.olp.jpa.domain.docu.be.repo;

import java.util.Objects;

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
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerLocEntity;
import com.olp.jpa.util.JpaUtil;

@Service("logisticPartnerLocService")
public class LogisticPartnerLocServiceImpl extends AbstractServiceImpl<LogisticPartnerLocEntity, Long>
		implements LogisticPartnerLocService {

	@Autowired
	@Qualifier("logisticPartnerLocRepository")
	private LogisticPartnerLocRepository logisticPartnerLocRepository;
	
	@Autowired
	@Qualifier("logisticPartnerRepository")
	private LogisticPartnerRepository logisticPartnerRepository;
	
	@Override
	protected JpaRepository<LogisticPartnerLocEntity, Long> getRepository() {
		return logisticPartnerLocRepository;
	}

	@Override
	protected ITextRepository<LogisticPartnerLocEntity, Long> getTextRepository() {
		return logisticPartnerLocRepository;
	}
	
	@Override
	public LogisticPartnerLocEntity findByLocationCode(String partnerCode, String locCode) {
		return logisticPartnerLocRepository.findByLocationCode(partnerCode, locCode);
	}

	@Override
	protected String getAlternateKeyAsString(LogisticPartnerLocEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"LogisticPartnerLoc\" : \"").append(entity.getLocationCode()).append("\" }");

		return (buff.toString()); 
	}
	
	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LogisticPartnerLocEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getPartnerRef() != null) {
				// it is possible to have LogisticPartner logPartner null
				LogisticPartnerEntity logPartner = entity.getPartnerRef(), logPartner2 = null;

				if (logPartner.getId() == null) {
					try {
						logPartner2 = logisticPartnerRepository.findByPartnerCode(logPartner.getPartnerCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find LovDefinition with code - " + logPartner.getPartnerCode());
					}
				} else {
					try {
						logPartner2 = logisticPartnerRepository.findOne(logPartner.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find LogisticPartner with id - " + logPartner.getId());
					}
				}
				if (logPartner2 == null)
					throw new EntityValidationException("Could not find LogisticPartner using either code or id !");

				entity.setPartnerRef(logPartner2);
				entity.setPartnerCode(logPartner.getPartnerCode());
			}
			
			if (EntityVdationType.PRE_UPDATE == type) {
				if(!isPrivilegedContext() && entity.getLifeCycleStatus() != LifeCycleStatus.ACTIVE  ) {//TODO - Change status
					throw new EntityValidationException("Update is not allowed. when state is " + entity.getLifeCycleStatus());
				}
			}
		}

		if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}		
	}

	@Override
	protected LogisticPartnerLocEntity doUpdate(LogisticPartnerLocEntity neu, LogisticPartnerLocEntity old) throws EntityValidationException {

		if (!Objects.equals(neu.getLocationCode(), old.getLocationCode()))
			throw new EntityValidationException(
					"The logictsicPartnerLocation does not match, existing - " + old.getLocationCode() + " , new - " + neu.getLocationCode());

		if (checkForUpdate(neu, old)) {
			old.setLocation(neu.getLocation());
			old.setLocationType(neu.getLocationType());
			old.setPartnerCode(neu.getPartnerCode());
			old.setPartnerRef(neu.getPartnerRef());
			JpaUtil.updateRevisionControl(old, true);
		}
		
		return (old);
	}
	
	@Override
	public boolean checkForUpdate(LogisticPartnerLocEntity neu, LogisticPartnerLocEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getLocationCode(), old.getLocationCode()) ||
				!Objects.equals(neu.getLocation(), old.getLocation())||
				!Objects.equals(neu.getPartnerCode(), old.getPartnerCode())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getPartnerRef() == null) {
			if (old.getPartnerRef() != null)
				result = true;
		} else {
			if (old.getPartnerRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getPartnerRef().getId(), old.getPartnerRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<LogisticPartnerLocEntity, Long>.Outcome postProcess(int opCode,
			LogisticPartnerLocEntity entity) throws EntityValidationException {
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

		return (result);
	}

	@Override
	protected AbstractServiceImpl<LogisticPartnerLocEntity, Long>.Outcome preProcess(int opCode,
			LogisticPartnerLocEntity entity) throws EntityValidationException {
		Outcome result = new Outcome();
		result.setResult(true);

		switch (opCode) {
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

		return (result);
	}
	
	private void preProcessAdd(LogisticPartnerLocEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
	}

	private void preDelete(LogisticPartnerLocEntity entity) throws EntityValidationException {
		if (entity.getPartnerRef() != null) {
			if(!isPrivilegedContext() && entity.getLifeCycleStatus() != LifeCycleStatus.ACTIVE  ) {//TO do change NEW as New is not there in enum
				throw new EntityValidationException("Cannot delete LogisticPartnerLoc. when state is " + entity.getLifeCycleStatus() + ".Only TERMINATION possible");
			}
		}
	}
}
