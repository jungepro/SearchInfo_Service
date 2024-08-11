package org.jeecg.modules.coderQ.controller.wxapp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.coderQ.entity.AppYonghu;
import org.jeecg.modules.coderQ.service.IAppYonghuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 *
 */
@RestController
@RequestMapping("wxapp")
public class DoLoginController {

    @Autowired
    private IAppYonghuService yonghuService;


    @GetMapping("toLogin")
    public Result<?> toLogin(String account, String pwd) {
        LambdaQueryWrapper<AppYonghu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppYonghu::getPhone, account);
        queryWrapper.eq(AppYonghu::getPwd, pwd);
        AppYonghu one = yonghuService.getOne(queryWrapper);
        if (one != null) {
            return Result.OK(one);
        } else {
            return Result.error("");
        }
    }


    @PostMapping("toRegister")
    public Result<?> toRegister(@RequestBody AppYonghu yonghu) {
        yonghuService.save(yonghu);
        return Result.OK(yonghu);
    }


    @GetMapping("doUpdatePwd")
    public Result<?> doUpdatePwd(String id, String pwd) {
        LambdaUpdateWrapper<AppYonghu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(AppYonghu::getPwd,pwd);
        updateWrapper.eq(AppYonghu::getId,id);
        yonghuService.update(updateWrapper);
        return Result.OK();
    }
}
