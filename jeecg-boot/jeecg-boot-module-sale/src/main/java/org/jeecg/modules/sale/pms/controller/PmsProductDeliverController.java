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
import org.jeecg.modules.sale.pms.entity.PmsProductDeliver;
import org.jeecg.modules.sale.pms.service.IPmsProductDeliverService;
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
 * @Description: 物流信息
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Slf4j
@Api(tags="物流信息")
@RestController
@RequestMapping("/pms/pmsProductDeliver")
public class PmsProductDeliverController extends JeecgController<PmsProductDeliver, IPmsProductDeliverService> {
	@Autowired
	private IPmsProductDeliverService pmsProductDeliverService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pmsProductDeliver
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "物流信息-分页列表查询")
	@ApiOperation(value="物流信息-分页列表查询", notes="物流信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PmsProductDeliver pmsProductDeliver,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PmsProductDeliver> queryWrapper = QueryGenerator.initQueryWrapper(pmsProductDeliver, req.getParameterMap());
		Page<PmsProductDeliver> page = new Page<PmsProductDeliver>(pageNo, pageSize);
		IPage<PmsProductDeliver> pageList = pmsProductDeliverService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param pmsProductDeliver
	 * @return
	 */
	@AutoLog(value = "物流信息-添加")
	@ApiOperation(value="物流信息-添加", notes="物流信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PmsProductDeliver pmsProductDeliver) {
		pmsProductDeliverService.save(pmsProductDeliver);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param pmsProductDeliver
	 * @return
	 */
	@AutoLog(value = "物流信息-编辑")
	@ApiOperation(value="物流信息-编辑", notes="物流信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PmsProductDeliver pmsProductDeliver) {
		pmsProductDeliverService.updateById(pmsProductDeliver);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物流信息-通过id删除")
	@ApiOperation(value="物流信息-通过id删除", notes="物流信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pmsProductDeliverService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "物流信息-批量删除")
	@ApiOperation(value="物流信息-批量删除", notes="物流信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pmsProductDeliverService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物流信息-通过id查询")
	@ApiOperation(value="物流信息-通过id查询", notes="物流信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PmsProductDeliver pmsProductDeliver = pmsProductDeliverService.getById(id);
		return Result.ok(pmsProductDeliver);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param pmsProductDeliver
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PmsProductDeliver pmsProductDeliver) {
      return super.exportXls(request, pmsProductDeliver, PmsProductDeliver.class, "物流信息");
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
      return super.importExcel(request, response, PmsProductDeliver.class);
  }

}
