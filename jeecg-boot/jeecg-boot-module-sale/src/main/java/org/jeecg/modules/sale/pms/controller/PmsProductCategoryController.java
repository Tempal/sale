package org.jeecg.modules.sale.pms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.sale.pms.entity.PmsProductCategory;
import org.jeecg.modules.sale.pms.service.IPmsProductCategoryService;
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
 * @Description: 产品分类
 * @Author: jeecg-boot
 * @Date:   2020-04-25
 * @Version: V1.0
 */
@Slf4j
@Api(tags="产品分类")
@RestController
@RequestMapping("/pms/pmsProductCategory")
public class PmsProductCategoryController extends JeecgController<PmsProductCategory, IPmsProductCategoryService> {
	@Autowired
	private IPmsProductCategoryService pmsProductCategoryService;

	/**
	 * 分页列表查询
	 *
	 * @param pmsProductCategory
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "产品分类-分页列表查询")
	@ApiOperation(value="产品分类-分页列表查询", notes="产品分类-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PmsProductCategory pmsProductCategory,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PmsProductCategory> queryWrapper = QueryGenerator.initQueryWrapper(pmsProductCategory, req.getParameterMap());
		Page<PmsProductCategory> page = new Page<PmsProductCategory>(pageNo, pageSize);
		IPage<PmsProductCategory> pageList = pmsProductCategoryService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * 添加
	 *
	 * @param pmsProductCategory
	 * @return
	 */
	@AutoLog(value = "产品分类-添加")
	@ApiOperation(value="产品分类-添加", notes="产品分类-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PmsProductCategory pmsProductCategory) {
		pmsProductCategoryService.save(pmsProductCategory);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param pmsProductCategory
	 * @return
	 */
	@AutoLog(value = "产品分类-编辑")
	@ApiOperation(value="产品分类-编辑", notes="产品分类-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PmsProductCategory pmsProductCategory, HttpServletRequest request) {
		// find current user
        String username = JwtUtil.getUserNameByToken(request);
//		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		pmsProductCategory.setUpdateBy(username);
      pmsProductCategory.setUpdateTime(new Date());
		pmsProductCategoryService.updateById(pmsProductCategory);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品分类-通过id删除")
	@ApiOperation(value="产品分类-通过id删除", notes="产品分类-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pmsProductCategoryService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "产品分类-批量删除")
	@ApiOperation(value="产品分类-批量删除", notes="产品分类-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pmsProductCategoryService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品分类-通过id查询")
	@ApiOperation(value="产品分类-通过id查询", notes="产品分类-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PmsProductCategory pmsProductCategory = pmsProductCategoryService.getById(id);
		return Result.ok(pmsProductCategory);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param pmsProductCategory
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PmsProductCategory pmsProductCategory) {
      return super.exportXls(request, pmsProductCategory, PmsProductCategory.class, "产品分类");
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
      return super.importExcel(request, response, PmsProductCategory.class);
  }

     /**
      * 查询总数
      *
      * @param
      * @return
      */
     @AutoLog(value = "产品分类-查询目前所有的数量")
     @ApiOperation(value="产品分类-查询目前所有的数量", notes="产品分类-查询目前所有的数量")
     @GetMapping(value = "/queryCount")
     public Result<?> queryCount() {
         Integer i = pmsProductCategoryService.count();
         return Result.ok(i);
     }

}
