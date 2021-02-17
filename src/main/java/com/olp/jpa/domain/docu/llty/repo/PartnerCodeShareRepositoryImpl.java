package com.olp.jpa.domain.docu.llty.repo;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.llty.model.PartnerCodeShareEntity;

@Repository("partnerCodeShareRepository")
public class PartnerCodeShareRepositoryImpl extends AbstractRepositoryImpl<PartnerCodeShareEntity, Long> implements PartnerCodeShareRepository {

	@Override
	@Transactional(readOnly = true)
	public List<PartnerCodeShareEntity> findByEffectiveDate(String partnerCode, Date date){
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<PartnerCodeShareEntity> query = getEntityManager()
				.createNamedQuery("LoyaltyCardEntity.findByEffectiveDate", PartnerCodeShareEntity.class);
		query.setParameter("partnerCode", partnerCode);
		query.setParameter("tenantId", tid);
		query.setParameter("date", date);
		List<PartnerCodeShareEntity> bean = query.getResultList();

		return (bean);
	}
	
	public List<PartnerCodeShareEntity> findByPartnerCode(String partnerCode){
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<PartnerCodeShareEntity> query = getEntityManager()
				.createNamedQuery("LoyaltyCardEntity.findByPartnerCode", PartnerCodeShareEntity.class);
		query.setParameter("partnerCode", partnerCode);
		query.setParameter("tenantId", tid);
		List<PartnerCodeShareEntity> bean = query.getResultList();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
