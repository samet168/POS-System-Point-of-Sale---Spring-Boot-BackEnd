package com.example.StoreMSI.Controller;

import com.example.StoreMSI.DTO.Order.OrderRequest;
import com.example.StoreMSI.DTO.Order.OrderResponse;
import com.example.StoreMSI.Model.Order;
import com.example.StoreMSI.Service.OrderService;
import com.example.StoreMSI.Util.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

     private final OrderService service;

    // ================= CREATE =================
//    @PostMapping
//    public ApiResponse<OrderResponse> create(
//            @Valid @RequestBody OrderRequest req
//    ) {
//        return ApiResponse.ok(service.create(req));
//    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest request) {
        Order newOrder = service.createOrder(request); // លែងត្រូវការ try-catch
        return ResponseEntity.ok(newOrder);
    }
    // ================= FILTER + PAGINATION =================
    @GetMapping("/filter")
    public ApiResponse<Page<OrderResponse>> filter(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(
                service.filter(id, userId, username, page, size)
        );
    }
}