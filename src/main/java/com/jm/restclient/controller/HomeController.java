package com.jm.restclient.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping("/")
    public String getHomePage(Model model) {
        String url = "http://91.241.64.178:7081/api/users";
        String code = RestTemplateClient.getCode(url);
        String message = "YOUR CODE";
        model.addAttribute("message", message);
        model.addAttribute("code", code);
        return "index";
    }

}
