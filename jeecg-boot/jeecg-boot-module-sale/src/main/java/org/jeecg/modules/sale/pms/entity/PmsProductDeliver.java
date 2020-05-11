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
 * @Description: 物流信息
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Data
@TableName("pms_product_deliver")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="pms_product_deliver对象", description="物流信息")
public class PmsProductDeliver {
    
	/**物流流水号*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "物流流水号")
	private java.lang.Integer id;
	/**快递号*/
	@Excel(name = "快递号", width = 15)
    @ApiModelProperty(value = "快递号")
	private java.lang.String trackingId;
	/**收货人id*/
	@Excel(name = "收货人id", width = 15)
    @ApiModelProperty(value = "收货人id")
	private java.lang.Integer receivePeopleId;
	/**发货人id*/
	@Excel(name = "发货人id", width = 15)
    @ApiModelProperty(value = "发货人id")
	private java.lang.Integer sendPeopleId;
	/**发货时间*/
	@Excel(name = "发货时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发货时间")
	private java.util.Date sendTime;
	/**是否收获，0还未收，1已经接收*/
	@Excel(name = "是否收获，0还未收，1已经接收", width = 15)
    @ApiModelProperty(value = "是否收获，0还未收，1已经接收")
	private java.lang.Integer receiveStatus;
	/**收货佐证，网址，上传到阿里云或者minio*/
	@Excel(name = "收货佐证，网址，上传到阿里云或者minio", width = 15)
    @ApiModelProperty(value = "收货佐证，网址，上传到阿里云或者minio")
	private java.lang.String certificate;
	/**公司名称*/
	@Excel(name = "公司名称", width = 15)
    @ApiModelProperty(value = "公司名称")
	private java.lang.String companyName;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
    @ApiModelProperty(value = "详细地址")
	private java.lang.String detailAddress;
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
