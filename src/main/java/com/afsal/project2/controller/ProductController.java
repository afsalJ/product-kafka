package com.afsal.project2.controller;

import com.afsal.project2.dto.EmployeeDto;
import com.afsal.project2.dto.ProductDto;
import com.afsal.project2.entity.Employee;
import com.afsal.project2.entity.Product;
import com.afsal.project2.entity.Range;
import com.afsal.project2.exception.RuleViolationException;
import com.afsal.project2.service.ProductService;
import com.afsal.project2.util.ProductMapper;
import com.afsal.project2.validation.Validator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/project2/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ProductDto getProductById(@PathVariable Integer id) {
        Product product = service.getProductById(id);
        return ProductMapper.toDto(product);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ProductDto> getAllProduct(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        List<Product> products = service.getAllProduct(page, size);
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(path="/{id}/employee", method = RequestMethod.GET)
    public EmployeeDto getEmployeeByProduct(@PathVariable Integer id){
        ProductDto productDto = getProductById(id);
        return productDto.getEmployee();
    }

    @RequestMapping(path="/save", method = RequestMethod.POST)
    public String saveOrUpdateProduct(@Valid @RequestBody ProductDto productDto){
        String message = Validator.isValid(productDto);
        if(!message.isEmpty()){
            throw new RuleViolationException(message);
        }

        return service.saveOrUpdateProduct(productDto);
    }

    @RequestMapping(path = "/save/range", method = RequestMethod.POST)
    public String saveProduct(@Valid @RequestBody Range range) {
        String message = Validator.isValid(range);
        if(!message.isEmpty()){
            throw new RuleViolationException(message);
        }
        int clientIdStart = range.getClientIdStart();
        int empIdStart = range.getEmpIdStart();

        int end = clientIdStart + range.getCount();

        for(int i=clientIdStart,j=empIdStart;i<end;i++, j++){
            EmployeeDto employeeDto = new EmployeeDto(j, "bot");
            ProductDto productDto = new ProductDto(i, employeeDto, "-1", "bot", true);
            service.saveOrUpdateProduct(productDto);
        }

        return "Successfully Inserted into kafka";
    }


    @RequestMapping(path="/{id}", method = RequestMethod.DELETE)
    public String deleteProduct(@PathVariable("id") Integer id) {
        return service.deleteProduct(id);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public String deleteAllProduct(){
        return service.deleteAllProduct();
    }

}
