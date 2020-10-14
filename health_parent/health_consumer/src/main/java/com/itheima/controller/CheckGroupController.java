package com.itheima.controller;


import com.itheima.health.dto.CheckGroupDTO;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import com.itheima.health.utils.resources.MessageConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "检查组项目")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;


    @PostMapping("checkGroup/add")
    @ApiOperation("新增检查组")
    public Result add(@RequestBody CheckGroupDTO checkGroupDTO){
        try {
            checkGroupService.addCheckGroup(checkGroupDTO);
            return  new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }


    @PostMapping("checkGroup/findPage")
    @ApiOperation(value ="分页查询检查项")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult  = checkGroupService.findPageCheckGroup(queryPageBean);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @GetMapping("checkGroup/findCheckItemInfoByGroupId/{id}")
    @ApiOperation(value ="编辑检查组项")
    public Result findCheckItemInfoByGroupId(@PathVariable("id")int id){
        try{
            Map map =  checkGroupService. findCheckItemInfoByGroupId(id);
            return  new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

    @GetMapping("checkGroup/findAll")
    public Result findAll(){
        try {
            List<CheckGroup> list = checkGroupService.list();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


}
