package com.itheima.controller;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import com.itheima.health.utils.resources.MessageConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags = "检查项目")

public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @GetMapping("checkItem/findAll")
    @ApiOperation(value ="查询检查项",notes = "查询所有检查项信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code=500,message="服务端代码异常")
    })
    public Result findAll(){
        try {
            List<CheckItem> list = checkItemService.list();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @PostMapping("checkItem/findPage")
    @ApiOperation(value ="分页查询检查项")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = checkItemService.findPage(queryPageBean);
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }



    @PostMapping("checkItem/add")
    @ApiOperation(value ="新增检查项")
    public Result addCheckItem(@RequestBody CheckItem checkItem){
        try {
            checkItemService.saveOrUpdate(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }
    @PostMapping("checkItem/update/{id}")
    @ApiOperation(value ="编辑检查项")
    public Result updateCheckItem(@PathVariable("id") int id){
        try {
            CheckItem checkItem = checkItemService.getById(id);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS,checkItem );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    @DeleteMapping("checkItem/deleteCheckItemById/{id}")
    @ApiOperation(value ="删除检查项")
    public Result  deleteCheckItemById(@PathVariable("id") int id){
        try {
            CheckItem checkItem = new CheckItem();
            checkItem.setIs_delete(1);  //  逻辑删除
            checkItem.setId(id);
            checkItemService.updateById(checkItem);
            return  new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }
  }


