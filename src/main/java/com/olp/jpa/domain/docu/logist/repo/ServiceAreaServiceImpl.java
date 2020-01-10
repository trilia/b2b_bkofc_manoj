package com.olp.jpa.domain.docu.logist.repo;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.logist.model.LogisticsEnum.Country;
import com.olp.jpa.domain.docu.logist.model.ServiceAreaEntity;
import com.olp.jpa.util.JpaUtil;

@Service("serviceAreaSvc")
public class ServiceAreaServiceImpl extends AbstractServiceImpl<ServiceAreaEntity, Long> implements ServiceAreaService {

	@Autowired
	@Qualifier("serviceAreaRepository")
	private ServiceAreaRepository serviceAreaRepository;

	@Override
	protected JpaRepository<ServiceAreaEntity, Long> getRepository() {
		return serviceAreaRepository;
	}

	@Override
	protected ITextRepository<ServiceAreaEntity, Long> getTextRepository() {
		return serviceAreaRepository;
	}

	@Override
	public List<ServiceAreaEntity> findBySvcClassCode(String svcClassCode) {
		return serviceAreaRepository.findBySvcClassCode(svcClassCode);
	}

	public boolean isServiceable(String postalCode, Country country) throws EntityValidationException {
		boolean isServiceable = false;
		List<ServiceAreaEntity> serviceAreas = serviceAreaRepository.findByCountry(country);
		for (ServiceAreaEntity serviceArea : serviceAreas) {
			isServiceable = false;
			if (postalCode != null) {
				String postalCodeFrom = serviceArea.getPostalCodeFrom();
				String postalCodeTo = serviceArea.getPostalCodeTo();

				Set<String> postalCodeExceptList = serviceArea.getPostalCodeExcept();
				if (postalCodeFrom != null && postalCodeTo != null) {
					try {
						int postalCodeFromInt = Integer.parseInt(postalCodeFrom);
						int postalCodeToInt = Integer.parseInt(postalCodeTo);
						int postalCodeInt = Integer.parseInt(postalCode);
						if (postalCodeInt >= postalCodeFromInt && postalCodeInt <= postalCodeToInt
								&& postalCodeExceptList.contains(postalCode) == false) {
							isServiceable = true;
						}

					} catch (Exception e) {
						throw new EntityValidationException("Exception in isServiceable ");
					}
				}
			}
		}

		return isServiceable;
	}

	@Override
	protected String getAlternateKeyAsString(ServiceAreaEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"ServiceArea\" : \"").append(entity.getSvcClassCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	public void validate(ServiceAreaEntity entity, EntityVdationType type) throws EntityValidationException {
		List<ServiceAreaEntity> listOfServiceAreas = serviceAreaRepository.findByPartnerId(entity.getPartnerId());
		for (ServiceAreaEntity serviceArea : listOfServiceAreas) {
			String postalCodeFrom = serviceArea.getPostalCodeFrom();
			String postalCodeTo = serviceArea.getPostalCodeTo();

			int postalCodeFromEachRow = Integer.parseInt(postalCodeFrom);
			int postalCodeToEachRow = Integer.parseInt(postalCodeTo);

			int inputPostalCodeFrom = Integer.parseInt(entity.getPostalCodeFrom());
			int inputPostalCodeTo = Integer.parseInt(entity.getPostalCodeTo());

			if (inputPostalCodeFrom < postalCodeFromEachRow || inputPostalCodeTo > postalCodeToEachRow) {
				throw new EntityValidationException(
						"ServiceArea validation Error ! Existing - " + entity.getPartnerId());
			}
		}

		if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	protected ServiceAreaEntity doUpdate(ServiceAreaEntity neu, ServiceAreaEntity old)
			throws EntityValidationException {

		if (!old.getSvcClassCode().equals(neu.getSvcClassCode())) {
			throw new EntityValidationException("ServiceArea cannot be updated ! Existing - " + old.getSvcClassCode()
					+ ", new - " + neu.getSvcClassCode());
		}

		boolean updated = checkForUpdate(neu, old);

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
		} // end if deletedLovValues not empty

		old.setPartnerId(neu.getPartnerId());
		old.setCountry(neu.getCountry());
		old.setPostalCodeExcept(neu.getPostalCodeExcept());
		old.setPostalCodeFrom(neu.getPostalCodeFrom());
		old.setPostalCodeTo(neu.getPostalCodeTo());
		old.setSvcClassRef(neu.getSvcClassRef());
		old.setStateOrCounty(neu.getStateOrCounty());

		JpaUtil.updateRevisionControl(old, true);
		// }

		return (old);
	}

	private boolean checkForUpdate(ServiceAreaEntity neu, ServiceAreaEntity old) {
		boolean result = false;
		if (!Objects.equals(neu.getPartnerId(), old.getPartnerId())
				|| !Objects.equals(neu.getCountry(), old.getCountry())
				|| !Objects.equals(neu.getPostalCodeFrom(), old.getPostalCodeFrom())
				|| !Objects.equals(neu.getPostalCodeTo(), old.getPostalCodeTo())
				|| !Objects.equals(neu.getStateOrCounty(), old.getStateOrCounty())
				|| !Objects.equals(neu.getSvcClassCode(), old.getSvcClassCode())
				|| !Objects.equals(neu.getSvcClassRef(), old.getSvcClassRef())) {
			result = true;
		}
		return result;
	}

	@Override
	protected AbstractServiceImpl<ServiceAreaEntity, Long>.Outcome postProcess(int opCode, ServiceAreaEntity entity)
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
	protected AbstractServiceImpl<ServiceAreaEntity, Long>.Outcome preProcess(int opCode, ServiceAreaEntity entity)
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

	private void preProcessAdd(ServiceAreaEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
	}

	private void preDelete(ServiceAreaEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext() && entity.getLifeCycleStatus() != LifeCycleStatus.ACTIVE) {// TODO
																								// -
																								// new
																								// status
																								// in
																								// enum
			throw new EntityValidationException("Cannot delete ServiceArea. when state is "
					+ entity.getLifeCycleStatus() + ".Only TERMINATION possible");
		}
	}
}
