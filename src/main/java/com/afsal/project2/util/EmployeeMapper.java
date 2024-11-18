package com.afsal.project2.util;

import com.afsal.project2.dto.EmployeeDto;
import com.afsal.project2.entity.Employee;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeDto employeeDto){
        return new Employee(employeeDto.getEmpId(), employeeDto.getName());
    }

    public static EmployeeDto toDto(Employee employee){
        return new EmployeeDto(employee.getEmpId(), employee.getName());
    }
}
