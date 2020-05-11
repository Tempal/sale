package org.jeecg.modules.sale.pms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.sale.pms.entity.PmsProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 商品详情
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
public interface PmsProductInfoMapper extends BaseMapper<PmsProductInfo> {

    /**
     *
     * @Description 按照id更新
     * @param assignmentId
     * @param Id
     * @return boolean
     * @date 2020/5/11 20:46
     * @auther Tempal
     */
    @Update("UPDATE PMS_PRODUCT_INFO SET ASSIGNMENT_ID = #{assignmentId} WHERE ID= #{id}")
    public boolean updateAssignmentById(String assignmentId,String Id);

    /***
     *
     * @Description 置空assignment_id
     * @param assignmentId
     * @return boolean
     * @date 2020/5/11 21:39
     * @auther Tempal
     */
    @Update("UPDATE PMS_PRODUCT_INFO SET ASSIGNMENT_ID = '' WHERE ASSIGNMENT_ID = #{assignmentId}")
    public boolean resetAssignmentById(String assignmentId);

    /***
     *
     * @Description 根据
     * @param assignmentId
     * @return java.util.List<org.jeecg.modules.sale.pms.entity.PmsProductInfo>
     * @date 2020/5/11 21:43
     * @auther Tempal
     */
    @Select("SELECT * FROM PMS_PRODUCT_INFO WHERE ASSIGNMENT_ID = #{assignmentId}")
    public List<PmsProductInfo> selectProductInfoByAssignmentId(String assignmentId);
}
