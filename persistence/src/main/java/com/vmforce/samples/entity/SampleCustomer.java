package com.vmforce.samples.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SampleCustomer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	
	@OneToMany(mappedBy="customer", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<SampleAddress> addresses;
	
	@Enumerated(EnumType.ORDINAL)
	private SampleColorPreference colorPreference;
	
	public SampleCustomer() {
		super();
		addresses = new HashSet<SampleAddress>();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<SampleAddress> getAddresses() {
		return addresses;
	}

	public SampleColorPreference getColorPreference() {
		return colorPreference;
	}

	public void setColorPreference(SampleColorPreference colorPreference) {
		this.colorPreference = colorPreference;
	}
	
}
