package com.example.StoreMSI.Controller;

import com.example.StoreMSI.DTO.Product.ProductRequest;
import com.example.StoreMSI.DTO.Product.ProductResponse;
import com.example.StoreMSI.Service.ProductService;
import com.example.StoreMSI.Util.ApiResponse;
import jakarta.validation.Valid;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    // ✅ CREATE (Multipart: JSON + file)
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
// ១. សម្រាប់ទទួល Raw JSON (គ្មានរូបភាព)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<ProductResponse> createFromJson(@Valid @RequestBody ProductRequest request) {
        // ហៅ Service ដែលមិនត្រូវការ file
        return ApiResponse.ok(service.createProduct(request, null));
    }

    // ២. សម្រាប់ទទួល Multipart (មានរូបភាព)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> createFromMultipart(
            @Valid @ModelAttribute ProductRequest request,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        // ហៅ Service ដែលត្រូវការ file
        return ApiResponse.ok(service.createProduct(request, file));
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
@PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> update(
            @PathVariable Long id,
            @Valid @ModelAttribute ProductRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return ApiResponse.ok(service.updateProduct(id, request, file));
    }


    // ✅ GET BY ID
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.getProductById(id));
    }

    // ✅ DELETE
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ApiResponse.ok("Deleted successfully");
    }
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll() {
        return ApiResponse.ok(service.getAllProducts());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @GetMapping("/filter")
    public ApiResponse<org.springframework.data.domain.Page<ProductResponse>> filter(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(
                service.filter(
                        id,
                        productName,
                        status,
                        categoryId,
                        brandId,
                        page,
                        size
                )
        );
    }
}