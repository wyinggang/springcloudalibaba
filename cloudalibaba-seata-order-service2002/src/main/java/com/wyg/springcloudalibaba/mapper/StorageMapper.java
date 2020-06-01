package com.wyg.springcloudalibaba.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StorageMapper {


    //扣减库存信息
    //@Update(" UPDATE t_storage SET used = used + #{count},residue = residue - #{count} WHERE product_id = #{productId}")
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}

