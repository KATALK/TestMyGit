package com.edimen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
//登录控制
@Controller
public class LoginController {

    @GetMapping("/toLoginPage")
    public String toLoginPage(Model model){
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }

    @PostMapping("/toLoginPage")
    public String toUserPage(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session,Model model){
        if (username==null || password==null) {
            session.setAttribute("loginUser",username);
            model.addAttribute("msg","您好，请输入账户和密码");
            System.out.println(username+password);
            return "login";
            //管理员账户
        }
        if(username.equals("admin")&&password.equals("admin")){
            session.setAttribute("loginUser",username);
            System.out.println(username+password);
            return "redirect:/user/getAllUser";//登录成功跳转到用户列表
        }else {
            session.setAttribute("loginUser",username);
            System.out.println(username.equals("admin"));
            model.addAttribute("msg","您非管理员或账户密码有误，请重新输入");
            return "login";
        }
    }

}
