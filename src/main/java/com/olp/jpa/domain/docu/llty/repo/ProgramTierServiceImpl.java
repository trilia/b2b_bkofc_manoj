package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.LifecycleStatus;
import com.olp.jpa.domain.docu.llty.model.LoyaltyProgramEntity;
import com.olp.jpa.domain.docu.llty.model.ProgramTierEntity;
import com.olp.jpa.util.JpaUtil;

@Service("programTierService")
public class ProgramTierServiceImpl extends AbstractServiceImpl<ProgramTierEntity, Long> implements ProgramTierService {

	@Autowired
	@Qualifier("programTierRepository")
	private ProgramTierRepository programTierRepository;

	@Autowired
	@Qualifier("loyaltyProgramRepository")
	private LoyaltyProgramRepository loyaltyProgramRepository;

	@Override
	protected JpaRepository<ProgramTierEntity, Long> getRepository() {
		return programTierRepository;
	}

	@Override
	protected ITextRepository<ProgramTierEntity, Long> getTextRepository() {
		return programTierRepository;
	}
	
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ProgramTierEntity> findAllSequencesByProgramCode(String programCode){
		List<ProgramTierEntity> listOfProgramTiers = programTierRepository.findAllSequencesByProgramCode(programCode);
		return listOfProgramTiers;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(ProgramTierEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getProgramRef() != null) {
				// it is possible to have destination lp null
				LoyaltyProgramEntity lp = entity.getProgramRef(), lp2 = null;

				if (lp.getId() == null) {
					try {
						lp2 = loyaltyProgramRepository.findByProgramCode(lp.getProgramCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find LoyaltyProgram with code - " + lp.getProgramCode());
					}
				} else {
					try {
						lp2 = loyaltyProgramRepository.findOne(lp.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find LoyaltyProgram with id - " + lp.getId());
					}
				}

				if (lp2 == null)
					throw new EntityValidationException("Could not find LoyaltyProgram using either code or id !");

				entity.setProgramRef(lp2);
				entity.setProgramCode(lp2.getProgramCode());
			}
		}
		
		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	public boolean checkForUpdate(ProgramTierEntity neu, ProgramTierEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getDescription(), old.getDescription())
				|| !Objects.equals(neu.getEffectiveFrom(), old.getEffectiveFrom())
				|| !Objects.equals(neu.getEffectiveUpto(), old.getEffectiveUpto())
				|| !Objects.equals(neu.getProgramCode(), old.getProgramCode())
				|| !Objects.equals(neu.getSpendConvFormula(), old.getSpendConvFormula())
				|| !Objects.equals(neu.getSpendConvProcess(), old.getSpendConvProcess())
				|| !Objects.equals(neu.getSpendConvRate(), old.getSpendConvRate())
				|| !Objects.equals(neu.getTierCode(), old.getTierCode())
				|| !Objects.equals(neu.getTierName(), old.getTierName())
				|| !Objects.equals(neu.getTierPointFrom(), old.getTierPointFrom())
				|| !Objects.equals(neu.getTierPointUpto(), old.getTierPointUpto())
				|| !Objects.equals(neu.getTierSequence(), old.getTierSequence())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getProgramRef() == null) {
			if (old.getProgramRef() != null)
				result = true;
		} else {
			if (old.getProgramRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getProgramRef().getId(), old.getProgramRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}
	
	@Override
	protected ProgramTierEntity doUpdate(ProgramTierEntity neu, ProgramTierEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getTierCode(), old.getTierCode()))
			throw new EntityValidationException("The programtier does not match, existing - "
					+ old.getTierCode() + " , new - " + neu.getTierCode());

		if (!Objects.equals(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		if (checkForUpdate(neu, old)) {
			old.setDescription(neu.getDescription());
			old.setEffectiveFrom(neu.getEffectiveFrom());
			old.setEffectiveUpto(neu.getEffectiveUpto());
			old.setProgramCode(neu.getProgramCode());
			old.setProgramRef(neu.getProgramRef());
			old.setSpendConvFormula(neu.getSpendConvFormula());
			old.setSpendConvProcess(neu.getSpendConvProcess());
			old.setSpendConvRate(neu.getSpendConvRate());
			old.setTierName(neu.getTierName());
			old.setTierPointFrom(neu.getTierPointFrom());
			old.setTierPointUpto(neu.getTierPointUpto());
			old.setTierSequence(neu.getTierSequence());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public ProgramTierEntity findByTierCode( String tierCode) {
		return programTierRepository.findByTierCode( tierCode);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public ProgramTierEntity findByTierSequence(String programCode, int sequence) {
		return programTierRepository.findByTierSequence(programCode, sequence);
	}

	@Override
	protected String getAlternateKeyAsString(ProgramTierEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"programTier\" : \"").append(entity.getTierCode()).append("\" }");
		return (buff.toString());
	}

	@Override
	protected AbstractServiceImpl<ProgramTierEntity, Long>.Outcome postProcess(int opCode, ProgramTierEntity entity)
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
	protected AbstractServiceImpl<ProgramTierEntity, Long>.Outcome preProcess(int opCode, ProgramTierEntity entity)
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
			preProcessUpdate(entity);
			validate(entity, true, EntityVdationType.PRE_UPDATE);
		case DELETE:
		case DELETE_BULK:
			preDelete(entity);
		default:
			break;
		}

		return (result);
	}

	private void preProcessAdd(ProgramTierEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
		List<ProgramTierEntity> listOfProgramTiers = findAllSequencesByProgramCode(entity.getProgramCode());
		if(!listOfProgramTiers.isEmpty()){
			ProgramTierEntity programTierDb = listOfProgramTiers.get(0);
			entity.setTierSequence(Math.addExact(programTierDb.getTierSequence(),10));
		}else {
			entity.setTierSequence(10);
		}
		try{
			ProgramTierEntity programTierExists = findByTierCode(/*entity.getProgramCode(),*/ entity.getTierCode());
			if(!StringUtils.isEmpty(programTierExists)){
				if(!entity.getEffectiveFrom().after(programTierExists.getEffectiveUpto())){
					//throw new EntityValidationException("There should not be overlap in tierPointFrom and tierPointTo");
				}
				if(!entity.getEffectiveUpto().after(entity.getEffectiveFrom())){
					//throw new EntityValidationException("There should not be overlap in tierPointFrom and tierPointTo");
				}
			}
		}catch(javax.persistence.NoResultException ex){
			//NO results
		}
		entity.setLifecycleStatus(LifecycleStatus.INACTIVE);
	}
	
	private void preProcessUpdate(ProgramTierEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_UPDATE);
		this.updateTenantWithRevision(entity);
		List<ProgramTierEntity> listOfProgramTiers = findAllSequencesByProgramCode(entity.getProgramCode());
		if(!listOfProgramTiers.isEmpty()){
			ProgramTierEntity programTierDb = listOfProgramTiers.get(0);
			entity.setTierSequence(Math.addExact(programTierDb.getTierSequence(),10));
		}else {
			entity.setTierSequence(10);
		}
		try{
			ProgramTierEntity programTierExists = findByTierCode(entity.getTierCode());
			if(!StringUtils.isEmpty(programTierExists)){
				if(!entity.getEffectiveFrom().after(programTierExists.getEffectiveUpto())){
					//throw new EntityValidationException("There should not be overlap in tierPointFrom and tierPointTo");
				}
				if(!entity.getEffectiveUpto().after(entity.getEffectiveFrom())){
					//throw new EntityValidationException("There should not be overlap in tierPointFrom and tierPointTo");
				}
			}
		}catch(javax.persistence.NoResultException ex){
			//NO results
		}
		entity.setLifecycleStatus(LifecycleStatus.INACTIVE);
	}

	private void preDelete(ProgramTierEntity entity) throws EntityValidationException {
		/*if (entity.getProgramRef() != null) {
			LoyaltyProgramEntity loyaltyProgram = entity.getProgramRef();
			if (!isPrivilegedContext() && loyaltyProgram.getLifecycleStatus() != LifecycleStatus.INACTIVE)
				throw new EntityValidationException(
						"Cannot delete programtier when programtier status is " + loyaltyProgram.getLifecycleStatus());
		}*/
	}

}
