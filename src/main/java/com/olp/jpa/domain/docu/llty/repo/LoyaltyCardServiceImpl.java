package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyEntity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyCardEntity;
import com.olp.jpa.util.JpaUtil;

@Repository("loyaltyCardService")
public class LoyaltyCardServiceImpl extends AbstractServiceImpl<LoyaltyCardEntity, Long> implements LoyaltyCardService {

	@Autowired
	@Qualifier("loyaltyCardRepository")
	private LoyaltyCardRepository loyaltyCardRepository;

	@Autowired
	@Qualifier("customerLoyaltyRepository")
	private CustomerLoyaltyRepository customerLoyaltyRepository;

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LoyaltyCardEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getCsLoyaltyRef() != null) {
				// it is possible to have destination cl null
				CustomerLoyaltyEntity cl = entity.getCsLoyaltyRef();
				CustomerLoyaltyEntity cl2 = null;

				if (cl.getId() == null) {
					try {
						List<CustomerLoyaltyEntity> cl3 = customerLoyaltyRepository
								.findByCustProgCode(cl.getCustomerCode(), cl.getProgramCode());
						if (cl3.isEmpty()) {
							throw new javax.persistence.NoResultException();
						}
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find CustomerLoyalty with customer code - " + cl.getCustomerCode());
					}
				} else {
					try {
						cl2 = customerLoyaltyRepository.findOne(cl.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find CustomerLoyalty with id - " + cl.getId());
					}
				}

				if (cl2 == null)
					throw new EntityValidationException("Could not find CustomerLoyalty using either code or id !");

				entity.setCsLoyaltyRef(cl2);
				entity.setCsLoyaltyCode(cl2.getCsLoyaltyCode());
			}
		}

		List<LoyaltyCardEntity> loyaltyCardList = findAllByCustomerCode(entity.getCustomerCode());
		if (!loyaltyCardList.isEmpty()) {

		}

		JpaUtil.updateRevisionControl(entity, true);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public LoyaltyCardEntity findByCustomerCode(String custCode) {
		return loyaltyCardRepository.findByCustomerCode(custCode);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<LoyaltyCardEntity> findAllByCustomerCode(String custCode) {
		return loyaltyCardRepository.findAllByCustomerCode(custCode);
	}

	@Override
	protected String getAlternateKeyAsString(LoyaltyCardEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"loyaltyCard\" : \"").append(entity.getCsLoyaltyCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	protected JpaRepository<LoyaltyCardEntity, Long> getRepository() {
		return loyaltyCardRepository;
	}

	@Override
	protected ITextRepository<LoyaltyCardEntity, Long> getTextRepository() {
		return loyaltyCardRepository;
	}

	@Override
	protected AbstractServiceImpl<LoyaltyCardEntity, Long>.Outcome postProcess(int opCode, LoyaltyCardEntity entity)
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
	protected AbstractServiceImpl<LoyaltyCardEntity, Long>.Outcome preProcess(int opCode, LoyaltyCardEntity entity)
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
	protected LoyaltyCardEntity doUpdate(LoyaltyCardEntity neu, LoyaltyCardEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getCsLoyaltyCode(), old.getCsLoyaltyCode()))
			throw new EntityValidationException("The customer loyalty card does not match, existing - "
					+ old.getCsLoyaltyCode() + " , new - " + neu.getCsLoyaltyCode());

		if (checkForUpdate(neu, old)) {
			old.setCsLoyaltyCode(neu.getCsLoyaltyCode());
			old.setCsLoyaltyRef(neu.getCsLoyaltyRef());
			old.setCustomerCode(neu.getCustomerCode());
			old.setCardNumber(neu.getCardNumber());
			old.setCardNumber2(neu.getCardNumber2());
			old.setCardNumber3(neu.getCardNumber3());
			old.setCardNumber4(neu.getCardNumber4());
			old.setCardNumber5(neu.getCardNumber5());
			old.setCardSource2(neu.getCardSource2());
			old.setCardSource3(neu.getCardSource3());
			old.setCardSource4(neu.getCardSource4());
			old.setCardSource5(neu.getCardSource5());
			old.setEffectiveFrom(neu.getEffectiveFrom());
			old.setEffectiveUpto(neu.getEffectiveUpto());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(LoyaltyCardEntity neu, LoyaltyCardEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getCsLoyaltyCode(), old.getCsLoyaltyCode())
				|| !Objects.equals(neu.getCustomerCode(), old.getCustomerCode())
				|| !Objects.equals(neu.getCardNumber(), old.getCardNumber())
				|| !Objects.equals(neu.getCardNumber2(), old.getCardNumber2())
				|| !Objects.equals(neu.getCardNumber3(), old.getCardNumber3())
				|| !Objects.equals(neu.getCardNumber4(), old.getCardNumber4())
				|| !Objects.equals(neu.getCardNumber5(), old.getCardNumber5())
				|| !Objects.equals(neu.getCardSource2(), old.getCardSource2())
				|| !Objects.equals(neu.getCardSource3(), old.getCardSource3())
				|| !Objects.equals(neu.getCardSource4(), old.getCardSource4())
				|| !Objects.equals(neu.getCardSource5(), old.getCardSource5())
				|| !Objects.equals(neu.getEffectiveFrom(), old.getEffectiveFrom())
				|| !Objects.equals(neu.getEffectiveUpto(), old.getEffectiveUpto())

		) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getCsLoyaltyRef() == null) {
			if (old.getCsLoyaltyRef() != null)
				result = true;
		} else {
			if (old.getCsLoyaltyRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getCsLoyaltyRef().getId(), old.getCsLoyaltyRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	private void preProcessAdd(LoyaltyCardEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(LoyaltyCardEntity entity) throws EntityValidationException {

		if (entity.getCsLoyaltyRef() != null) {
			CustomerLoyaltyEntity customerLoyalty = entity.getCsLoyaltyRef();
			if (!isPrivilegedContext())
				throw new EntityValidationException(
						"Cannot delete CustomerLoyaltyCard when CustomerLoyaltyEntity status is "
								+ customerLoyalty.getStatus());
		}

	}

}
