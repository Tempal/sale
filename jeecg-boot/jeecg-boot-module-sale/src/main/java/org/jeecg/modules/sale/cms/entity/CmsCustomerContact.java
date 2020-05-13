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
 * @Description: 联系人管理
 * @Author: jeecg-boot
 * @Date:   2020-04-27
 * @Version: V1.0
 */
@Data
@TableName("cms_customer_contact")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="cms_customer_contact对象", description="联系人管理")
public class CmsCustomerContact {

	/**用户id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "用户id")
	private java.lang.Long id;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
	private java.lang.String username;
	/**公司名*/
	@Excel(name = "公司名", width = 15)
    @ApiModelProperty(value = "公司名")
	private java.lang.String company;
	/**昵称*/
	@Excel(name = "昵称", width = 15)
    @ApiModelProperty(value = "昵称")
	private java.lang.String nickname;
	/**手机号码*/
	@Excel(name = "手机号码", width = 15)
    @ApiModelProperty(value = "手机号码")
	private java.lang.String phone;
	/**性别：0->未知；1->男；2->女*/
	@Excel(name = "性别：0->未知；1->男；2->女", width = 15)
    @ApiModelProperty(value = "性别：0->未知；1->男；2->女")
	private java.lang.Integer gender;
	/**所在城市*/
	@Excel(name = "所在城市", width = 15)
    @ApiModelProperty(value = "所在城市")
	private java.lang.String city;
	/**职业*/
	@Excel(name = "职业", width = 15)
    @ApiModelProperty(value = "职业")
	private java.lang.String job;
	/**职业*/
	@Excel(name = "地址", width = 15)
	@ApiModelProperty(value = "地址")
	private java.lang.String address;
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
