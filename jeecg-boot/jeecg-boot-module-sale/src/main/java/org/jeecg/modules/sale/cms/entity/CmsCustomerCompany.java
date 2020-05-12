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
 * @Description: 公司管理
 * @Author: jeecg-boot
 * @Date:   2020-04-28
 * @Version: V1.0
 */
@Data
@TableName("cms_customer_company")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="cms_customer_company对象", description="公司管理")
public class CmsCustomerCompany {
    
	/**公司名*/
	@Excel(name = "公司名", width = 15)
    @ApiModelProperty(value = "公司名")
	private java.lang.String name;
	/**市*/
	@Excel(name = "市", width = 15)
    @ApiModelProperty(value = "市")
	private java.lang.String city;
	/**公司地址*/
	@Excel(name = "公司地址", width = 15)
    @ApiModelProperty(value = "公司地址")
	private java.lang.String detailAddress;
	/**收货地址*/
	@Excel(name = "收货地址", width = 15)
    @ApiModelProperty(value = "收货地址")
	private java.lang.String receiveAddress;
	/**公司类型：0内部单位，1外部单位*/
	@Excel(name = "公司类型：0内部单位，1外部单位", width = 15)
    @ApiModelProperty(value = "公司类型：0内部单位，1外部单位")
	private java.lang.Integer companyType;
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
