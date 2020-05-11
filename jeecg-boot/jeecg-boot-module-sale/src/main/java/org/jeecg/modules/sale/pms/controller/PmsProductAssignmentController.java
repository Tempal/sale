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
import org.jeecg.modules.sale.pms.entity.PmsProductAssignment;
import org.jeecg.modules.sale.pms.entity.PmsProductInfo;
import org.jeecg.modules.sale.pms.service.IPmsProductAssignmentService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.sale.pms.service.IPmsProductInfoService;
import org.jeecg.modules.sale.pms.vo.PmsProductAssignmentPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 货物分配
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Slf4j
@Api(tags="货物分配")
@RestController
@RequestMapping("/pms/pmsProductAssignment")
public class PmsProductAssignmentController extends JeecgController<PmsProductAssignment, IPmsProductAssignmentService> {
	@Autowired
	private IPmsProductAssignmentService pmsProductAssignmentService;

	 @Autowired
	 private IPmsProductInfoService pmsProductInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param pmsProductAssignment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "货物分配-分页列表查询")
	@ApiOperation(value="货物分配-分页列表查询", notes="货物分配-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PmsProductAssignment pmsProductAssignment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PmsProductAssignment> queryWrapper = QueryGenerator.initQueryWrapper(pmsProductAssignment, req.getParameterMap());
		Page<PmsProductAssignment> page = new Page<PmsProductAssignment>(pageNo, pageSize);
		IPage<PmsProductAssignment> pageList = pmsProductAssignmentService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * 添加
	 * 修改 tempal
	 *
	 * @param pmsProductAssignmentPage
	 * @return
	 */
	@AutoLog(value = "货物分配-添加")
	@ApiOperation(value="货物分配-添加", notes="货物分配-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PmsProductAssignmentPage pmsProductAssignmentPage) {
		PmsProductAssignment pmsProductAssignment=new PmsProductAssignment();
		BeanUtils.copyProperties(pmsProductAssignmentPage, pmsProductAssignment);
		pmsProductAssignmentService.saveMain(pmsProductAssignment,pmsProductAssignmentPage.getPmsProductInfoList());
//		pmsProductAssignmentService.save(pmsProductAssignment);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param pmsProductAssignmentPage
	 * @return
	 */
	@AutoLog(value = "货物分配-编辑")
	@ApiOperation(value="货物分配-编辑", notes="货物分配-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PmsProductAssignmentPage pmsProductAssignmentPage) {
		PmsProductAssignment pmsProductAssignment=new PmsProductAssignment();
		BeanUtils.copyProperties(pmsProductAssignmentPage, pmsProductAssignment);
		pmsProductAssignmentService.updateMain(pmsProductAssignment,pmsProductAssignmentPage.getPmsProductInfoList());
//		pmsProductAssignmentService.updateById(pmsProductAssignment);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "货物分配-通过id删除")
	@ApiOperation(value="货物分配-通过id删除", notes="货物分配-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pmsProductAssignmentService.delMain(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "货物分配-批量删除")
	@ApiOperation(value="货物分配-批量删除", notes="货物分配-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pmsProductAssignmentService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "货物分配-通过id查询")
	@ApiOperation(value="货物分配-通过id查询", notes="货物分配-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PmsProductAssignment pmsProductAssignment = pmsProductAssignmentService.getById(id);
		return Result.ok(pmsProductAssignment);
	}

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "货物分配-通过id查询货物详情")
	 @ApiOperation(value="货物分配-通过id查询货物详情", notes="货物分配-通过id查询货物详情")
	 @GetMapping(value = "/queryProductInfoById")
	 public Result<?> queryProductInfoById(@RequestParam(name="id",required=true) String id) {
		 List<PmsProductInfo> pmsProductInfoList = pmsProductInfoService.selectTicketsByMainId(id);
		 return Result.ok(pmsProductInfoList);
	 }

  /**
   * 导出excel,暂时有问题
   *
   * @param request
   * @param pmsProductAssignment
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PmsProductAssignment pmsProductAssignment) {
      return super.exportXls(request, pmsProductAssignment, PmsProductAssignment.class, "货物分配");
  }

  /**
   * 通过excel导入数据,暂时有问题
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      return super.importExcel(request, response, PmsProductAssignment.class);
  }

}
