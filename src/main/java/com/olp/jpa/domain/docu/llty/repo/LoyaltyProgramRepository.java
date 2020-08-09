package com.olp.jpa.domain.docu.llty.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.LoyaltyProgramEntity;

@NoRepositoryBean
public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgramEntity, Long>, ITextRepository<LoyaltyProgramEntity, Long>{

	public LoyaltyProgramEntity findByProgramCode(String programCode);
}
