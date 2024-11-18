package com.afsal.project2.util;


import com.afsal.project2.dto.ProductDto;
import com.afsal.project2.entity.Employee;
import com.afsal.project2.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductDto productDto){
        Product product = new Product();
        if(productDto.getId() != null) product.setId(productDto.getId());
        product.setEmployee(EmployeeMapper.toEntity(productDto.getEmployee()));
        product.setClientId(productDto.getClientId());
        product.setName(productDto.getName());
        product.setSkuCode(productDto.getSkuCode());
        product.setEnable(productDto.getEnable());
        return product;
    }

    public static ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setEmployee(EmployeeMapper.toDto(product.getEmployee()));
        productDto.setId(product.getId());
        productDto.setClientId(product.getClientId());
        productDto.setName(product.getName());
        productDto.setSkuCode(product.getSkuCode());
        productDto.setEnable(product.getEnable());
        return productDto;
    }

    public static Product toEntity(String str){
        String[] arr = str.split(",");
        Product product = new Product();
        product.setId(Integer.parseInt(arr[0]));
        product.setName(arr[1]);
        product.setEnable(Boolean.parseBoolean(arr[2]));
        product.setClientId(Integer.parseInt(arr[3]));
        product.setSkuCode(arr[4]);
        Employee employee = new Employee(Integer.parseInt(arr[5]), arr[6]);
        product.setEmployee(employee);
        return product;
    }

}
