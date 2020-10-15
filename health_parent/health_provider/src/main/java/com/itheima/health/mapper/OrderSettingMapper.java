package com.itheima.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface OrderSettingMapper extends BaseMapper<OrderSetting> {
    @Select("select orderdate , number ,reservations  from  t_ordersetting  where  ORDERDATE BETWEEN #{beginDate} and #{endDate}")
    List<Map> findOrderSettingsData(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    @Update("update t_ordersetting set number=#{number} where orderdate=#{orderDate}")
    void updateOrderSettingData(@Param("orderDate") String orderDate, @Param("number") String number);
}
