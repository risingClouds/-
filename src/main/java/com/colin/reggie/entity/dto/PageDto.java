package com.colin.reggie.entity.dto;

import lombok.Data;

@Data
public class PageDto {
    private Integer page;
    private Integer pageSize;
    private String name;
}
