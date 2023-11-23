package com.ray.ipasample.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ToString
public class MemberForm {
    @NotEmpty(message = "이름은 필수입니다...")
    private String name;
    private String city;
    private String street;
    private String zipcode;

}
