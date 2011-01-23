
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vmforce.samples.dao.SampleCustomerDAO;
import com.vmforce.samples.entity.SampleAddress;
import com.vmforce.samples.entity.SampleColorPreference;
import com.vmforce.samples.entity.SampleCustomer;

import static org.junit.Assert.*;

public class JPATest {

	private static ApplicationContext context = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("persistence-context.xml");
		
		deleteCustomers();
	}

	private static void deleteCustomers() {
		
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query query = em.createQuery("delete from SampleCustomer sc");
		query.executeUpdate();
		
		query = em.createQuery("delete from SampleAddress sa");
		query.executeUpdate();
		
		tx.commit();
		em.close();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private static EntityManager getEntityManager() {
		EntityManagerFactory emFactory = context.getBean("entityManagerFactory", EntityManagerFactory.class);
		EntityManager em = emFactory.createEntityManager();
		return em;
	}
	
	@Test
	public void testJPAConnection() throws Exception {
		EntityManager em = getEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		SampleCustomer customer = new SampleCustomer();
		customer.setFirstName("John");
		customer.setLastName("Smith");
		customer.setPhoneNumber("415-555-1212");
		customer.setColorPreference(SampleColorPreference.BLUE);
		
		SampleAddress homeAddress = new SampleAddress();
		populateHomeAddress(customer, homeAddress);
		
		SampleAddress workAddress = new SampleAddress();
		populateWorkAddress(customer, workAddress);
		
		customer.getAddresses().add(homeAddress);
		customer.getAddresses().add(workAddress);
		
		em.persist(customer);
		
		tx.commit();
		
		em.close();
		
		System.out.println("Database ID for new customer = " + customer.getId());
		assertNotNull(customer.getId());
		assertEquals(18, customer.getId().length());
		
		// *******************************
		// Load it back from the database
		EntityManager readTestEm = getEntityManager();
		EntityTransaction readTx = readTestEm.getTransaction();
		readTx.begin();
		
		SampleCustomer dbCustomer = readTestEm.find(SampleCustomer.class, customer.getId());
		
		assertTrue("John".equals(dbCustomer.getFirstName()));
		assertTrue("Smith".equals(dbCustomer.getLastName()));
		assertTrue("415-555-1212".equals(dbCustomer.getPhoneNumber()));
		assertEquals(dbCustomer.getColorPreference(), SampleColorPreference.BLUE);
		
		assertEquals(2, dbCustomer.getAddresses().size());		
		
		for (SampleAddress dbAddress : dbCustomer.getAddresses()) {
			if ("home".equals(dbAddress.getNickName())) {
				assertTrue(dbAddress.getStreet1().equals(homeAddress.getStreet1()));
				assertTrue(dbAddress.getZip().equals(homeAddress.getZip()));
			} else if ("work".equals(dbAddress.getNickName())) {
				assertTrue(dbAddress.getStreet1().equals(workAddress.getStreet1()));
				assertTrue(dbAddress.getZip().equals(workAddress.getZip()));
			} else {
				fail("Unrecognized nickname in address loaded from database : " + dbAddress.getNickName());
			}
		}
		
		readTx.commit();
		readTestEm.close();
	}

	private void populateWorkAddress(SampleCustomer customer,
			SampleAddress workAddress) {
		workAddress.setStreet1("515 California St");
		workAddress.setStreet2("Suite 223");
		workAddress.setCity("San Francisco");
		workAddress.setState("CA");
		workAddress.setZip("94105");
		workAddress.setNickName("work");
		workAddress.setCustomer(customer);
	}

	private void populateHomeAddress(SampleCustomer customer,
			SampleAddress homeAddress) {
		homeAddress.setStreet1("1123 Steiner St");
		homeAddress.setStreet2("Apt 2");
		homeAddress.setCity("San Francisco");
		homeAddress.setState("CA");
		homeAddress.setZip("94115");
		homeAddress.setNickName("home");
		homeAddress.setCustomer(customer);
	}

	@Test
	public void testDAOFind () {
		deleteCustomers();
		
		// Create a customer
		EntityManager em = getEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		SampleCustomer customer = new SampleCustomer();
		customer.setFirstName("John");
		customer.setLastName("Smith");
		customer.setPhoneNumber("415-555-1212");
		customer.setColorPreference(SampleColorPreference.BLUE);
		
		SampleAddress homeAddress = new SampleAddress();
		populateHomeAddress(customer, homeAddress);
		
		SampleAddress workAddress = new SampleAddress();
		populateWorkAddress(customer, workAddress);
		
		customer.getAddresses().add(homeAddress);
		customer.getAddresses().add(workAddress);
		
		em.persist(customer);
		
		tx.commit();
		
		em.close();
		
		// Test retrieval with DAO
		SampleCustomerDAO dao = context.getBean("sampleCustomerDAO", SampleCustomerDAO.class);
		List<SampleCustomer> customers = dao.getAllCustomers();
		assertEquals(1, customers.size());
		SampleCustomer dbCustomer = customers.get(0);
		assertEquals(dbCustomer.getFirstName(), customer.getFirstName());
	}
	
}


