package com.afsal.project2.service;

import com.afsal.project2.config.KafkaConfig;
import com.afsal.project2.dto.ProductDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, ProductDto> kafkaTemplate;

    @Autowired
    Logger logger;

    public void sendMessage(ProductDto productDto){
        CompletableFuture<SendResult<String, ProductDto>> completableFuture = kafkaTemplate.send(KafkaConfig.PUBLISH_TOPIC, productDto);

        completableFuture.whenComplete((result, ex)->{
            String message = "";
            if(ex==null){
                message = "Sent product client-id:"+productDto.getClientId()+
                        " empId:"+productDto.getEmployee().getEmpId()+
                        " partition:"+result.getRecordMetadata().partition();
                logger.info(message);
            }
            else{
                message = "Data doesn't sent !!!!!!!!!!!!!";
                logger.info(message);
            }
        });
    }
}
