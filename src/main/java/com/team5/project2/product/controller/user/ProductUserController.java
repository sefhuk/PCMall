package com.team5.project2.product.controller.user;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.service.CategoryService;
import com.team5.project2.product.dto.response.ProductResponseDto;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.mapper.ProductMapper;
import com.team5.project2.product.service.ProductService;
import com.team5.project2.user.domain.UserDetail;
import com.team5.project2.user.mapper.UserMapper;
import com.team5.project2.user.service.UserService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/product")
@RequiredArgsConstructor
public class ProductUserController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public String getProductListPage(
        @RequestParam(value = "category", defaultValue = "CPU") String category,
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
        @AuthenticationPrincipal UserDetail user,
        Model model) {

        List<CategoryDTO> categories = categoryService.getAllCategories();

        CategoryDTO findCategory;
        try {
            findCategory = categoryService.getCategoryByName(category).get(0);
        } catch (Exception e) {
            throw new RuntimeException("카테고리를 찾을 수 없습니다.");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> pagedProducts = productService.findProductByCategoryIdPaging(findCategory.getId(), pageable);

        List<ProductResponseDto> products = pagedProducts.getContent().stream()
            .filter(p -> Objects.equals(p.getCategory().getName(), category))
            .map(ProductMapper.INSTANCE::productToProductResponseDto)
            .toList();

        String role = user.getAuthorities().iterator().next().getAuthority();

        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", pagedProducts.getTotalPages());
        model.addAttribute("user", user);
        model.addAttribute("role", role);

        return "product/product-list";
    }

    @GetMapping("/{id}")
    public String getProductDetailPage(@PathVariable Long id,
        @AuthenticationPrincipal UserDetail user, Model model) {

        List<CategoryDTO> categories = categoryService.getAllCategories();
        ProductResponseDto product = ProductMapper.INSTANCE.productToProductResponseDto(
            productService.findProduct(id));

        String role = user.getAuthorities().iterator().next().getAuthority();

        Long userId = userService.findUserByEmail(user.getUsername()).getId();

        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("role", role);
        model.addAttribute("userId", userId);

        return "product/product-detail";
    }
}
