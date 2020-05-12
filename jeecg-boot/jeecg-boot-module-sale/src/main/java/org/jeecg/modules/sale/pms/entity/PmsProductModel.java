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
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 产品型号
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Data
@TableName("pms_product_model")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="pms_product_model对象", description="产品型号")
public class PmsProductModel {
    
	/**产品型号*/
	@Excel(name = "产品型号", width = 15)
    @ApiModelProperty(value = "产品型号")
	private java.lang.String modelName;
	/**市场价*/
	@Excel(name = "市场价", width = 15)
    @ApiModelProperty(value = "市场价")
	private java.math.BigDecimal originalPrice;
	/**销量*/
	@Excel(name = "销量", width = 15)
    @ApiModelProperty(value = "销量")
	private java.lang.Integer sale;
	/**库存*/
	@Excel(name = "库存", width = 15)
    @ApiModelProperty(value = "库存")
	private java.lang.Integer stock;
	/**库存预警值*/
	@Excel(name = "库存预警值", width = 15)
    @ApiModelProperty(value = "库存预警值")
	private java.lang.Integer lowStock;
	/**照片*/
	@Excel(name = "照片", width = 15)
    @ApiModelProperty(value = "照片")
	private java.lang.String pic;
	/**单位*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
	private java.lang.String unit;
	/**商品描述*/
	@Excel(name = "商品描述", width = 15)
    @ApiModelProperty(value = "商品描述")
	private java.lang.Object description;
	/**关键词*/
	@Excel(name = "关键词", width = 15)
    @ApiModelProperty(value = "关键词")
	private java.lang.String keywords;
	/**分类id*/
	@Excel(name = "分类id", width = 15)
    @ApiModelProperty(value = "分类id")
	private java.lang.Integer productCategoryId;
	/**品牌名称*/
	@Excel(name = "品牌名称", width = 15)
    @ApiModelProperty(value = "品牌名称")
	private java.lang.String brandName;
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
