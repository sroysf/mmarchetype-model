<%@page import="com.vmforce.samples.entity.SampleAddress"%>
<%@ page language="java" import="com.vmforce.samples.entity.SampleCustomer, java.util.*"%>
<HTML>
<HEAD>
<TITLE>Sample Customer List</TITLE>
</HEAD>
<BODY>
	<H1>List of Customers</H1>
	
	<%
		List<SampleCustomer> customers = (List<SampleCustomer>)request.getAttribute("customers");
		
		for (SampleCustomer customer : customers) {
			int numAddresses = customer.getAddresses().size();
			
			out.println("<h3>" + customer.getFirstName() + " " + customer.getLastName() + "</h3>");
			out.println("<ul>");
			out.println("<li>" + customer.getPhoneNumber() + "</li>");
			out.println("<li>Prefers " + customer.getColorPreference() + "</li>");
			
			out.println("<ul>");
			Set<SampleAddress> addresses = customer.getAddresses();
			for (SampleAddress address : addresses) {
				out.println("<li><b><u>" + address.getNickName() + "</u></b></li>");
				out.println("<li>" + address.getStreet1() + "</li>");
				out.println("<li>" + address.getStreet2() + "</li>");
				out.println("<li>" + address.getCity() + "," + address.getState() + " " + address.getZip() + "</li>");
			}
			out.println("</ul>");
			
			out.println("</ul>");
		}
	%>
	
	<p/>
	<a href="/logout">Logout</a>
</BODY>
</HTML>