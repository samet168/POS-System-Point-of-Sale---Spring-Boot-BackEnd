package com.example.StoreMSI.Repository;
import com.example.StoreMSI.Model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    // អាចបន្ថែម Method នេះសម្រាប់ឆែកមើលឈ្មោះប្រេនជាន់គ្នា
    boolean existsByName(String name);
}