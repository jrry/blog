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

package com.github.jrry.blog.controller.admin;

import com.github.jrry.blog.entity.Image;
import com.github.jrry.blog.forms.groups.IdGroup;
import com.github.jrry.blog.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.github.jrry.blog.forms.ImageForm;
import com.github.jrry.blog.service.ImageService;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author Jarosław Pawłowski
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/image")
public class ImageManager {

    private final ImageService imageService;

    @GetMapping("/list")
    public String getImages(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Image> images = imageService.getImages(page);
        model.addAttribute("images", images);
        model.addAttribute("paginationNumbers", PaginationUtils.generateThreeNumbers(images));
        return "admin/image/image-list";
    }

    @GetMapping("/new")
    public String getImageDetails(Model model) {
        model.addAttribute("image", new ImageForm());
        return "admin/image/image-new";
    }

    @PostMapping("/new")
    public String saveImage(@Valid @ModelAttribute("image") ImageForm imageForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/image/image-new";
        }
        imageService.saveImage(imageForm);
        return "redirect:/adm/image/list";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editImage(@PathVariable("id") Long imageId, Model model) {
        ImageForm imageForm = imageService.getImageFormById(imageId);
        model.addAttribute("image", imageForm);
        return "admin/image/image-edit";
    }

    @RequestMapping(value = "/edit", method = {PUT, POST})
    public String updateImage(@Validated(IdGroup.class) @ModelAttribute("image") ImageForm imageForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/image/image-edit";
        }
        imageService.updateImage(imageForm);
        return "redirect:/adm/image/list";
    }
}
