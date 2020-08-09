package org.ypq.controller;

import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ypq.service.StorageService;

import java.sql.SQLException;

@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping(value = "/deduct")
    public String deduct(@RequestParam String commodityCode, @RequestParam Integer num) throws SQLException {
        System.out.println("storage XID " + RootContext.getXID());
        return storageService.deduct(commodityCode, num);
    }

}
