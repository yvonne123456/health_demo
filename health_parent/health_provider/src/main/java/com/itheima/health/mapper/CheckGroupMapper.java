package com.itheima.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface  CheckGroupMapper extends BaseMapper<CheckGroup> {
    @Insert("insert into t_checkgroup_checkitem values(#{groupId},#{checkitemIds})")

    void addCheckGroupAndCheckItemIds(@Param("groupId") Integer groupId,@Param("checkitemIds") Integer checkitemIds);

    @Select("select  CHECKITEM_ID from  t_checkgroup_checkitem where CHECKGROUP_ID=#{id}")
    int[] findCheckItemIdsByGroupId(int id);

}
