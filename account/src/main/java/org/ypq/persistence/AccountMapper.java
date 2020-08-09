package org.ypq.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    Account selectByUserId(@Param("userId") String userId);

    int updateMoneyById(Account record);

    int updateFreezeById(Account record);

    int updateFreezeAndMoneyById(Account record);

}