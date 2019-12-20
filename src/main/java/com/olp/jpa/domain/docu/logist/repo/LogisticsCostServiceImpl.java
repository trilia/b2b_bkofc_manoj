package com.olp.jpa.domain.docu.logist.repo;

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
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fin.model.TaxGroupEntity;
import com.olp.jpa.domain.docu.fin.repo.TaxGroupRepository;
import com.olp.jpa.domain.docu.logist.model.LogisticsCostEntity;
import com.olp.jpa.domain.docu.logist.model.ServiceCategoryEntity;
import com.olp.jpa.util.JpaUtil;

@Service("logisticsCostService")
public class LogisticsCostServiceImpl extends AbstractServiceImpl<LogisticsCostEntity, Long>
		implements LogisticsCostService {

	@Autowired
	@Qualifier("logisticsCostRepository")
	private LogisticsCostRepository logisticsCostRepository;

	@Autowired
	@Qualifier("serviceCategoryRepository")
	private ServiceCategoryRepository serviceCategoryRepository;

	@Autowired
	@Qualifier("taxGroupRepository")
	private TaxGroupRepository taxGroupRepository;

	@Override
	protected JpaRepository<LogisticsCostEntity, Long> getRepository() {
		return logisticsCostRepository;
	}

	@Override
	protected ITextRepository<LogisticsCostEntity, Long> getTextRepository() {
		return logisticsCostRepository;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LogisticsCostEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getSvcCatgRef() != null) {
				// it is possible to have destination sc null
				ServiceCategoryEntity sc = entity.getSvcCatgRef(), sc2 = null;

				try {
					sc2 = serviceCategoryRepository.findOne(sc.getId());
					// Only One active/inactive logisticsCost

					List<LogisticsCostEntity> logisticsCostList = sc2.getCostEstimate();
					int noOfActiveCount = 0;
					int noOfInActiveCount = 0;
					for (LogisticsCostEntity logisticsCostEntity : logisticsCostList) {
						if (logisticsCostEntity.getLifecycleStatus() == LifeCycleStatus.ACTIVE) {
							noOfActiveCount = noOfActiveCount + 1;
						} else if (logisticsCostEntity.getLifecycleStatus() == LifeCycleStatus.INACTIVE) {
							noOfInActiveCount = noOfActiveCount + 1;
						}

						if (noOfActiveCount >= 0 && noOfInActiveCount >= 0) {
							throw new EntityValidationException(
									"For a given ServiceCategoryEntity record there should be only one ACTIVE / INACTIVE LogisticCostEntity record "+sc.getId());
						}

					}

				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find ServiceCategory with id - " + sc.getId());
				}

				entity.setSvcCatgRef(sc2);

			}

			if (entity.getTaxGroupRef() != null) {
				// it is possible to have destination tg null
				TaxGroupEntity tg = entity.getTaxGroupRef(), tg2 = null;

				if (tg.getId() == null) {
					try {
						tg2 = taxGroupRepository.findByGroupCode(tg.getGroupCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find TaxGroup with code - " + tg.getGroupCode());
					}
				} else {
					try {
						tg2 = taxGroupRepository.findOne(tg.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find TaxGroup with id - " + tg.getId());
					}
				}

				if (tg2 == null)
					throw new EntityValidationException("Could not find TaxGroup using either code or id !");

				entity.setTaxGroupRef(tg2);
				entity.setTaxGroupCode(tg2.getGroupCode());
			}
		}

		if (EntityVdationType.PRE_INSERT == type) {
			//this.updateTenantWithRevision(entity);
			JpaUtil.updateRevisionControl(entity, true);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	protected String getAlternateKeyAsString(LogisticsCostEntity arg0) {
		return "";
	}

	@Override
	protected LogisticsCostEntity doUpdate(LogisticsCostEntity neu, LogisticsCostEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		if (checkForUpdate(neu, old)) {
			old.setBasicCost(neu.getBasicCost());
			old.setEffectiveDate(neu.getEffectiveDate());
			old.setNonOffsetTax(neu.getNonOffsetTax());
			old.setNonOffsetTaxPct(neu.getNonOffsetTaxPct());
			old.setOffsetTax(neu.getOffsetTax());
			old.setOffsetTaxPct(neu.getOffsetTaxPct());
			old.setSvcCatgRef(neu.getSvcCatgRef());
			old.setTaxGroupCode(neu.getTaxGroupCode());
			old.setTaxGroupRef(neu.getTaxGroupRef());
			old.setTotalCost(neu.getTotalCost());
			old.setVolWeightMax(neu.getVolWeightMax());
			old.setVolWeightMin(neu.getVolWeightMin());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(LogisticsCostEntity neu, LogisticsCostEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getBasicCost(), old.getBasicCost())
				|| !Objects.equals(neu.getEffectiveDate(), old.getEffectiveDate())
				|| !Objects.equals(neu.getNonOffsetTax(), old.getNonOffsetTax())
				|| !Objects.equals(neu.getNonOffsetTaxPct(), old.getNonOffsetTaxPct())
				|| !Objects.equals(neu.getOffsetTax(), old.getOffsetTax())
				|| !Objects.equals(neu.getOffsetTaxPct(), old.getOffsetTaxPct())
				|| !Objects.equals(neu.getTaxGroupCode(), old.getTaxGroupCode())
				|| !Objects.equals(neu.getTotalCost(), old.getTotalCost())
				|| !Objects.equals(neu.getVolWeightMax(), old.getVolWeightMax())
				|| !Objects.equals(neu.getVolWeightMin(), old.getVolWeightMin())) {

			result = true;
		}

		if (result)
			return (true);

		if (neu.getSvcCatgRef() == null) {
			if (old.getSvcCatgRef() != null)
				result = true;
		} else {
			if (old.getSvcCatgRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getSvcCatgRef().getId(), old.getSvcCatgRef().getId())) {
					result = true;
				}
			}
		}

		if (neu.getTaxGroupRef() == null) {
			if (old.getTaxGroupRef() != null)
				result = true;
		} else {
			if (old.getTaxGroupRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getTaxGroupRef().getId(), old.getTaxGroupRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<LogisticsCostEntity, Long>.Outcome postProcess(int opCode, LogisticsCostEntity entity)
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
	protected AbstractServiceImpl<LogisticsCostEntity, Long>.Outcome preProcess(int opCode, LogisticsCostEntity entity)
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
			break;
		default:
			break;
		}

		return (result);
	}

	private void preProcessAdd(LogisticsCostEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
	}

	private void preDelete(LogisticsCostEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext() && entity.getLifecycleStatus() != LifeCycleStatus.ACTIVE) {// TODO
																								// //
																								// enum
																								// new
																								// status
			throw new EntityValidationException("Cannot delete LogisticsCost. when state is "
					+ entity.getLifecycleStatus() + ".Only TERMINATION possible");
		}
	}

}
