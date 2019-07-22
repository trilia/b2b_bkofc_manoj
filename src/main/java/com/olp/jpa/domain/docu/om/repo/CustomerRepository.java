package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, ITextRepository<CustomerEntity, Long> {

	public CustomerEntity findByCustomerCode(String customerCode);
}
