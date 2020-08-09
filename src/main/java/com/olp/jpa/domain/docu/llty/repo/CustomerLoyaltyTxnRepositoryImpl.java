package com.olp.jpa.domain.docu.llty.repo;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTxnEntity;

@Repository("customerLoyaltyTxnRepository")
public class CustomerLoyaltyTxnRepositoryImpl extends AbstractRepositoryImpl<CustomerLoyaltyTxnEntity, Long> implements CustomerLoyaltyTxnRepository {
	
	@Override
	@Transactional(readOnly=true)
	public List<CustomerLoyaltyTxnEntity> findByCustProgCode(String customerCode, Date fromDate, Date toDate) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<CustomerLoyaltyTxnEntity> query = getEntityManager().createNamedQuery("CustomerLoyaltyTxnEntity.findByCustProgCode", CustomerLoyaltyTxnEntity.class);
        query.setParameter("customerCode", customerCode);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("tenant", tid);
        List<CustomerLoyaltyTxnEntity> bean = query.getResultList();
        
        return(bean);
	}
	

	@Override
	public String getLazyLoadElements() {
		return null;
	}


}
