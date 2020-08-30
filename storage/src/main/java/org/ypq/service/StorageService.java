package org.ypq.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ypq.persistence.Storage;
import org.ypq.persistence.StorageMapper;

//@Component
@Service
public class StorageService implements IStorage{

    @Autowired
    private StorageMapper storageMapper;

    @Transactional
    public String deduct(String commodityCode, int count) {
        //There is a latent isolation problem here.
        //I hope that users can solve it and deepen their understanding of seata isolation.
        //At the bottom I will put a reference solution.
        Storage storage = storageMapper.findByCommodityCode(commodityCode);
        storage.setCount(storage.getCount() - count);
        storageMapper.updateCountById(storage);
        return "the rest of " + commodityCode + " is " + storage.getCount() + "   ";
    }

    @Override
    @Transactional
    public boolean prepare(BusinessActionContext actionContext, String commodityCode, int orderCount) {
        Storage storage = storageMapper.findByCommodityCode(commodityCode);
        storage.setFreezeCount(storage.getFreezeCount() + orderCount);
        storageMapper.updateFreezeById(storage);
        return true;
    }

    @Override
    @Transactional
    public boolean commit(BusinessActionContext actionContext) {
        String commodityCode = (String) actionContext.getActionContext("commodityCode");
        int count = (Integer) actionContext.getActionContext("orderCount");

        Storage storage = storageMapper.findByCommodityCode(commodityCode);
        storage.setFreezeCount(storage.getFreezeCount() - count);
        storage.setCount(storage.getCount() - count);
        storageMapper.updateFreezeAndCountById(storage);
        return true;
    }

    @Override
    @Transactional
    public boolean rollback(BusinessActionContext actionContext) {
        String commodityCode = (String) actionContext.getActionContext("commodityCode");
        int count = (Integer) actionContext.getActionContext("orderCount");

        Storage storage = storageMapper.findByCommodityCode(commodityCode);
        storage.setFreezeCount(storage.getFreezeCount() - count);
        storageMapper.updateFreezeById(storage);
        return true;
    }
}
