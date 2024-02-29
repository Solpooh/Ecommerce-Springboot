package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model, Principal principal) {
        // Principal은 현재 사용자를 나타내는 객체로, 인증된 사용자의 정보를 제공
        if (principal == null) {
            return "redirect:/login";
        }
        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("title", "상품 관리");
        model.addAttribute("products", productDtoList);
        model.addAttribute("size", productDtoList.size());

        return "products";
    }
    @GetMapping("/add-product")
    public String addProductForm(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categories);
        return "add-product";
    }
    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product")ProductDto productDto,
                              @RequestParam("imageProduct")MultipartFile imageProduct,
                              RedirectAttributes attributes) {

        try {
            productService.save(imageProduct, productDto);
            attributes.addFlashAttribute("success", "등록 완료!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "등록 실패");
        }
        return "redirect:/products";
    }
}
