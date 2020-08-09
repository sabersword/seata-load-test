package org.ypq.persistence;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    int insert(Order record);

    int updateNormal(Order record);

    int delete(Order record);

}