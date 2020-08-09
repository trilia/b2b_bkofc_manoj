package com.olp.jpa.domain.docu.llty.repo;

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
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyEntity;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTierEntity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.ParticipationStatus;
import com.olp.jpa.domain.docu.llty.model.ProgramTierEntity;
import com.olp.jpa.util.JpaUtil;

@Service("customerLoyaltyTierService")
public class CustomerLoyaltyTierServiceImpl extends AbstractServiceImpl<CustomerLoyaltyTierEntity, Long>
		implements CustomerLoyaltyTierService {

	@Autowired
	@Qualifier("customerLoyaltyTierRepository")
	private CustomerLoyaltyTierRepository customerLoyaltyTierRepository;

	@Autowired
	@Qualifier("customerLoyaltyRepository")
	private CustomerLoyaltyRepository customerLoyaltyRepository;

	@Autowired
	@Qualifier("programTierRepository")
	private ProgramTierRepository programTierRepository;

	@Override
	protected JpaRepository<CustomerLoyaltyTierEntity, Long> getRepository() {
		return customerLoyaltyTierRepository;
	}

	@Override
	protected ITextRepository<CustomerLoyaltyTierEntity, Long> getTextRepository() {
		return customerLoyaltyTierRepository;
	}

	@Override
	protected String getAlternateKeyAsString(CustomerLoyaltyTierEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"customerLoyaltyTier\" : \"").append(entity.getCsLoyaltyTierCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(CustomerLoyaltyTierEntity entity, boolean valParent, EntityVdationType type)
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

			if (entity.getProgramTierRef() != null) {
				ProgramTierEntity pt = entity.getProgramTierRef();
				ProgramTierEntity pt2 = null;

				if (pt.getId() == null) {
					try {
						pt2 = programTierRepository.findByTierCode(pt.getProgramCode(), pt.getTierCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find ProgramTier with program code - " + pt.getProgramCode());
					}
				} else {
					try {
						pt2 = programTierRepository.findOne(pt.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find ProgramTier with id - " + pt.getId());
					}
				}

				if (pt2 == null)
					throw new EntityValidationException("Could not find ProgramTier using either code or id !");

				entity.setProgramTierRef(pt2);
				entity.setProgramTierCode(pt2.getProgramCode());
			}

		}

		if (EntityVdationType.PRE_INSERT == type) {
			entity.setStatus(ParticipationStatus.ACTIVE);
			entity.setEndDate(null);
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CustomerLoyaltyTierEntity> findByCustTierCode(String customerCode, String programCode,
			String tierCode) {
		return customerLoyaltyTierRepository.findByCustTierCode(customerCode, programCode, tierCode);
	}

	@Override
	public boolean checkForUpdate(CustomerLoyaltyTierEntity neu, CustomerLoyaltyTierEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getCsLoyaltyCode(), old.getCsLoyaltyCode())
				|| !Objects.equals(neu.getCustomerCode(), old.getCustomerCode())
				|| !Objects.equals(neu.getEndDate(), old.getEndDate())
				|| !Objects.equals(neu.getProgramTierCode(), old.getProgramTierCode())
				|| !Objects.equals(neu.getStartDate(), old.getStartDate())
				|| !Objects.equals(neu.getStatus(), old.getStatus())
				|| !Objects.equals(neu.getTierCode(), old.getTierCode())) {
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

		if (neu.getProgramTierRef() == null) {
			if (old.getProgramTierRef() != null)
				result = true;
		} else {
			if (old.getProgramTierRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getProgramTierRef().getId(), old.getProgramTierRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected CustomerLoyaltyTierEntity doUpdate(CustomerLoyaltyTierEntity neu, CustomerLoyaltyTierEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getCsLoyaltyTierCode(), old.getCsLoyaltyTierCode()))
			throw new EntityValidationException("The customer loyalty tier does not match, existing - "
					+ old.getCsLoyaltyTierCode() + " , new - " + neu.getCsLoyaltyTierCode());

		if (checkForUpdate(neu, old)) {
			old.setCsLoyaltyCode(neu.getCsLoyaltyCode());
			old.setCsLoyaltyRef(neu.getCsLoyaltyRef());
			old.setCustomerCode(neu.getCustomerCode());
			old.setEndDate(neu.getEndDate());
			old.setProgramTierCode(neu.getProgramTierCode());
			old.setProgramTierRef(neu.getProgramTierRef());
			old.setStartDate(neu.getStartDate());
			old.setStatus(neu.getStatus());
			old.setTierCode(neu.getTierCode());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	protected AbstractServiceImpl<CustomerLoyaltyTierEntity, Long>.Outcome postProcess(int opCode,
			CustomerLoyaltyTierEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<CustomerLoyaltyTierEntity, Long>.Outcome preProcess(int opCode,
			CustomerLoyaltyTierEntity entity) throws EntityValidationException {
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

	private void preProcessAdd(CustomerLoyaltyTierEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(CustomerLoyaltyTierEntity entity) throws EntityValidationException {
		if (entity.getCsLoyaltyRef() != null) {
			CustomerLoyaltyEntity customerLoyalty = entity.getCsLoyaltyRef();
			if (!isPrivilegedContext())
				throw new EntityValidationException(
						"Cannot delete CustomerLoyaltyTier when CustomerLoyaltyTierEntity status is "
								+ customerLoyalty.getStatus());
		}
	}

}
