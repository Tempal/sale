package org.jeecg.modules.sale.pms.controller;

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
import org.jeecg.modules.sale.pms.entity.PmsProductModel;
import org.jeecg.modules.sale.pms.service.IPmsProductModelService;
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
 * @Description: 产品型号
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Slf4j
@Api(tags="产品型号")
@RestController
@RequestMapping("/pms/pmsProductModel")
public class PmsProductModelController extends JeecgController<PmsProductModel, IPmsProductModelService> {
	@Autowired
	private IPmsProductModelService pmsProductModelService;

	/**
	 * 分页列表查询
	 *
	 * @param pmsProductModel
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "产品型号-分页列表查询")
	@ApiOperation(value="产品型号-分页列表查询", notes="产品型号-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PmsProductModel pmsProductModel,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PmsProductModel> queryWrapper = QueryGenerator.initQueryWrapper(pmsProductModel, req.getParameterMap());
		Page<PmsProductModel> page = new Page<PmsProductModel>(pageNo, pageSize);
		IPage<PmsProductModel> pageList = pmsProductModelService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * 添加
	 *
	 * @param pmsProductModel
	 * @return
	 */
	@AutoLog(value = "产品型号-添加")
	@ApiOperation(value="产品型号-添加", notes="产品型号-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PmsProductModel pmsProductModel) {
		pmsProductModelService.save(pmsProductModel);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param pmsProductModel
	 * @return
	 */
	@AutoLog(value = "产品型号-编辑")
	@ApiOperation(value="产品型号-编辑", notes="产品型号-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PmsProductModel pmsProductModel) {
		pmsProductModelService.updateById(pmsProductModel);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品型号-通过id删除")
	@ApiOperation(value="产品型号-通过id删除", notes="产品型号-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pmsProductModelService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "产品型号-批量删除")
	@ApiOperation(value="产品型号-批量删除", notes="产品型号-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pmsProductModelService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品型号-通过id查询")
	@ApiOperation(value="产品型号-通过id查询", notes="产品型号-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PmsProductModel pmsProductModel = pmsProductModelService.getById(id);
		return Result.ok(pmsProductModel);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param pmsProductModel
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PmsProductModel pmsProductModel) {
      return super.exportXls(request, pmsProductModel, PmsProductModel.class, "产品型号");
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
      return super.importExcel(request, response, PmsProductModel.class);
  }

     /**
      * 查询所有商品型号
      *
      * @param
      * @return
      */
     @AutoLog(value = "商品品牌-查询所有商品品牌")
     @ApiOperation(value="商品品牌-查询所有商品品牌", notes="商品品牌-查询所有商品品牌")
     @GetMapping(value = "/queryAllModel")
     public Result<?> queryAllModel() {
         QueryWrapper<PmsProductModel> queryWrapper = new QueryWrapper<>();
         queryWrapper.select("model_name");
         return Result.ok(pmsProductModelService.list(queryWrapper));
     }

}
