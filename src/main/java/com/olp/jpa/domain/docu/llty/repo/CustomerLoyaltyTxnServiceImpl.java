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
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyEntity;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTxnEntity;
import com.olp.jpa.domain.docu.om.repo.CustomerRepository;
import com.olp.jpa.util.JpaUtil;

@Service("customerLoyaltyTxnService")
public class CustomerLoyaltyTxnServiceImpl extends AbstractServiceImpl<CustomerLoyaltyTxnEntity, Long>
		implements CustomerLoyaltyTxnService {

	@Autowired
	@Qualifier("customerLoyaltyTxnRepository")
	private CustomerLoyaltyTxnRepository customerLoyaltyTxnRepository;

	@Autowired
	@Qualifier("customerLoyaltyRepository")
	private CustomerLoyaltyRepository customerLoyaltyRepository;

	@Autowired
	@Qualifier("customerRepository")
	private CustomerRepository customerRepository;

	@Override
	protected JpaRepository<CustomerLoyaltyTxnEntity, Long> getRepository() {
		return customerLoyaltyTxnRepository;
	}

	@Override
	protected ITextRepository<CustomerLoyaltyTxnEntity, Long> getTextRepository() {
		return customerLoyaltyTxnRepository;
	}

	@Override
	protected String getAlternateKeyAsString(CustomerLoyaltyTxnEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"customerLoyaltyTxn\" : \"").append(entity.getTxnCode()).append("\" }");

		return (buff.toString());
	}
	
	@Override
	public List<CustomerLoyaltyTxnEntity> findByCustomerCode(String customerCode){
		List<CustomerLoyaltyTxnEntity> listOfCustomerLoyaltyTxns = customerLoyaltyTxnRepository.findByCustomerCode(customerCode);
		return listOfCustomerLoyaltyTxns;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CustomerLoyaltyTxnEntity> findByCustProgCode(String customerCode, Date fromDate, Date toDate) {
		return customerLoyaltyTxnRepository.findByCustProgCode(customerCode, fromDate, toDate);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(CustomerLoyaltyTxnEntity entity, boolean valParent, EntityVdationType type)
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

			if (entity.getCustomerRef() != null) {
				CustomerEntity ce = entity.getCustomerRef();
				CustomerEntity ce2 = null;

				if (ce.getId() == null) {
					try {
						ce2 = customerRepository.findByCustomerCode(ce.getCustomerCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find Customer with customer code - " + ce.getCustomerCode());
					}
				} else {
					try {
						ce2 = customerRepository.findOne(ce.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find Customer with id - " + ce.getId());
					}
				}

				if (ce2 == null)
					throw new EntityValidationException("Could not find ProgramTier using either code or id !");

				entity.setCustomerRef(ce2);
				entity.setCustomerCode(ce2.getCustomerCode());
			}
		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	public boolean checkForUpdate(CustomerLoyaltyTxnEntity neu, CustomerLoyaltyTxnEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getCreditPoints(), old.getCreditPoints())
				|| !Objects.equals(neu.getCsLoyaltyCode(), old.getCsLoyaltyCode())
				|| !Objects.equals(neu.getCustomerCode(), old.getCustomerCode())
				|| !Objects.equals(neu.getDescription(), old.getDescription())
				|| !Objects.equals(neu.getTierName(), old.getTierName())
				|| !Objects.equals(neu.getTotalCreditPoints(), old.getTotalCreditPoints())
				|| !Objects.equals(neu.getTotalTxnValue(), old.getTotalTxnValue())
				|| !Objects.equals(neu.getTxnDate(), old.getTxnDate())
				|| !Objects.equals(neu.getTxnType(), old.getTxnType())
				|| !Objects.equals(neu.getTxnValue(), old.getTxnValue())
				|| !Objects.equals(neu.getValueDate(), old.getValueDate())) {
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

		if (neu.getCustomerRef() == null) {
			if (old.getCustomerRef() != null)
				result = true;
		} else {
			if (old.getCustomerRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getCustomerRef().getId(), old.getCustomerRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected CustomerLoyaltyTxnEntity doUpdate(CustomerLoyaltyTxnEntity neu, CustomerLoyaltyTxnEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getTxnCode(), old.getTxnCode()))
			throw new EntityValidationException("The customer loyalty txn does not match, existing - "
					+ old.getTxnCode() + " , new - " + neu.getTxnCode());
		if (!Objects.equals(neu.getTotalCreditPoints(), old.getTotalCreditPoints()))
			throw new EntityValidationException("The customer loyalty txn not allowed to update for TotalCreditPoints - "
					+ old.getTotalCreditPoints() + " , new - " + neu.getTotalCreditPoints());

		if (!Objects.equals(neu.getCreditPoints(), old.getCreditPoints()))
			throw new EntityValidationException("The customer loyalty txn not allowed to update for CreditPoints - "
					+ old.getCreditPoints() + " , new - " + neu.getCreditPoints());

		if (checkForUpdate(neu, old)) {
			old.setCreditPoints(neu.getCreditPoints());
			old.setCsLoyaltyCode(neu.getCsLoyaltyCode());
			old.setCsLoyaltyRef(neu.getCsLoyaltyRef());
			old.setCustomerCode(neu.getCustomerCode());
			old.setCustomerRef(neu.getCustomerRef());
			old.setDescription(neu.getDescription());
			old.setTierName(neu.getTierName());
			old.setTotalCreditPoints(neu.getTotalCreditPoints());
			old.setTotalTxnValue(neu.getTotalTxnValue());
			old.setTxnDate(neu.getTxnDate());
			old.setTxnType(neu.getTxnType());
			old.setTxnValue(neu.getTxnValue());
			old.setValueDate(neu.getValueDate());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	protected AbstractServiceImpl<CustomerLoyaltyTxnEntity, Long>.Outcome postProcess(int opCode,
			CustomerLoyaltyTxnEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<CustomerLoyaltyTxnEntity, Long>.Outcome preProcess(int opCode,
			CustomerLoyaltyTxnEntity entity) throws EntityValidationException {
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

	private void preProcessAdd(CustomerLoyaltyTxnEntity entity) throws EntityValidationException {
		List<CustomerLoyaltyTxnEntity> listOfTxns = findByCustomerCode(entity.getCustomerCode());
		if(!listOfTxns.isEmpty()){
			CustomerLoyaltyTxnEntity customerEntityTxnLatest = listOfTxns.get(0);
			entity.setTotalCreditPoints(customerEntityTxnLatest.getTotalCreditPoints().add(
					entity.getCreditPoints()));
		}
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(CustomerLoyaltyTxnEntity entity) throws EntityValidationException {
		/*if (!isPrivilegedContext())
			throw new EntityValidationException(
					"Cannot delete customerLoyaltyTxn when txn code is " + entity.getTxnCode());*/
	}
}
