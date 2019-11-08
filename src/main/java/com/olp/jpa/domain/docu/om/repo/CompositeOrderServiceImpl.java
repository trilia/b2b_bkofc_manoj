package com.olp.jpa.domain.docu.om.repo;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.olp.jpa.domain.docu.om.model.CompositeOrderEntity;
import com.olp.jpa.domain.docu.om.model.CompositeOrderLineEntity;
import com.olp.jpa.util.JpaUtil;

@Service("compositeOrderService")
public class CompositeOrderServiceImpl extends AbstractServiceImpl<CompositeOrderEntity, Long>
		implements CompositeOrderService {

	@Autowired
	@Qualifier("compositeOrderRepository")
	private CompositeOrderRepository compositeOrderRepository;

	@Autowired
	@Qualifier("compositeOrderLineService")
	private CompositeOrderLineService compositeOrderLineService;

	@Override
	protected JpaRepository<CompositeOrderEntity, Long> getRepository() {
		return compositeOrderRepository;
	}

	@Override
	protected ITextRepository<CompositeOrderEntity, Long> getTextRepository() {
		return compositeOrderRepository;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public CompositeOrderEntity findByCompOrderNum(String compOrderNum) {
		return compositeOrderRepository.findByCompOrderNum(compOrderNum);
	}

	@Override
	protected String getAlternateKeyAsString(CompositeOrderEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"compositeorder\" : \"").append(entity.getCompOrderNum()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(CompositeOrderEntity entity, EntityVdationType type) throws EntityValidationException {
		List<CompositeOrderLineEntity> compositeOrderLines = entity.getOrderLines();
		if (compositeOrderLines != null && !compositeOrderLines.isEmpty()) {
			for (CompositeOrderLineEntity compositeOrderLine : compositeOrderLines) {
				compositeOrderLine.setCompOrderRef(entity);
				compositeOrderLine.setCompOrderNum(entity.getCompOrderNum());
				compositeOrderLineService.validate(compositeOrderLine, false, type);
			}
		}

	}

	@Override
	protected AbstractServiceImpl<CompositeOrderEntity, Long>.Outcome postProcess(int opCode,
			CompositeOrderEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<CompositeOrderEntity, Long>.Outcome preProcess(int opCode,
			CompositeOrderEntity entity) throws EntityValidationException {
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

	@Override
	protected CompositeOrderEntity doUpdate(CompositeOrderEntity neu, CompositeOrderEntity old)
			throws EntityValidationException {

		if (!old.getCompOrderNum().equals(neu.getCompOrderNum())) {
			throw new EntityValidationException("CompositeOrderEntity cannot be updated ! Existing - "
					+ old.getCompOrderNum() + ", new - " + neu.getCompOrderNum());
		}

		// validate(neu, EntityVdationType.PRE_UPDATE);

		List<Long> deletedOrderLines = new ArrayList<Long>();

		if (old.getOrderLines() != null && !old.getOrderLines().isEmpty()) {
			for (CompositeOrderLineEntity oldOrderLine : old.getOrderLines()) {
				boolean found = false;
				if (neu.getOrderLines() != null && !neu.getOrderLines().isEmpty()) {
					for (CompositeOrderLineEntity newOrderLine : neu.getOrderLines()) {
						if (Objects.equals(newOrderLine.getId(), oldOrderLine.getId())) {
							found = true;
							break;
						}
					} // end for new orderLines
				}
				if (!found) {
					// orderLines deleted
					deletedOrderLines.add(oldOrderLine.getId());
				}
			}
		}

		boolean updated = false;
		if (!deletedOrderLines.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			if (neu.getOrderLines() != null && !neu.getOrderLines().isEmpty()) {
				updateOrderLines(neu, old); // end for
											// neu.getOrderLines
			} // end if neu.getOrderLines != null

			if (!deletedOrderLines.isEmpty()) {
				// dissociate CompOrderLines
				deleteOrderLines(old, deletedOrderLines);
			} // end if deletedCompOrderLines not empty

			old.setCompOrderDate(neu.getCompOrderDate());
			old.setCompOrderStatus(neu.getCompOrderStatus());
			old.setOrderValue(neu.getOrderValue());
			old.setPaymentMethod(neu.getPaymentMethod());
			old.setPaymentStatus(neu.getPaymentStatus());
			JpaUtil.updateRevisionControl(old, true);

		}
		return (old);
	}

	private void preProcessAdd(CompositeOrderEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		String compOrderNum = Long.toHexString(Double.doubleToLongBits(Math.random()));
		entity.setCompOrderNum(compOrderNum);
	}

	private void preDelete(CompositeOrderEntity entity) throws EntityValidationException {
		/*if (!isPrivilegedContext())
			throw new EntityValidationException("Cannot delete compositeorder when state is ");
	*/}

	private void updateOrderLines(CompositeOrderEntity neu, CompositeOrderEntity old) throws EntityValidationException {
		for (CompositeOrderLineEntity newCompositeOrderLine : neu.getOrderLines()) {
			CompositeOrderLineEntity oldCompositeOrderLine2 = null;
			boolean found = false;
			if (old.getOrderLines() != null && !old.getOrderLines().isEmpty()) {
				for (CompositeOrderLineEntity oldCompositeOrderLine : old.getOrderLines()) {
					if (Objects.equals(newCompositeOrderLine.getId(), oldCompositeOrderLine.getId())) {
						oldCompositeOrderLine2 = oldCompositeOrderLine;
						found = true;
						break;
					}
				} // end for old.getCompositeOrderLine
			} // end if old.getCompositeOrderLine

			if (!found) {
				// new CompositeOrderLine being added
				newCompositeOrderLine.setCompOrderRef(old);
				newCompositeOrderLine.setCompOrderNum(old.getCompOrderNum());
				compositeOrderLineService.validate(newCompositeOrderLine, false, EntityVdationType.PRE_INSERT);
				old.getOrderLines().add(newCompositeOrderLine);
			} else {
				boolean compOrderLineUpdated = compositeOrderLineService.checkForUpdate(newCompositeOrderLine,
						oldCompositeOrderLine2);
				if (compOrderLineUpdated) {
					compositeOrderLineService.update(newCompositeOrderLine);
				}
			}
		}
	}

	private void deleteOrderLines(CompositeOrderEntity old, List<Long> deletedCompOrderLines) {
		Iterator<CompositeOrderLineEntity> compOrderLinesEntityIter = old.getOrderLines().iterator();
		for (Long id : deletedCompOrderLines) {
			boolean found = false;
			CompositeOrderLineEntity oldCompOrderLine = null;
			while (compOrderLinesEntityIter.hasNext()) {
				oldCompOrderLine = compOrderLinesEntityIter.next();
				if (Objects.equals(id, oldCompOrderLine.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				compositeOrderLineService.delete(id);
			}
		}
	}

	public boolean checkForUpdate(CompositeOrderEntity neu, CompositeOrderEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getCompOrderDate(), old.getCompOrderDate())
				|| !Objects.equals(neu.getCompOrderStatus(), old.getCompOrderStatus())
				|| !Objects.equals(neu.getOrderValue(), old.getOrderValue())
				|| !Objects.equals(neu.getPaymentMethod(), old.getPaymentMethod())
				|| !Objects.equals(neu.getPaymentStatus(), old.getPaymentStatus())) {
			result = true;
			return (result);
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
					result = checkForOrderLines(neu.getOrderLines(), old.getOrderLines());
					return (result);
				}
			}
		}

		return result;
	}

	private boolean checkForOrderLines(List<CompositeOrderLineEntity> newOrderLines,
			List<CompositeOrderLineEntity> oldOrderLines) {

		boolean result = false;
		Iterator<CompositeOrderLineEntity> oldOrderLinesEntityIter = oldOrderLines.iterator();
		Iterator<CompositeOrderLineEntity> newOrderLinesEntityIter = newOrderLines.iterator();
		CompositeOrderLineEntity oldOrderLine = null;
		CompositeOrderLineEntity newOrderLine = null;

		while (newOrderLinesEntityIter.hasNext()) {
			newOrderLine = newOrderLinesEntityIter.next();
			Long newEntityId = newOrderLine.getId();
			if (newEntityId != null) {
				while (oldOrderLinesEntityIter.hasNext()) {
					oldOrderLine = oldOrderLinesEntityIter.next();
					Long oldEntityId = oldOrderLine.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = compositeOrderLineService.checkForUpdate(newOrderLine, oldOrderLine);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = compositeOrderLineService.checkForUpdate(newOrderLine, oldOrderLine);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

}
