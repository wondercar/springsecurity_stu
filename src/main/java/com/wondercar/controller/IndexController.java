package com.wondercar.controller;

import com.wondercar.entity.Users;
import com.wondercar.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wondercar
 * @since: 2023-03-19
 */
@Controller
public class IndexController {
    @Autowired
    LoginService loginService;

    @GetMapping({"/","/main"})
    public String index() {
        return "main";
    }

    @GetMapping("login")
    public String login() {
        return "/login";
    }

//    @PostMapping("login")
//    public String login(String username, String pwd) {
//        if (username == "" && pwd == "") {
//            throw new NullPointerException();
//        }
//        loginService.loadUserByUsername(username);
//        return "main";
//    }

    @GetMapping("fail")
    public String failUrl(){
        return "fail";
    }

    @GetMapping("/unauth")
    @ResponseBody
    public String accessDeny() {
        return "You do not have access to this page!";
    }

    @GetMapping("findAll")
    @ResponseBody
    public String findAll() {
        return "findAll";
    }

    @GetMapping("find")
    @ResponseBody
    public String find() {
        return "find";
    }

    @PostMapping("success")
    @ResponseBody
    public String success() {
        return "success";
    }

    @PostMapping("failReq")
    @ResponseBody
    public String fail() {
        return "fail";
    }

    /**
     * 带ROLE角色权限鉴权判断
     * @return
     */
    @GetMapping("userAccess")
    @ResponseBody
    @Secured({"ROLE_user"})
    public String userAccess() {
        return "Hello User Access!";
    }

    /**
     * 具有指定菜单访问权限才能访问
     * @return
     */
    @GetMapping("menuAccess")
    @ResponseBody
    /**
     * PreAuthorize在方法执行前进行权限验证 适合验证角色权限
     */
    @PreAuthorize("hasAnyAuthority('menu:system')")
    /**
     * PostAuthorize在方法执行后进行权限验证 适合验证带有返回值的权限
     */
//    @PostAuthorize("hasAnyAuthority('menu:system')")
    public String menuAccess() {
        return "Hello Menu Access!";
    }

    @GetMapping("/postfilterData")
    @ResponseBody
    /**
     * 权限验证后对数据进行过滤 留下username为admin1的元素
     */
    @PostFilter("filterObject.username == 'admin1'")
    public List<Users> postfilterData(){
        List<Users> list = new ArrayList<>();
        list.add(new Users(1L,"zs","123"));
        list.add(new Users(2L,"admin1","456"));
        return list;
    }

    @GetMapping("/prefilterData")
    @ResponseBody
    /**
     * 进入控制器之前对数据进行过滤
     * 留下id能被2整除的元素
     */
    @PreFilter(value = "filterObject.id%2 == 1")
    public List<Users> prefilterData(@RequestBody List<Users> users){
        List<Users> list = new ArrayList<>();
        list.add(new Users(1L,"zs","123"));
        list.add(new Users(2L,"ls","456"));
        return list;
    }
}
