package com.itheima.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SetmealMapper extends BaseMapper<Setmeal> {
    @Insert("insert into t_setmeal_checkgroup values(#{id},#{gid})")
    void setMealAdd(@Param("id") Integer id,@Param("gid") Integer gid);
}
