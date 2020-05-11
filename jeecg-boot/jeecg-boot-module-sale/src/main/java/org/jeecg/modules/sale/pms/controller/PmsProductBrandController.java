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
import org.jeecg.modules.sale.pms.entity.PmsProductBrand;
import org.jeecg.modules.sale.pms.service.IPmsProductBrandService;
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
 * @Description: 商品品牌
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品品牌")
@RestController
@RequestMapping("/pms/pmsProductBrand")
public class PmsProductBrandController extends JeecgController<PmsProductBrand, IPmsProductBrandService> {
	@Autowired
	private IPmsProductBrandService pmsProductBrandService;

	/**
	 * 分页列表查询
	 *
	 * @param pmsProductBrand
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "商品品牌-分页列表查询")
	@ApiOperation(value="商品品牌-分页列表查询", notes="商品品牌-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PmsProductBrand pmsProductBrand,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PmsProductBrand> queryWrapper = QueryGenerator.initQueryWrapper(pmsProductBrand, req.getParameterMap());
		Page<PmsProductBrand> page = new Page<PmsProductBrand>(pageNo, pageSize);
		IPage<PmsProductBrand> pageList = pmsProductBrandService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * 添加
	 *
	 * @param pmsProductBrand
	 * @return
	 */
	@AutoLog(value = "商品品牌-添加")
	@ApiOperation(value="商品品牌-添加", notes="商品品牌-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PmsProductBrand pmsProductBrand) {
		pmsProductBrandService.save(pmsProductBrand);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param pmsProductBrand
	 * @return
	 */
	@AutoLog(value = "商品品牌-编辑")
	@ApiOperation(value="商品品牌-编辑", notes="商品品牌-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PmsProductBrand pmsProductBrand) {
		pmsProductBrandService.updateById(pmsProductBrand);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品品牌-通过id删除")
	@ApiOperation(value="商品品牌-通过id删除", notes="商品品牌-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pmsProductBrandService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品品牌-批量删除")
	@ApiOperation(value="商品品牌-批量删除", notes="商品品牌-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pmsProductBrandService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品品牌-通过id查询")
	@ApiOperation(value="商品品牌-通过id查询", notes="商品品牌-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PmsProductBrand pmsProductBrand = pmsProductBrandService.getById(id);
		return Result.ok(pmsProductBrand);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param pmsProductBrand
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PmsProductBrand pmsProductBrand) {
      return super.exportXls(request, pmsProductBrand, PmsProductBrand.class, "商品品牌");
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
      return super.importExcel(request, response, PmsProductBrand.class);
  }
	 /**
	  * 查询所有商品品牌
	  *
	  * @param
	  * @return
	  */
	 @AutoLog(value = "商品品牌-查询所有商品品牌")
	 @ApiOperation(value="商品品牌-查询所有商品品牌", notes="商品品牌-查询所有商品品牌")
	 @GetMapping(value = "/queryAllBrand")
	 public Result<?> queryAllBrand() {
		 QueryWrapper<PmsProductBrand> queryWrapper = new QueryWrapper<>();
		 queryWrapper.select("brand_name");
		 return Result.ok(pmsProductBrandService.list(queryWrapper));
	 }
}
