package com.github.jrry.blog.utils;

import org.springframework.data.domain.Page;

public final class PaginationUtils {

    public static <T> int[] generateThreeNumbers(Page<T> page) {
        int prev = page.getNumber();
        int next = page.getNumber() + 2;
        if (page.getTotalPages() > 3) {
            if (prev < 1)
                return new int[]{1, 2, 3};
            if (next > page.getTotalPages())
                return new int[]{page.getTotalPages() - 2, page.getTotalPages() - 1, page.getTotalPages()};
            return new int[]{prev, page.getNumber() + 1, next};
        }
        return new int[]{1, 2};
    }
}