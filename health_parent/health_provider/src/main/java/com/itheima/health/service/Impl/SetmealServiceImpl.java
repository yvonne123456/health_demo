package com.itheima.health.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.health.dto.SetmealDTO;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.mapper.SetmealMapper;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.resources.RedisConstant;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void saveSetMeal(SetmealDTO setmealDTO) {
        save(setmealDTO);
        Integer id = setmealDTO.getId();
        for (Integer gid : setmealDTO.getCheckgroupIds()) {
            baseMapper.setMealAdd(id,gid);
        }
        if (setmealDTO.getImg()!=null){

            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmealDTO.getImg());
        }
    }

    @Override
    public PageResult findPageSetMeal(QueryPageBean queryPageBean) {
        //  mp   配置插件：  完成分页 查询
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper();//   封装查询条件
        queryWrapper.eq("is_delete", 0);
        Page<Setmeal> page = null;
        if(StringUtils.isEmpty(queryPageBean.getQueryString())){
            page = page(new Page<Setmeal>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize()),queryWrapper);
        }else{
            //   select  * from  xxxx where  name like  or  code like  ? xxx  limit ?,? 查询
            queryWrapper.and(i->i.like("name",queryPageBean.getQueryString()).or().like("HELPCODE",queryPageBean.getQueryString()));
            page = page(new Page<Setmeal>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize()),queryWrapper);
        }
        PageResult result = new PageResult(page.getTotal(),page.getRecords());
        return result;
    }

    @Override
    public Map updateSetMealById(int id) {
        return null;
    }
}
