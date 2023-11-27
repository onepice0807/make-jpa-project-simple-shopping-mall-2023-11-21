package com.ray.ipasample.controller;

import com.ray.ipasample.domain.Address;
import com.ray.ipasample.domain.Member;
import com.ray.ipasample.dto.MemberForm;
import com.ray.ipasample.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor // final이 있는 필드만 생성자를 만들어 줌
public class MemberController {

//    @Autowired
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createMemberForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Valid MemberForm memberForm, BindingResult bindingResult) throws Exception {
        System.out.println(memberForm.toString());

        if (bindingResult.hasErrors()) {  // 유효성 검사에 에러가 있다면...
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model) {
        model.addAttribute("memberList", memberService.findMembers());

        return "members/memberList";
    }
}
