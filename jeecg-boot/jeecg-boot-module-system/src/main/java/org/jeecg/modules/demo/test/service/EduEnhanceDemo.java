package org.jeecg.modules.demo.test.service;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component("EduEnhanceDemo")
public class EduEnhanceDemo implements CgformEnhanceJavaInter {
    @Override
    public int execute(String s, Map<String, Object> map) throws BusinessException {
        return 0;
    }

    @Override
    public int execute(String s, JSONObject jsonObject) throws BusinessException {
        System.out.println("---我是自定义java增强测试bean---");
        System.out.println("---当前tableName---"+s);
        System.out.println("---当前Json数据---"+ jsonObject.toJSONString());
        OnlCgformFieldMapper onlCgformFieldMapper= SpringContextUtils.getBean(OnlCgformFieldMapper.class);
        Map<String,Object> params=new HashMap<>();
        String sql="update " +s+" set edu_data = #{eduDate,jdbcType=DATE} where id = #{id,jdbcType=VARCHAR}";
        params.put("execute_sql_string",sql);
        params.put("eduDate",new Date());
        params.put("id",jsonObject.getString("id"));
        onlCgformFieldMapper.executeUpdatetSQL(params);
        System.out.println("---增强java方法----");
        return 0;
    }
}
