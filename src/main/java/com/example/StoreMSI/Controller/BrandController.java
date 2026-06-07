package com.example.StoreMSI.Controller;


import com.example.StoreMSI.DTO.Brand.BrandRequest;
import com.example.StoreMSI.DTO.Brand.BrandResponse;
import com.example.StoreMSI.Service.BrandService;
import com.example.StoreMSI.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands") // រាល់ការហៅទៅកាន់ API ប្រេន ត្រូវផ្តើមដោយ /api/brands
public class BrandController {

    @Autowired
    private BrandService brandService;

    // 1. បង្កើតប្រេនថ្មី (Create) -> POST: http://localhost:8080/api/brands
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
    @PostMapping
    public ApiResponse<BrandResponse> createBrand(@Valid  @RequestBody BrandRequest request) {
        BrandResponse response = brandService.createBrand(request);
        return ApiResponse.ok(response);
    }

    // 2. ទាញយកប្រេនទាំងអស់ (Read All) -> GET: http://localhost:8080/api/brands
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @GetMapping
    public ApiResponse<List<BrandResponse>> getAllBrands() {
        List<BrandResponse> response = brandService.getAllBrands();
        return ApiResponse.ok(response);
    }

    // 3. ទាញយកប្រេនតាម ID (Read By ID) -> GET: http://localhost:8080/api/brands/{id}
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getBrandById(@PathVariable Long id) {
        BrandResponse response = brandService.getBrandById(id);
        return ApiResponse.ok(response);
    }

    // 4. កែប្រែទិន្នន័យប្រេន (Update) -> PUT: http://localhost:8080/api/brands/{id}
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
    @PutMapping("/{id}")
    public ApiResponse<BrandResponse> updateBrand(@PathVariable Long id, @RequestBody BrandRequest request) {
        BrandResponse response = brandService.updateBrand(id, request);
        return ApiResponse.ok(response);
    }

    // 5. លុបប្រេនចេញ (Delete) -> DELETE: http://localhost:8080/api/brands/{id}
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ApiResponse.ok("បានលុបប្រេនដោយជោគជ័យ!");
    }
}