package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivated();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("title", "상품 업로드");
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);

        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes attributes) {
        try {
            productService.update(imageProduct, productDto);
            attributes.addFlashAttribute("success", "업데이트 완료!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "업데이트 실패");
        }
        return "redirect:/products";
    }

    @RequestMapping(value = "/enabled-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            productService.enableById(id);
            attributes.addFlashAttribute("success", "상품 복구 완료!!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "상품 복구 실패");
        }
        return "redirect:/products";
    }

    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            productService.deleteById(id);
            attributes.addFlashAttribute("success", "삭제 완료!!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "삭제 실패");
        }
        return "redirect:/products";
    }
}
