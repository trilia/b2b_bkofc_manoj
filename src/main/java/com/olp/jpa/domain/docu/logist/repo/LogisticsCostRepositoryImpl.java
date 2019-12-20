package com.olp.jpa.domain.docu.logist.repo;

import org.springframework.stereotype.Repository;

import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.logist.model.LogisticsCostEntity;

@Repository("logisticsCostRepository")
public class LogisticsCostRepositoryImpl extends AbstractRepositoryImpl<LogisticsCostEntity, Long>
		implements LogisticsCostRepository {

	/*@Override
	@Transactional(readOnly = true)
	public LogisticsCostEntity findBySvcCategoryCode(String svcCategoryCode) {
		IContext ctx = ContextManager.getContext();

		TypedQuery<LogisticsCostEntity> query = getEntityManager()
				.createNamedQuery("LogisticsCostEntity.findBySvcCategoryCode", LogisticsCostEntity.class);
		query.setParameter("svcCategoryCode", svcCategoryCode);
		LogisticsCostEntity bean = query.getSingleResult();

		return (bean);
	}
	*/
	@Override
	public String getLazyLoadElements() {
		return "";
	}

}
