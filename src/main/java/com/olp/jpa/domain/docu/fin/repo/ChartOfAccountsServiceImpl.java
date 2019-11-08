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
import com.olp.jpa.domain.docu.comm.model.LovDefinitionEntity;
import com.olp.jpa.domain.docu.comm.repo.LovDefinitionRepository;
import com.olp.jpa.domain.docu.fin.model.ChartOfAccountsEntity;
import com.olp.jpa.util.JpaUtil;

@Service("chartOfAccountsService")
public class ChartOfAccountsServiceImpl extends AbstractServiceImpl<ChartOfAccountsEntity, Long>
		implements ChartOfAccountsService {

	@Autowired
	@Qualifier("chartOfAccountsRepository")
	private ChartOfAccountsRepository chartOfAccountsRepository;

	@Autowired
	@Qualifier("lovDefinitionRepository")
	private LovDefinitionRepository lovDefinitionRepository;

	@Override
	protected JpaRepository<ChartOfAccountsEntity, Long> getRepository() {
		return chartOfAccountsRepository;
	}

	@Override
	protected ITextRepository<ChartOfAccountsEntity, Long> getTextRepository() {
		return chartOfAccountsRepository;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public ChartOfAccountsEntity findbyCoaCode(String coaCode) {
		return chartOfAccountsRepository.findbyCoaCode(coaCode);
	}

	@Override
	protected String getAlternateKeyAsString(ChartOfAccountsEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"ChartOfAccount\" : \"").append(entity.getCoaCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	protected AbstractServiceImpl<ChartOfAccountsEntity, Long>.Outcome postProcess(int opCode,
			ChartOfAccountsEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<ChartOfAccountsEntity, Long>.Outcome preProcess(int opCode,
			ChartOfAccountsEntity entity) throws EntityValidationException {
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
			break;
		default:
			break;
		}

		return (result);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(ChartOfAccountsEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			// it is possible to have lovDefinition ld null
			// set LovSegment1
			LovDefinitionEntity ld = entity.getSegment1LovRef();
			LovDefinitionEntity ld2 = findLovSegmentRef(ld);
			entity.setSegment1LovRef(ld2);
			entity.setSegment1LovCode(ld2.getLovCode());

			// set LovSegment2
			ld = entity.getSegment2LovRef();
			ld2 = findLovSegmentRef(ld);
			entity.setSegment2LovRef(ld2);
			entity.setSegment2LovCode(ld2.getLovCode());

			// set LovSegment3
			ld = entity.getSegment3LovRef();
			ld2 = findLovSegmentRef(ld);
			entity.setSegment3LovRef(ld2);
			entity.setSegment3LovCode(ld2.getLovCode());

			// set LovSegment4
			ld = entity.getSegment4LovRef();
			ld2 = findLovSegmentRef(ld);
			entity.setSegment4LovRef(ld2);
			entity.setSegment4LovCode(ld2.getLovCode());

			// set LovSegment5
			ld = entity.getSegment5LovRef();
			ld2 = findLovSegmentRef(ld);
			entity.setSegment5LovRef(ld2);
			entity.setSegment5LovCode(ld2.getLovCode());
		}
		if (entity.getNumSegments() > 5) {
			throw new EntityValidationException(
					"No Of Segments cannot be morethan 5 for ChartOfAccounts - " + entity.getId());
		}
		if (entity.getNumSegments() >= 1 && entity.getSegment1LovRef() == null) {
			throw new EntityValidationException(
					"Segment1LovRef cannot be null as NoOfSegments morethan 1 for ChartOfAccounts - " + entity.getId());
		}
		if (entity.getNumSegments() >= 2 && entity.getSegment2LovRef() == null) {
			throw new EntityValidationException(
					"Segment2LovRef cannot be null as NoOfSegments morethan 2 for ChartOfAccounts - " + entity.getId());
		}
		if (entity.getNumSegments() >= 3 && entity.getSegment3LovRef() == null) {
			throw new EntityValidationException(
					"Segment3LovRef cannot be null as NoOfSegments morethan 3 for ChartOfAccounts - " + entity.getId());
		}
		if (entity.getNumSegments() >= 4 && entity.getSegment4LovRef() == null) {
			throw new EntityValidationException(
					"Segment4LovRef cannot be null as NoOfSegments morethan 4 for ChartOfAccounts - " + entity.getId());
		}
		if (entity.getNumSegments() >= 5 && entity.getSegment5LovRef() == null) {
			throw new EntityValidationException(
					"Segment5LovRef cannot be null as NoOfSegments morethan 5 for ChartOfAccounts - " + entity.getId());
		}
		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	protected ChartOfAccountsEntity doUpdate(ChartOfAccountsEntity neu, ChartOfAccountsEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getCoaCode(), old.getCoaCode()))
			throw new EntityValidationException("The chartOfAccounts does not match, existing - " + old.getCoaCode()
					+ " , new - " + neu.getCoaCode());

		if (!Objects.equals(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		if (checkForUpdate(neu, old)) {
			old.setCoaName(neu.getCoaName());
			old.setNumSegments(neu.getNumSegments());
			old.setSegment1LovCode(neu.getSegment1LovCode());
			old.setSegment1LovRef(neu.getSegment1LovRef());
			old.setSegment2LovCode(neu.getSegment2LovCode());
			old.setSegment2LovRef(neu.getSegment2LovRef());
			old.setSegment3LovCode(neu.getSegment3LovCode());
			old.setSegment3LovRef(neu.getSegment3LovRef());
			old.setSegment4LovCode(neu.getSegment4LovCode());
			old.setSegment4LovRef(neu.getSegment4LovRef());
			old.setSegment5LovCode(neu.getSegment5LovCode());
			old.setSegment5LovRef(neu.getSegment5LovRef());

			JpaUtil.updateRevisionControl(old, true);
		}
		return (old);
	}

	@Override
	public boolean checkForUpdate(ChartOfAccountsEntity neu, ChartOfAccountsEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getCoaCode(), old.getCoaCode()) || !Objects.equals(neu.getCoaName(), old.getCoaName())
				|| !Objects.equals(neu.getNumSegments(), old.getNumSegments())
				|| !Objects.equals(neu.getSegment1LovCode(), old.getSegment1LovCode())
				|| !Objects.equals(neu.getSegment2LovCode(), old.getSegment2LovCode())
				|| !Objects.equals(neu.getSegment3LovCode(), old.getSegment3LovCode())
				|| !Objects.equals(neu.getSegment4LovCode(), old.getSegment4LovCode())
				|| !Objects.equals(neu.getSegment5LovCode(), old.getSegment5LovCode())) {
			result = true;
		}
		if (result)
			return (true);

		// check lovSegment1 is updated
		if (neu.getSegment1LovRef() == null) {
			if (old.getSegment1LovRef() != null)
				result = true;
		} else {
			if (old.getSegment1LovRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getSegment1LovRef().getId(), old.getSegment1LovRef().getId())) {
					result = true;
				}
			}
		}

		// check lovSegment2 is updated
		if (neu.getSegment2LovRef() == null) {
			if (old.getSegment2LovRef() != null)
				result = true;
		} else {
			if (old.getSegment2LovRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getSegment2LovRef().getId(), old.getSegment2LovRef().getId())) {
					result = true;
				}
			}
		}

		// check lovSegment3 is updated
		if (neu.getSegment3LovRef() == null) {
			if (old.getSegment3LovRef() != null)
				result = true;
		} else {
			if (old.getSegment3LovRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getSegment3LovRef().getId(), old.getSegment3LovRef().getId())) {
					result = true;
				}
			}
		}

		// check lovSegment4 is updated
		if (neu.getSegment4LovRef() == null) {
			if (old.getSegment4LovRef() != null)
				result = true;
		} else {
			if (old.getSegment4LovRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getSegment4LovRef().getId(), old.getSegment4LovRef().getId())) {
					result = true;
				}
			}
		}

		// check lovSegment5 is updated
		if (neu.getSegment5LovRef() == null) {
			if (old.getSegment5LovRef() != null)
				result = true;
		} else {
			if (old.getSegment5LovRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getSegment5LovRef().getId(), old.getSegment5LovRef().getId())) {
					result = true;
				}
			}
		}
		return result;
	}

	private void preProcessAdd(ChartOfAccountsEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(ChartOfAccountsEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext()) {
			throw new EntityValidationException("Cannot delete ChartOfAccounts  " + entity.getCoaCode());
		}
	}

	private LovDefinitionEntity findLovSegmentRef(LovDefinitionEntity ld) throws EntityValidationException {
		LovDefinitionEntity ld2 = null;
		if (ld != null) {
			if (ld.getId() == null) {
				try {
					ld2 = lovDefinitionRepository.findByLovCode(ld.getLovCode());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find LovDefinition with code - " + ld.getLovCode());
				}
			} else {
				try {
					ld2 = lovDefinitionRepository.findOne(ld.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find LovDefinition with id - " + ld.getId());
				}
			}
			if (ld2 == null)
				throw new EntityValidationException("Could not find LovDifinition using either code or id !");
		}
		return ld2;
	}
}
