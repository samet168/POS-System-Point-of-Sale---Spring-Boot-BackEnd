package com.example.StoreMSI.Service;



import com.example.StoreMSI.DTO.Brand.BrandRequest;
import com.example.StoreMSI.DTO.Brand.BrandResponse;
import com.example.StoreMSI.Model.Brand;
import com.example.StoreMSI.Model.BrandMapper;
import com.example.StoreMSI.Repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // កុំភ្លេចដាក់ Annotation នេះ ដើម្បីឱ្យ Spring ស្គាល់វាជា Service Layer
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    // 1. បង្កើតប្រេនថ្មី (Create)
    public BrandResponse createBrand(BrandRequest request) {
        // ឆែកមើលថាតើមានឈ្មោះប្រេននេះក្នុង Database ហើយឬនៅ
        if (brandRepository.existsByName(request.getName())) {
            throw new RuntimeException("ឈ្មោះប្រេននេះមានរួចហើយ!");
        }

        // បំលែងពី DTO ទៅជា Entity រួចរក្សាទុកក្នុង Database
        Brand brand = brandMapper.toEntity(request);
        Brand savedBrand = brandRepository.save(brand);

        // បំលែង Entity ដែលរក្សាទុកហើយ ទៅជា DTO Response បោះទៅឱ្យ Controller
        return brandMapper.toResponse(savedBrand);
    }

    // 2. ទាញយកប្រេនទាំងអស់ (Read All)
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 3. ទាញយកប្រេនតាម ID (Read By ID)
    public BrandResponse getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញប្រេនដែលមាន ID: " + id));
        return brandMapper.toResponse(brand);
    }

    // 4. កែប្រែទិន្នន័យប្រេន (Update)
    public BrandResponse updateBrand(Long id, BrandRequest request) {
        // ស្វែងរកប្រេនដែលចង់កែប្រែជាមុនសិន
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញប្រេនដែលមាន ID: " + id));

        // ចាប់ផ្តើមកែប្រែឈ្មោះ
        brand.setName(request.getName());
        Brand updatedBrand = brandRepository.save(brand);

        return brandMapper.toResponse(updatedBrand);
    }

    // 5. លុបប្រេនចេញ (Delete)
    public void deleteBrand(Long id) {
        // ស្វែងរកមើលសិន បើមានទើបលុប បើមិនមានបោះ Error
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញប្រេនដែលមាន ID: " + id));
        brandRepository.delete(brand);
    }
}
