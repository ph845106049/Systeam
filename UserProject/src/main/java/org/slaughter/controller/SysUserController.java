package org.slaughter.controller;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import main.java.org.slaughter.utils.SaltMD5Util;
import main.java.org.slaughter.utils.StringRedisOps;
import org.slaughter.filter.LoginFilter;
import org.slaughter.service.SysUserService;
import org.slaughter.utils.R;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xinyang
 * @since 2024-01-29
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private StringRedisOps stringRedisOps;

    @GetMapping
    public R getPage(Page page ){
        return R.ok(sysUserService.page(page));
    }

    @PutMapping
    public R insert(@RequestBody org.slaughter.entity.SysUserEntity sysUser){
        org.slaughter.entity.SysUserEntity newSysUser = sysUserService.getOne(Wrappers.<org.slaughter.entity.SysUserEntity>lambdaQuery().eq(org.slaughter.entity.SysUserEntity::getUserName,sysUser.getUserName()));
        if(newSysUser != null){
            return R.error("用户名重复");
        }
        sysUser.setPassword(SaltMD5Util.generateSaltPassword(sysUser.getPassword()));
        return R.ok(sysUserService.save(sysUser));
    }

    @PostMapping
    public R update(@RequestBody org.slaughter.entity.SysUserEntity sysUser){
        return R.ok(sysUserService.updateById(sysUser));
    }

    @DeleteMapping
    public R delete(@RequestBody org.slaughter.entity.SysUserEntity sysUser){
        return R.ok(sysUserService.removeById(sysUser));
    }


    @PostMapping("login")
    public R login(@RequestBody org.slaughter.entity.SysUserEntity sysUser){


        return R.ok(sysUserService.save(sysUser));
    }

//    @PostMapping("logOut")
//    public R logOut(@RequestBody org.slaughter.entity.SysUserEntity sysUser){
//
//
//        return R.ok();
//    }

    @GetMapping("/toLogin")
    public String toLogin(Model model, HttpServletRequest request) {
        Object userInfo = request.getSession().getAttribute(LoginFilter.USER_INFO);
        //不为空，则是已登陆状态
        if (null != userInfo){
            String ticket = UUID.randomUUID().toString();
            stringRedisOps.opsForValue().set(ticket, JSONObject.toJSONString(userInfo),2, TimeUnit.SECONDS);
            return "redirect:"+request.getParameter("url")+"?ticket="+ticket;
        }
        org.slaughter.entity.SysUserEntity user = new org.slaughter.entity.SysUserEntity();
        user.setUserName("laowang");
        user.setPassword("laowang");
//        user.setBackurl(request.getParameter("url"));
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public void login(@ModelAttribute org.slaughter.entity.SysUserEntity user, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        System.out.println("backurl:"+user.getBackurl());
        request.getSession().setAttribute(LoginFilter.USER_INFO,user);
        //登陆成功，创建用户信息票据
        String ticket = UUID.randomUUID().toString();
        stringRedisOps.opsForValue().set(ticket,JSONObject.toJSONString(user),20, TimeUnit.SECONDS);
        //重定向，回原url  ---a.com
        if (null == user.getBackurl() || user.getBackurl().length()==0){
            response.sendRedirect("/index");
        } else {
            response.sendRedirect(user.getBackurl()+"?ticket="+ticket);
        }
    }

    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Object user = request.getSession().getAttribute(LoginFilter.USER_INFO);
        org.slaughter.entity.SysUserEntity userInfo = (org.slaughter.entity.SysUserEntity) user;
        modelAndView.setViewName("index");
        modelAndView.addObject("user", userInfo);
        request.getSession().setAttribute("test","123");
        return modelAndView;
    }

}

