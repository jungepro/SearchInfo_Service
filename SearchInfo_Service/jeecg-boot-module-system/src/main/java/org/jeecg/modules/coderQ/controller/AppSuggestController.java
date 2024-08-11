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
import org.jeecg.modules.coderQ.entity.AppSuggest;
import org.jeecg.modules.coderQ.service.IAppSuggestService;

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
 * @Description: app_suggest
 * @Author: coderH
 * @Date: 2023-04-28
 * @Version: V1.0
 */
@Api(tags = "app_suggest")
@RestController
@RequestMapping("/coderQ/appSuggest")
@Slf4j
public class AppSuggestController extends JeecgController<AppSuggest, IAppSuggestService> {
    @Autowired
    private IAppSuggestService appSuggestService;

    /**
     * 分页列表查询
     *
     * @param appSuggest
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "app_suggest-分页列表查询")
    @ApiOperation(value = "app_suggest-分页列表查询", notes = "app_suggest-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(AppSuggest appSuggest,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        LambdaQueryWrapper<AppSuggest> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(appSuggest.getUserphone()), AppSuggest::getUserphone, appSuggest.getUserphone());
        queryWrapper.orderByDesc(AppSuggest::getCreateTime);
        Page<AppSuggest> page = new Page<AppSuggest>(pageNo, pageSize);
        IPage<AppSuggest> pageList = appSuggestService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param appSuggest
     * @return
     */
    @AutoLog(value = "app_suggest-添加")
    @ApiOperation(value = "app_suggest-添加", notes = "app_suggest-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody AppSuggest appSuggest) {
        appSuggestService.save(appSuggest);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param appSuggest
     * @return
     */
    @AutoLog(value = "app_suggest-编辑")
    @ApiOperation(value = "app_suggest-编辑", notes = "app_suggest-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody AppSuggest appSuggest) {
        appSuggestService.updateById(appSuggest);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "app_suggest-通过id删除")
    @ApiOperation(value = "app_suggest-通过id删除", notes = "app_suggest-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        appSuggestService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "app_suggest-批量删除")
    @ApiOperation(value = "app_suggest-批量删除", notes = "app_suggest-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.appSuggestService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "app_suggest-通过id查询")
    @ApiOperation(value = "app_suggest-通过id查询", notes = "app_suggest-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        AppSuggest appSuggest = appSuggestService.getById(id);
        if (appSuggest == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(appSuggest);
    }

}
