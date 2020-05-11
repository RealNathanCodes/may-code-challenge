package com.mindex.challenge.service.impl;

import java.util.List;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is the ReportingStructure service.
 */
@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(final String id) {
        LOG.debug("Getting ReportingStructure for employee with id [{}]", id);

        final Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        // normally I eschew the direct use of new, I would inject a ReportingStructure factory and ask it to give me
        // a ReportingStructure. I feel like that might be overkill for this exercise though, so I will use new glue.
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(getNumberofReports(employee));

        return reportingStructure;
    }

    /**
     * We're going to play a bit fast and loose in here. If we have empl
     * @param employee
     * @return
     */
    private int getNumberofReports(Employee employee) {
        List<Employee> directReports = employee.getDirectReports();
        int numberOfReports = 0;
        if(directReports == null || directReports.size() == 0) {
            return numberOfReports;
        }
        for (Employee emp : directReports) {
            if (emp == null) {
                LOG.error("Error in getNumberofReports. Employee [{}] has a directReport employee that is null", employee.getEmployeeId());
                continue;
            }
            // We have employees, but these are not fully populated, 
            //so let's make sure we have real employees before we do anything with them
            Employee actualEmployee = employeeRepository.findByEmployeeId(emp.getEmployeeId());
            if (actualEmployee != null) {
                numberOfReports++;
                numberOfReports += getNumberofReports(actualEmployee);
            } else {
                LOG.error("Error in getNumberofReports. Employee [{}] has a directReport employee id [{}] that does not exist", employee.getEmployeeId(), emp.getEmployeeId());
            }
        }
        return numberOfReports;
    }
}
