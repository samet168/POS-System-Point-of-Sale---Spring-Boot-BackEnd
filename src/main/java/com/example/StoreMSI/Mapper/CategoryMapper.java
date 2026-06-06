package com.example.StoreMSI.Mapper;

import com.example.StoreMSI.DTO.Category.CategoryRequest;
import com.example.StoreMSI.DTO.Category.CategoryResponse;
import com.example.StoreMSI.Model.Category; // Import ពី Model ត្រឹមត្រូវហើយ
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // បំប្លែងពី Request DTO ទៅជា Entity (សម្រាប់យកទៅ Save ក្នុង DB)
    // 💡 បានកែប្រែ៖ ដក com.example.StoreMSI.Service. ចេញ ទុកតែ Category ធម្មតា
    public Category toEntity(CategoryRequest request) {
        if (request == null) {
            return null;
        }
        Category category = new Category();
        category.setName(request.getName());
        return category;
    }

    // បំប្លែងពី Entity ទៅជា Response DTO (សម្រាប់បោះទៅឱ្យ Frontend វិញ)
    public CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        return response;
    }

    // សម្រាប់ប្រើប្រាស់ពេលចង់ធ្វើ Update ទិន្នន័យលើ Entity ដែលមានស្រាប់
    public void updateEntityFromRequest(CategoryRequest request, Category category) {
        if (request == null || category == null) {
            return;
        }
        category.setName(request.getName());
    }
}