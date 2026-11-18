
package com.example.employee_management.dto;

public class DepartmentStatisticsDTO {

    private String departmentName;
    private long employeeCount;

    public DepartmentStatisticsDTO() {
    }

    public DepartmentStatisticsDTO(String departmentName, long employeeCount) {
        this.departmentName = departmentName;
        this.employeeCount = employeeCount;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(long employeeCount) {
        this.employeeCount = employeeCount;
    }
}
