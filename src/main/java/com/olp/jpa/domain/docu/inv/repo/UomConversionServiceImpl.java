package com.olp.jpa.domain.docu.inv.repo;

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
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasureEntity;
import com.olp.jpa.domain.docu.inv.model.UomConversionEntity;
import com.olp.jpa.util.JpaUtil;

@Service("uomConversionService")
public class UomConversionServiceImpl extends AbstractServiceImpl<UomConversionEntity, Long>
		implements UomConversionService {

	@Autowired
	@Qualifier("uomConversionRepository")
	private UomConversionRepository uomConversionRepo;

	@Autowired
	@Qualifier("unitOfMeasureRepository")
	private UnitOfMeasureRepository uomUnitOfMeasureRepo;

	@Override
	protected JpaRepository<UomConversionEntity, Long> getRepository() {
		return uomConversionRepo;
	}

	@Override
	protected ITextRepository<UomConversionEntity, Long> getTextRepository() {
		return uomConversionRepo;
	}

	@Override
	protected String getAlternateKeyAsString(UomConversionEntity uomConversionEntity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"unitOfMeasure\" : \"").append(uomConversionEntity.getEntrySequence()).append("\" }");
		// todo -what to do if multiple alt key
		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(UomConversionEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		
		if (valParent) {
			
			if (entity.getDestUomRef() != null) {
				
				// it is possible to have destination uom null
				UnitOfMeasureEntity uom = entity.getDestUomRef(), uom2 = null;
				
				if (uom.getId() == null) {
					try {
						uom2 = uomUnitOfMeasureRepo.findByUomCode(uom.getUomCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find UnitOfMeasure with code - " + uom.getUomCode());
					}
				} else {
					try {
						uom2 = uomUnitOfMeasureRepo.findOne(uom.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find UnitOfMeasure with id - " + uom.getId());
					}
				}

				if (uom2 == null)
					throw new EntityValidationException("Could not find UnitOfMeasure using either code or id !");

				entity.setDestUomRef(uom2);
				entity.setDestUomCode(uom.getUomCode());
				
			}
			
			if (entity.getSrcUomRef() != null) {
				
				// it is possible to have source uom null
				
				UnitOfMeasureEntity uom = entity.getSrcUomRef(), uom2 = null;
				if (uom == null)
					throw new EntityValidationException("UnitOfMeasure reference cannot be null !!");

				if (uom.getId() == null) {
					try {
						uom2 = uomUnitOfMeasureRepo.findByUomCode(uom.getUomCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find UnitOfMeasure with code - " + uom.getUomCode());
					}
				} else {
					try {
						uom2 = uomUnitOfMeasureRepo.findOne(uom.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find UnitOfMeasure with id - " + uom.getId());
					}
				}

				if (uom2 == null)
					throw new EntityValidationException("Could not find UnitOfMeasure using either code or id !");

				entity.setSrcUomRef(uom2);
				entity.setSrcUomCode(uom.getUomCode());
			}
			

		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}
	
	@Override
	protected UomConversionEntity doUpdate(UomConversionEntity neu, UomConversionEntity old) throws EntityValidationException {
		
		if (!Objects.equals(neu.getEntrySequence(), old.getEntrySequence()))
			throw new EntityValidationException("The entry sequence does not match, existing - " + old.getEntrySequence() + " , new - " + neu.getEntrySequence());
		
		if (!Objects.equals(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}
		
		if (checkForUpdate(neu, old)) {
			old.setConvFactor(neu.getConvFactor());
			old.setConvFunction(neu.getConvFunction());
			old.setDestUomCode(neu.getDestUomCode());
			old.setDestUomRef(neu.getDestUomRef());
			old.setSrcUomCode(neu.getSrcUomCode());
			old.setSrcUomRef(neu.getSrcUomRef());
			
			JpaUtil.updateRevisionControl(old, true);
		}
		
		return(old);
	}

	@Override
	public boolean checkForUpdate(UomConversionEntity neu, UomConversionEntity old) {
		// TODO Auto-generated method stub
		
		boolean result = false;
		
		if (!Objects.equals(neu.getConvFactor(), old.getConvFactor()) ||
				!Objects.equals(neu.getConvFunction(), old.getConvFunction()) ||
				!Objects.equals(neu.getEntrySequence(), old.getEntrySequence()) ||
				!Objects.equals(neu.getLifecycleStatus(), old.getLifecycleStatus())
				) {
			result = true;
		}
		
		if (result)
			return(true);
		
		if (neu.getDestUomRef() == null) {
			if (old.getDestUomRef() != null)
				result = true;
		} else {
			if (old.getDestUomRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getDestUomRef().getId(), old.getDestUomRef().getId())) {
					result = true;
				}
			}
		}
		
		if (result)
			return(true);
		
		if (neu.getSrcUomRef() == null) {
			if (old.getSrcUomRef() != null)
				result = true;
		} else {
			if (old.getSrcUomRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getSrcUomRef().getId(), old.getSrcUomRef().getId())) {
					result = true;
				}
			}
		}
		
		return result;
	}

	@Override
	protected Outcome postProcess(int opCode, UomConversionEntity arg1)
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
	protected Outcome preProcess(int opCode, UomConversionEntity entity)
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
	public List<UomConversionEntity> findBySrcUom(String srcUomCode) {
		List<UomConversionEntity> uomConversionEntityList = uomConversionRepo.findBySrcUom(srcUomCode);
		return uomConversionEntityList;
	}

	@Override
	public List<UomConversionEntity> findByDestUom(String destUomCode) {
		List<UomConversionEntity> uomConversionEntityList = uomConversionRepo.findByDestUom(destUomCode);
		return uomConversionEntityList;
	}

	@Override
	public UomConversionEntity findBySrcTarget(String srcUomCode, String destUomCode) {
		UomConversionEntity uomConversionEntity = uomConversionRepo.findBySrcTarget(srcUomCode, destUomCode);
		return uomConversionEntity;
	}

	private void preProcessAdd(UomConversionEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(UomConversionEntity entity) throws EntityValidationException {
		if (entity.getDestUomRef() != null || entity.getSrcUomRef() != null) {
			UnitOfMeasureEntity uom = entity.getDestUomRef();
			if (!isPrivilegedContext() && uom.getLifecycleStatus() != LifeCycleStatus.INACTIVE)
				throw new EntityValidationException(
						"Cannot delete uom when unitOfstatus status is " + uom.getLifecycleStatus());
		}
	}
}
