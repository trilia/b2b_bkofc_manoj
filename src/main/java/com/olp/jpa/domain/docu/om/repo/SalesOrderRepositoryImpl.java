/**
 * 
 */
package com.olp.jpa.domain.docu.om.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.om.model.SalesOrderEntity;

/**
 * @author Jayesh
 *
 */
@Repository("salesOrderRepository")
public class SalesOrderRepositoryImpl extends AbstractRepositoryImpl<SalesOrderEntity,Long> implements SalesOrderRepository {

	@Override
	@Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
	public SalesOrderEntity findbyOrderNumber(String orderNumber, int partNumber) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<SalesOrderEntity> query = getEntityManager().createNamedQuery("SalesOrder.findByOrderNumber", SalesOrderEntity.class);
        query.setParameter("orderNumber", orderNumber); 
        query.setParameter("partNumber", partNumber);
        
        SalesOrderEntity bean = query.getSingleResult();
        
        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
