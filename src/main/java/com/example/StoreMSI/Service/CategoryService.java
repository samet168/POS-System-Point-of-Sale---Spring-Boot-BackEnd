package com.example.StoreMSI.Service;

import com.example.StoreMSI.Model.Category; // បន្ថែមសម្រាប់ស្គាល់ Entity
import com.example.StoreMSI.DTO.Category.CategoryRequest;
import com.example.StoreMSI.DTO.Category.CategoryResponse;
import com.example.StoreMSI.Mapper.CategoryMapper;
import com.example.StoreMSI.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;


    private final CategoryMapper categoryMapper;


    // 1. បង្កើតប្រភេទថ្មី (Create)
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("ប្រភេទទំនិញឈ្មោះនេះមានរួចរាល់ហើយ!");
        }
        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category); // កែប្រែរួចជា Entity
        return categoryMapper.toResponse(savedCategory);
    }

    // 2. ទាញយកប្រភេទទាំងអស់ (Read All)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 3. ទាញយកប្រភេទតាម ID (Read One)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញប្រភេទទំនិញដែលមាន ID: " + id + " ឡើយ"));
        return categoryMapper.toResponse(category);
    }

    // 4. កែប្រែទិន្នន័យ (Update)
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញប្រភេទទំនិញដែលត្រូវកែប្រែឡើយ"));

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("ឈ្មោះថ្មីនេះមានគេប្រើរួចហើយ!");
        }

        categoryMapper.updateEntityFromRequest(request, category);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }

    // 5. លុបទិន្នន័យ (Delete)
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("មិនអាចលុបបានទេ ព្រោះរកមិនឃើញ ID នេះឡើយ");
        }
        categoryRepository.deleteById(id);
    }
}