package com.github.jrry.blog.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.github.jrry.blog.forms.ImageForm;
import com.github.jrry.blog.service.ImageService;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/image")
public class ImageManager {

    private final ImageService imageService;

    @GetMapping("/list")
    public String getImages(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("images", imageService.getImages(page));
        return "admin/image-list";
    }

    @GetMapping("/new")
    public String getImageDetails(Model model) {
        model.addAttribute("image", new ImageForm());
        return "admin/image-new";
    }

    @PostMapping("/new")
    public String saveImage(@Valid @ModelAttribute("image") ImageForm imageForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/image-new";
        }
        imageService.saveImage(imageForm);
        return "redirect:/adm/image/list";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editImage(@PathVariable("id") Long imageId, Model model) {
        ImageForm imageForm = imageService.getImageFormById(imageId);
        model.addAttribute("image", imageForm);
        return "admin/image-edit";
    }

    @RequestMapping(value = "/edit/{id:\\d+}", method = {PUT, POST})
    public String updateImage(@Valid @ModelAttribute("imageForm") ImageForm imageForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/image-edit";
        }
        imageService.updateImage(imageForm);
        return "redirect:/adm/image/list";
    }
}
