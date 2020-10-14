package com.itheima.controller;

import com.itheima.health.dto.SetmealDTO;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.aliyunoss.AliyunUtils;
import com.itheima.health.utils.resources.MessageConstant;
import com.itheima.health.utils.resources.RedisConstant;
import com.itheima.health.utils.resources.UploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController   //  @Controller +@ResponseBody
@Api(tags = "套餐管理")
public class SetmealController {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("setMeal/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile file) {
        try {
            //  调用阿里云 oss  sdk   完成  图片上传
            String originalFilename = file.getOriginalFilename();
            String uuidfilename = UploadUtils.generateRandonFileName(originalFilename);
            AliyunUtils.uploadMultiPartFileToAliyun(file.getBytes(), uuidfilename);

            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_RESOURCES,uuidfilename);


            //   服务端生成图片uuid 名称 回送给 前端
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, uuidfilename);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }


    @PostMapping("setMeal/add")
    public Result add(@RequestBody SetmealDTO setmealDTO) {
        try {
            setmealService.saveSetMeal(setmealDTO);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @PostMapping("setMeal/findPage")
    @ApiOperation(value ="分页查询检查项")
    public Result findPageSetMeal(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = setmealService.findPageSetMeal(queryPageBean);
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    @DeleteMapping("setMeal/deleteSetMealById/{id}")
    @ApiOperation(value ="删除检查项")
    public Result  deleteSetMealById(@PathVariable("id") int id){
        try {
            Setmeal setmeal = new Setmeal();
            setmeal.setIs_delete(1);  //  逻辑删除
            setmeal.setId(id);
            setmealService.updateById(setmeal);
            return  new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS );
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }


    @PostMapping("updateSetMeal/update/{id}")
    @ApiOperation(value ="编辑检查项")
    public Result updateSetMeal(@PathVariable("id") int id){
        try {
          Map map =setmealService.updateSetMealById(id);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS, map );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }
}