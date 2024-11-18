package com.afsal.project2.validation;

import com.afsal.project2.dto.EmployeeDto;
import com.afsal.project2.dto.ProductDto;
import com.afsal.project2.entity.Range;

public class Validator{
    public static String isValid( ProductDto productDto) {
        String message = "";
        message += isValidEmployee(productDto.getEmployee());
        if(productDto.getClientId() <=0){
            message+="ClientId should be more than zero\n";
        }
        return message;
    }

    public static String isValidEmployee(EmployeeDto employeeDto) {
        String message = "";
        if(employeeDto.getEmpId()<=0){
            message += "Employee id should be more than zero\n";
        }

        return message;
    }

    public static String isValid(Range range){
        Integer clientIdStart = range.getClientIdStart();
        Integer empIdStart = range.getEmpIdStart();
        Integer count = range.getCount();

        String message="";
        if(clientIdStart <= 0 || empIdStart<=0 || count<=0){
            message += "clientIdStart, empIdStart and count are must be more than 0";
        }

        return message;
    }
}
