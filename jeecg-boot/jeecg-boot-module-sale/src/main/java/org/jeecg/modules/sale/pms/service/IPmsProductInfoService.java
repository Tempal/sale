package org.jeecg.modules.sale.pms.service;

import org.jeecg.modules.sale.pms.entity.PmsProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 商品详情
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
public interface IPmsProductInfoService extends IService<PmsProductInfo> {
    public List<PmsProductInfo> selectProductByAssignmentId(String mainId);

    public List<PmsProductInfo> selectProductByNullAssignmentId();

}
