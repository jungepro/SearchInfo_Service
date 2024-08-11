package org.jeecg.modules.coderQ.controller.wxapp;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jeecg.qywx.api.message.vo.News;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.coderQ.entity.AppNews;
import org.jeecg.modules.coderQ.entity.AppSuggest;
import org.jeecg.modules.coderQ.service.IAppNewsService;
import org.jeecg.modules.coderQ.service.IAppSuggestService;
import org.jeecg.modules.coderQ.service.IAppYonghuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("wxapp")
public class FrontController {

    @Autowired
    private IAppNewsService newsService;

    @Autowired
    private IAppSuggestService suggestService;


    @GetMapping("getNews")
    public Result<?> getNews(String title) {
        LambdaQueryWrapper<AppNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(AppNews::getTitle, title);
        queryWrapper.orderByDesc(AppNews::getCreateTime);
        List<AppNews> list = newsService.list(queryWrapper);
        return Result.OK(list);
    }

    @GetMapping("getNewsById")
    public Result<?> getNewsById(String id) {
        AppNews byId = newsService.getById(id);
        return Result.OK(byId);
    }

    @GetMapping("doSuggest")
    public Result<?> doSuggest(String phone, String text) {
        AppSuggest suggest = new AppSuggest();
        suggest.setInfo(text);
        suggest.setUserphone(phone);
        suggestService.save(suggest);
        return Result.OK();
    }


    @GetMapping("getSuggest")
    public Result<?> getSuggest(String phone) {
        LambdaQueryWrapper<AppSuggest> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppSuggest::getUserphone, phone);
        queryWrapper.orderByDesc(AppSuggest::getCreateTime);
        List<AppSuggest> list = suggestService.list(queryWrapper);
        return Result.OK(list);
    }
}
