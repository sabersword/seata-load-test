package org.ypq.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StorageMapper {

    Storage findByCommodityCode(@Param("commodityCode") String commodityCode);

    int updateCountById(Storage record);

    int updateFreezeById(Storage record);

    int updateFreezeAndCountById(Storage record);
}