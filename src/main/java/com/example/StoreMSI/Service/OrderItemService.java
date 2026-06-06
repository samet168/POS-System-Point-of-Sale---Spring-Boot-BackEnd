package com.example.StoreMSI.Service;

import com.example.StoreMSI.DTO.OrderItem.OrderItemRequest;
import com.example.StoreMSI.DTO.OrderItem.OrderItemResponse;
import com.example.StoreMSI.Mapper.OrderItemMapper;
import com.example.StoreMSI.Model.Order;
import com.example.StoreMSI.Model.OrderItem;
import com.example.StoreMSI.Model.Product;
import com.example.StoreMSI.Repository.OrderItemRepository;
import com.example.StoreMSI.Repository.OrderRepository;
import com.example.StoreMSI.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderItemService {

    @Autowired private OrderItemRepository repo;
    @Autowired private OrderRepository orderRepo;
    @Autowired private ProductRepository productRepo;
    @Autowired private OrderItemMapper mapper;

    // ================= CREATE =================
    public OrderItemResponse create(OrderItemRequest req) {

        Order order = orderRepo.findById(req.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepo.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem item = mapper.toEntity(req);
        item.setOrder(order);
        item.setProduct(product);

        return mapper.toResponse(repo.save(item));
    }

    // ================= UPDATE =================
    public OrderItemResponse update(Long id, OrderItemRequest req) {

        OrderItem item = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));

        item.setQuantity(req.getQuantity());

        if (req.getUnitPrice() != null) {
            item.setUnitPrice(java.math.BigDecimal.valueOf(req.getUnitPrice()));
        }

        return mapper.toResponse(repo.save(item));
    }

    // ================= DELETE =================
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // ================= GET ALL =================
    public List<OrderItemResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ================= GET BY ID =================
    public OrderItemResponse getById(Long id) {
        return mapper.toResponse(
                repo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    // ================= FILTER + PAGINATION =================
    public Page<OrderItemResponse> filter(
            Long orderId,
            Long productId,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "id"));

        Specification<OrderItem> spec = Specification.unrestricted();

        if (orderId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("order").get("id"), orderId));
        }

        if (productId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("product").get("id"), productId));
        }

        return repo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}