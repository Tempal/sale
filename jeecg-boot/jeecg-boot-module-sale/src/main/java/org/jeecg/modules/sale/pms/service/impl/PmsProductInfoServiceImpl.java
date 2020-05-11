package org.jeecg.modules.sale.pms.service.impl;

import org.jeecg.modules.sale.pms.entity.PmsProductInfo;
import org.jeecg.modules.sale.pms.mapper.PmsProductInfoMapper;
import org.jeecg.modules.sale.pms.service.IPmsProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 商品详情
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Service
public class PmsProductInfoServiceImpl extends ServiceImpl<PmsProductInfoMapper, PmsProductInfo> implements IPmsProductInfoService {
    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Override
    public List<PmsProductInfo> selectTicketsByMainId(String mainId){
        return pmsProductInfoMapper.selectProductInfoByAssignmentId(mainId);
    }
}
