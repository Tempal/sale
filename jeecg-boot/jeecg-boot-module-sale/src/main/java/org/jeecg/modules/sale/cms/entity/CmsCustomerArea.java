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
 * @Description: 地区管理
 * @Author: jeecg-boot
 * @Date:   2020-04-27
 * @Version: V1.0
 */
@Data
@TableName("cms_customer_area")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="cms_customer_area对象", description="地区管理")
public class CmsCustomerArea {
    
	/**地区编码，0：无地区，1开始*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "地区编码，0：无地区，1开始")
	private java.lang.Integer id;
	/**地区名*/
	@Excel(name = "地区名", width = 15)
    @ApiModelProperty(value = "地区名")
	private java.lang.String area;
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
