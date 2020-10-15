package com.itheima.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.poi.POIUtils;
import com.itheima.health.utils.resources.MessageConstant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class OrderSettingController {
    @Reference
    private OrderSettingService ordersettingService;

    @PostMapping("OrderSetting/importOrderSettings")
    public Result upload(@RequestParam("excelFile") MultipartFile file) {
        try {
            //String[0] 预约时间 String[1]最大预约数量
            List<String[]> list = POIUtils.readExcel(file);
            ordersettingService.batchOrderSetting(list);
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    } catch (Exception e) {
        e.printStackTrace();
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }
    }

    @GetMapping("OrderSetting/findSettingData/{year}/{month}")
    public Result findSettingData(@PathVariable("year") String year, @PathVariable("month") String month) {

        try {
            Map mapList = ordersettingService.findSettingData(year, month);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, mapList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


    @PutMapping("OrderSetting/updateOrderSettingData/{orderDate}/{number}")
    public Result updateOrderSettingData(@PathVariable("orderDate") String orderDate, @PathVariable("number") String number) {

        try {
            ordersettingService.updateOrderSettingData(orderDate, number);
            return new Result(true, MessageConstant.EDIT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ORDERSETTING_FAIL);
        }
    }

}