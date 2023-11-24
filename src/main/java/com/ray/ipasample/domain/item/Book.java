package com.ray.ipasample.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "B") // dtype의 컬럼 값이 B이면 Book이 된다
public class Book extends Item {

    private String author;
    private String isbn;
}
