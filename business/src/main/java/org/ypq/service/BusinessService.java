package org.ypq.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class BusinessService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Reference
    private IOrder order;
    @Reference
    private IStorage storage;

    @GlobalTransactional
    public String purchase(String userId, String commodityCode, int orderCount) {
        String result = restTemplate.getForObject("http://storage-service/deduct?commodityCode=" + commodityCode + "&num=" + orderCount, String.class);
        result += restTemplate.getForObject("http://order-service/create?userId=" + userId + "&commodityCode=" + commodityCode + "&num=" + orderCount, String.class);
        if (result.contains("not enough")) {
            throw new RuntimeException("Business out of money");
        }
        return result;
    }

    @GlobalTransactional
    public String purchaseTCC(String userId, String commodityCode, int orderCount) {
        boolean storageResult = storage.prepare(null, commodityCode, orderCount);
        boolean orderResult = order.prepare(null,userId, commodityCode, orderCount);
        if (!(storageResult && orderResult)) {
            throw new RuntimeException("global rollback!");
        }
        return "global commit!";
    }

}
