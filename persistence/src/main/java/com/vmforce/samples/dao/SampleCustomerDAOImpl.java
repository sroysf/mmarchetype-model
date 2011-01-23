package com.vmforce.samples.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.vmforce.samples.entity.SampleCustomer;

public class SampleCustomerDAOImpl extends JpaDaoSupport implements SampleCustomerDAO {

	@Override
	public List<SampleCustomer> getAllCustomers() {
		return (List<SampleCustomer>) getJpaTemplate().find("SELECT FROM SampleCustomer sc");
	}

}
