package com.mindex.challenge.data;
import java.util.Date;

/**
 * Compensation class
 */
public class Compensation {
    private String employeeId;
    private Employee employee;
    private int salary;
    private Date effectiveDate;

    public Compensation(){}

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) return true;
        if (!(obj instanceof Compensation)) return false;
        Compensation c = (Compensation)obj;
        return c.getEmployee().getEmployeeId().equals(this.getEmployee().getEmployeeId());
    }
}
