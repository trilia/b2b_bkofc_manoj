package com.olp.jpa.domain.docu.llty.repo;

import java.util.Date;
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
import com.olp.jpa.domain.docu.llty.model.PartnerCodeShareEntity;
import com.olp.jpa.domain.docu.llty.model.PartnerValidityEntity;
import com.olp.jpa.util.JpaUtil;

@Service("partnerCodeShareService")
public class PartnerCodeShareServiceImpl extends AbstractServiceImpl<PartnerCodeShareEntity, Long>
		implements PartnerCodeShareService {

	@Autowired
	@Qualifier("partnerCodeShareRepository")
	private PartnerCodeShareRepository partnerCodeShareRepository;

	@Autowired
	@Qualifier("partnerValidityRepository")
	private PartnerValidityRepository partnerValidityRepository;

	@Override
	protected JpaRepository<PartnerCodeShareEntity, Long> getRepository() {
		return partnerCodeShareRepository;
	}

	@Override
	protected ITextRepository<PartnerCodeShareEntity, Long> getTextRepository() {
		return partnerCodeShareRepository;
	}

	@Override
	protected String getAlternateKeyAsString(PartnerCodeShareEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"partnerCodeShare\" : \"").append(entity.getPartnerCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<PartnerCodeShareEntity> findByEffectiveDate(String partnerCode, Date date) {
		return partnerCodeShareRepository.findByEffectiveDate(partnerCode, date);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<PartnerCodeShareEntity> findByPartnerCode(String partnerCode) {
		return partnerCodeShareRepository.findByPartnerCode(partnerCode);
	}

	@Override
	protected PartnerCodeShareEntity doUpdate(PartnerCodeShareEntity neu, PartnerCodeShareEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getPartnerCode(), old.getPartnerCode()))
			throw new EntityValidationException("The partner code does not match, existing - " + old.getPartnerCode()
					+ " , new - " + neu.getPartnerCode());

		if (!isPrivilegedContext())
			throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");

		if (checkForUpdate(neu, old)) {
			old.setEffectiveFrom(neu.getEffectiveFrom());
			old.setConversionRateIn(neu.getConversionRateIn());
			old.setConversionRateOut(neu.getConversionRateOut());
			old.setCreditPointsIn(neu.getCreditPointsIn());
			old.setCsLoyaltyCode(neu.getCsLoyaltyCode());
			old.setRevisionControl(neu.getRevisionControl());
			old.setTxnMaxNum(neu.getTxnMaxNum());
			old.setTxnMaxValue(neu.getTxnMaxValue());
			old.setTxnMinNum(neu.getTxnMinNum());
			old.setTxnMinValue(neu.getTxnMinValue());
			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(PartnerCodeShareEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getPartnerValidityRef() != null) {
				// it is possible to have destination lp null
				PartnerValidityEntity lp = entity.getPartnerValidityRef();
				PartnerValidityEntity lp2 = null;

				if (lp.getId() == null) {
					try {
						List<PartnerValidityEntity> lp2List = partnerValidityRepository
								.findByPartnerCode(entity.getPartnerCode());
						if (lp2List == null) {
							throw new javax.persistence.NoResultException();
						}
						lp2 = lp2List.get(0);
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find CustomerLoyalty with customer code - " + lp.getPartnerCode());
					}
				} else {
					try {
						lp2 = partnerValidityRepository.findOne(lp.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find LoyaltyPartner with id - " + lp.getId());
					}
				}

				if (lp2 == null)
					throw new EntityValidationException("Could not find PartnerValidity using either code or id !");

				entity.setPartnerValidityRef(lp2);
				entity.setPartnerCode(lp2.getPartnerCode());
			}

			if (EntityVdationType.PRE_INSERT == type) {
				this.updateTenantWithRevision(entity);
			} else if (EntityVdationType.PRE_UPDATE == type) {
				JpaUtil.updateRevisionControl(entity, true);
			}

		}

	}

	@Override
	public boolean checkForUpdate(PartnerCodeShareEntity old, PartnerCodeShareEntity neu) {
		boolean result = false;

		if (!Objects.equals(neu.getEffectiveFrom(), old.getEffectiveFrom())
				|| !Objects.equals(neu.getConversionRateIn(), old.getConversionRateIn())
				|| !Objects.equals(neu.getConversionRateOut(), old.getConversionRateOut())
				|| !Objects.equals(neu.getCreditPointsIn(), old.getCreditPointsIn())
				|| !Objects.equals(neu.getCreditPointsOut(), old.getCreditPointsOut())
				|| !Objects.equals(neu.getCsLoyaltyCode(), old.getCsLoyaltyCode())
				|| !Objects.equals(neu.getTxnMaxNum(), old.getTxnMaxNum())
				|| !Objects.equals(neu.getTxnMaxValue(), old.getTxnMaxValue())
				|| !Objects.equals(neu.getTxnMinNum(), old.getTxnMinNum())
				|| !Objects.equals(neu.getTxnMinValue(), old.getTxnMinValue())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getPartnerValidityRef() == null) {
			if (old.getPartnerValidityRef() != null)
				result = true;
		} else {
			if (old.getPartnerValidityRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getPartnerValidityRef().getId(), old.getPartnerValidityRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<PartnerCodeShareEntity, Long>.Outcome postProcess(int opCode,
			PartnerCodeShareEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<PartnerCodeShareEntity, Long>.Outcome preProcess(int opCode,
			PartnerCodeShareEntity entity) throws EntityValidationException {
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

	private void preProcessAdd(PartnerCodeShareEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(PartnerCodeShareEntity entity) throws EntityValidationException {
		if (entity.getPartnerValidityRef() != null) {
			PartnerValidityEntity partnerValidityEntity = entity.getPartnerValidityRef();
			if (partnerValidityEntity != null && !isPrivilegedContext())
				throw new EntityValidationException(
						"Cannot delete PartnerCodeShare when PartnerValidity is " + entity.getId());
		}
	}

}
