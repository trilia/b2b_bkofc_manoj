package com.olp.jpa.domain.docu.fin.repo;

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
import com.olp.jpa.domain.docu.comm.model.OrgCalendarEntity;
import com.olp.jpa.domain.docu.comm.repo.OrgCalendarRepository;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;
import com.olp.jpa.domain.docu.fin.model.FinCalendarEntity;
import com.olp.jpa.util.JpaUtil;

@Service("finCalendarService")
public class FinCalendarServiceImpl extends AbstractServiceImpl<FinCalendarEntity, Long> implements FinCalendarService {

	@Autowired
	@Qualifier("finCalendarRepository")
	private FinCalendarRepository finCalendarRepository;

	@Autowired
	@Qualifier("orgCalendarRepository")
	private OrgCalendarRepository orgCalendarRepository;

	@Override
	protected JpaRepository<FinCalendarEntity, Long> getRepository() {
		return finCalendarRepository;
	}

	@Override
	protected ITextRepository<FinCalendarEntity, Long> getTextRepository() {
		return finCalendarRepository;
	}

	@Override
	protected String getAlternateKeyAsString(FinCalendarEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"FinCalendar\" : \"").append(entity.getPeriodName()).append("\" }");
		return (buff.toString());
	}

	@Override
	public FinCalendarEntity findbyCalendarCode(String calCode, String periodMan) {
		FinCalendarEntity entity = finCalendarRepository.findbyCalendarCode(calCode, periodMan);
		return entity;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(FinCalendarEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getOrgCalendarRef() != null) {
				// it is possible to have orgCal null
				OrgCalendarEntity orgCal = entity.getOrgCalendarRef(), orgCal2 = null;

				if (orgCal.getId() == null) {
					try {
						orgCal2 = orgCalendarRepository.findbyCalendarCode(orgCal.getCalendarCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find OrgCalendar with code - " + orgCal.getCalendarCode());
					}
				} else {
					try {
						orgCal2 = orgCalendarRepository.findOne(orgCal.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find OrgCalendar with id - " + orgCal.getId());
					}
				}

				if (orgCal2 == null)
					throw new EntityValidationException("Could not find OrgCalendar using either code or id !");

				entity.setOrgCalendarRef(orgCal2);
				entity.setOrgCalendarCode(orgCal.getCalendarCode());
			}

		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	protected FinCalendarEntity doUpdate(FinCalendarEntity neu, FinCalendarEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getPeriodName(), old.getPeriodName()))
			throw new EntityValidationException("The fin calendar does not match, existing - " + old.getPeriodName()
					+ " , new - " + neu.getPeriodName());

		if (checkForUpdate(neu, old)) {
			old.setOrgCalendarCode(neu.getOrgCalendarCode());
			old.setOrgCalendarRef(neu.getOrgCalendarRef());
			old.setPeriodMonth(neu.getPeriodMonth());
			old.setPeriodStatus(neu.getPeriodStatus());
			old.setPeriodYear(neu.getPeriodYear());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(FinCalendarEntity neu, FinCalendarEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getOrgCalendarCode(), old.getOrgCalendarCode())
				|| !Objects.equals(neu.getPeriodMonth(), old.getPeriodMonth())
				|| !Objects.equals(neu.getPeriodStatus(), old.getPeriodStatus())
				|| !Objects.equals(neu.getPeriodYear(), old.getPeriodYear())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getOrgCalendarRef() == null) {
			if (old.getOrgCalendarRef() != null)
				result = true;
		} else {
			if (old.getOrgCalendarRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getOrgCalendarRef().getId(), old.getOrgCalendarRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<FinCalendarEntity, Long>.Outcome postProcess(int opCode, FinCalendarEntity entity)
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
	protected AbstractServiceImpl<FinCalendarEntity, Long>.Outcome preProcess(int opCode, FinCalendarEntity entity)
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

	private void preProcessAdd(FinCalendarEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(FinCalendarEntity entity) throws EntityValidationException {
		if (entity.getOrgCalendarRef() != null) {
			OrgCalendarEntity orgCal = entity.getOrgCalendarRef();
			if (!isPrivilegedContext() && orgCal.getLifecycleStage() != LifeCycleStage.INACTIVE)
				throw new EntityValidationException(
						"Cannot delete uom when AccountSubCategory status is " + orgCal.getLifecycleStage());
		}
	}

}
