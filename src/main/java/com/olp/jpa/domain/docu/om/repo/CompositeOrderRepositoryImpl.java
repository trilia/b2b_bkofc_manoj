package com.olp.jpa.domain.docu.om.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.om.model.CompositeOrderEntity;

@Repository("compositeOrderRepository")
public class CompositeOrderRepositoryImpl extends AbstractRepositoryImpl<CompositeOrderEntity, Long>
implements CompositeOrderRepository{

	@Override
	public CompositeOrderEntity findByCompOrderNum(String compOrderNum) {
		IContext ctx = ContextManager.getContext();
		TypedQuery<CompositeOrderEntity> query = getEntityManager()
				.createNamedQuery("CompositeOrderEntity.findByCompOrderNum", CompositeOrderEntity.class);
		query.setParameter("compOrderNum", compOrderNum);
		CompositeOrderEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}

}
