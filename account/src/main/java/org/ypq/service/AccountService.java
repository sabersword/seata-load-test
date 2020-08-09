package org.ypq.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ypq.persistence.Account;
import org.ypq.persistence.AccountMapper;

import java.math.BigDecimal;

@Service
public class AccountService implements IAccount {

    @Autowired
    private AccountMapper accountMapper;

    @Transactional
    public Account deduct(String userId, int num){
        Account account = accountMapper.selectByUserId(userId);
        account.setMoney(account.getMoney().subtract(new BigDecimal(num)));
        accountMapper.updateMoneyById(account);
        if (num < 0 || account.getMoney().compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }
        return account;
    }

    @Override
    public boolean prepare(BusinessActionContext actionContext, String userId, int orderCount) {
        Account account = accountMapper.selectByUserId(userId);
        // freeze + num > money ,余额不足以冻结
        if (account.getFreezeMoney().add(new BigDecimal(orderCount)).compareTo(account.getMoney()) > 0) {
            return false;
        }
        account.setFreezeMoney(account.getFreezeMoney().add(new BigDecimal(orderCount)));
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String userId = (String) actionContext.getActionContext("userId");
        int num = (Integer) actionContext.getActionContext("orderCount");
        Account account = accountMapper.selectByUserId(userId);
        account.setFreezeMoney(account.getFreezeMoney().subtract(new BigDecimal(num)));
        account.setMoney(account.getMoney().subtract(new BigDecimal(num)));
        accountMapper.updateFreezeAndMoneyById(account);
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        String userId = (String) actionContext.getActionContext("userId");
        int num = (Integer) actionContext.getActionContext("orderCount");
        Account account = accountMapper.selectByUserId(userId);
        account.setFreezeMoney(account.getFreezeMoney().subtract(new BigDecimal(num)));
        accountMapper.updateFreezeById(account);
        return true;
    }

}
