package com.example.StoreMSI.Service;

import com.example.StoreMSI.DTO.Product.ProductRequest;
import com.example.StoreMSI.DTO.Product.ProductResponse;
import com.example.StoreMSI.Mapper.ProductMapper;
import com.example.StoreMSI.Model.Product;
import com.example.StoreMSI.Repository.BrandRepository;
import com.example.StoreMSI.Repository.CategoryRepository;
import com.example.StoreMSI.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RestController
public class ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private CategoryRepository catRepo;

    @Autowired
    private BrandRepository brandRepo;

    // ✅ CREATE PRODUCT
    public ProductResponse createProduct(ProductRequest req, MultipartFile file) {

        var cat = catRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញ Category"));

        var brand = brandRepo.findById(req.getBrandId())
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញ Brand"));

        Product product = mapper.toEntity(req);

        product.setCategory(cat);
        product.setBrand(brand);

        if (file != null && !file.isEmpty()) {
            product.setImageUrl(fileService.saveFile(file));
        }

        return mapper.toResponse(repo.save(product));
    }

    public ProductResponse updateProduct(Long id, ProductRequest req, MultipartFile file) {

        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("មិនឃើញផលិតផល"));

        // update fields
        existing.setName(req.getName());
        existing.setBarcode(req.getBarcode());
        existing.setCostPrice(req.getCostPrice());
        existing.setSellingPrice(req.getSellingPrice());
        existing.setQuantity(req.getQuantity());

        var cat = catRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញ Category"));

        var brand = brandRepo.findById(req.getBrandId())
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញ Brand"));

        existing.setCategory(cat);
        existing.setBrand(brand);

        // update image
        if (file != null && !file.isEmpty()) {

            if (existing.getImageUrl() != null) {
                fileService.deleteFile(existing.getImageUrl());
            }

            existing.setImageUrl(fileService.saveFile(file));
        }

        return mapper.toResponse(repo.save(existing));
    }
    // ✅ DELETE PRODUCT
    public void deleteProduct(Long id) {

        Product p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("មិនឃើញផលិតផល"));

        if (p.getImageUrl() != null) {
            fileService.deleteFile(p.getImageUrl());
        }

        repo.deleteById(id);
    }

    // ✅ GET ALL
    public List<ProductResponse> getAllProducts() {
        return repo.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ✅ GET BY ID
    public ProductResponse getProductById(Long id) {
        return mapper.toResponse(
                repo.findById(id)
                        .orElseThrow(() -> new RuntimeException("មិនឃើញផលិតផល"))
        );
    }



    public Page<ProductResponse> filter(
            Long id,
            String productName,
            Boolean status,
            Long categoryId,
            Long brandId,
            int page,
            int size
    ) {

        Specification<Product> spec = Specification.unrestricted();

        if (id != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("id"), id));
        }

        if (productName != null && !productName.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("name")),
                            "%" + productName.toLowerCase() + "%"
                    ));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status));
        }

        if (categoryId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("category").get("id"), categoryId));
        }

        if (brandId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("brand").get("id"), brandId));
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Order.desc("id"))
        );

        return repo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}