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
import com.olp.jpa.domain.docu.comm.model.GenericSequenceEntity;
import com.olp.jpa.domain.docu.comm.repo.GenericSequenceRepository;
import com.olp.jpa.domain.docu.fa.model.AssetCategoryEntity;
import com.olp.jpa.domain.docu.fa.model.AssetEntity;
import com.olp.jpa.domain.docu.fa.model.DeprScheduleEntity;
import com.olp.jpa.domain.docu.fa.model.FaEnums.AssetLifeCycleStage;
import com.olp.jpa.util.JpaUtil;

@Service("assetService")
public class AssetServiceImpl extends AbstractServiceImpl<AssetEntity, Long> implements AssetService {

	@Autowired
	@Qualifier("assetRepository")
	private AssetRepository assetRepository;

	@Autowired
	@Qualifier("deprScheduleRepository")
	private DeprScheduleRepository deprScheduleRepository;

	@Autowired
	@Qualifier("assetCategoryRepository")
	private AssetCategoryRepository assetCategoryRepository;

	@Autowired
	@Qualifier("genericSequenceRepository")
	private GenericSequenceRepository sequenceRepository;

	@Override
	protected JpaRepository<AssetEntity, Long> getRepository() {
		return assetRepository;
	}

	@Override
	protected ITextRepository<AssetEntity, Long> getTextRepository() {
		return assetRepository;
	}

	@Override
	protected String getAlternateKeyAsString(AssetEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"asset\" : \"").append(entity.getAssetCode()).append("\" }");
		return (buff.toString());
	}

	@Override
	public AssetEntity findByAssetCode(String assetCode) {
		return assetRepository.findByAssetCode(assetCode);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(AssetEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {

		if (valParent) {
			if (entity.getDeprScheduleRef() != null) {
				// it is possible to have destination DeprSchedule null
				DeprScheduleEntity deprSchedule = entity.getDeprScheduleRef(), deprSchedule2 = null;

				if (deprSchedule.getId() == null) {
					try {
						deprSchedule2 = deprScheduleRepository.findByScheduleCode(deprSchedule.getDeprScheduleCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find DeprSchedule with code - " + deprSchedule.getDeprScheduleCode());
					}
				} else {
					try {
						deprSchedule2 = deprScheduleRepository.findOne(deprSchedule.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find DeprSchedule with id - " + deprSchedule.getId());
					}
				}

				if (deprSchedule2 == null)
					throw new EntityValidationException("Could not find DeprSchedule using either code or id !");

				entity.setDeprScheduleRef(deprSchedule2);
				entity.setDeprScheduleCode(deprSchedule.getDeprScheduleCode());

			}
			if (entity.getCategoryRef() != null) {
				// it is possible to have destination Category null
				AssetCategoryEntity assetCategory = entity.getCategoryRef(), assetCategory2 = null;

				if (assetCategory.getId() == null) {
					try {
						assetCategory2 = assetCategoryRepository.findByCategoryCode(assetCategory.getCategoryCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find AssetCategory with code - " + assetCategory.getCategoryCode());
					}
				} else {
					try {
						assetCategory2 = assetCategoryRepository.findOne(assetCategory.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find AssetCategory with id - " + assetCategory.getId());
					}
				}

				if (assetCategory2 == null)
					throw new EntityValidationException("Could not find AssetCategory using either code or id !");

				entity.setCategoryRef(assetCategory2);
				entity.setCategoryCode(assetCategory.getCategoryCode());
			}

		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	public boolean checkForUpdate(AssetEntity neu, AssetEntity old) throws EntityValidationException {

		boolean result = false;

		if (!Objects.equals(neu.getAcquisitionCost(), old.getAcquisitionCost())
				|| !Objects.equals(neu.getAcquisitionDate(), old.getAcquisitionDate())
				|| !Objects.equals(neu.getDateWrittenOff(), old.getDateWrittenOff())
				|| !Objects.equals(neu.getResidualValue(), old.getResidualValue())
				|| !Objects.equals(neu.getRetiredDate(), old.getRetiredDate())
				|| !Objects.equals(neu.getWrittenOffValue(), old.getWrittenOffValue())
				|| !Objects.equals(neu.getAssetLifecycle(), old.getAssetLifecycle())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getDeprScheduleRef() == null) {
			if (old.getDeprScheduleRef() != null)
				result = true;
		} else {
			if (old.getDeprScheduleRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getDeprScheduleRef().getId(), old.getDeprScheduleRef().getId())) {
					result = true;
				}
			}
		}

		if (result)
			return (true);

		if (neu.getCategoryRef() == null) {
			if (old.getCategoryRef() != null)
				result = true;
		} else {
			if (old.getCategoryRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getCategoryRef().getId(), old.getCategoryRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<AssetEntity, Long>.Outcome postProcess(int opCode, AssetEntity entity)
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
	protected AbstractServiceImpl<AssetEntity, Long>.Outcome preProcess(int opCode, AssetEntity entity)
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

	@Override
	protected AssetEntity doUpdate(AssetEntity neu, AssetEntity old) throws EntityValidationException {

		if (!Objects.equals(neu.getAssetCode(), old.getAssetCode()))
			throw new EntityValidationException("The Category code does not match, existing - " + old.getCategoryCode()
					+ " , new - " + neu.getCategoryCode());

		if (!Objects.equals(neu.getAssetLifecycle(), old.getAssetLifecycle())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		if (checkForUpdate(neu, old)) {
			old.setAcquisitionCost(neu.getAcquisitionCost());
			old.setAcquisitionDate(neu.getAcquisitionDate());
			old.setAssetLifecycle(neu.getAssetLifecycle());
			old.setCategoryCode(neu.getCategoryCode());
			old.setCategoryRef(neu.getCategoryRef());
			old.setDateWrittenOff(neu.getDateWrittenOff());
			old.setDeprScheduleCode(neu.getDeprScheduleCode());
			old.setDeprScheduleRef(neu.getDeprScheduleRef());
			old.setResidualValue(neu.getResidualValue());
			old.setRetiredDate(neu.getRetiredDate());
			old.setWrittenOff(neu.isWrittenOff());
			old.setWrittenOffValue(neu.getWrittenOffValue());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	private void preProcessAdd(AssetEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		GenericSequenceEntity seq = new GenericSequenceEntity();
		JpaUtil.updateRevisionControl(seq, false);
		sequenceRepository.save(seq);

		String assetCode = entity.getCategoryRef().getDefaultPrefix() + seq.getId();
		entity.setAssetCode(assetCode);

		this.updateTenantWithRevision(entity);
	}

	private void preDelete(AssetEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext() && entity.getAssetLifecycle() != AssetLifeCycleStage.NEW)
			throw new EntityValidationException("Cannot delete asset status is " + entity.getAssetLifecycle());

	}

}
