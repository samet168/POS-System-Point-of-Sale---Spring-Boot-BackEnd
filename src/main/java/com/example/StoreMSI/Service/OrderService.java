package com.example.StoreMSI.Service;

import com.example.StoreMSI.DTO.Order.OrderRequest;
import com.example.StoreMSI.DTO.Order.OrderResponse;
import com.example.StoreMSI.Mapper.OrderMapper;
import com.example.StoreMSI.Model.Order;
import com.example.StoreMSI.Model.User;
import com.example.StoreMSI.Repository.OrderRepository;
import com.example.StoreMSI.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
public class OrderService {

    @Autowired private OrderRepository orderRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private OrderMapper mapper;

    // ================= CREATE =================
    public OrderResponse create(OrderRequest req) {

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = mapper.toEntity(req);
        order.setUser(user);

        return mapper.toResponse(orderRepo.save(order));
    }


    public Order createOrder(OrderRequest request) {
        // ១. រក User តាមរយៈ userId ដែលផ្ញើមក
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ២. គណនាប្រាក់អាប់
        BigDecimal change = request.getReceivedAmount().subtract(request.getTotalAmount());

        // ៣. បង្កើត Order
        Order order = new Order();
        order.setTotalAmount(request.getTotalAmount());
        order.setReceivedAmount(request.getReceivedAmount());
        order.setChangeAmount(change);

        // ៤. កំណត់ User ចូលក្នុង Order (ជំនួសឱ្យ setUserId)
        order.setUser(user);

        return orderRepo.save(order);
    }


    // ================= FILTER + PAGINATION =================
    public Page<OrderResponse> filter(
            Long id,
            Long userId,
            String username,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Specification<Order> spec = Specification.unrestricted();

        if (id != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("id"), id));
        }

        if (userId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("user").get("id"), userId));
        }

        if (username != null && !username.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("user").get("username")),
                            "%" + username.toLowerCase() + "%"));
        }

        return orderRepo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}