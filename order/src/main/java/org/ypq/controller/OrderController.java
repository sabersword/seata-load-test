package org.ypq.controller;


import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ypq.service.OrderService;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/create")
    public String create(@RequestParam String userId, @RequestParam String commodityCode, @RequestParam Integer num) {
        System.out.println("order XID " + RootContext.getXID());
        return orderService.create(userId, commodityCode, num);
    }

}
