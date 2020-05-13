package org.jeecg.modules.sale.pms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.sale.pms.entity.PmsProductInfo;
import org.jeecg.modules.sale.pms.service.IPmsProductInfoService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 商品详情
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品详情")
@RestController
@RequestMapping("/pms/pmsProductInfo")
public class PmsProductInfoController extends JeecgController<PmsProductInfo, IPmsProductInfoService> {
	@Autowired
	private IPmsProductInfoService pmsProductInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param pmsProductInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "商品详情-分页列表查询")
	@ApiOperation(value="商品详情-分页列表查询", notes="商品详情-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PmsProductInfo pmsProductInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		// add by zcy
		Result<IPage<PmsProductInfo>> result = new Result<IPage<PmsProductInfo>>();

		QueryWrapper<PmsProductInfo> queryWrapper = QueryGenerator.initQueryWrapper(pmsProductInfo, req.getParameterMap());
		Page<PmsProductInfo> page = new Page<PmsProductInfo>(pageNo, pageSize);
		IPage<PmsProductInfo> pageList = pmsProductInfoService.page(page, queryWrapper);

		// add by zcy
		//result.setSuccess(true);
		//result.setResult(pageList);
		return Result.ok(pageList);
		//return result;
	}

	/**
	 * 添加
	 *
	 * @param pmsProductInfo
	 * @return
	 */
	@AutoLog(value = "商品详情-添加")
	@ApiOperation(value="商品详情-添加", notes="商品详情-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PmsProductInfo pmsProductInfo) {
		pmsProductInfoService.save(pmsProductInfo);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param pmsProductInfo
	 * @return
	 */
	@AutoLog(value = "商品详情-编辑")
	@ApiOperation(value="商品详情-编辑", notes="商品详情-编辑")
	@PutMapping(value = "/edit")
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Result<?> edit(@RequestBody PmsProductInfo pmsProductInfo) {
		pmsProductInfoService.updateById(pmsProductInfo);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品详情-通过id删除")
	@ApiOperation(value="商品详情-通过id删除", notes="商品详情-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pmsProductInfoService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品详情-批量删除")
	@ApiOperation(value="商品详情-批量删除", notes="商品详情-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pmsProductInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "商品详情-通过id查询")
	 @ApiOperation(value="商品详情-通过id查询", notes="商品详情-通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		 PmsProductInfo pmsProductInfo = pmsProductInfoService.getById(id);
		 return Result.ok(pmsProductInfo);
	 }

	 /**
	  * 查询assignmentId为空的
	  *
	  * @param
	  * @return
	  */
	 @AutoLog(value = "商品详情-查询assignmentId为空的所有的商品")
	 @ApiOperation(value="商品详情-通过assignmentId为空的所有的商品", notes="商品详情-通过assignmentId为空的所有的商品")
	 @GetMapping(value = "/queryAssignmentIdNull")
	 public Result<?> queryAssignmentIdNull() {
		 List<PmsProductInfo> pmsProductInfoList = pmsProductInfoService.selectProductByNullAssignmentId();
		 return Result.ok(pmsProductInfoList);
	 }

  /**
   * 导出excel
   *
   * @param request
   * @param pmsProductInfo
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PmsProductInfo pmsProductInfo) {
      return super.exportXls(request, pmsProductInfo, PmsProductInfo.class, "商品详情");
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
      return super.importExcel(request, response, PmsProductInfo.class);
  }

}
