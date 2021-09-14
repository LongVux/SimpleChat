package com.example.simplechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");
        String topic = (String) request.getSession().getAttribute("topic");

        if(username == null || username.isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);

        if(topic != null && !topic.isEmpty()){
            model.addAttribute("topic", topic);
        }

        return "chat";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "" ) String username, @RequestParam(defaultValue = "publicChatRoom") String topic) {
        username = username.trim();

        if(username.isEmpty()) {
            return "login";
        }
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("topic", topic);

        return "redirect:/";
    }

    @RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession(true).invalidate();

        return "redirect:/login";
    }
}
