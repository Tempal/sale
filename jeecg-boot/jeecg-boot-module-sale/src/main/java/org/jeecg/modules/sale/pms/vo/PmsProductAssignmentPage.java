package org.jeecg.modules.sale.pms.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.sale.pms.entity.PmsProductInfo;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Classname PmsProductAssignmentPage
 * @Description TODO
 * @Date 2020/5/11 12:46
 * @Created by Tempal
 */
@Data
public class PmsProductAssignmentPage {
    /**流水号*/
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**订单号*/
    @Excel(name = "订单号", width = 15)
    private java.lang.String orderId;
    /**物流号*/
    @Excel(name = "物流号", width = 15)
    private java.lang.String deliverId;
    /**地区*/
    @Excel(name = "地区", width = 15)
    private java.lang.String areaId;
    /**创建人*/
    @Excel(name = "创建人", width = 15)
    private java.lang.String createBy;
    /**更新人*/
    @Excel(name = "更新人", width = 15)
    private java.lang.String updateBy;
    /**创建日期*/
    @Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date createTime;
    /**更新日期*/
    @Excel(name = "更新日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date updateTime;

    @ExcelCollection(name="商品")
    private List<PmsProductInfo> pmsProductInfoList;
}
