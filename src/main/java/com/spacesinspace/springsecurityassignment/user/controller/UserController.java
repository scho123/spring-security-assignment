package com.spacesinspace.springsecurityassignment.user.controller;

import com.spacesinspace.springsecurityassignment.user.model.dto.SignupDTO;
import com.spacesinspace.springsecurityassignment.user.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("signup")
    public void signup() {}

    @PostMapping("signup")
    public ModelAndView singup(ModelAndView mv, @ModelAttribute SignupDTO newUserInfo) {
        Integer result = userService.regist(newUserInfo);

        String message = null;

        if (result == null) {
            message = "이미 해당 정보로 가입된 회원이 조회됩니다.";
            System.out.println(message);
            mv.setViewName("user/signup");
        } else if (result == 0) {
            message = "회원가입에 실패했습니다. 다시 시도해주세요";
            System.out.println(message);
            mv.setViewName("/user/signup");
        } else if (result >= 1) {
            message = "회원가입이 성공적으로 완료되었습니다";
            System.out.println(message);
            mv.setViewName("auth/login");
        } else {
            message = "알 수 없는 오류가 발생했습니다. 관리자에게 문의해주세요.";
            System.out.println(message);
            mv.setViewName("user/signup");
        }

        mv.addObject("message", message);

        return mv;
    }
}
