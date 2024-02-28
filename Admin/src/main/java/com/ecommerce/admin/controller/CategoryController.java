package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categories")
    public String categories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title", "Category");
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }
    @PostMapping("/add-category")
    public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes) {
        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "추가 성공");
        } catch (DataIntegrityViolationException e) {
            attributes.addAttribute("failed", "이름 중복!!");
        }
        catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "추가 실패");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes attributes) {
        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success", "수정 완료!!");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "이름 중복");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "서버 에러!");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id, RedirectAttributes attributes) {
        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success", "삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "삭제 실패");
        }
        return "redirect:/categories";
    }


}
