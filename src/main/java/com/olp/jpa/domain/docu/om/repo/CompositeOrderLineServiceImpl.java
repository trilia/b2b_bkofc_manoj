package com.olp.jpa.domain.docu.om.repo;

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
import com.olp.jpa.domain.docu.be.model.MerchantEntity;
import com.olp.jpa.domain.docu.be.repo.MerchantRepository;
import com.olp.jpa.domain.docu.om.model.CompositeOrderEntity;
import com.olp.jpa.domain.docu.om.model.CompositeOrderLineEntity;
import com.olp.jpa.domain.docu.om.model.SalesOrderEntity;
import com.olp.jpa.util.JpaUtil;

@Service("compositeOrderLineService")
public class CompositeOrderLineServiceImpl extends AbstractServiceImpl<CompositeOrderLineEntity, Long>
		implements CompositeOrderLineService {

	@Autowired
	@Qualifier("compositeOrderLineRepository")
	private CompositeOrderLineRepository compositeOrderLineRepository;

	@Autowired
	@Qualifier("compositeOrderRepository")
	private CompositeOrderRepository compositeOrderRepository;

	@Autowired
	@Qualifier("merchantRepository")
	private MerchantRepository merchantRepository;

	@Autowired
	@Qualifier("salesOrderRepository")
	private SalesOrderRepository salesOrderRepository;

	@Override
	protected JpaRepository<CompositeOrderLineEntity, Long> getRepository() {
		return compositeOrderLineRepository;
	}

	@Override
	protected ITextRepository<CompositeOrderLineEntity, Long> getTextRepository() {
		return compositeOrderLineRepository;
	}

	@Override
	protected String getAlternateKeyAsString(CompositeOrderLineEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"compositeorderline\" : \"").append(entity.getCompOrderNum()).append("\" }");
		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public CompositeOrderLineEntity findByCompOrderLine(String compOrderNum, int lineNum) {
		return compositeOrderLineRepository.findByCompOrderLine(compOrderNum, lineNum);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(CompositeOrderLineEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getCompOrderRef() != null) {
				// it is possible to have destination compositeOrder null
				CompositeOrderEntity co = entity.getCompOrderRef(), co2 = null;

				if (co.getId() == null) {
					try {
						co2 = compositeOrderRepository.findByCompOrderNum(co.getCompOrderNum());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find CompositeOrder with name - " + co.getCompOrderNum());
					}
				} else {
					try {
						co2 = compositeOrderRepository.findOne(co.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find CompositeOrder with id - " + co.getId());
					}
				}

				if (co2 == null)
					throw new EntityValidationException("Could not find CompositeOrder using either code or id !");

				entity.setCompOrderRef(co2);
				entity.setCompOrderNum(co2.getCompOrderNum());
			}

			// check for merchant
			if (entity.getMerchantRef() != null) {
				// it is possible to have destination compositeOrder null
				MerchantEntity me = entity.getMerchantRef(), me2 = null;

				if (me.getId() == null) {
					try {
						me2 = merchantRepository.findByMerchantCode(me.getMerchantCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find Merchant with code - " + me.getMerchantCode());
					}
				} else {
					try {
						me2 = merchantRepository.findOne(me.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find Merchant with id - " + me.getId());
					}
				}

				if (me2 == null)
					throw new EntityValidationException("Could not find Merchant using either code or id !");

				entity.setMerchantRef(me2);
				entity.setMerchTenantId(me2.getTenantId());
			}

			// check for salesOrder
			if (entity.getSalesOrderRef() != null) {
				// it is possible to have destination compositeOrder null
				SalesOrderEntity so = entity.getSalesOrderRef(), so2 = null;

				if (so.getId() == null) {
					try {
						so2 = salesOrderRepository.findbyOrderNumber(so.getOrderNumber(), so.getOrderPart());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find SalesOrder with code - " + so.getOrderNumber());
					}
				} else {
					try {
						so2 = salesOrderRepository.findOne(so.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find SalesOrder with id - " + so.getId());
					}
				}

				if (so2 == null)
					throw new EntityValidationException("Could not find SalesOrder using either code or id !");

				entity.setSalesOrderRef(so2);
				entity.setSalesOrderNum(so2.getOrderNumber());
			}

			if (EntityVdationType.PRE_UPDATE == type) {
				JpaUtil.updateRevisionControl(entity, true);
			}
		}
	}

	@Override
	protected CompositeOrderLineEntity doUpdate(CompositeOrderLineEntity neu, CompositeOrderLineEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getLineNum(), old.getLineNum()))
			throw new EntityValidationException("The compOrderLine linenum does not match, existing - "
					+ old.getLineNum() + " , new - " + neu.getLineNum());

		if (checkForUpdate(neu, old)) {
			old.setCompOrderNum(neu.getCompOrderNum());
			old.setMerchTenantId(neu.getMerchTenantId());
			old.setOrderStatus(neu.getOrderStatus());
			old.setSalesOrderNum(neu.getSalesOrderNum());
			old.setCompOrderRef(neu.getCompOrderRef());
			old.setMerchantRef(neu.getMerchantRef());
			old.setSalesOrderRef(neu.getSalesOrderRef());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(CompositeOrderLineEntity neu, CompositeOrderLineEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getCompOrderNum(), old.getCompOrderNum())
				|| !Objects.equals(neu.getMerchTenantId(), old.getMerchTenantId())
				|| !Objects.equals(neu.getOrderStatus(), old.getOrderStatus())
				|| !Objects.equals(neu.getSalesOrderNum(), old.getSalesOrderNum())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getCompOrderRef() == null) {
			if (old.getCompOrderRef() != null)
				result = true;
		} else {
			if (old.getCompOrderRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getCompOrderRef().getId(), old.getCompOrderRef().getId())) {
					result = true;
				}
			}
		}
		if (result)
			return (true);

		if (neu.getMerchantRef() == null) {
			if (old.getMerchantRef() != null)
				result = true;
		} else {
			if (old.getMerchantRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getMerchantRef().getId(), old.getMerchantRef().getId())) {
					result = true;
				}
			}
		}
		if (result)
			return (true);

		if (neu.getSalesOrderRef() == null) {
			if (old.getSalesOrderRef() != null)
				result = true;
		} else {
			if (old.getSalesOrderRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getSalesOrderRef().getId(), old.getSalesOrderRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<CompositeOrderLineEntity, Long>.Outcome postProcess(int opCode,
			CompositeOrderLineEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<CompositeOrderLineEntity, Long>.Outcome preProcess(int opCode,
			CompositeOrderLineEntity entity) throws EntityValidationException {
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

	private void preProcessAdd(CompositeOrderLineEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
	}

	private void preDelete(CompositeOrderLineEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext())
			throw new EntityValidationException("Cannot delete compositeOrderLine ");

	}
}
