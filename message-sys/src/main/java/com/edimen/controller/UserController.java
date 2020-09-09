package com.edimen.controller;


import com.edimen.domain.User;
import com.edimen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户控制层
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username")String username) {
        return userService.findUserByUserName(username);
    }

    //获取所有用户，并跳转到用户列表
    @GetMapping("/getAllUser")
    public ModelAndView getUser(@RequestParam(value = "start",defaultValue = "0")Integer start,
                                @RequestParam(value = "limit",defaultValue = "5")Integer limit) {
        start = start < 0 ? 0 : start;//判断是否是首页
        Sort sort = Sort.by(Sort.Direction.DESC,"id");//根据id倒序排序
        Pageable pageable = PageRequest.of(start,limit,sort);
        Page<User> page = userService.findAll(pageable);
        ModelAndView mv = new ModelAndView();
        mv.addObject("page",page);
        mv.setViewName("user_list");//跳转到用户列表页面
        return mv;
    }

    //跳转到添加用户页面
    @GetMapping(value = "/toAddUser")
    public String toUser_edit() {
        return "addUser";
    }
    //添加用户
    @PostMapping(value = "/addUser")
    public String postUser(User user) {
        User user1 = userService.saveUser(user);
        System.out.println(user1.getName());
        return "redirect:/user/getAllUser";//返回用户列表
    }

    //向修改页面跳转
    @GetMapping("/select/{id}")
    public String getUser(@PathVariable("id") Long id,Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user",user);
        return "user_edit";
    }
    //修改用户
   @PutMapping("/update/{id}")
    public String putUser(@PathVariable Long id,User user) {
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setName(user.getName());
        u.setId(id);
        userService.updateUser(u);
        return "redirect:/user/getAllUser";//返回用户列表
    }

    //删除用户
    @RequestMapping(value = "/deleteUser/{id}") //http://localhost:8080/deleteUser/2
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/user/getAllUser";//返回用户列表
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        //清除session
        session.invalidate();
        //重定向到登录页面的跳转方法
        return "redirect:/toLoginPage";
    }
}
