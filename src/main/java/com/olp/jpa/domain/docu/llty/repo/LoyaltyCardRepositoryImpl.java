package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.llty.model.LoyaltyCardEntity;

@Repository("loyaltyCardRepository")
public class LoyaltyCardRepositoryImpl extends AbstractRepositoryImpl<LoyaltyCardEntity, Long>
		implements LoyaltyCardRepository {

	@Override
	@Transactional(readOnly = true)
	public LoyaltyCardEntity findByCustomerCode(String custCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<LoyaltyCardEntity> query = getEntityManager()
				.createNamedQuery("LoyaltyCardEntity.findByCustomerCode", LoyaltyCardEntity.class);
		query.setParameter("custCode", custCode);
		query.setParameter("tenantId", tid);
		LoyaltyCardEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	@Transactional(readOnly = true)
	public List<LoyaltyCardEntity> findAllByCustomerCode(String custCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<LoyaltyCardEntity> query = getEntityManager()
				.createNamedQuery("LoyaltyCardEntity.findAllByCustomerCode", LoyaltyCardEntity.class);
		query.setParameter("custCode", custCode);
		query.setParameter("tenantId", tid);
		List<LoyaltyCardEntity> bean = query.getResultList();
		return bean;
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}

}
