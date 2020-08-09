package org.ypq.controller;

import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ypq.service.BusinessService;

@RestController
public class BusinessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private BusinessService businessService;

    /**
     * 减库存，下订单
     */
    @GetMapping(value = "/businessAT")
    public String purchaseAT(String userId, String commodityCode, int orderCount) {
        LOGGER.info("purchaseAT begin ... xid: " + RootContext.getXID());
        try {
            return businessService.purchase(userId, commodityCode, orderCount);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
