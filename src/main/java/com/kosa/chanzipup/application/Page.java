package com.kosa.chanzipup.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;


@Getter
public class Page<T extends Collection> {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private static final int START_PAGE_NUMBER = 0;

    private T list;

    private int startPageNumber = START_PAGE_NUMBER;

    private int pageNumber;

    private int totalPage;

    private int pageSize;

    public Page(T list, int pageSize, int pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalPage = Integer.valueOf(calculateTotalPage(list.size(), pageSize));
        this.list = list;
    }

    private int calculateTotalPage(int size, int pageSize) {
        return (int) Math.ceil((double) size / pageSize);
    }

    public static <T extends Collection> Page<T> ofDefault(T list) {
        return new Page<>(list, DEFAULT_PAGE_SIZE, START_PAGE_NUMBER);
    }

    public static <T extends Collection> Page<T> of(T list, int pageSize, int pageNumber) {
        return new Page<>(list, pageSize, pageNumber);
    }

    public int getOffset() {
        return pageNumber * pageNumber;
    }

    public Collection getSlice() {
        int offset = getOffset();

        return this.list.stream()
                .skip(offset)
                .limit(pageSize)
                .toList();

    }

}
