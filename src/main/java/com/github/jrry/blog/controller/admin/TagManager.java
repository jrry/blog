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

import com.github.jrry.blog.entity.Tag;
import com.github.jrry.blog.forms.TagForm;
import com.github.jrry.blog.forms.groups.IdGroup;
import com.github.jrry.blog.service.TagService;
import com.github.jrry.blog.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author Jarosław Pawłowski
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/tag")
public class TagManager {

    private final TagService tagService;

    @GetMapping("/list")
    public String getTags(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Tag> tags = tagService.getTags(page);
        model.addAttribute("tags", tags);
        model.addAttribute("paginationNumbers", PaginationUtils.generateThreeNumbers(tags));
        return "admin/tag/tag-list";
    }

    @GetMapping("/new")
    public String getTagDetails(Model model) {
        model.addAttribute("tag", new TagForm());
        return "admin/tag/tag-new";
    }

    @PostMapping("/new")
    public String saveTag(@Valid @ModelAttribute("tag") TagForm tagForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/tag/tag-new";
        }
        tagService.saveTag(tagForm);
        return "redirect:/adm/tag/list";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editTag(@PathVariable("id") Long tagId, Model model) {
        model.addAttribute("tag", tagService.getTagFormById(tagId));
        return "admin/tag/tag-edit";
    }

    @RequestMapping(value = "/edit", method = {PUT, POST})
    public String updateTag(@Validated(IdGroup.class) @ModelAttribute("tag") TagForm tagForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/tag/tag-edit";
        }
        tagService.updateTag(tagForm);
        return "redirect:/adm/tag/list";
    }
}
