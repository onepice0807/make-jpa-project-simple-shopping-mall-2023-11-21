package com.ray.ipasample.controller;


import com.ray.ipasample.Exception.NotEnoughStockException;
import com.ray.ipasample.domain.Member;
import com.ray.ipasample.domain.item.Item;
import com.ray.ipasample.dto.OrderSearchCriteria;
import com.ray.ipasample.service.ItemService;
import com.ray.ipasample.service.MemberService;
import com.ray.ipasample.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping(value="/order")
    public String createOrderForm(Model model) {

        List<Member> memberList = memberService.findMembers();
        List<Item> items = itemService.findAll();

        model.addAttribute("members", memberList);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping(value="/order")
    public String createOrder(@RequestParam("memberId") long memberId,
                              @RequestParam("itemId") long itemId,
                              @RequestParam("count") int count) throws NotEnoughStockException {
        orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(Model model, @ModelAttribute("orderSearch")OrderSearchCriteria orderSearch) {
        model.addAttribute("orders", orderService.findOrder(orderSearch));

        return "order/orderList";
    }

    // 주문 취소
    @PostMapping(value="/orders/{orderId}/orderCancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }

}
