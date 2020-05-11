package org.jeecg.modules.sale.pms.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @Description: 商品详情
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Data
@TableName("pms_product_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="pms_product_info对象", description="商品详情")
public class PmsProductInfo {

	/**id标志*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id标志")
	//private java.lang.Long id;
	private java.lang.String id;
	//private BigInteger id;
	/**序列号*/
	@Excel(name = "序列号", width = 15)
    @ApiModelProperty(value = "序列号")
	private java.lang.String productSn;
	/**别名*/
	@Excel(name = "别名", width = 15)
    @ApiModelProperty(value = "别名")
	private java.lang.String nickName;
	/**型号*/
	@Excel(name = "型号", width = 15)
    @ApiModelProperty(value = "型号")
	private java.lang.String modelName;
	/**注册状态*/
	@Excel(name = "注册状态", width = 15)
	@Dict(dicCode = "pms_camera_register")
    @ApiModelProperty(value = "注册状态")
	private java.lang.Integer registerStatus;
	/**上架状态：0->下架；1->上架*/
	@Excel(name = "上架状态：0->下架；1->上架", width = 15)
	@Dict(dicCode = "pms_camera_up")
    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
	private java.lang.Integer publishStatus;
	/**维修次数*/
	@Excel(name = "维修次数", width = 15)
    @ApiModelProperty(value = "维修次数")
	private java.lang.Integer maintainTime;
	/**进货时间*/
	@Excel(name = "进货时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "进货时间")
	private java.util.Date purchaseTime;
	/**分配id*/
	@Excel(name = "分配号", width = 15)
	@ApiModelProperty(value = "分配号")
	private java.lang.String assignmentId;
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
