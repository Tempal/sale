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
import org.jeecg.modules.sale.cms.entity.CmsCustomerContact;
import org.jeecg.modules.sale.cms.service.ICmsCustomerContactService;
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
 * @Description: 联系人管理
 * @Author: jeecg-boot
 * @Date:   2020-04-27
 * @Version: V1.0
 */
@Slf4j
@Api(tags="联系人管理")
@RestController
@RequestMapping("/cms/cmsCustomerContact")
public class CmsCustomerContactController extends JeecgController<CmsCustomerContact, ICmsCustomerContactService> {
	@Autowired
	private ICmsCustomerContactService cmsCustomerContactService;

	/**
	 * 分页列表查询
	 *
	 * @param cmsCustomerContact
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "联系人管理-分页列表查询")
	@ApiOperation(value="联系人管理-分页列表查询", notes="联系人管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CmsCustomerContact cmsCustomerContact,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CmsCustomerContact> queryWrapper = QueryGenerator.initQueryWrapper(cmsCustomerContact, req.getParameterMap());
		Page<CmsCustomerContact> page = new Page<CmsCustomerContact>(pageNo, pageSize);
		IPage<CmsCustomerContact> pageList = cmsCustomerContactService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * 添加
	 *
	 * @param cmsCustomerContact
	 * @return
	 */
	@AutoLog(value = "联系人管理-添加")
	@ApiOperation(value="联系人管理-添加", notes="联系人管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CmsCustomerContact cmsCustomerContact) {
		cmsCustomerContactService.save(cmsCustomerContact);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param cmsCustomerContact
	 * @return
	 */
	@AutoLog(value = "联系人管理-编辑")
	@ApiOperation(value="联系人管理-编辑", notes="联系人管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CmsCustomerContact cmsCustomerContact) {
		cmsCustomerContactService.updateById(cmsCustomerContact);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "联系人管理-通过id删除")
	@ApiOperation(value="联系人管理-通过id删除", notes="联系人管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		cmsCustomerContactService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "联系人管理-批量删除")
	@ApiOperation(value="联系人管理-批量删除", notes="联系人管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.cmsCustomerContactService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "联系人管理-通过id查询")
	@ApiOperation(value="联系人管理-通过id查询", notes="联系人管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CmsCustomerContact cmsCustomerContact = cmsCustomerContactService.getById(id);
		return Result.ok(cmsCustomerContact);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param cmsCustomerContact
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, CmsCustomerContact cmsCustomerContact) {
      return super.exportXls(request, cmsCustomerContact, CmsCustomerContact.class, "联系人管理");
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
      return super.importExcel(request, response, CmsCustomerContact.class);
  }

	 /**
	  * 查询联系人
	  *
	  * @param
	  * @return
	  */
	 @AutoLog(value = "联系人管理-查询所有联系人")
	 @ApiOperation(value="联系人管理-查询所有联系人", notes="订单类型-查询所有联系人")
	 @GetMapping(value = "/queryAllContact")
	 public Result<?> queryAllContact( ) {
		 QueryWrapper<CmsCustomerContact> queryWrapper = new QueryWrapper<>();
		 queryWrapper.select("id,username,company,phone,address");
		 return Result.ok(cmsCustomerContactService.list(queryWrapper));
	 }

}
