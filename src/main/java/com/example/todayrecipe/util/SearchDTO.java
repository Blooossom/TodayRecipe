package com.example.todayrecipe.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchDTO {

    private int page;
    private int recordSize;
    private int pageSize;
    private String keyword;
    private String searchType;
    private Pagination pagination;


    public SearchDTO(){
        this.page = 1;
        this.recordSize = 10;
        this.pageSize = 10;
    }



}
