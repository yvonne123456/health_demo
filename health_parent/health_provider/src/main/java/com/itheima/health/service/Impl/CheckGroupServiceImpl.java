package com.itheima.health.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.health.dto.CheckGroupDTO;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.mapper.CheckGroupMapper;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import com.itheima.health.service.CheckItemService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CheckGroupServiceImpl extends ServiceImpl<CheckGroupMapper,CheckGroup> implements CheckGroupService {

    @Autowired
     private CheckItemService checkItemService;

    @Override
    public void addCheckGroup(CheckGroupDTO checkGroupDTO) {
        save(checkGroupDTO);
        Integer groupId = checkGroupDTO.getId();
        for (Integer checkitemIds : checkGroupDTO.getCheckItemId()) {
        baseMapper.addCheckGroupAndCheckItemIds(groupId,checkitemIds);
        }
    }

    @Override
    public PageResult  findPageCheckGroup(QueryPageBean queryPageBean) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_delete", 0);
        Page<CheckGroup> page = null;
        if (StringUtils.isEmpty(queryPageBean.getQueryString())){
        page = page(new Page<CheckGroup>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize()),queryWrapper);

        }else {
            queryWrapper.like("name", queryPageBean.getQueryString());
            page = page(new Page<CheckGroup>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize()),queryWrapper);
        }
        PageResult result = new PageResult(page.getTotal(), page.getRecords());
                  return result;

        }

    @Override
    public Map findCheckItemInfoByGroupId(int id) {
        Map map = new HashMap();
        List<CheckItem> list = checkItemService.list();
        map.put("checkItems",list);
        //  根据检查组id  去中间表查询 对应检查项ids
        int[]  checkItemIds = baseMapper.findCheckItemIdsByGroupId(id);
        map.put("checkItemIds",checkItemIds);
        return map;

    }


}
