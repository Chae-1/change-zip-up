package com.kosa.chanzipup.api.company.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyServiceTest {

    @Test
    void collectionTest() {
        List<Long> updateList = new LinkedList<>(List.of(1L, 2L, 3L, 4L, 5L));
        List<Long> currentList = new LinkedList<>(List.of(3L, 4L));

        updateList.removeAll(currentList);

        System.out.println(updateList);
    }
}