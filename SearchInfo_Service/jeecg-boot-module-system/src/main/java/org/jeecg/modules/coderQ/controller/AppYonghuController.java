package org.jeecg.modules.coderQ.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.coderQ.entity.AppYonghu;
import org.jeecg.modules.coderQ.service.IAppYonghuService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: app_yonghu
 * @Author: coderH
 * @Date: 2023-04-28
 * @Version: V1.0
 */
@Api(tags = "app_yonghu")
@RestController
@RequestMapping("/coderQ/appYonghu")
@Slf4j
public class AppYonghuController extends JeecgController<AppYonghu, IAppYonghuService> {
    @Autowired
    private IAppYonghuService appYonghuService;

    /**
     * 分页列表查询
     *
     * @param appYonghu
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "app_yonghu-分页列表查询")
    @ApiOperation(value = "app_yonghu-分页列表查询", notes = "app_yonghu-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(AppYonghu appYonghu,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        LambdaQueryWrapper<AppYonghu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(appYonghu.getPhone()), AppYonghu::getPhone, appYonghu.getPhone());
        queryWrapper.orderByDesc(AppYonghu::getCreateTime);
        Page<AppYonghu> page = new Page<AppYonghu>(pageNo, pageSize);
        IPage<AppYonghu> pageList = appYonghuService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param appYonghu
     * @return
     */
    @AutoLog(value = "app_yonghu-添加")
    @ApiOperation(value = "app_yonghu-添加", notes = "app_yonghu-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody AppYonghu appYonghu) {
        appYonghuService.save(appYonghu);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param appYonghu
     * @return
     */
    @AutoLog(value = "app_yonghu-编辑")
    @ApiOperation(value = "app_yonghu-编辑", notes = "app_yonghu-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody AppYonghu appYonghu) {
        appYonghuService.updateById(appYonghu);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "app_yonghu-通过id删除")
    @ApiOperation(value = "app_yonghu-通过id删除", notes = "app_yonghu-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        appYonghuService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "app_yonghu-批量删除")
    @ApiOperation(value = "app_yonghu-批量删除", notes = "app_yonghu-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.appYonghuService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "app_yonghu-通过id查询")
    @ApiOperation(value = "app_yonghu-通过id查询", notes = "app_yonghu-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        AppYonghu appYonghu = appYonghuService.getById(id);
        if (appYonghu == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(appYonghu);
    }

}
