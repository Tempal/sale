package org.jeecg.modules.sale.cms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.sale.cms.entity.CmsCustomerOrder;
import org.jeecg.modules.sale.cms.service.ICmsCustomerOrderService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-04-28
 * @Version: V1.0
 */
@Slf4j
@Api(tags="订单管理")
@RestController
@RequestMapping("/cms/cmsCustomerOrder")
public class CmsCustomerOrderController extends JeecgController<CmsCustomerOrder, ICmsCustomerOrderService> {
	@Autowired
	private ICmsCustomerOrderService cmsCustomerOrderService;

	/**
	 * 分页列表查询
	 *
	 * @param cmsCustomerOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "订单管理-分页列表查询")
	@ApiOperation(value="订单管理-分页列表查询", notes="订单管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CmsCustomerOrder cmsCustomerOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CmsCustomerOrder> queryWrapper = QueryGenerator.initQueryWrapper(cmsCustomerOrder, req.getParameterMap());
		Page<CmsCustomerOrder> page = new Page<CmsCustomerOrder>(pageNo, pageSize);
		IPage<CmsCustomerOrder> pageList = cmsCustomerOrderService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * 添加
	 *
	 * @param cmsCustomerOrder
	 * @return
	 */
	@AutoLog(value = "订单管理-添加")
	@ApiOperation(value="订单管理-添加", notes="订单管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CmsCustomerOrder cmsCustomerOrder) {
		cmsCustomerOrderService.save(cmsCustomerOrder);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param cmsCustomerOrder
	 * @return
	 */
	@AutoLog(value = "订单管理-编辑")
	@ApiOperation(value="订单管理-编辑", notes="订单管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CmsCustomerOrder cmsCustomerOrder) {
		cmsCustomerOrderService.updateById(cmsCustomerOrder);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单管理-通过id删除")
	@ApiOperation(value="订单管理-通过id删除", notes="订单管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		cmsCustomerOrderService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单管理-批量删除")
	@ApiOperation(value="订单管理-批量删除", notes="订单管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.cmsCustomerOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单管理-通过id查询")
	@ApiOperation(value="订单管理-通过id查询", notes="订单管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CmsCustomerOrder cmsCustomerOrder = cmsCustomerOrderService.getById(id);
		return Result.ok(cmsCustomerOrder);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param cmsCustomerOrder
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, CmsCustomerOrder cmsCustomerOrder) {
      return super.exportXls(request, cmsCustomerOrder, CmsCustomerOrder.class, "订单管理");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      return super.importExcel(request, response, CmsCustomerOrder.class);
  }


	 /**
	  * 查询所有订单号
	  *
	  * @param
	  * @return
	  */
	 @AutoLog(value = "商品品牌-查询所有订单号")
	 @ApiOperation(value="商品品牌-查询所有订单号", notes="商品品牌-查询所有订单号")
	 @GetMapping(value = "/queryAllOrderId")
	 public Result<?> queryAllOrderId() {
		 QueryWrapper<CmsCustomerOrder> queryWrapper = new QueryWrapper<>();
		 queryWrapper.select("order_id,assigned_area");
		 return Result.ok(cmsCustomerOrderService.list(queryWrapper));
	 }
}
