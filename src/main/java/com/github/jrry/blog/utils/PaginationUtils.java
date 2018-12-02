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

/**
 * @author Jarosław Pawłowski
 */
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