package org.jeecg.modules.sale.pms.service;

import org.jeecg.modules.sale.pms.entity.PmsProductAssignment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.sale.pms.entity.PmsProductInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 货物分配
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
public interface IPmsProductAssignmentService extends IService<PmsProductAssignment> {

    /**
     * 添加一对多
     *
     */
    public void saveMain(PmsProductAssignment pmsProductAssignment, List<PmsProductInfo> pmsProductInfoList) ;

    /**
     * 修改一对多
     *
     */
    public void updateMain(PmsProductAssignment pmsProductAssignment, List<PmsProductInfo> pmsProductInfoList);

    /**
     * 删除一对多
     * @param id
     */
    public void delMain (String id);

    /**
     * 批量删除一对多
     * @param idList
     */
    public void delBatchMain (Collection<? extends Serializable> idList);

}
