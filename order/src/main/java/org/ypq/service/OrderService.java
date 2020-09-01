package org.ypq.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.ypq.IAccount;
import org.ypq.IOrder;
import org.ypq.persistence.Order;
import org.ypq.persistence.OrderMapper;

import java.math.BigDecimal;

@Component
@Service
public class OrderService implements IOrder {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Reference(retries = 0, timeout = 60 * 1000)
    private IAccount account;

    @Transactional
    public String create(String userId, String commodityCode, Integer count){
        BigDecimal orderMoney = new BigDecimal(count);
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(count);
        order.setMoney(orderMoney);
        order.setStatus("NORMAL");
        orderMapper.insert(order);
        String result = "order is [userId:" + userId + "],[commodityCode:" + commodityCode +"],[count:" + count +"]    ";
        result += restTemplate.getForObject("http://account-service/deduct?userId=" + userId + "&num=" + count, String.class);
        return result;
    }

    @Override
    @Transactional
    public boolean prepare(BusinessActionContext actionContext, String userId, String commodityCode, int orderCount) {
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(new BigDecimal(orderCount));
        order.setStatus("PRESERVED");
        orderMapper.insert(order);
        return account.prepare(null, userId, orderCount);
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
//        Integer orderId = (Integer) actionContext.getActionContext("orderIdMap");
//        Order order = new Order();
//        order.setId(orderId);
//        order.setStatus("NORMAL");
//        int row = orderMapper.updateNormal(order);
//        if (row != 1) {
//            throw new RuntimeException("order commit affect " + row);
//        }
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
//        Integer orderId = (Integer) actionContext.getActionContext("orderId");
//        Order order = new Order();
//        order.setId(orderId);
//        int row = orderMapper.delete(order);
//        if (row != 1) {
//            throw new RuntimeException("order rollback affect " + row);
//        }
        return true;
    }
}
