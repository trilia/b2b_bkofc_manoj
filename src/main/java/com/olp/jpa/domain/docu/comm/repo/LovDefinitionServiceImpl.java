package com.olp.jpa.domain.docu.comm.repo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

@Service("lovDefinitionService")
public class LovDefinitionServiceImpl extends AbstractServiceImpl<LovDefinitionEntity, Long>
		implements LovDefinitionService {

	@Autowired
	@Qualifier("orgCalendarRepository")
	private LovDefinitionRepository lovDefinitionRepository;

	@Autowired
	@Qualifier("lovValuesService")
	private LovValuesService lovValuesService;

	@Override
	protected JpaRepository<LovDefinitionEntity, Long> getRepository() {
		return lovDefinitionRepository;
	}

	@Override
	protected ITextRepository<LovDefinitionEntity, Long> getTextRepository() {
		return lovDefinitionRepository;
	}

	@Override
	public LovDefinitionEntity findByLovCode(String lovCode) {
		return lovDefinitionRepository.findByLovCode(lovCode);
	}

	@Override
	protected String getAlternateKeyAsString(LovDefinitionEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"LovDefinition\" : \"").append(entity.getLovCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	protected AbstractServiceImpl<LovDefinitionEntity, Long>.Outcome postProcess(int opCode, LovDefinitionEntity entity)
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
	protected AbstractServiceImpl<LovDefinitionEntity, Long>.Outcome preProcess(int opCode, LovDefinitionEntity entity)
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
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LovDefinitionEntity entity, EntityVdationType type) throws EntityValidationException {
		List<LovValuesEntity> lovValuesList = entity.getLovValues();
		if (lovValuesList != null && !lovValuesList.isEmpty()) {
			for (LovValuesEntity lovValues : lovValuesList) {
				lovValues.setLovDefRef(entity);
				lovValues.setLovCode(entity.getLovCode());
				lovValuesService.validate(lovValues, false, type);
			}
		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	protected LovDefinitionEntity doUpdate(LovDefinitionEntity neu, LovDefinitionEntity old)
			throws EntityValidationException {

		if (!old.getLovCode().equals(neu.getLovCode())) {
			throw new EntityValidationException(
					"LovDefinition cannot be updated ! Existing - " + old.getLovCode() + ", new - " + neu.getLovCode());
		}

		// validate(neu, EntityVdationType.PRE_UPDATE);

		List<Long> deletedLovValuesList = new ArrayList<Long>();

		if (old.getLovValues() != null && !old.getLovValues().isEmpty()) {
			for (LovValuesEntity oldLuvValues : old.getLovValues()) {
				boolean found = false;
				if (neu.getLovValues() != null && !neu.getLovValues().isEmpty()) {
					for (LovValuesEntity newLovValues : neu.getLovValues()) {
						if (Objects.equals(newLovValues.getId(), oldLuvValues.getId())) {
							found = true;
							break;
						}
					} // end for new lovValuesEntity
				}
				if (!found) {
					// lovValues deleted
					deletedLovValuesList.add(oldLuvValues.getId());

				}
			}
		}

		boolean updated = false;
		if (!deletedLovValuesList.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			if (neu.getLovValues() != null && !neu.getLovValues().isEmpty()) {
				updateLovValues(neu, old); // end for
											// neu.getLovValues
			} // end if neu.getLovValues != null

			if (!deletedLovValuesList.isEmpty()) {
				// dissociate LovValues
				deleteLovValues(old, deletedLovValuesList);
			} // end if deletedLovValues not empty

			old.setEnabled(neu.isEnabled());
			old.setLovName(neu.getLovName());
			old.setLovType(neu.getLovType());
			JpaUtil.updateRevisionControl(old, true);
			// }
		}
		return (old);
	}

	private boolean checkForUpdate(LovDefinitionEntity neu, LovDefinitionEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getLovCode(), old.getLovCode()) || !Objects.equals(neu.getLovName(), old.getLovName())
				|| !Objects.equals(neu.getLovType(), old.getLovType())) {

			result = true;
		}

		if (neu.getLovValues() == null || neu.getLovValues().isEmpty()) {
			if (old.getLovValues() != null && !old.getLovValues().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getLovValues() == null || old.getLovValues().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getLovValues().size(), old.getLovValues().size())) {
					result = true;
					return (result);
				} else {
					result = checkForLovValues(neu.getLovValues(), old.getLovValues());
					return (result);
				}
			}
		}
		return (result);
	}

	private void updateLovValues(LovDefinitionEntity neu, LovDefinitionEntity old) throws EntityValidationException {
		for (LovValuesEntity newLovValues : neu.getLovValues()) {
			LovValuesEntity oldLuvValues2 = null;
			boolean found = false;
			if (old.getLovValues() != null && !old.getLovValues().isEmpty()) {
				for (LovValuesEntity oldLovValues : old.getLovValues()) {
					if (Objects.equals(newLovValues.getId(), oldLovValues.getId())) {
						oldLuvValues2 = oldLovValues;
						found = true;
						break;
					}
				} // end for old.getLovValues
			} // end if old.getLovValues

			if (!found) {
				// new LovValues being added
				newLovValues.setLovDefRef(old);
				newLovValues.setLovCode(old.getLovCode());
				newLovValues.setRevisionControl(getRevisionControl());
				newLovValues.setTenantId(getTenantId());

				lovValuesService.validate(newLovValues, false, EntityVdationType.PRE_INSERT);
				old.getLovValues().add(newLovValues);
			} else {
				boolean lovValuesUpdated = lovValuesService.checkForUpdate(newLovValues, oldLuvValues2);
				if (lovValuesUpdated) {
					lovValuesService.update(newLovValues);
				}
			}

		}
	}

	private void deleteLovValues(LovDefinitionEntity old, List<Long> deletedLovValues) {
		Iterator<LovValuesEntity> lovValuesEntityIter = old.getLovValues().iterator();
		for (Long id : deletedLovValues) {
			boolean found = false;
			LovValuesEntity oldLovValues = null;
			while (lovValuesEntityIter.hasNext()) {
				oldLovValues = lovValuesEntityIter.next();
				if (Objects.equals(id, oldLovValues.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				lovValuesService.delete(id);
			}
		}
	}

	private boolean checkForLovValues(List<LovValuesEntity> newLovValues, List<LovValuesEntity> oldLovValues) {

		boolean result = false;
		Iterator<LovValuesEntity> oldLovValuesEntityIter = oldLovValues.iterator();
		Iterator<LovValuesEntity> newLovValuesEntityIter = newLovValues.iterator();
		LovValuesEntity oldLovValue = null;
		LovValuesEntity newLovValue = null;

		while (newLovValuesEntityIter.hasNext()) {
			newLovValue = newLovValuesEntityIter.next();
			Long newEntityId = newLovValue.getId();
			if (newEntityId != null) {
				while (oldLovValuesEntityIter.hasNext()) {
					oldLovValue = oldLovValuesEntityIter.next();
					Long oldEntityId = oldLovValue.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = lovValuesService.checkForUpdate(newLovValue, oldLovValue);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = lovValuesService.checkForUpdate(newLovValue, oldLovValue);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

	private void preProcessAdd(LovDefinitionEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(LovDefinitionEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext()) {
			throw new EntityValidationException("Cannot delete LovDefinition ");
		}
	}
}
