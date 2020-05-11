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
import org.jeecg.modules.sale.cms.entity.CmsCustomerOrderType;
import org.jeecg.modules.sale.cms.service.ICmsCustomerOrderTypeService;
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
 * @Description: 订单类型
 * @Author: jeecg-boot
 * @Date:   2020-04-28
 * @Version: V1.0
 */
@Slf4j
@Api(tags="订单类型")
@RestController
@RequestMapping("/cms/cmsCustomerOrderType")
public class CmsCustomerOrderTypeController extends JeecgController<CmsCustomerOrderType, ICmsCustomerOrderTypeService> {
	@Autowired
	private ICmsCustomerOrderTypeService cmsCustomerOrderTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param cmsCustomerOrderType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "订单类型-分页列表查询")
	@ApiOperation(value="订单类型-分页列表查询", notes="订单类型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CmsCustomerOrderType cmsCustomerOrderType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CmsCustomerOrderType> queryWrapper = QueryGenerator.initQueryWrapper(cmsCustomerOrderType, req.getParameterMap());
		Page<CmsCustomerOrderType> page = new Page<CmsCustomerOrderType>(pageNo, pageSize);
		IPage<CmsCustomerOrderType> pageList = cmsCustomerOrderTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * 添加
	 *
	 * @param cmsCustomerOrderType
	 * @return
	 */
	@AutoLog(value = "订单类型-添加")
	@ApiOperation(value="订单类型-添加", notes="订单类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CmsCustomerOrderType cmsCustomerOrderType) {
		cmsCustomerOrderTypeService.save(cmsCustomerOrderType);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param cmsCustomerOrderType
	 * @return
	 */
	@AutoLog(value = "订单类型-编辑")
	@ApiOperation(value="订单类型-编辑", notes="订单类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CmsCustomerOrderType cmsCustomerOrderType) {
		cmsCustomerOrderTypeService.updateById(cmsCustomerOrderType);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单类型-通过id删除")
	@ApiOperation(value="订单类型-通过id删除", notes="订单类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		cmsCustomerOrderTypeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单类型-批量删除")
	@ApiOperation(value="订单类型-批量删除", notes="订单类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.cmsCustomerOrderTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单类型-通过id查询")
	@ApiOperation(value="订单类型-通过id查询", notes="订单类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CmsCustomerOrderType cmsCustomerOrderType = cmsCustomerOrderTypeService.getById(id);
		return Result.ok(cmsCustomerOrderType);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param cmsCustomerOrderType
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, CmsCustomerOrderType cmsCustomerOrderType) {
      return super.exportXls(request, cmsCustomerOrderType, CmsCustomerOrderType.class, "订单类型");
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
      return super.importExcel(request, response, CmsCustomerOrderType.class);
  }

	 /**
	  * 查询所有地区
	  *
	  * @param
	  * @return
	  */
	 @AutoLog(value = "订单类型-查询订单类型")
	 @ApiOperation(value="订单类型-查询订单类型", notes="订单类型-查询订单类型")
	 @GetMapping(value = "/queryAllType")
	 public Result<?> queryArea( ) {
		 QueryWrapper<CmsCustomerOrderType> queryWrapper = new QueryWrapper<>();
		 queryWrapper.select("order_type_name");
		 return Result.ok(cmsCustomerOrderTypeService.list(queryWrapper));
	 }
}
