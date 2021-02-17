package com.olp.jpa.domain.docu.llty.repo;

import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.llty.model.PartnerValidityEntity;

@Repository("partnerValidityRepository")
public class PartnerValidityRepositoryImpl extends AbstractRepositoryImpl<PartnerValidityEntity, Long>
implements PartnerValidityRepository {

	@Override
	@Transactional(readOnly = true)
	public PartnerValidityEntity findByEffectiveDate(String partnerCode, Date date) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<PartnerValidityEntity> query = getEntityManager()
				.createNamedQuery("PartnerValidityEntity.findByEffectiveDate", PartnerValidityEntity.class);
		query.setParameter("partnerCode", partnerCode);
		query.setParameter("date", date);
		query.setParameter("tenantId", tid);
		PartnerValidityEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PartnerValidityEntity> findByPartnerCode(String partnerCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<PartnerValidityEntity> query = getEntityManager()
				.createNamedQuery("PartnerValidityEntity.findByPartnerCode", PartnerValidityEntity.class);
		query.setParameter("partnerCode", partnerCode);
		query.setParameter("tenantId", tid);
		List<PartnerValidityEntity> bean = query.getResultList();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
