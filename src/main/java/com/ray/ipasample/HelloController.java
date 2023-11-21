package com.ray.ipasample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("hello")
    public String hello(Model model) {
        Student s = new Student();
        s.setName("ray");

        model.addAttribute("name", s.getName());

        return "hello";
    }
}
