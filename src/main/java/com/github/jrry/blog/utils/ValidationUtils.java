/*
 * Copyright (c) 2018 Jarosław Pawłowski
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.jrry.blog.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.github.jrry.blog.common.errors.NotFoundException;

import java.util.function.Function;

/**
 * @author Jarosław Pawłowski
 */
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
