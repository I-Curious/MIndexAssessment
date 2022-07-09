package com.mindex.challenge.data;

/**
 * Reporting structure class
 */
public class ReportingStructure {

    private Employee employee;
    private int numberOfReports;

    /**
     * Constaructor
     * @param employee Employee who's reporting structure is generated
     * @param numberOfReports Number of employees who report to the employee
     */
    public ReportingStructure(Employee employee, int numberOfReports){
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
