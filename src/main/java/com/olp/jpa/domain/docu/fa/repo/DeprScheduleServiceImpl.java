package com.olp.jpa.domain.docu.fa.repo;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fa.model.DeprScheduleEntity;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;
import com.olp.jpa.util.JpaUtil;

@Service("deprScheduleService")
public class DeprScheduleServiceImpl extends AbstractServiceImpl<DeprScheduleEntity, Long>
		implements DeprScheduleService {

	@Autowired
	@Qualifier("deprScheduleRepository")
	private DeprScheduleRepository deprScheduleRepo;

	@Override
	protected JpaRepository<DeprScheduleEntity, Long> getRepository() {
		return deprScheduleRepo;
	}

	@Override
	protected ITextRepository<DeprScheduleEntity, Long> getTextRepository() {
		return deprScheduleRepo;
	}

	@Override
	protected String getAlternateKeyAsString(DeprScheduleEntity deprScheduleEntity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"deprSchedule\" : \"").append(deprScheduleEntity.getDeprScheduleCode()).append("\" }");
		return (buff.toString());
	}

	@Override
	public DeprScheduleEntity findByScheduleCode(String deprScheduleCode) {
		DeprScheduleEntity deprScheduleEntity = deprScheduleRepo.findByScheduleCode(deprScheduleCode);
		return deprScheduleEntity;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(DeprScheduleEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	public boolean checkForUpdate(DeprScheduleEntity neu, DeprScheduleEntity old) throws EntityValidationException {
		boolean result = false;

		if (!Objects.equals(neu.getDeprScheduleName(), old.getDeprScheduleName())
				|| !Objects.equals(neu.getDeprPct(), old.getDeprPct())
				|| !Objects.equals(neu.getDeprType(), old.getDeprType())
				|| !Objects.equals(neu.getDeprTypeImpl(), old.getDeprTypeImpl())) {
			result = true;
		}
		return result;
	}
	
	protected DeprScheduleEntity doUpdate(DeprScheduleEntity neu, DeprScheduleEntity old)
			throws EntityValidationException {
		if (!Objects.equals(neu.getDeprScheduleCode(), old.getDeprScheduleCode()))
			throw new EntityValidationException("The depr schedule code does not match, existing - "
					+ old.getDeprScheduleCode() + " , new - " + neu.getDeprScheduleCode());
		
		if (!Objects.equals(neu.getLifecycleStage(), old.getLifecycleStage())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}
		
		if (old.getLifecycleStage() != LifeCycleStage.NEW && !isPrivilegedContext()) {
			if (!Objects.equals(neu.getDeprPct(), old.getDeprPct())
					|| !Objects.equals(neu.getDeprTypeImpl(), old.getDeprTypeImpl())) {
				throw new EntityValidationException(
						"Cannot update deprPct or DeprTypeImpl for LifeCycleStage not NEW !!");
			}
		}

		if (checkForUpdate(neu, old)) {
			old.setDeprScheduleCode(neu.getDeprScheduleCode());
			old.setDeprScheduleName(neu.getDeprScheduleName());
			old.setDeprPct(neu.getDeprPct());
			old.setDeprType(neu.getDeprType());
			old.setDeprTypeImpl(neu.getDeprTypeImpl());
			old.setLifecycleStage(neu.getLifecycleStage());

			JpaUtil.updateRevisionControl(old, true);
		}
		return (old);

	}

	@Override
	protected AbstractServiceImpl<DeprScheduleEntity, Long>.Outcome postProcess(int opCode, DeprScheduleEntity entity)
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

		return (result);
	}

	@Override
	protected AbstractServiceImpl<DeprScheduleEntity, Long>.Outcome preProcess(int opCode, DeprScheduleEntity entity)
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
			validate(entity, true, EntityVdationType.PRE_UPDATE);
		case DELETE:
		case DELETE_BULK:
			preDelete(entity);
		default:
			break;
		}

		return (result);
	}

	private void preProcessAdd(DeprScheduleEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(DeprScheduleEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext() && entity.getLifecycleStage() != LifeCycleStage.NEW)
			throw new EntityValidationException(
					"Cannot delete deprschedule when state is " + entity.getLifecycleStage());
	}

}
