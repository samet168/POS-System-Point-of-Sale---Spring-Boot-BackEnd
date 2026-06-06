package com.example.StoreMSI.DTO.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    @NotBlank(message = " name is required")
    private String name;
}