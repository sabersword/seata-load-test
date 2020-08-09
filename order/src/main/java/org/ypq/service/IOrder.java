package org.ypq.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface IOrder {

    @TwoPhaseBusinessAction(name = "DubboTccActionTwo", commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepare(BusinessActionContext actionContext,
                           @BusinessActionContextParameter(paramName = "userId") String userId,
                           @BusinessActionContextParameter(paramName = "commodityCode") String commodityCode,
                           @BusinessActionContextParameter(paramName = "orderCount") int orderCount,
                           @BusinessActionContextParameter(paramName = "orderIdMap") Map<String, Integer> orderIdMap);


    public boolean commit(BusinessActionContext actionContext);

    public boolean rollback(BusinessActionContext actionContext);
}
