package com.nazjara.controller;

import com.nazjara.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("recipeId", id);

        return "recipe/imageform";
    }

    @PostMapping("recipe/{id}/image")
    public String saveImage(@PathVariable String id, @RequestParam("image") MultipartFile image) {
        imageService.saveImage(Long.valueOf(id), image);

        return String.format("redirect:/recipe/%s", id);
    }
}