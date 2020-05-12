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
import org.jeecg.modules.sale.cms.entity.CmsCustomerCompany;
import org.jeecg.modules.sale.cms.service.ICmsCustomerCompanyService;
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
 * @Description: 公司管理
 * @Author: jeecg-boot
 * @Date:   2020-04-28
 * @Version: V1.0
 */
@Slf4j
@Api(tags="公司管理")
@RestController
@RequestMapping("/cms/cmsCustomerCompany")
public class CmsCustomerCompanyController extends JeecgController<CmsCustomerCompany, ICmsCustomerCompanyService> {
	@Autowired
	private ICmsCustomerCompanyService cmsCustomerCompanyService;

	/**
	 * 分页列表查询
	 *
	 * @param cmsCustomerCompany
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "公司管理-分页列表查询")
	@ApiOperation(value="公司管理-分页列表查询", notes="公司管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CmsCustomerCompany cmsCustomerCompany,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CmsCustomerCompany> queryWrapper = QueryGenerator.initQueryWrapper(cmsCustomerCompany, req.getParameterMap());
		Page<CmsCustomerCompany> page = new Page<CmsCustomerCompany>(pageNo, pageSize);
		IPage<CmsCustomerCompany> pageList = cmsCustomerCompanyService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * 添加
	 *
	 * @param cmsCustomerCompany
	 * @return
	 */
	@AutoLog(value = "公司管理-添加")
	@ApiOperation(value="公司管理-添加", notes="公司管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CmsCustomerCompany cmsCustomerCompany) {
		cmsCustomerCompanyService.save(cmsCustomerCompany);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param cmsCustomerCompany
	 * @return
	 */
	@AutoLog(value = "公司管理-编辑")
	@ApiOperation(value="公司管理-编辑", notes="公司管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CmsCustomerCompany cmsCustomerCompany) {
		cmsCustomerCompanyService.updateById(cmsCustomerCompany);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "公司管理-通过id删除")
	@ApiOperation(value="公司管理-通过id删除", notes="公司管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		cmsCustomerCompanyService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "公司管理-批量删除")
	@ApiOperation(value="公司管理-批量删除", notes="公司管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.cmsCustomerCompanyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "公司管理-通过id查询")
	@ApiOperation(value="公司管理-通过id查询", notes="公司管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CmsCustomerCompany cmsCustomerCompany = cmsCustomerCompanyService.getById(id);
		return Result.ok(cmsCustomerCompany);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param cmsCustomerCompany
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, CmsCustomerCompany cmsCustomerCompany) {
      return super.exportXls(request, cmsCustomerCompany, CmsCustomerCompany.class, "公司管理");
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
      return super.importExcel(request, response, CmsCustomerCompany.class);
  }
     /**
      * 通过id查询
      *
      * @param
      * @return
      */
     @AutoLog(value = "商品型号-查询所有公司名称")
     @ApiOperation(value="商品型号-查询所有公司名称", notes="商品型号-查询所有公司名称")
     @GetMapping(value = "/queryAllCompany")
     public Result<?> queryAllCompany() {
         QueryWrapper<CmsCustomerCompany> queryWrapper = new QueryWrapper<>();
         queryWrapper.select("name");
         return Result.ok(cmsCustomerCompanyService.list(queryWrapper));
     }


 }
