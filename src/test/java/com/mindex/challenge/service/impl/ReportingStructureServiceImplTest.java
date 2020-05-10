package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String employeeIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeIdUrl = "http://localhost:" + port + "/reportingstructure/{id}";
    }

    // I'm copying the existing unit test, but by golly, this feels a lot more like an integration test than a unit test.
    // But, when in Rome I guess.
    @Test
    public void testNumberOfReportsIsZeroWhenEmployeeHasNoReports() {
        // arrange
        String testEmployeeId = "c0c2293d-16bd-4603-8e08-638a9d18b22c";

        // Create checks
        ReportingStructure reportingStructure = restTemplate.getForEntity(employeeIdUrl, ReportingStructure.class, testEmployeeId).getBody();
        assertNotNull(reportingStructure);
        assertNotNull(reportingStructure.getEmployee());
        assertEquals(reportingStructure.getEmployee().getEmployeeId(), testEmployeeId);
        assertEquals(reportingStructure.getNumberOfReports(), 0);
    }

        // I'm copying the existing unit test, but by golly, this feels a lot more like an integration test than a unit test.
    // But, when in Rome I guess.
    @Test
    public void testNumberOfReportsIsFourWhenEmployeeIsJohnLennon() {
        // arrange
        String testEmployeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        // Create checks
        ReportingStructure reportingStructure = restTemplate.getForEntity(employeeIdUrl, ReportingStructure.class, testEmployeeId).getBody();
        assertNotNull(reportingStructure);
        assertNotNull(reportingStructure.getEmployee());
        assertEquals(reportingStructure.getEmployee().getEmployeeId(), testEmployeeId);
        assertEquals(reportingStructure.getNumberOfReports(), 4);
    }
}
