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
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 合同管理
 * @Author: jeecg-boot
 * @Date:   2020-04-28
 * @Version: V1.0
 */
@Data
@TableName("cms_customer_contract")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="cms_customer_contract对象", description="合同管理")
public class CmsCustomerContract {

	/**流水号*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "流水号")
	private java.lang.Long id;
	/**合同编号*/
	@Excel(name = "合同编号", width = 15)
    @ApiModelProperty(value = "合同编号")
	private java.lang.String contractNo;
	/**签订时间*/
	@Excel(name = "签订时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "签订时间")
	private java.util.Date signTime;
	/**有效时间*/
	@Excel(name = "有效时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效时间")
	private java.util.Date validTime;
	/**签订公司（甲方）*/
	@Excel(name = "签订公司（甲方）", width = 15)
    @ApiModelProperty(value = "签订公司（甲方）")
	private java.lang.String partyA;
	/**签订公司（乙方）*/
	@Excel(name = "签订公司（乙方）", width = 15)
    @ApiModelProperty(value = "签订公司（乙方）")
	private java.lang.String partyB;
	/**甲方联系人*/
	@Excel(name = "甲方联系人", width = 15)
    @ApiModelProperty(value = "甲方联系人")
	private java.lang.String partyAPeople;
	/**乙方联系人*/
	@Excel(name = "乙方联系人", width = 15)
    @ApiModelProperty(value = "乙方联系人")
	private java.lang.String partyBPeople;
	@Excel(name = "甲方联系人", width = 15)
	@ApiModelProperty(value = "甲方联系电话")
	private java.lang.String partyAPhone;
	/**乙方联系人*/
	@Excel(name = "乙方联系人", width = 15)
	@ApiModelProperty(value = "乙方联系电话")
	private java.lang.String partyBPhone;
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
