package com.olp.jpa.domain.docu.llty.repo;

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
import com.olp.jpa.domain.docu.llty.model.LoyaltyPartnerEntity;
import com.olp.jpa.domain.docu.llty.model.PartnerValidityEntity;
import com.olp.jpa.util.JpaUtil;

@Service("loyaltyPartnerService")
public class LoyaltyPartnerServiceImpl extends AbstractServiceImpl<LoyaltyPartnerEntity, Long>
		implements LoyaltyPartnerService {

	@Autowired
	@Qualifier("loyaltyPartnerRepository")
	private LoyaltyPartnerRepository loyaltyPartnerRepository;

	@Autowired
	@Qualifier("partnerValidityService")
	private PartnerValidityService partnerValidityService;

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public LoyaltyPartnerEntity findByPartnerCode(String partnerCode) {
		return loyaltyPartnerRepository.findByPartnerCode(partnerCode);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LoyaltyPartnerEntity entity, EntityVdationType type) throws EntityValidationException {
		List<PartnerValidityEntity> listOfPartnerValidityEntity = entity.getPartnerValidities();
		if (listOfPartnerValidityEntity != null && !listOfPartnerValidityEntity.isEmpty()) {
			for (PartnerValidityEntity partnerValidityEntity : listOfPartnerValidityEntity) {
				partnerValidityEntity.setPartnerRef(entity);
				partnerValidityEntity.setPartnerCode(entity.getPartnerCode());
				partnerValidityService.validate(partnerValidityEntity, false, type);
			}
		}

		if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	protected String getAlternateKeyAsString(LoyaltyPartnerEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"loyaltyPartner\" : \"").append(entity.getPartnerCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	protected JpaRepository<LoyaltyPartnerEntity, Long> getRepository() {
		return loyaltyPartnerRepository;
	}

	@Override
	protected ITextRepository<LoyaltyPartnerEntity, Long> getTextRepository() {
		return loyaltyPartnerRepository;
	}

	@Override
	protected AbstractServiceImpl<LoyaltyPartnerEntity, Long>.Outcome postProcess(int opCode,
			LoyaltyPartnerEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<LoyaltyPartnerEntity, Long>.Outcome preProcess(int opCode,
			LoyaltyPartnerEntity entity) throws EntityValidationException {
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
	protected LoyaltyPartnerEntity doUpdate(LoyaltyPartnerEntity neu, LoyaltyPartnerEntity old)
			throws EntityValidationException {
		if (!old.getPartnerCode().equals(neu.getPartnerCode())) {
			throw new EntityValidationException("LoyaltyPartner cannot be updated ! Existing - " + old.getPartnerCode()
					+ ", new - " + neu.getPartnerCode());
		}

		List<Long> deletedPartnerValidities = new ArrayList<Long>();

		if (old.getPartnerValidities() != null && !old.getPartnerValidities().isEmpty()) {
			for (PartnerValidityEntity oldPartnerValidity : old.getPartnerValidities()) {
				boolean found = false;
				if (neu.getPartnerValidities() != null && !neu.getPartnerValidities().isEmpty()) {
					for (PartnerValidityEntity newPartnervalidity : neu.getPartnerValidities()) {
						if (Objects.equals(newPartnervalidity.getId(), oldPartnerValidity.getId())) {
							found = true;
							break;
						}
					} // end for new PartnerValidity
				}
				if (!found) {
					// PartnerValidity deleted
					if (isPrivilegedContext()) {
						deletedPartnerValidities.add(oldPartnerValidity.getId());
					} else {
						throw new EntityValidationException(
								"Cannot delete PartnerValidities  " + oldPartnerValidity.getPartnerCode());
					}
				}
			}
		}

		boolean updated = false;
		if (!deletedPartnerValidities.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			if (neu.getPartnerValidities() != null && !neu.getPartnerValidities().isEmpty()) {
				updatePartnerValidities(neu, old); // end for
				// neu.getPartnerValidities
			} // end if neu.getPartnerValidities != null

			if (!deletedPartnerValidities.isEmpty()) {
				// dissociate getPartnerValidities
				deletePartnerValidities(old, deletedPartnerValidities);
			} // end if getPartnerValidities not empty

			old.setLocationCode(neu.getLocationCode());
			old.setLocationRef(neu.getLocationRef());

			if (neu.getLocationRef() != null) {
				old.getLocationRef().setAddressLine1(neu.getLocationRef().getAddressLine1());
				old.getLocationRef().setAddressLine2(neu.getLocationRef().getAddressLine2());
				old.getLocationRef().setAddressLine3(neu.getLocationRef().getAddressLine3());
				old.getLocationRef().setAddressLine4(neu.getLocationRef().getAddressLine4());
				old.getLocationRef().setCity(neu.getLocationRef().getCity());
				old.getLocationRef().setCountry(neu.getLocationRef().getCountry());
				old.getLocationRef().setEndDate(neu.getLocationRef().getEndDate());
				old.getLocationRef().setLocationSource(neu.getLocationRef().getLocationSource());
				old.getLocationRef().setLocationType(neu.getLocationRef().getLocationType());
				old.getLocationRef().setStartDate(neu.getLocationRef().getStartDate());
				old.getLocationRef().setStateOrProvince(neu.getLocationRef().getStateOrProvince());
				old.getLocationRef().setZipCode(neu.getLocationRef().getZipCode());
			}
			old.setPartnerName(neu.getPartnerName());
			old.setRevisionControl(neu.getRevisionControl());
			JpaUtil.updateRevisionControl(old, true);
		}
		return (old);
	}

	private void updatePartnerValidities(LoyaltyPartnerEntity neu, LoyaltyPartnerEntity old)
			throws EntityValidationException {
		for (PartnerValidityEntity newPartnerValidity : neu.getPartnerValidities()) {
			PartnerValidityEntity oldPartnerValidity2 = null;
			boolean found = false;
			if (old.getPartnerValidities() != null && !old.getPartnerValidities().isEmpty()) {
				for (PartnerValidityEntity oldPartnerValidity : old.getPartnerValidities()) {
					if (Objects.equals(newPartnerValidity.getId(), oldPartnerValidity.getId())) {
						oldPartnerValidity2 = oldPartnerValidity;
						found = true;
						break;
					}
				} // end for old.getPartnerValidities
			} // end if old.getPartnerValidities

			if (!found) {
				// new partnerValidity being added
				newPartnerValidity.setPartnerRef(old);
				newPartnerValidity.setPartnerCode(old.getPartnerCode());
				newPartnerValidity.setTenantId(getTenantId());

				partnerValidityService.validate(newPartnerValidity, false, EntityVdationType.PRE_INSERT);
				old.getPartnerValidities().add(newPartnerValidity);
			} else {
				boolean customerLoyaltyTierUpdated = partnerValidityService.checkForUpdate(newPartnerValidity,
						oldPartnerValidity2);
				if (customerLoyaltyTierUpdated) {
					partnerValidityService.update(newPartnerValidity);
				}
			}
		}
	}

	private void deletePartnerValidities(LoyaltyPartnerEntity old, List<Long> deletedPartnerValidityEntities) {
		Iterator<PartnerValidityEntity> partnerValidityEntityIter = old.getPartnerValidities().iterator();
		for (Long id : deletedPartnerValidityEntities) {
			boolean found = false;
			PartnerValidityEntity oldPartnerValidity = null;
			while (partnerValidityEntityIter.hasNext()) {
				oldPartnerValidity = partnerValidityEntityIter.next();
				if (Objects.equals(id, oldPartnerValidity.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				old.getPartnerValidities().remove(oldPartnerValidity);
				partnerValidityService.delete(id);
			}
		}
	}

	private boolean checkForUpdate(LoyaltyPartnerEntity neu, LoyaltyPartnerEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getLocationCode(), old.getLocationCode())
				|| !Objects.equals(neu.getPartnerName(), old.getPartnerName())
				|| !Objects.equals(neu.getRevisionControl(), old.getRevisionControl())) {

			result = true;
			return (result);
		}

		if (neu.getPartnerValidities() == null || neu.getPartnerValidities().isEmpty()) {
			if (old.getPartnerValidities() != null && !old.getPartnerValidities().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getPartnerValidities() == null || old.getPartnerValidities().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getPartnerValidities().size(), old.getPartnerValidities().size())) {
					result = true;
					return (result);
				} else {
					result = checkForPartnerValidities(neu.getPartnerValidities(), old.getPartnerValidities());
					return (result);
				}
			}
		}

		if (neu.getLocationRef() == null) {
			if (old.getLocationRef() != null) {
				result = true;
				return (result);
			}
		} else {
			if (old.getLocationRef() == null) {
				result = true;
				return (result);
			} else {
				// both not null

				if (neu.getLocationRef().getId() != null) {
					if (!Objects.equals(neu.getLocationRef().getId(), old.getLocationRef().getId())) {
						result = true;
						return (result);
					}
				}

				// this location is tied to a specific warehouse. Hence updates
				// to location is done as part of
				// LoyaltyPartner update. Therefore checking individual fields
				// of location for update

				if (!Objects.equals(neu.getLocationRef().getLocationAlias(), old.getLocationRef().getLocationAlias())
						|| !Objects.equals(neu.getLocationRef().getLocationSource(),
								old.getLocationRef().getLocationSource())
						|| !Objects.equals(neu.getLocationRef().getLocationType(),
								old.getLocationRef().getLocationType())
						|| !Objects.equals(neu.getLocationRef().getStartDate(), old.getLocationRef().getStartDate())
						|| !Objects.equals(neu.getLocationRef().getEndDate(), old.getLocationRef().getEndDate())
						|| !Objects.equals(neu.getLocationRef().getStateOrProvince(),
								old.getLocationRef().getStateOrProvince())
						|| !Objects.equals(neu.getLocationRef().getAddressLine1(),
								old.getLocationRef().getAddressLine1())
						|| !Objects.equals(neu.getLocationRef().getAddressLine2(),
								old.getLocationRef().getAddressLine2())
						|| !Objects.equals(neu.getLocationRef().getAddressLine3(),
								old.getLocationRef().getAddressLine3())
						|| !Objects.equals(neu.getLocationRef().getAddressLine4(),
								old.getLocationRef().getAddressLine4())
						|| !Objects.equals(neu.getLocationRef().getCity(), old.getLocationRef().getCity())
						|| !Objects.equals(neu.getLocationRef().getZipCode(), old.getLocationRef().getZipCode())
						|| !Objects.equals(neu.getLocationRef().getCountry(), old.getLocationRef().getCountry())) {
					result = true;
					return (result);
				}
			}
		}

		return result;
	}

	private boolean checkForPartnerValidities(List<PartnerValidityEntity> newPartnerValidities,
			List<PartnerValidityEntity> oldPartnerValidities) {

		boolean result = false;
		Iterator<PartnerValidityEntity> oldPartnerValidityEntityTier = oldPartnerValidities.iterator();
		Iterator<PartnerValidityEntity> newPartnerValidityEntityTier = newPartnerValidities.iterator();
		PartnerValidityEntity oldPartnerValidity = null;
		PartnerValidityEntity newPartnerValidity = null;

		while (newPartnerValidityEntityTier.hasNext()) {
			newPartnerValidity = newPartnerValidityEntityTier.next();
			Long newEntityId = newPartnerValidity.getId();
			if (newEntityId != null) {
				while (oldPartnerValidityEntityTier.hasNext()) {
					oldPartnerValidity = oldPartnerValidityEntityTier.next();
					Long oldEntityId = oldPartnerValidity.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = partnerValidityService.checkForUpdate(newPartnerValidity, oldPartnerValidity);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = partnerValidityService.checkForUpdate(newPartnerValidity, oldPartnerValidity);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

	private void preProcessAdd(LoyaltyPartnerEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(LoyaltyPartnerEntity entity) throws EntityValidationException {
		if (entity.getLocationRef() != null) {
			if (!isPrivilegedContext()) {
				throw new EntityValidationException("Cannot delete LoyaltyPartnerEntity. when state is "
						+ entity.getPartnerCode() + ".Only TERMINATION possible");
			}
		}
	}

}
