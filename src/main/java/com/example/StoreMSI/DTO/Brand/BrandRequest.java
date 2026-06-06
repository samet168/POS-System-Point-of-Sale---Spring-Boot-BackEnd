package com.example.StoreMSI.DTO.Brand;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {
    @NotBlank(message = " name is required")
    private String name;
}