package com.afsal.project2.dto;

import com.afsal.project2.entity.Employee;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {


    private Integer id;

    @NotNull(message = "employee is mandatory (It must have empId and name)")
    @Valid
    private EmployeeDto employee;

    @NotNull(message = "client_id must not be null")
    private Integer clientId;

    @NotBlank(message="name must not be blank")
    private String name;

    @NotBlank(message = "skuCode must not be blank")
    private String skuCode;

    @NotNull(message = "Enable flag must not be null")
    private Boolean enable;

    public ProductDto(int clientId, EmployeeDto employee, String skuCode, String name, Boolean enable) {
        this.clientId = clientId;
        this.employee = employee;
        this.skuCode = skuCode;
        this.name = name;
        this.enable = enable;
    }
}
