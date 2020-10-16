package com.itheima.health.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.health.mapper.OrderSettingMapper;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.date.DateUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OrderSetting>  implements OrderSettingService {
    @Override
    public void batchOrderSetting(List<String[]> list) {

        List<OrderSetting> orderSettingSave= new ArrayList();

        List<OrderSetting>orderSettingUpdate = new ArrayList();

        List<OrderSetting> allorderSettings = StringTOList(list);

        for (OrderSetting allorderSetting : allorderSettings) {
            QueryWrapper queryWrapper = new QueryWrapper();
            Date orderDate = allorderSetting.getOrderDate();
            queryWrapper.eq("ORDERDATE",orderDate);
            OrderSetting orderSettingExist = baseMapper.selectOne(queryWrapper);
            if (orderSettingExist==null){

                orderSettingSave.add(allorderSetting);
            }else {
                allorderSetting.setId(orderSettingExist.getId());
                orderSettingUpdate.add(allorderSetting);
            }
        }
        saveBatch(orderSettingSave);
        updateBatchById(orderSettingUpdate);
    }

    /**
     *   excel读取的数据  转换 List<OrderSetting> 集合对象
     * @param list
     * @return
     */
    private List<OrderSetting> StringTOList(List<String[]> list) {
        if(list!=null&&list.size()!=0){
            List<OrderSetting>  orderSettingsList = new ArrayList<OrderSetting>();
            for (String[] strings : list) {
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setNumber(Integer.parseInt(strings[1]));
                Date date = DateUtils.parseString2Date(strings[0], "yyyy/MM/dd");
                orderSetting.setOrderDate(date);
                orderSetting.setReservations(0);
                orderSettingsList.add(orderSetting);
            }
            return orderSettingsList;
        }else{
            return  null;
        }
    }

    @Override
    public Map findSettingData(String year, String month) {
        String beginDate = year+"-"+month+"-1";
        String endDate = year+"-"+month+"-31";
        List<Map> mapList =  baseMapper.findOrderSettingsData(beginDate,endDate);
        Map<String,OrderSetting> map = new HashMap<>();
        for (Map map1 : mapList) {
            OrderSetting orderSetting = new OrderSetting();
            Integer number = (Integer) map1.get("number");
            Integer reservations = (Integer) map1.get("reservations");
            Date orderdate = (Date)map1.get("orderdate");
            orderSetting.setNumber(number);
            orderSetting.setReservations(reservations);
            map.put(DateUtils.parseDate2String(orderdate,"yyyy-MM-dd"),orderSetting);

        }
        return map;
    }

    @Override
    public void updateOrderSettingData(String orderDate, String number) {

    baseMapper.updateOrderSettingData(orderDate,number);

    }

}
