package com.example.StoreMSI.Controller;

import com.example.StoreMSI.DTO.Category.CategoryRequest;
import com.example.StoreMSI.DTO.Category.CategoryResponse;
import com.example.StoreMSI.Service.CategoryService;
import com.example.StoreMSI.Util.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 1. បង្កើតប្រភេទថ្មី (Create)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
    @PostMapping
    public ApiResponse<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        return ApiResponse.ok(categoryService.createCategory(request));
    }

    // 2. ទាញយកប្រភេទទាំងអស់ (Read All)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll() {
        return ApiResponse.ok(categoryService.getAllCategories());
    }

    // 3. ទាញយកប្រភេទតាម ID (Read One)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(categoryService.getCategoryById(id));
    }

    // 4. កែប្រែទិន្នន័យ (Update)

    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        return ApiResponse.ok(categoryService.updateCategory(id, request));
    }

    // 5. លុបទិន្នន័យ (Delete)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.ok("បានលុបប្រភេទទំនិញលេខ " + id + " ដោយជោគជ័យ!");
    }
}