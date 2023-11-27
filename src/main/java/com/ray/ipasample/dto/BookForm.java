package com.ray.ipasample.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookForm {

    private  Long id; // 상품 수정을 위한 필드

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
