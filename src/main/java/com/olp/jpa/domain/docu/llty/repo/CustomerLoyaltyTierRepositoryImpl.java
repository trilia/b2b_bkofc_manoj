package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTierEntity;

@Repository("customerLoyaltyTierRepository")
public class CustomerLoyaltyTierRepositoryImpl extends AbstractRepositoryImpl<CustomerLoyaltyTierEntity, Long>
		implements CustomerLoyaltyTierRepository {

	@Override
	@Transactional(readOnly = true)
	public List<CustomerLoyaltyTierEntity> findByCustTierCode(String customerCode, String programCode,
			String tierCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<CustomerLoyaltyTierEntity> query = getEntityManager()
				.createNamedQuery("CustomerLoyaltyTierEntity.findByCustTierCode", CustomerLoyaltyTierEntity.class);
		query.setParameter("customerCode", customerCode);
		query.setParameter("programCode", programCode);
		query.setParameter("tierCode", tierCode);
		query.setParameter("tenant", tid);
		List<CustomerLoyaltyTierEntity> bean = query.getResultList();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}

}
