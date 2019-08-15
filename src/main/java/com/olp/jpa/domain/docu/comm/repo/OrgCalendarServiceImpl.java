package com.olp.jpa.domain.docu.comm.repo;

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
import com.olp.jpa.domain.docu.comm.model.CalPeriodicity;
import com.olp.jpa.domain.docu.comm.model.CalUsageType;
import com.olp.jpa.domain.docu.comm.model.OrgCalendarEntity;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;
import com.olp.jpa.util.JpaUtil;

@Service("orgCalendarService")
public class OrgCalendarServiceImpl extends AbstractServiceImpl<OrgCalendarEntity, Long> implements OrgCalendarService {

	@Autowired
	@Qualifier("orgCalendarRepository")
	private OrgCalendarRepository orgCalendarRepository;

	@Override
	protected JpaRepository<OrgCalendarEntity, Long> getRepository() {
		return orgCalendarRepository;
	}

	@Override
	protected ITextRepository<OrgCalendarEntity, Long> getTextRepository() {
		return orgCalendarRepository;
	}

	@Override
	protected String getAlternateKeyAsString(OrgCalendarEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"OrgCalendar\" : \"").append(entity.getCalendarCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public OrgCalendarEntity findbyCalendarCode(String calCode) {
		OrgCalendarEntity entity = orgCalendarRepository.findbyCalendarCode(calCode);
		return entity;
	}

	@Override
	protected AbstractServiceImpl<OrgCalendarEntity, Long>.Outcome postProcess(int opCode, OrgCalendarEntity entity)
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
	protected AbstractServiceImpl<OrgCalendarEntity, Long>.Outcome preProcess(int opCode, OrgCalendarEntity entity)
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
	public void validate(OrgCalendarEntity entity, EntityVdationType type) throws EntityValidationException {
		// If periodicity is Yearly, HalfYearly or Quarterly,
		// cannot be null and derive the endMonth

		if (entity.getPeriodicity() == CalPeriodicity.YEARLY || entity.getPeriodicity() == CalPeriodicity.HALFYEARLY
				|| entity.getPeriodicity() == CalPeriodicity.QUARTERLY) {
			if (entity.getStartMonth() == null) {
				throw new EntityValidationException(
						"OrgCalendar startMonth cannot be null " + entity.getCalendarCode());																							// end-month
			}
		}

		if (entity.getUsageType() == CalUsageType.FINANCIAL) {
			entity.setPeriodicity(CalPeriodicity.YEARLY);
		}
		if (EntityVdationType.PRE_INSERT == type) {
			entity.setLifecycleStage(LifeCycleStage.NEW);
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	protected OrgCalendarEntity doUpdate(OrgCalendarEntity neu, OrgCalendarEntity old)
			throws EntityValidationException {
		if (!old.getCalendarCode().equals(neu.getCalendarCode())) {
			throw new EntityValidationException("OrgCalendarEntity cannot be updated ! Existing - "
					+ old.getCalendarCode() + ", new - " + neu.getCalendarCode());
		}
		
		//If usageType is FINANCIAL, when status is changed from NEW to ACTIVE, 
				//create entries in trl_fin_cal_periods for one year. Start with startYear (if provided) .
				//If not use current year.
		if (old.getUsageType() == CalUsageType.FINANCIAL && old.getLifecycleStage() == LifeCycleStage.NEW
				&& neu.getLifecycleStage() == LifeCycleStage.ACTIVE) {

			FinCalendarEntity finEntity = new FinCalendarEntity();
			finEntity.setOrgCalendarCode(old.getCalendarCode());
			finEntity.setOrgCalendarRef(old);
			if (old.getStartYear() != null) {
				finEntity.setPeriodYear(old.getStartYear());
			}else{
				finEntity.setPeriodYear(Year.now().getValue());
			}
			LocalDate today = LocalDate.now(); 
			String periodName = today.format(DateTimeFormatter.ofPattern("MMM-yyyy"));
			finEntity.setPeriodName(periodName);
			YearMonth yearMonth = YearMonth.now();
			
			finEntity.setPeriodMonth(CalendarMonth.decode(yearMonth.getMonth().getValue()));
			finEntity.setPeriodStatus(CalPeriodStatus.READY);//TODO -Check
			finCalendarService.add(finEntity);
		}

		boolean updated = checkForUpdate(neu, old);
		if (updated) {
			if (isPrivilegedContext()) {
				old.setCalendarCode(neu.getCalendarCode());
				old.setCalendarName(neu.getCalendarName());
				old.setEndDay(neu.getEndDay());
				old.setEndMonth(neu.getEndMonth());
				old.setEndYear(neu.getEndYear());
				old.setPeriodicity(neu.getPeriodicity());
				old.setStartDay(neu.getStartDay());
				old.setStartMonth(neu.getStartMonth());
				old.setStartYear(neu.getStartYear());
				old.setUsageType(neu.getUsageType());
				JpaUtil.updateRevisionControl(old, true);
			}
		}
		return old;
	}

	private boolean checkForUpdate(OrgCalendarEntity neu, OrgCalendarEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getCalendarCode(), old.getCalendarCode())
				|| !Objects.equals(neu.getCalendarName(), old.getCalendarName())
				|| !Objects.equals(neu.getUsageType(), old.getUsageType())
				|| !Objects.equals(neu.getEndDay(), old.getEndDay())
				|| !Objects.equals(neu.getEndMonth(), old.getEndMonth())
				|| !Objects.equals(neu.getPeriodicity(), old.getPeriodicity())
				|| !Objects.equals(neu.getStartDay(), old.getStartDay())
				|| !Objects.equals(neu.getStartMonth(), old.getStartMonth())
				|| !Objects.equals(neu.getStartYear(), old.getStartYear())) {

			result = true;

		}
		return (result);
	}

	@Override
	public Long requestStatusChange(String calendarCode, LifeCycleStage status) throws EntityValidationException {
		Long result = -1L;
        
		OrgCalendarEntity orgCal = null;

        try {
        	orgCal = orgCalendarRepository.findbyCalendarCode(calendarCode);
        } catch (javax.persistence.NoResultException ex) {
            throw new EntityValidationException("Could not find OrgCalendar with code - " + calendarCode);
            // no-op
        }
        
        if (orgCal == null)
            throw new EntityValidationException("Could not find OrgCalendar with code - " + calendarCode);
        
        if (orgCal.getLifecycleStage() == LifeCycleStage.INACTIVE) {
            if (status != LifeCycleStage.INACTIVE) {
                if (isPrivilegedContext()) {
                	orgCal.setLifecycleStage(status);
                    JpaUtil.updateRevisionControl(orgCal, true);
                    result = 0L;
                } 
            }
        } else if (orgCal.getLifecycleStage() == LifeCycleStage.ACTIVE) {
            if (status == LifeCycleStage.INACTIVE) {
                if (isPrivilegedContext()) {
                	orgCal.setLifecycleStage(status);
                    JpaUtil.updateRevisionControl(orgCal, true);
                    result = 0L;
                } else {
                    throw new EntityValidationException("Cannot set status to INACTIVE when current status is ACTIVE !");
                }
            } else if (status == LifeCycleStage.SUSPENDED || status == LifeCycleStage.TERMINATED) {
                if (isPrivilegedContext()) {
                	orgCal.setLifecycleStage(status);
                    JpaUtil.updateRevisionControl(orgCal, true);
                    result = 0L;
                } 
            }
        } else if (orgCal.getLifecycleStage() == LifeCycleStage.SUSPENDED) {
            if (status == LifeCycleStage.ACTIVE || status == LifeCycleStage.TERMINATED) {
                if (isPrivilegedContext()) {
                	orgCal.setLifecycleStage(status);
                    JpaUtil.updateRevisionControl(orgCal, true);
                    result = 0L;
                } 
            } else if (status == LifeCycleStage.INACTIVE) {
                if (isPrivilegedContext()) {
                	orgCal.setLifecycleStage(status);
                    JpaUtil.updateRevisionControl(orgCal, true);
                    result = 0L;
                } else {
                    throw new EntityValidationException("Cannot change status to INACTIVE when current status is SUSPENDED !");
                }
            }
        } else if (orgCal.getLifecycleStage() == LifeCycleStage.TERMINATED) {
            if (isPrivilegedContext()) {
            	orgCal.setLifecycleStage(status);
                JpaUtil.updateRevisionControl(orgCal, true);
                result = 0L;
            } else {
                throw new EntityValidationException("Cannot change status to " + status + " when current status is TERMINATED !");
            }
        }
		return result;
	}

	private void preProcessAdd(OrgCalendarEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(OrgCalendarEntity entity) throws EntityValidationException {
		if (entity.getLifecycleStage() != LifeCycleStage.NEW || !isPrivilegedContext()) {
			throw new EntityValidationException("Cannot delete OrgCalendar ");
		}
	}

}
