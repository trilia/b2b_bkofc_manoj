package com.olp.jpa.domain.docu.llty.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.llty.model.LoyaltyPartnerEntity;

@Repository("loyaltyPartnerRepository")
public class LoyaltyPartnerRepositoryImpl extends AbstractRepositoryImpl<LoyaltyPartnerEntity, Long>
implements LoyaltyPartnerRepository {

	@Override
	@Transactional(readOnly = true)
	public LoyaltyPartnerEntity findByPartnerCode(String partnerCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<LoyaltyPartnerEntity> query = getEntityManager()
				.createNamedQuery("LoyaltyPartnerEntity.findByPartnerCode", LoyaltyPartnerEntity.class);
		query.setParameter("partnerCode", partnerCode);
		query.setParameter("tenantId", tid);
		LoyaltyPartnerEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
