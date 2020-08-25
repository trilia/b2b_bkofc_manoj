package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyEntity;

@Repository("customerLoyaltyRepository")
public class CustomerLoyaltyRepositoryImpl extends AbstractRepositoryImpl<CustomerLoyaltyEntity, Long> implements CustomerLoyaltyRepository {

	@Override
	@Transactional(readOnly=true)
	public List<CustomerLoyaltyEntity> findByCustProgCode(String customerCode, String programCode) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<CustomerLoyaltyEntity> query = getEntityManager().createNamedQuery("CustomerLoyaltyEntity.findByCustProgCode", CustomerLoyaltyEntity.class);
        query.setParameter("customerCode", customerCode);
        query.setParameter("programCode", programCode);
        query.setParameter("tenant", tid);
        List<CustomerLoyaltyEntity> bean = query.getResultList();
        
        return(bean);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<CustomerLoyaltyEntity> findByCustomerCode(String customerCode){
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<CustomerLoyaltyEntity> query = getEntityManager().createNamedQuery("CustomerLoyaltyEntity.findByCustProgCode", CustomerLoyaltyEntity.class);
        query.setParameter("customerCode", customerCode);
        query.setParameter("tenant", tid);
        List<CustomerLoyaltyEntity> bean = query.getResultList();
        
        return(bean);
	}
	
	@Override
	public String getLazyLoadElements() {
		return null;
	}
}
