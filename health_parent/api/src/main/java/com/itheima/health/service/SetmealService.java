package com.itheima.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.health.dto.SetmealDTO;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.Map;

public interface SetmealService extends IService<Setmeal> {

    void saveSetMeal(SetmealDTO setmealDTO);

    PageResult findPageSetMeal(QueryPageBean queryPageBean);

    Map updateSetMealById(int id);
}
