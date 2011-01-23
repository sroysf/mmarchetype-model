package com.vmforce.samples;

import java.util.List;

import javax.inject.Inject;

import com.vmforce.samples.dao.SampleCustomerDAO;
import com.vmforce.samples.entity.SampleCustomer;

public class SampleCustomerServiceImpl implements SampleCustomerService {

	@Inject
	SampleCustomerDAO dao;
	
	@Override
	public List<SampleCustomer> findAllCustomers() {
		return dao.getAllCustomers();
	}

}
