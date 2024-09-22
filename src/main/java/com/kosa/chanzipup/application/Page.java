package com.kosa.chanzipup.application;

import java.util.Collection;
import lombok.Getter;


@Getter
public class Page<T extends Collection> {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private static final int START_PAGE_NUMBER = 1;

    private T list;

    private int currentPage;

    private int totalPage;

    private int pageSize;

    public Page(T list, int pageSize) {
        this.list = list;
        this.pageSize = pageSize;
        this.currentPage = START_PAGE_NUMBER;
        this.totalPage = Integer.valueOf(calculateTotalPage(list, pageSize));
    }

    private int calculateTotalPage(T list, int pageSize) {
        return (int) Math.ceil((double) list.size() / pageSize);
    }

    public static <T extends Collection> Page<T> ofDefault(T list) {
        return new Page<>(list, DEFAULT_PAGE_SIZE);
    }
}
