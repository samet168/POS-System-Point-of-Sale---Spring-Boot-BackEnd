package com.example.StoreMSI.Model;



import com.example.StoreMSI.DTO.Brand.BrandRequest;
import com.example.StoreMSI.DTO.Brand.BrandResponse;
import com.example.StoreMSI.Model.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public Brand toEntity(BrandRequest request) {
        if (request == null) return null;
        Brand brand = new Brand();
        brand.setName(request.getName());
        return brand;
    }

    public BrandResponse toResponse(Brand brand) {
        if (brand == null) return null;
        BrandResponse response = new BrandResponse();
        response.setId(brand.getId());
        response.setName(brand.getName());
        return response;
    }
}