package com.example.StoreMSI.Controller;

import com.example.StoreMSI.DTO.OrderItem.OrderItemRequest;
import com.example.StoreMSI.DTO.OrderItem.OrderItemResponse;
import com.example.StoreMSI.Service.OrderItemService;
import com.example.StoreMSI.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired private OrderItemService service;

    // ================= CREATE =================
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @PostMapping
    public ApiResponse<OrderItemResponse> create(
            @Valid @RequestBody OrderItemRequest req
    ) {
        return ApiResponse.ok(service.create(req));
    }

    // ================= UPDATE =================
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @PutMapping("/{id}")
    public ApiResponse<OrderItemResponse> update(
            @PathVariable Long id,
            @RequestBody OrderItemRequest req
    ) {
        return ApiResponse.ok(service.update(id, req));
    }

    // ================= DELETE =================
     @PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Deleted successfully");
    }

    // ================= GET ALL =================
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @GetMapping
    public ApiResponse<List<OrderItemResponse>> getAll() {
        return ApiResponse.ok(service.getAll());
    }

    // ================= GET BY ID =================
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @GetMapping("/{id}")
    public ApiResponse<OrderItemResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.getById(id));
    }

    // ================= FILTER =================
    @PreAuthorize("hasAnyAuthority('ADMIN', 'admin', 'CASHIER', 'cashier')")
    @GetMapping("/filter")
    public ApiResponse<Page<OrderItemResponse>> filter(
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(
                service.filter(orderId, productId, page, size)
        );
    }
}