package org.jeecg.modules.sale.cms.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-04-28
 * @Version: V1.0
 */
@Data
@TableName("cms_customer_order")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="cms_customer_order对象", description="订单管理")
public class CmsCustomerOrder {

	/**订单id*/
	@Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
	private java.lang.String orderId;
	/**产品名*/
	@Excel(name = "产品名", width = 15)
    @ApiModelProperty(value = "产品名")
	private java.lang.String modelName;
	/**供应量，已发货物数*/
	@Excel(name = "供应量，已发货物数", width = 15)
    @ApiModelProperty(value = "供应量，已发货物数")
	private java.lang.Integer orderSupply;
	/**需求量*/
	@Excel(name = "需求量", width = 15)
    @ApiModelProperty(value = "需求量")
	private java.lang.Integer orderNumber;
	/**订单类型，0采购，1租转买，2销售，3租赁，4代理，5调试*/
	@Excel(name = "订单类型，0采购，1租转买，2销售，3租赁，4代理，5调试", width = 15)
    @ApiModelProperty(value = "订单类型，0采购，1租转买，2销售，3租赁，4代理，5调试")
	private java.lang.String orderType;
	/**assignedArea*/
	@Excel(name = "assignedArea", width = 15)
    @ApiModelProperty(value = "assignedArea")
	private java.lang.String assignedArea;
	/**contractId*/
	@Excel(name = "contractId", width = 15)
    @ApiModelProperty(value = "contractId")
	private java.lang.String contractId;
	/**订单金额*/
	@Excel(name = "订单金额", width = 15)
    @ApiModelProperty(value = "订单金额")
	private java.math.BigDecimal money;
	/**下订单时间*/
	@Excel(name = "下订单时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下订单时间")
	private java.util.Date orderTime;
	/**有效时间*/
	@Excel(name = "有效时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效时间")
	private java.util.Date avaliableTime;
	/**是否有效,0无效，1有效*/
	@Excel(name = "是否有效,0无效，1有效", width = 15)
    @ApiModelProperty(value = "是否有效,0无效，1有效")
	@Dict(dicCode = "valid_status")
	private java.lang.Integer validStatus;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
}
