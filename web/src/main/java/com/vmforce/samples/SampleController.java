package com.vmforce.samples;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vmforce.samples.entity.SampleCustomer;

@Controller
public class SampleController {
	
	@Inject
	private SampleCustomerService customerService; 
	
	@RequestMapping(value="/secure/customers", method=RequestMethod.GET)
	public String listCustomers (Model model) {
		List<SampleCustomer> customers = customerService.findAllCustomers();
		model.addAttribute("customers", customers);
		
		System.out.println("Service, customers = " + customers);
		
		return "customerList";
	}
}
