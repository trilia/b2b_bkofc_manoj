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
import com.olp.jpa.domain.docu.llty.model.LoyaltyPartnerEntity;
import com.olp.jpa.domain.docu.llty.model.PartnerValidityEntity;
import com.olp.jpa.util.JpaUtil;

@Service("partnerValidityService")
public class PartnerValidityServiceImpl extends AbstractServiceImpl<PartnerValidityEntity, Long>
		implements PartnerValidityService {

	@Autowired
	@Qualifier("partnerValidityRepository")
	private PartnerValidityRepository partnerValidityRepository;

	@Autowired
	@Qualifier("loyaltyPartnerRepository")
	private LoyaltyPartnerRepository loyaltyPartnerRepository;

	@Override
	protected String getAlternateKeyAsString(PartnerValidityEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"partnerValidity\" : \"").append(entity.getPartnerCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	protected JpaRepository<PartnerValidityEntity, Long> getRepository() {
		return partnerValidityRepository;
	}

	@Override
	protected ITextRepository<PartnerValidityEntity, Long> getTextRepository() {
		return partnerValidityRepository;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(PartnerValidityEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getPartnerRef() != null) {
				// it is possible to have destination lp null
				LoyaltyPartnerEntity lp = entity.getPartnerRef();
				LoyaltyPartnerEntity lp2 = null;

				if (lp.getId() == null) {
					try {
						lp2 = loyaltyPartnerRepository.findByPartnerCode(entity.getPartnerCode());
						if (lp2 == null) {
							throw new javax.persistence.NoResultException();
						}
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find CustomerLoyalty with customer code - " + lp.getPartnerCode());
					}
				} else {
					try {
						lp2 = loyaltyPartnerRepository.findOne(lp.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find LoyaltyPartner with id - " + lp.getId());
					}
				}

				if (lp2 == null)
					throw new EntityValidationException("Could not find LoyaltyPartner using either code or id !");

				entity.setPartnerRef(lp2);
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
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public PartnerValidityEntity findByEffectiveDate(String partnerCode, Date date) {
		return partnerValidityRepository.findByEffectiveDate(partnerCode, date);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<PartnerValidityEntity> findByPartnerCode(String partnerCode) {
		return partnerValidityRepository.findByPartnerCode(partnerCode);
	}

	@Override
	protected PartnerValidityEntity doUpdate(PartnerValidityEntity neu, PartnerValidityEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getPartnerCode(), old.getPartnerCode()))
			throw new EntityValidationException("The partner code does not match, existing - " + old.getPartnerCode()
					+ " , new - " + neu.getPartnerCode());

		if (!isPrivilegedContext())
			throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");

		if (checkForUpdate(neu, old)) {
			old.setEffectiveFrom(neu.getEffectiveFrom());
			old.setEffectiveUpto(neu.getEffectiveUpto());
			old.setPartnerRef(neu.getPartnerRef());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(PartnerValidityEntity old, PartnerValidityEntity neu) {
		boolean result = false;

		if (!Objects.equals(neu.getEffectiveFrom(), old.getEffectiveFrom())
				|| !Objects.equals(neu.getEffectiveUpto(), old.getEffectiveUpto())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getPartnerRef() == null) {
			if (old.getPartnerRef() != null)
				result = true;
		} else {
			if (old.getPartnerRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getPartnerRef().getId(), old.getPartnerRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<PartnerValidityEntity, Long>.Outcome postProcess(int opCode,
			PartnerValidityEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<PartnerValidityEntity, Long>.Outcome preProcess(int opCode,
			PartnerValidityEntity entity) throws EntityValidationException {
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

	private void preProcessAdd(PartnerValidityEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(PartnerValidityEntity entity) throws EntityValidationException {
		if (entity.getPartnerRef() != null) {
			LoyaltyPartnerEntity loyaltyPartner = entity.getPartnerRef();
			if (loyaltyPartner != null && !isPrivilegedContext())
				throw new EntityValidationException(
						"Cannot delete loyaltyPartner when PartnerValidity is " + entity.getId());
		}
	}

}
