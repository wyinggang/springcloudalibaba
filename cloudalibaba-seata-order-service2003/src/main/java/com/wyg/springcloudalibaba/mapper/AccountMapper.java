package com.wyg.springcloudalibaba.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;

@Mapper
public interface AccountMapper {

    /**
     * 扣减账户余额
     */
    //@Update("PDATE t_account SET residue = residue - #{money},used = used + #{money} WHERE user_id = #{userId}")
    void decrease(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
