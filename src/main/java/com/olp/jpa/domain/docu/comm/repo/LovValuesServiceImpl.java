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
import com.olp.jpa.domain.docu.comm.model.LovDefinitionEntity;
import com.olp.jpa.domain.docu.comm.model.LovValuesEntity;
import com.olp.jpa.util.JpaUtil;

@Service("lovValuesServcie")
public class LovValuesServiceImpl extends AbstractServiceImpl<LovValuesEntity, Long> implements LovValuesService {

	@Autowired
	@Qualifier("lovValuesRepository")
	private LovValuesRepository lovValuesRepository;

	@Autowired
	@Qualifier("lovDefinitionRepository")
	private LovDefinitionRepository lovDefinitionRepository;

	@Override
	protected JpaRepository<LovValuesEntity, Long> getRepository() {
		return lovValuesRepository;
	}

	@Override
	protected ITextRepository<LovValuesEntity, Long> getTextRepository() {
		return lovValuesRepository;
	}

	@Override
	public LovValuesEntity findbyLovValue(String lovCode, String value) {
		return lovValuesRepository.findByLovValue(lovCode, value);
	}

	@Override
	protected String getAlternateKeyAsString(LovValuesEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"LovValues\" : \"").append(entity.getLovCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LovValuesEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getLovDefRef() != null) {
				// it is possible to have lovDefinition ld null
				LovDefinitionEntity ld = entity.getLovDefRef(), ld2 = null;

				if (ld.getId() == null) {
					try {
						ld2 = lovDefinitionRepository.findByLovCode(ld.getLovCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find LovDefinition with code - " + ld.getLovCode());
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

				entity.setLovDefRef(ld2);
				entity.setLovCode(ld.getLovCode());
			}
		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	protected LovValuesEntity doUpdate(LovValuesEntity neu, LovValuesEntity old) throws EntityValidationException {

		if (!Objects.equals(neu.getLovCode(), old.getLovCode()))
			throw new EntityValidationException(
					"The lov values does not match, existing - " + old.getLovCode() + " , new - " + neu.getLovCode());

		if (checkForUpdate(neu, old)) {
			old.setEnabled(neu.isEnabled());
			old.setValue(neu.getValue());
			old.setLovDefRef(neu.getLovDefRef());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(LovValuesEntity neu, LovValuesEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getLovCode(), old.getLovCode()) || !Objects.equals(neu.getValue(), old.getValue())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getLovDefRef() == null) {
			if (old.getLovDefRef() != null)
				result = true;
		} else {
			if (old.getLovDefRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getLovDefRef().getId(), old.getLovDefRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<LovValuesEntity, Long>.Outcome postProcess(int opCode, LovValuesEntity entity)
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
	protected AbstractServiceImpl<LovValuesEntity, Long>.Outcome preProcess(int opCode, LovValuesEntity entity)
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

	private void preProcessAdd(LovValuesEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(LovValuesEntity entity) throws EntityValidationException {
		if (entity.getLovDefRef() != null) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot delete lovValues");
		}
	}
}
