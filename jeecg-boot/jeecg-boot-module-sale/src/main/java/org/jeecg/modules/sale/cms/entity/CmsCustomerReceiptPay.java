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
 * @Description: 收付款管理
 * @Author: jeecg-boot
 * @Date:   2020-04-28
 * @Version: V1.0
 */
@Data
@TableName("cms_customer_receipt_pay")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="cms_customer_receipt_pay对象", description="收付款管理")
public class CmsCustomerReceiptPay {

	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**发票号*/
	@Excel(name = "发票号", width = 15)
    @ApiModelProperty(value = "发票号")
	private java.lang.String invoiceNumber;
	/**订单号*/
	@Excel(name = "订单号", width = 15)
    @ApiModelProperty(value = "订单号")
	private java.lang.String orderId;
	/**收付款金额*/
	@Excel(name = "收付款金额", width = 15)
    @ApiModelProperty(value = "收付款金额")
	private java.math.BigDecimal money;
	/**是否收到钱，0还未收到，1已经收到*/
	@Excel(name = "是否收到钱，0还未收到，1已经收到", width = 15)
	@Dict(dicCode = "finish_status")
    @ApiModelProperty(value = "是否收到钱，0还未收到，1已经收到")
	private java.lang.Integer finishStatus;
	/**是否开发票，0还未开发票，1已经开发票*/
	@Excel(name = "是否开发票，0还未开发票，1已经开发票", width = 15)
	@Dict(dicCode = "finish_status")
    @ApiModelProperty(value = "是否开发票，0还未开发票，1已经开发票")
	private java.lang.Integer billStatus;
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
