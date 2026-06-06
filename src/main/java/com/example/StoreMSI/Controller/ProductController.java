package com.example.StoreMSI.Controller;

import com.example.StoreMSI.DTO.Product.ProductRequest;
import com.example.StoreMSI.DTO.Product.ProductResponse;
import com.example.StoreMSI.Service.ProductService;
import com.example.StoreMSI.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    // ✅ CREATE (Multipart: JSON + file)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> create(
            @Valid @ModelAttribute ProductRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return ApiResponse.ok(service.createProduct(request, file));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> update(
            @PathVariable Long id,
            @Valid @ModelAttribute ProductRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return ApiResponse.ok(service.updateProduct(id, request, file));
    }


    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.getProductById(id));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ApiResponse.ok("Deleted successfully");
    }
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll() {
        return ApiResponse.ok(service.getAllProducts());
    }

    @GetMapping("/filter")
    public ApiResponse<List<ProductResponse>> filter(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId
    ) {
        return ApiResponse.ok(
                service.filter(id, productName, status, categoryId, brandId)
        );
    }
}