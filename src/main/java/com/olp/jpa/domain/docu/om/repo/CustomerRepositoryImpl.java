package com.olp.jpa.domain.docu.om.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;

@Repository("customerRepository")
public class CustomerRepositoryImpl  extends AbstractRepositoryImpl<CustomerEntity,Long>implements CustomerRepository{

	@Override
	@Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
	public CustomerEntity findByCustomerCode(String customerCode) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<CustomerEntity> query = getEntityManager().createNamedQuery("Customer.findByCode", CustomerEntity.class);
        query.setParameter("code", customerCode);
        CustomerEntity bean = query.getSingleResult();
        
        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}

}
