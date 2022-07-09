package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }


    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    /**
     * Endpoint that generates the number of reports for an Employee.
     * @param id Employee id who's number of reports needs to be generated.
     * @return Reporting Structure of the Employee.
     */
    @GetMapping("/employee/report/{id}")
    public ReportingStructure generateReport(@PathVariable String id){
        LOG.debug("Received report create request for id [{}]", id);

        Employee employee = employeeService.read(id);
        int numberOfReports = calculateNumberOfReports(employee, 0);
        return new ReportingStructure(employee, numberOfReports);
    }

    /**
     * Compensation endpoint to store the compensations
     * @param compensation Compensation that needs to be stored in the Repository
     * @return compensation that is just stored
     */
    @PostMapping("/employee/compensation")
    public Compensation createCompensation(@RequestBody Compensation compensation){
        LOG.debug("Received compensation create request for [{}]", compensation);

        compensation.setEmployeeId(compensation.getEmployee().getEmployeeId());
        employeeService.addCompensation(compensation);
        return compensation;
    }

    /**
     * Endpoint that returns the compensation of the employee
     * @param id Employee id if the employee who's compensation is requested
     * @return compensation of the employee
     */
    @GetMapping("/employee/compensation/{id}")
    public Compensation getCompensation(@PathVariable String id){
        LOG.debug("Received compensation get request for [{}]", id);

        Compensation compensation = employeeService.readCompensation(id);
        return compensation;
    }

    /**
     * Calculate Number of Reports
     * @param employee employee who's reporting number to be calculated
     * @param numberOfReports number of reports
     * @return number of reports
     */
    public int calculateNumberOfReports(Employee employee, int numberOfReports){
        if(employee.getDirectReports() == null) {
            return numberOfReports;
        }
        for (Employee nextEmployeeWithJustID: employee.getDirectReports()){
            Employee nextEmployee = employeeService.read(nextEmployeeWithJustID.getEmployeeId());
            numberOfReports = calculateNumberOfReports(nextEmployee, numberOfReports);
            numberOfReports++;
        }
        return numberOfReports;
    }

}
