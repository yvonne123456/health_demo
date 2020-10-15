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

        //  解析excel 将数据 转换成Java 对象
        List<OrderSetting>  orderSettings = StringTOList(list);
        List<OrderSetting>  orderSettingSave = new ArrayList<OrderSetting>();//   存放没有id 的预约对象
        List<OrderSetting>  orderSettingUpdate = new ArrayList<OrderSetting>();//   存放有id 的预约对象
        //  循环遍历集合  查询数据库表中有无 当前日期记录
        for (OrderSetting orderSetting : orderSettings) {
            Date orderDate = orderSetting.getOrderDate();
            QueryWrapper<OrderSetting> queryWrapper = new QueryWrapper<OrderSetting>();
            queryWrapper.eq("ORDERDATE",orderDate);
            OrderSetting orderSettingIsExist = baseMapper.selectOne(queryWrapper);//  根据条件查询单一记录对象
            if(orderSettingIsExist!=null){
                //  批量修改  需要将id  封装到List集合中
                orderSetting.setId(orderSettingIsExist.getId());//     查询id  封装到 添加OrderSetting 对象
                orderSettingUpdate.add(orderSetting);
            }else{
                orderSettingSave.add(orderSetting);
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
