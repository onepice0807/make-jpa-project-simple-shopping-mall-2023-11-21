package com.ray.ipasample.controller;

import com.ray.ipasample.dto.MemberForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class MemberController {

    public String createMember(Model model) {
    model.addAttribute("memberForm", new MemberForm());
    return "members/createMemberForm";
    }
}
