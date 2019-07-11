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
import com.olp.jpa.domain.docu.fa.model.AssetCategoryEntity;
import com.olp.jpa.domain.docu.fa.model.DeprScheduleEntity;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;
import com.olp.jpa.util.JpaUtil;

@Service("assetCategoryService")
public class AssetCategoryServiceImpl extends AbstractServiceImpl<AssetCategoryEntity, Long>
		implements AssetCategoryService {

	@Autowired
	@Qualifier("assetCategoryRepository")
	private AssetCategoryRepository assetCategoryRepository;

	@Autowired
	@Qualifier("deprScheduleRepository")
	private DeprScheduleRepository deprScheduleRepo;

	@Override
	protected JpaRepository<AssetCategoryEntity, Long> getRepository() {
		return assetCategoryRepository;
	}

	@Override
	protected ITextRepository<AssetCategoryEntity, Long> getTextRepository() {
		return assetCategoryRepository;
	}

	@Override
	protected String getAlternateKeyAsString(AssetCategoryEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"AssetCategory\" : \"").append(entity.getCategoryCode()).append("\" }");
		return (buff.toString());
	}

	@Override
	public AssetCategoryEntity findByCategoryCode(String categoryCode) {
		return assetCategoryRepository.findByCategoryCode(categoryCode);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(AssetCategoryEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getDefaultDeprScheduleRef() != null) {
				// it is possible to have destination DeprSchedule null
				DeprScheduleEntity deprSchedule = entity.getDefaultDeprScheduleRef(), deprSchedule2 = null;

				if (deprSchedule.getId() == null) {
					try {
						deprSchedule2 = deprScheduleRepo.findByScheduleCode(deprSchedule.getDeprScheduleCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find DeprSchedule with code - " + deprSchedule.getDeprScheduleCode());
					}
				} else {
					try {
						deprSchedule2 = deprScheduleRepo.findOne(deprSchedule.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find DeprSchedule with id - " + deprSchedule.getId());
					}
				}

				if (deprSchedule2 == null)
					throw new EntityValidationException("Could not find DeprSchedule using either code or id !");

				entity.setDefaultDeprScheduleRef(deprSchedule2);
				entity.setDefaultDeprScheduleCode(deprSchedule.getDeprScheduleCode());

			}
		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	public boolean checkForUpdate(AssetCategoryEntity neu, AssetCategoryEntity old) throws EntityValidationException {
		boolean result = false;

		if (!Objects.equals(neu.getDefaultPrefix(), old.getDefaultPrefix())||
				!Objects.equals(neu.getLifeCycleStage(), old.getLifeCycleStage()) 
				)  {
			result = true;
		}
		if (result)
			return (true);

		if (neu.getDefaultDeprScheduleRef() == null) {
			if (old.getDefaultDeprScheduleRef() != null)
				result = true;
		} else {
			if (old.getDefaultDeprScheduleRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getDefaultDeprScheduleRef().getId(), old.getDefaultDeprScheduleRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AssetCategoryEntity doUpdate(AssetCategoryEntity neu, AssetCategoryEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getCategoryCode(), old.getCategoryCode()))
			throw new EntityValidationException("The Category code does not match, existing - " + old.getCategoryCode()
					+ " , new - " + neu.getCategoryCode());

		if (!Objects.equals(neu.getLifeCycleStage(), old.getLifeCycleStage())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		if (checkForUpdate(neu, old)) {
			old.setDefaultDeprScheduleCode(neu.getDefaultDeprScheduleCode());
			old.setDefaultDeprScheduleRef(neu.getDefaultDeprScheduleRef());
			old.setDefaultPrefix(neu.getDefaultPrefix());
			old.setLifeCycleStage(neu.getLifeCycleStage());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	protected AbstractServiceImpl<AssetCategoryEntity, Long>.Outcome postProcess(int opCode, AssetCategoryEntity entity)
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
	protected AbstractServiceImpl<AssetCategoryEntity, Long>.Outcome preProcess(int opCode, AssetCategoryEntity entity)
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

	private void preProcessAdd(AssetCategoryEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(AssetCategoryEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext() && entity.getLifeCycleStage() != LifeCycleStage.NEW)
			throw new EntityValidationException("Cannot delete asset Category status is " + entity.getLifeCycleStage());

	}

}
