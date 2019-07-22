package com.olp.jpa.domain.docu.om.repo;

import java.util.ArrayList;
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
import com.olp.jpa.domain.docu.om.model.OrderEnums.DeliveryType;
import com.olp.jpa.domain.docu.om.model.OrderEnums.OrderStatus;
import com.olp.jpa.domain.docu.om.model.SalesOrderEntity;
import com.olp.jpa.domain.docu.om.model.SalesOrderLineEntity;
import com.olp.jpa.util.JpaUtil;

@Service("salesOrderService")
public class SalesOrderServiceImpl extends AbstractServiceImpl<SalesOrderEntity, Long> implements SalesOrderService {

	@Autowired
	@Qualifier("salesOrderRepository")
	private SalesOrderRepository salesOrderRepository;

	@Autowired
	@Qualifier("salesOrderLineService")
	private SalesOrderLineService salesOrderLineService;

	@Autowired
	@Qualifier("customerRepository")
	private CustomerRepository customerRepository;

	@Override
	protected JpaRepository<SalesOrderEntity, Long> getRepository() {
		return salesOrderRepository;
	}

	@Override
	protected ITextRepository<SalesOrderEntity, Long> getTextRepository() {
		return salesOrderRepository;
	}

	@Override
	protected String getAlternateKeyAsString(SalesOrderEntity salesOrderEntity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"salesOrder\" : \"").append(salesOrderEntity.getOrderNumber()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public SalesOrderEntity findbyOrderNumber(String orderNumber, int partNumber) {
		SalesOrderEntity salesOrderEntity = salesOrderRepository.findbyOrderNumber(orderNumber, partNumber);

		return salesOrderEntity;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(SalesOrderEntity entity, EntityVdationType type) throws EntityValidationException {

		Date deliveryByDate = entity.getDeliverByDate();
		if (entity.getDeliveryType() == DeliveryType.COMMITTED) {
			if (deliveryByDate == null) {
				throw new EntityValidationException(
						"SalesOrder deliveryByDate cannot be null for DeliveryType commited for "
								+ entity.getOrderNumber());
			}

		}
		Date orderDate = entity.getOrderDate();
		if (deliveryByDate != null) {
			if (deliveryByDate.before(orderDate)) {
				throw new EntityValidationException(
						"SalesOrder deliveryByDate cannot be before orderDate for " + entity.getOrderNumber());

			}
		}

		List<SalesOrderLineEntity> orderLines = entity.getOrderLines();
		if (orderLines != null && !orderLines.isEmpty()) {
			for (SalesOrderLineEntity orderLine : orderLines) {
				orderLine.setOrderRef(entity);
				orderLine.setOrderNumber(entity.getOrderNumber());
				salesOrderLineService.validate(orderLine, false, type);
			}
		}

		if (entity.getParentOrderRef() != null) {
			// it is possible to have destination uom null
			SalesOrderEntity soeParent = entity.getParentOrderRef(), soeParent2 = null;

			if (soeParent.getId() == null) {
				try {
					soeParent2 = findbyOrderNumber(soeParent.getOrderNumber(), soeParent.getOrderPart());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find Sales Order ParentOrderRef with code - " + soeParent.getOrderNumber());
				}
			} else {
				try {
					soeParent2 = salesOrderRepository.findOne(soeParent.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find Sales Order ParentOrderRef with id - " + soeParent.getOrderNumber());
				}
			}

			if (soeParent2 == null)
				throw new EntityValidationException(
						"Could not find Sales Order ParentOrderRef using either code or id !");

			entity.setParentOrderRef(soeParent2);
			entity.setOrderNumber(soeParent2.getOrderNumber());

		}

		if (entity.getCustomerRef() != null) {
			// it is possible to have source customer null

			CustomerEntity coe = entity.getCustomerRef(), coe2 = null;
			if (coe == null)
				throw new EntityValidationException("Customer reference cannot be null !!");

			if (coe.getId() == null) {
				try {
					coe2 = customerRepository.findByCustomerCode(coe.getCustomerCode());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find CustomerEntity with code - " + coe.getCustomerCode());
				}
			} else {
				try {
					coe2 = customerRepository.findOne(coe.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find Customer with id - " + coe.getId());
				}
			}

			if (coe2 == null)
				throw new EntityValidationException("Could not find Customer using either code or id !");
			entity.setCustomerRef(coe2);
			entity.setCustomerCode(coe2.getCustomerCode());
		}

		// TODO -check if no lifecycle stil we need to update revison control ?
		if (EntityVdationType.PRE_INSERT == type) {
			if (entity.getOrderStatus() != OrderStatus.RECEIVED) {
				throw new EntityValidationException(
						"Could not create Sales Order with the OrderStatus with OrderNumber - "
								+ entity.getOrderNumber());
			}
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	protected SalesOrderEntity doUpdate(SalesOrderEntity neu, SalesOrderEntity old) throws EntityValidationException {

		if (!old.getOrderNumber().equals(neu.getOrderNumber())) {
			throw new EntityValidationException("SalesOrderEntity cannot be updated ! Existing - "
					+ old.getOrderNumber() + ", new - " + neu.getOrderNumber());
		}

		if (!old.getOrderNumber().equals(neu.getOrderNumber()) || old.getOrderPart() != neu.getOrderPart()) {
			throw new EntityValidationException(
					"SalesOrderEntity cannot be updated ! Existing - " + old.getOrderNumber() + " " + old.getOrderPart()
							+ ", new - " + neu.getOrderNumber() + " " + neu.getOrderPart());
		}

		ArrayList<Long> deletedSalesOrderLines = new ArrayList<>();

		if (old.getOrderLines() != null && !old.getOrderLines().isEmpty()) {
			for (SalesOrderLineEntity oldSol : old.getOrderLines()) {
				boolean foundSalesLine = false;

				if (neu.getOrderLines() != null && !neu.getOrderLines().isEmpty()) {
					for (SalesOrderLineEntity newSol : neu.getOrderLines()) {
						if (Objects.equals(newSol.getId(), oldSol.getId())) {
							foundSalesLine = true;
							break;
						}
					} // end for SalesOrderLines
				}
				if (!foundSalesLine) {
					deletedSalesOrderLines.add(oldSol.getId());
				}

			} // end for old Sales order line
		}

		boolean updated = false;

		if (!deletedSalesOrderLines.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			if (isPrivilegedContext()) {
				// carry out the update
				if (neu.getOrderLines() != null && !neu.getOrderLines().isEmpty()) {
					for (SalesOrderLineEntity newOrderLines : neu.getOrderLines()) {
						SalesOrderLineEntity oldOrderLine2 = null;
						boolean found = false;
						if (old.getOrderLines() != null && !old.getOrderLines().isEmpty()) {
							for (SalesOrderLineEntity oldOrderLine : old.getOrderLines()) {
								if (Objects.equals(newOrderLines.getId(), oldOrderLine.getId())) {
									oldOrderLine2 = oldOrderLine;
									found = true;
									break;
								}
							} // end for old.OrderLines
						} // end if old.OrderLines

						if (!found) {
							// new OrderLines being added
							newOrderLines.setOrderRef(old);
							newOrderLines.setOrderNumber(old.getOrderNumber());
							newOrderLines.setRevisionControl(getRevisionControl());
							newOrderLines.setTenantId(getTenantId());

							salesOrderLineService.validate(newOrderLines, false, EntityVdationType.PRE_INSERT);
							old.getOrderLines().add(newOrderLines);
						} else {
							boolean zoneUpdated = salesOrderLineService.checkForUpdate(newOrderLines, oldOrderLine2);
							if (zoneUpdated) {
								salesOrderLineService.update(newOrderLines);
							}
						}

					} // end for neu.SalesOrderLine
				} // end if neu.SalesOrderLine != null

				if (!deletedSalesOrderLines.isEmpty()) {
					// dissociate sales Order Line
					for (Long id : deletedSalesOrderLines) {
						int pos = -1;
						for (int i = 0; i < old.getOrderLines().size(); i++) {
							SalesOrderLineEntity oldZone = old.getOrderLines().get(i);

							if (Objects.equals(id, oldZone.getId())) {
								pos = i;
								break;
							}
						}
						if (pos > -1) {
							old.getOrderLines().remove(pos);
							salesOrderLineService.delete(id);
						}
					}
				}

				old.setCustomerCode(neu.getCustomerCode());
				old.setCustomerRef(neu.getCustomerRef());
				old.setDeliverByDate(neu.getDeliverByDate());
				old.setDeliveryType(neu.getDeliveryType());
				old.setOrderDate(neu.getOrderDate());
				old.setOrderNumber(neu.getOrderNumber());
				old.setOrderPart(neu.getOrderPart());
				old.setOrderSource(neu.getOrderSource());
				old.setOrderStatus(neu.getOrderStatus());
				old.setOrderType(neu.getOrderType());
				old.setParentOrderRef(neu.getParentOrderRef());
				old.setParentOrderNum(neu.getParentOrderNum());
				old.setShippingAddress(neu.getShippingAddress());

				JpaUtil.updateRevisionControl(old, true);

			}
		}

		return (old);
	}

	private boolean checkForUpdate(SalesOrderEntity neu, SalesOrderEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getCustomerCode(), old.getCustomerCode())
				|| !Objects.equals(neu.getDeliverByDate(), old.getDeliverByDate())
				|| !Objects.equals(neu.getDeliveryType(), old.getDeliveryType())
				|| !Objects.equals(neu.getOrderDate(), old.getOrderDate())
				|| !Objects.equals(neu.getOrderNumber(), old.getOrderNumber())
				|| neu.getOrderPart() != old.getOrderPart()
				|| !Objects.equals(neu.getOrderStatus(), old.getOrderStatus())
				|| !Objects.equals(neu.getOrderSource(), old.getOrderSource())
				|| !Objects.equals(neu.getOrderType(), old.getOrderType())
				|| !Objects.equals(neu.getParentOrderNum(), old.getParentOrderNum())
				|| !Objects.equals(neu.getShippingAddress(), old.getShippingAddress())) {

			result = true;
			return (result);
		}

		if (neu.getParentOrderRef() == null) {
			if (old.getParentOrderRef() != null) {
				result = true;
				return (result);
			}
		} else {
			if (old.getParentOrderRef() == null) {
				result = true;
				return (result);
			} else {
				// both not null
				if (neu.getParentOrderRef().getId() == null) {
					if (!Objects.equals(neu.getParentOrderRef().getOrderNumber(),
							old.getParentOrderRef().getOrderNumber())) {
						result = true;
						return (result);
					}
				} else {
					if (!Objects.equals(neu.getParentOrderRef().getId(), old.getParentOrderRef().getId())) {
						result = true;
						return (result);
					}
				}
			}
		}

		if (neu.getCustomerRef() == null) {
			if (old.getCustomerRef() != null) {
				result = true;
				return (result);
			}
		} else {
			if (old.getCustomerRef() == null) {
				result = true;
				return (result);
			} else {
				// both not null
				if (neu.getCustomerRef().getId() == null) {
					if (!Objects.equals(neu.getCustomerRef().getCustomerCode(),
							old.getCustomerRef().getCustomerCode())) {
						result = true;
						return (result);
					}
				} else {
					if (!Objects.equals(neu.getCustomerRef().getId(), old.getCustomerRef().getId())) {
						result = true;
						return (result);
					}
				}
			}
		}

		if (neu.getOrderLines() == null || neu.getOrderLines().isEmpty()) {
			if (old.getOrderLines() != null && !old.getOrderLines().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getOrderLines() == null || old.getOrderLines().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getOrderLines().size(), old.getOrderLines().size())) {
					result = true;
					return (result);
				} else {
					result = checkForUpdateConversions(neu, old);
					return (result);
				}
			}
		}

		return result;
	}

	private boolean checkForUpdateConversions(SalesOrderEntity neu, SalesOrderEntity old) {

		boolean result = false;

		for (int i = 0; i < neu.getOrderLines().size(); i++) {
			SalesOrderLineEntity newOrderLines = neu.getOrderLines().get(i), oldOrderLines = old.getOrderLines().get(i);
			if (newOrderLines.getId() != null) {
				if (!Objects.equals(newOrderLines.getId(), oldOrderLines.getId())) {
					result = true;
					return (result);
				}
			} else {
				boolean outcome = salesOrderLineService.checkForUpdate(newOrderLines, oldOrderLines);
				if (!outcome) {
					result = true;
					return (result);
				}
			}
		} // end for
		return result;
	}

	@Override
	protected AbstractServiceImpl<SalesOrderEntity, Long>.Outcome postProcess(int opCode, SalesOrderEntity entity)
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
	protected AbstractServiceImpl<SalesOrderEntity, Long>.Outcome preProcess(int opCode, SalesOrderEntity entity)
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
			validate(entity, EntityVdationType.PRE_UPDATE);
		case DELETE:
		case DELETE_BULK:
			preDelete(entity);
			break;
		default:
			break;
		}

		return (result);
	}

	private void preProcessAdd(SalesOrderEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(SalesOrderEntity entity) throws EntityValidationException {
		/*if (!isPrivilegedContext())
			throw new EntityValidationException("Cannot delete SalesOrder ");*/
	}

}
