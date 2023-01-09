package com.example.todayrecipe.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    private int totalRecordCount;

    private int totalPageCount;

    private int startPage;

    private int endPage;

    private int limitStart;

    private boolean existPrevPge;

    private boolean existNextPage;

    public Pagination(int totalRecordCount, SearchDTO params) {
        if (totalRecordCount > 0) {
            this.totalRecordCount = totalRecordCount;
            this.calculation(params);
        }
    }

    public void calculation(SearchDTO params) {
        totalRecordCount = ((totalRecordCount - 1) / params.getRecordSize()) + 1;

        if (params.getPage() > totalPageCount) {
            params.setPage(totalPageCount);
        }

        startPage = ((params.getPage() - 1) / params.getPageSize()) * params.getPageSize() + 1;

        endPage = startPage + params.getPageSize() - 1;

        if (endPage > totalPageCount) {
            endPage = totalPageCount;
        }

        limitStart = (params.getPage() - 1) * params.getRecordSize();

        existPrevPge = startPage != 1;

        existNextPage = (endPage * params.getRecordSize()) < totalRecordCount;

    }




}
