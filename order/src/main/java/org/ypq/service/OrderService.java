package org.ypq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.ypq.persistence.Order;
import org.ypq.persistence.OrderMapper;

import java.math.BigDecimal;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public String create(String userId, String commodityCode, Integer count){
        BigDecimal orderMoney = new BigDecimal(count);
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(count);
        order.setMoney(orderMoney);
        orderMapper.insert(order);
        String result = "order is [userId:" + userId + "],[commodityCode:" + commodityCode +"],[count:" + count +"]    ";
        result += restTemplate.getForObject("http://account-service/deduct?userId=" + userId + "&num=" + count, String.class);
        return result;
    }

}
