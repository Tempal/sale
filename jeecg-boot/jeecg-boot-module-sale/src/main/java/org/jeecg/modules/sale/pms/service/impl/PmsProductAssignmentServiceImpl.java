package org.jeecg.modules.sale.pms.service.impl;

import org.jeecg.modules.sale.pms.entity.PmsProductAssignment;
import org.jeecg.modules.sale.pms.entity.PmsProductInfo;
import org.jeecg.modules.sale.pms.mapper.PmsProductAssignmentMapper;
import org.jeecg.modules.sale.pms.mapper.PmsProductInfoMapper;
import org.jeecg.modules.sale.pms.service.IPmsProductAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 货物分配
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Service
public class PmsProductAssignmentServiceImpl extends ServiceImpl<PmsProductAssignmentMapper, PmsProductAssignment> implements IPmsProductAssignmentService {
    @Autowired
    private PmsProductAssignmentMapper pmsProductAssignmentMapper;
    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;

    /**
     * 添加一对多
     *
     */
    @Override
    @Transactional
    public void saveMain(PmsProductAssignment pmsProductAssignment, List<PmsProductInfo> pmsProductInfoList){
        pmsProductAssignmentMapper.insert(pmsProductAssignment);
        if (pmsProductInfoList != null) {
            for (PmsProductInfo entity : pmsProductInfoList) {
                entity.setAssignmentId(pmsProductAssignment.getId());
                pmsProductInfoMapper.updateById(entity);
            }
        }
    }

    /**
     * 修改一对多
     *
     */
    public void updateMain(PmsProductAssignment pmsProductAssignment, List<PmsProductInfo> pmsProductInfoList){
        pmsProductAssignmentMapper.updateById(pmsProductAssignment);
        String sId=pmsProductAssignment.getId();
        //2.子表数据更新
        if (pmsProductInfoList != null) {
            for (PmsProductInfo entity : pmsProductInfoList) {
//                entity.setAssignmentId(pmsProductAssignment.getId());
                pmsProductInfoMapper.updateAssignmentById(sId,entity.getId());
            }
        }
    }

    /**
     * 删除一对多
     * @param id
     */
    public void delMain (String id){
        pmsProductAssignmentMapper.deleteById(id);
        //2.子表数据更新，置空Assignment字段
        pmsProductInfoMapper.resetAssignmentById(id);
    }

    /**
     * 批量删除一对多
     * @param idList
     */
    public void delBatchMain (Collection<? extends Serializable> idList){
        for(Serializable id:idList) {
            pmsProductAssignmentMapper.deleteById(id);
            //2.子表数据更新，置空Assignment字段
            pmsProductInfoMapper.resetAssignmentById(id.toString());
        }
    }
}
