package com.olp.jpa.domain.docu.om.repo;

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
import com.olp.jpa.domain.docu.inv.model.ProductSkuEntity;
import com.olp.jpa.domain.docu.inv.repo.ProductSkuRepository;
import com.olp.jpa.domain.docu.om.model.OrderEnums.OrderLineStatus;
import com.olp.jpa.domain.docu.om.model.OrderEnums.OrderLineType;
import com.olp.jpa.domain.docu.om.model.OrderEnums.OrderStatus;
import com.olp.jpa.domain.docu.om.model.SalesOrderEntity;
import com.olp.jpa.domain.docu.om.model.SalesOrderLineEntity;
import com.olp.jpa.util.JpaUtil;

@Service("salesOrderLineService")
public class SalesOrderLineServiceImpl extends AbstractServiceImpl<SalesOrderLineEntity, Long>
		implements SalesOrderLineService {

	@Autowired
	@Qualifier("salesOrderLineRepository")
	private SalesOrderLineRepository salesOrderLineRepository;

	@Autowired
	@Qualifier("salesOrderRepository")
	private SalesOrderRepository salesOrderRepository;

	@Autowired
	@Qualifier("prodSkuRepository")
	private ProductSkuRepository productSkuRepository;

	@Override
	protected JpaRepository<SalesOrderLineEntity, Long> getRepository() {
		return salesOrderLineRepository;
	}

	@Override
	protected ITextRepository<SalesOrderLineEntity, Long> getTextRepository() {
		return salesOrderLineRepository;
	}

	@Override
	protected String getAlternateKeyAsString(SalesOrderLineEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"salesOrderLine\" : \"").append(entity.getOrderNumber()).append(entity.getPartNumber())
				.append(entity.getLineNumber()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public SalesOrderLineEntity findbyOrderLineNumber(String orderNumber, int partNumber, int lineNumber) {
		SalesOrderLineEntity entity = salesOrderLineRepository.findByOrderLineNumber(orderNumber, partNumber,
				lineNumber);
		return entity;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(SalesOrderLineEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (entity.getLineType() == OrderLineType.PROMOTION) {
			Float unitRate = entity.getUnitRate();
			if (unitRate != Float.valueOf(0)) {
				throw new EntityValidationException("UnitRate should be zero with code - " + entity.getOrderNumber());

			}
		}
		Float returnQuantity = entity.getReturnQuantity();
		Float orderQuantity = entity.getOrderQuantity();
		if (returnQuantity > orderQuantity) {
			throw new EntityValidationException(
					"returnQuantity cannot be greater than orderquantity with code - " + entity.getOrderNumber());

		}

		if (valParent) {
			if (entity.getOrderRef() != null) {

				// it is possible to have order soe null
				SalesOrderEntity soe = entity.getOrderRef(), soe2 = null;

				if (soe.getId() == null) {
					try {
						soe2 = salesOrderRepository.findbyOrderNumber(soe.getOrderNumber(), soe.getOrderPart());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find SalesOrder with code - "
								+ soe.getOrderNumber() + " " + soe.getOrderPart());
					}
				} else {
					try {
						soe2 = salesOrderRepository.findOne(soe.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find Sales Order with id - " + soe.getId());
					}
				}

				if (soe2 == null)
					throw new EntityValidationException("Could not find Sales Order using either ordernumber or id !");

				entity.setOrderRef(soe2);
				entity.setOrderNumber(soe.getOrderNumber());

			}

			if (entity.getProductSkuRef() != null) {

				// it is possible to have product skf null

				ProductSkuEntity pse = entity.getProductSkuRef(), pse2 = null;
				if (pse == null)
					throw new EntityValidationException("UnitOfMeasure reference cannot be null !!");

				if (pse.getId() == null) {
					try {
						pse2 = productSkuRepository.findBySkuCode(pse.getSkuCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find ProductSku with code - " + pse.getSkuCode());
					}
				} else {
					try {
						pse2 = productSkuRepository.findOne(pse.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find ProductSku with id - " + pse.getId());
					}
				}

				if (pse2 == null)
					throw new EntityValidationException("Could not find UnitOfMeasure using either code or id !");

				entity.setProductSkuRef(pse2);
				entity.setProductSkuCode(pse.getSkuCode());
			}

		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	protected SalesOrderLineEntity doUpdate(SalesOrderLineEntity neu, SalesOrderLineEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getOrderNumber(), old.getOrderNumber()))
			throw new EntityValidationException("The order number does not match, existing - " + old.getOrderNumber()
					+ " , new - " + neu.getOrderNumber());

		if (checkForUpdate(neu, old)) {
			old.setLineNumber(neu.getLineNumber());
			old.setLineStatus(neu.getLineStatus());
			old.setLineType(neu.getLineType());
			old.setOrderNumber(neu.getOrderNumber());
			old.setOrderQuantity(neu.getOrderQuantity());
			old.setOrderRef(neu.getOrderRef());
			old.setPartNumber(neu.getPartNumber());
			old.setProductSkuCode(neu.getProductSkuCode());
			old.setProductSkuRef(neu.getProductSkuRef());
			old.setReturnQuantity(neu.getReturnQuantity());
			old.setReturnStatus(neu.isReturnStatus());
			old.setUnitRate(neu.getUnitRate());
			old.setUom(neu.getUom());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(SalesOrderLineEntity neu, SalesOrderLineEntity old) {

		boolean result = false;

		if (!Objects.equals(neu.getLineNumber(), old.getLineNumber())
				|| !Objects.equals(neu.getLineStatus(), old.getLineStatus())
				|| !Objects.equals(neu.getLineType(), old.getLineType())
				|| !Objects.equals(neu.getOrderNumber(), old.getOrderNumber())
				|| !Objects.equals(neu.getOrderQuantity(), old.getOrderQuantity())
				|| !Objects.equals(neu.getPartNumber(), old.getPartNumber())
				|| !Objects.equals(neu.getProductSkuCode(), old.getProductSkuCode())
				|| !Objects.equals(neu.getReturnQuantity(), old.getReturnQuantity())
				|| !Objects.equals(neu.getUnitRate(), old.getUnitRate())
				|| !Objects.equals(neu.getUom(), old.getUom())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getOrderRef() == null) {
			if (old.getOrderRef() != null)
				result = true;
		} else {
			if (old.getOrderRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getOrderRef().getId(), old.getOrderRef().getId())) {
					result = true;
				}
			}
		}

		if (result)
			return (true);

		if (neu.getProductSkuRef() == null) {
			if (old.getProductSkuRef() != null)
				result = true;
		} else {
			if (old.getProductSkuRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getProductSkuRef().getId(), old.getProductSkuRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<SalesOrderLineEntity, Long>.Outcome postProcess(int opCode,
			SalesOrderLineEntity entity) throws EntityValidationException {
		Outcome result = new Outcome();
		result.setResult(true);

		switch (opCode) {
		case ADD:
		case ADD_BULK:
		case UPDATE:
		case UPDATE_BULK:
			postProcessAdd(entity);
			break;
		case DELETE:
		case DELETE_BULK:
		default:
			break;
		}

		return (result);
	}

	@Override
	protected AbstractServiceImpl<SalesOrderLineEntity, Long>.Outcome preProcess(int opCode,
			SalesOrderLineEntity entity) throws EntityValidationException {

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

	private void postProcessAdd(SalesOrderLineEntity entity) throws EntityValidationException {
		OrderLineStatus lineStatus = entity.getLineStatus();

		SalesOrderEntity soe = entity.getOrderRef();
		if (lineStatus != OrderLineStatus.RECEIVED) {
			if (soe != null) {
				soe.setOrderStatus(OrderStatus.PROCESSING);
			}
		}

		List<SalesOrderLineEntity> sols = soe.getOrderLines();
		boolean status = true;
		for (SalesOrderLineEntity sol : sols) {
			if (sol.getLineStatus() != OrderLineStatus.DELIVERED) {
				status = false;
				break;
			}
		}
		if (status) {
			soe.setOrderStatus(OrderStatus.FULFILLED);
		}

		this.updateTenantWithRevision(entity);
	}

	private void preProcessAdd(SalesOrderLineEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(SalesOrderLineEntity entity) throws EntityValidationException {
		//if (entity.getOrderRef() != null || entity.getOrderRef() != null) {
			/*if (!isPrivilegedContext())*/
				//throw new EntityValidationException("Cannot delete SalesOrderLine ");
		//}
	}

}
