package com.github.jrry.blog.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.github.jrry.blog.common.errors.NotFoundException;

import java.util.function.Function;

public final class ValidationUtils {
    public static void notFoundWhenNegativePage(int page) {
        if (NumberUtils.isNegative(page))
            throw new NotFoundException();
    }

    public static <T> void notFoundWhenPageIsEmpty(Page<T> page) {
        if (page.isEmpty())
            throw new NotFoundException();
    }

    public static <T> Page<T> pageValidation(Function<Pageable, Page<T>> function, int pageNumber) {
        notFoundWhenNegativePage(pageNumber);
        Page<T> page = function.apply(PageRequest.of(pageNumber, 10));
        notFoundWhenPageIsEmpty(page);
        return page;
    }
}
