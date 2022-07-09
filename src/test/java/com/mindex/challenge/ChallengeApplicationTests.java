package com.mindex.challenge;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChallengeApplicationTests {

	private String reportUrl;
	private String compensationUrl;
	private String compensationUrlId;
	private Employee testEmployee;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;


	/**
	 * Basic setup done before running tests.
	 */
	@Before
	public void setup() {
		reportUrl = "http://localhost:" + port + "/employee/report/{id}";
		compensationUrl = "http://localhost:" + port + "/employee/compensation";
		compensationUrlId = "http://localhost:" + port + "/employee/compensation/{id}";

		testEmployee = new Employee();
		testEmployee.setFirstName("Jack");
		testEmployee.setLastName("Reacher");
		testEmployee.setDepartment("Computer");
		testEmployee.setPosition("Tester");
		testEmployee.setEmployeeId("Abc123");
	}

	/**
	 * Tests the creation of reports for a particular employee.
	 */
	@Test
	public void testReport(){
		int numOfReportsDefault = 4;
		String employeeIdDefault = "16a596ae-edd3-4847-99fe-c4518e82c86f";
		ReportingStructure readReport = restTemplate.getForEntity(
				reportUrl, ReportingStructure.class, employeeIdDefault
		).getBody();
		assert readReport != null;
		assertEquals(numOfReportsDefault, readReport.getNumberOfReports());
	}

	/**
	 * Tests the creation and retrieval of compensation for an Employee.
	 */
	@Test
	public void testCompensation(){
		Compensation testCompensation = new Compensation();
		testCompensation.setEmployee(testEmployee);
		testCompensation.setSalary(100000);
		testCompensation.setEffectiveDate(new Date());
		Compensation createdCompensation = restTemplate.postForEntity(
				compensationUrl, testCompensation, Compensation.class
		).getBody();

		Compensation readCompensation = restTemplate.getForEntity(
				compensationUrlId, Compensation.class, testEmployee.getEmployeeId()
		).getBody();
		assertEquals(readCompensation, createdCompensation);
	}

}
