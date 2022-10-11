package ru.gifservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TagServiceTest {

    private static final Double CURRENCY_NOW_RICH = 64.2345;
    private static final Double CURRENCY_YEST_RICH = 64.2332;


    private static final Double CURRENCY_NOW_BROKE = 64.23;
    private static final Double CURRENCY_YEST_BROKE = 64.5;

    @Autowired
    private TagService tagService;


    @Test
    void getRichTagTest() {
        Assertions.assertEquals(tagService.getTag(CURRENCY_NOW_RICH, CURRENCY_YEST_RICH), "rich");
    }

    @Test
    void getBrokeTagTest() {
        Assertions.assertEquals(tagService.getTag(CURRENCY_NOW_BROKE, CURRENCY_YEST_BROKE), "broke");
    }
}
