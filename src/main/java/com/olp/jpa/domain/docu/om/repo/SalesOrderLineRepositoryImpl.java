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
import com.olp.jpa.domain.docu.om.model.SalesOrderLineEntity;

/**
 * @author Jayesh
 *
 */
@Repository("salesOrderLineRepository")
public class SalesOrderLineRepositoryImpl extends AbstractRepositoryImpl<SalesOrderLineEntity,Long> implements SalesOrderLineRepository {

	@Override
	@Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
	public SalesOrderLineEntity findByOrderLineNumber(String orderNumber, int partNumber, int lineNumber) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<SalesOrderLineEntity> query = getEntityManager().createNamedQuery("SalesOrderLine.findByOrderLineNumber", SalesOrderLineEntity.class);
        query.setParameter("orderNumber", orderNumber); 
        query.setParameter("partNumber", partNumber);
        query.setParameter("lineNumber", lineNumber);
        
        SalesOrderLineEntity bean = query.getSingleResult();
        
        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}
}
