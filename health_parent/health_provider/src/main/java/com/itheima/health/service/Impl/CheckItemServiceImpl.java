package com.itheima.health.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.mapper.CheckItemMapper;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



@Service
@Transactional
public class CheckItemServiceImpl extends ServiceImpl<CheckItemMapper, CheckItem> implements CheckItemService {


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        QueryWrapper queryWrapper = new QueryWrapper();//   封装查询条件
        queryWrapper.eq("is_delete", 0);
        Page<CheckItem> page = null;
        if (StringUtils.isEmpty(queryPageBean.getQueryString())) {
            page = page(new Page<CheckItem>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize()), queryWrapper);
        } else {

            queryWrapper.like("name", queryPageBean.getQueryString());
            page = page(new Page<CheckItem>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize()), queryWrapper);
        }
        PageResult result = new PageResult(page.getTotal(), page.getRecords());
        return result;
    }

}






