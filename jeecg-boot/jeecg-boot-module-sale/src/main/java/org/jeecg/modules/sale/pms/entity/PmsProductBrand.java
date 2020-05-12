package org.jeecg.modules.sale.pms.entity;

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
 * @Description: 商品品牌
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Data
@TableName("pms_product_brand")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="pms_product_brand对象", description="商品品牌")
public class PmsProductBrand {

	/**brandName*/
	@Excel(name = "brandName", width = 15)
    @ApiModelProperty(value = "brandName")
	private java.lang.String brandName;
	/**首字母*/
	@Excel(name = "首字母", width = 15)
    @ApiModelProperty(value = "首字母")
	private java.lang.String firstLetter;
	/**品牌制造商*/
	@Excel(name = "品牌制造商", width = 15)
	@Dict(dicCode = "factory_status")
    @ApiModelProperty(value = "品牌制造商")
	private java.lang.Integer factoryStatus;
	/**产品数量*/
	@Excel(name = "产品数量", width = 15)
    @ApiModelProperty(value = "产品数量")
	private java.lang.Integer productCount;
	/**品牌logo*/
	@Excel(name = "品牌logo", width = 15)
    @ApiModelProperty(value = "品牌logo")
	private java.lang.String logo;
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
