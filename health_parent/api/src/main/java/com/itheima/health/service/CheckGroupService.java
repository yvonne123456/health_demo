package com.itheima.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.health.dto.CheckGroupDTO;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.Map;


public interface CheckGroupService extends IService<CheckGroup> {
    void addCheckGroup(CheckGroupDTO checkGroupDTO);
    
    PageResult findPageCheckGroup(QueryPageBean queryPageBean);

    Map findCheckItemInfoByGroupId(int id);
}
