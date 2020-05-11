package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Compensation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationEmployeeIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationEmployeeIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testExceptionIsThrownWithInvalidId() {
        String testEmployeeId = "2";
        boolean exceptionThrown = false;
        try {
            restTemplate.getForEntity(compensationEmployeeIdUrl, Compensation[].class, testEmployeeId).getBody();    
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assertEquals(exceptionThrown, true);
    }

    @Test
    public void testPostingCompensationAppendsToPreviousCompensationRecords() {
        // arrange
        String testEmployeeId = "23";
        Date testEffectiveDate = new Date();
        Double testSalary1 = 23.4;
        Double testSalary2 = 23433.99;
        Double testSalary3 = 223.25;

        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(testEmployeeId);
        Compensation testCompensation = new Compensation();       
        testCompensation.setEmployee(testEmployee);
        testCompensation.setEffectiveDate(testEffectiveDate);
        testCompensation.setSalary(testSalary1);

        // act
        restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        Compensation[] compensationGet1 = restTemplate.getForEntity(compensationEmployeeIdUrl, Compensation[].class, testEmployeeId).getBody(); 

        testCompensation.setSalary(testSalary2);
        restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        Compensation[] compensationGet2 = restTemplate.getForEntity(compensationEmployeeIdUrl, Compensation[].class, testEmployeeId).getBody(); 

        testCompensation.setSalary(testSalary3);
        restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        Compensation[] compensationGet3 = restTemplate.getForEntity(compensationEmployeeIdUrl, Compensation[].class, testEmployeeId).getBody(); 

        // assert
        assertEquals(compensationGet1.length, 1);
        assertEquals(compensationGet2.length, 2);
        assertEquals(compensationGet3.length, 3);
    }
}
