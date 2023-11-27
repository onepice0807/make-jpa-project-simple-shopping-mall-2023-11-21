package com.ray.ipasample.controller;

import com.ray.ipasample.domain.item.Book;
import com.ray.ipasample.domain.item.Item;
import com.ray.ipasample.dto.BookForm;
import com.ray.ipasample.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor // final이 있는 필드만 생성자를 만들어 줌
public class itemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createItemForm(Model model) {
        model.addAttribute("itemForm", new BookForm());

        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String createItem(BookForm bookForm) {
        System.out.println(bookForm.toString());

//        Book newBook = new Book();
//        newBook.setName(bookForm.getName());
//        newBook.setPrice(bookForm.getPrice());
//        newBook.setStockQuantity(bookForm.getStockQuantity());
//        newBook.setAuthor(bookForm.getAuthor());
//        newBook.setIsbn(bookForm.getIsbn());

        Book newBook = new Book(bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity(), bookForm.getAuthor(), bookForm.getIsbn());
        this.itemService.saveItem(newBook);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String List(Model model) {
        List<Item> items = this.itemService.findAll();
        model.addAttribute("items", items);

        return  "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book)this.itemService.findOne(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setName(item.getName());
        bookForm.setId(item.getId());
        bookForm.setPrice(item.getPrice());
        bookForm.setStockQuantity(item.getStockQuantity());
        bookForm.setAuthor(item.getAuthor());
        bookForm.setIsbn(item.getIsbn());

        model.addAttribute("form", bookForm);

        return "items/updateItemForm";

    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm bookform) {
        
        // ItemRepository.save() 에 의해 merge()로 업데이트 시킨 구문이다. marge()를 사용한 코드 -> 모든 속성이 update가 됨
        // 병함시 값이 null 인 값도 업데이트가 된다 -> 위험
        
//        Book updatedBook = new Book(bookform.getName(), bookform.getPrice(), bookform.getStockQuantity(), bookform.getAuthor(), bookform.getIsbn());
//        updatedBook.setId(itemId); // 수정될 상품의 id
//
//        this.itemService.saveItem(updatedBook); 
        
        // 변경 감지 기법을 사용하는 것이 바람직하다
        this.itemService.updateItem(itemId,bookform.getName(), bookform.getPrice(), bookform.getStockQuantity(), bookform.getAuthor(), bookform.getIsbn());

        return "redirect:/items";
    }

}
