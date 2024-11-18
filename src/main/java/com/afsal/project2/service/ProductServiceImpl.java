package com.afsal.project2.service;

import com.afsal.project2.dao.ProductDaoImpl;
import com.afsal.project2.dto.ProductDto;
import com.afsal.project2.entity.Product;
import com.afsal.project2.exception.ResourceAlreadyFoundException;
import com.afsal.project2.exception.ResourceNotFoundException;
import com.afsal.project2.util.ProductMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements  ProductService{
    @Autowired
    private ProductDaoImpl repository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private Logger logger;

    private static final String PRODUCT_CACHE_KEY = "PRODUCT";

    @Override
    public Product getProductById(Integer id) {
        String str = (String) redisTemplate.opsForHash().get(PRODUCT_CACHE_KEY, ""+id);

        if(str == null){
            logger.error("invalid id is requested (Not found)");
            throw new ResourceNotFoundException("Product with id "+id+",Not Found");
        }

        Product product = ProductMapper.toEntity(str);
        logger.info("Product with id:{} has retrieved from redis", id);
        return product;
    }


    @Override
    public List<Product> getAllProduct(int page, int size) {

        List<Object> objects =  redisTemplate.opsForHash().values(PRODUCT_CACHE_KEY);
        if (objects.isEmpty()){
            logger.info("There is no data in the redis cache");
            return new ArrayList<>();
        }
        logger.info("Products has retrieved from the redis");
        int start = page*size;
        if(start >= objects.size()){
            return new ArrayList<>();
        }
        int end = Math.min(start+size, objects.size());
        List<String> strs = (List<String>)(List<?>) objects.subList(start, end);

        return strs.stream().map(ProductMapper::toEntity).collect(Collectors.toList());
    }


    @Override
    public String  saveOrUpdateProduct(ProductDto productDto){
        Product product = ProductMapper.toEntity(productDto);
        Product product1 = repository.isPresent(product);
        Product product2 = repository.isPresentEmployee(product);
        if(product1==null && product2==null){
            logger.info("Data is being sent by kafka");
            kafkaService.sendMessage(productDto);
            return "Successfully sent to kafka";
        }

        logger.info("Not sent to kafka because of Exception");
        String message = "";
        if(product1!=null){
            message += "Product with (client ID - skuCode) exists\n"+product1+"\n";
        }
        if(product2!=null){
            message += "Product with emp_id exists\n"+product2+"\n";
        }

        throw new ResourceAlreadyFoundException("Not sent to kafka because product already exists\n"+message);
    }


    @Override
    public String deleteProduct(Integer id) {
        try {
            repository.deleteById(id);
            redisTemplate.opsForHash().delete(PRODUCT_CACHE_KEY, "" + id);
            logger.info("Deleted successfully");
            return "Deleted Successfully";
        }catch (Exception ex){
            throw new ResourceNotFoundException("Product with id:"+id+", Not found");
        }
    }

    @Override
    public String deleteAllProduct() {
        repository.deleteAll();
        redisTemplate.delete(PRODUCT_CACHE_KEY);
        logger.info("All Products are deleted");
        return "All products are Deleted Successfully";
    }
}
