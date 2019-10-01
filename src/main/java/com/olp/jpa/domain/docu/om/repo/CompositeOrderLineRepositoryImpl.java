package com.olp.jpa.domain.docu.om.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.om.model.CompositeOrderLineEntity;

@Repository("compositeOrderLineRepository")
public class CompositeOrderLineRepositoryImpl extends AbstractRepositoryImpl<CompositeOrderLineEntity, Long>
implements CompositeOrderLineRepository {

	@Override
	public CompositeOrderLineEntity findByCompOrderLine(String compOrderNum, int lineNum) {
		IContext ctx = ContextManager.getContext();
		TypedQuery<CompositeOrderLineEntity> query = getEntityManager()
				.createNamedQuery("CompositeOrderLineEntity.findByCompOrderLine", CompositeOrderLineEntity.class);
		query.setParameter("compOrderNum", compOrderNum);
		query.setParameter("lineNum", lineNum);
		CompositeOrderLineEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}

}
