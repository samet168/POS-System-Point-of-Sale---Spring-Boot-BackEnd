package com.example.StoreMSI.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private final Path root = Paths.get("uploads");

    public String saveFile(MultipartFile file) {
        try {
            if (!Files.exists(root)) Files.createDirectories(root);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
            return fileName; // រក្សាទុកតែឈ្មោះ file
        } catch (IOException e) {
            throw new RuntimeException("មិនអាចរក្សាទុករូបភាពបានទេ!");
        }
    }

    // មុខងារសម្រាប់លុប File
    public void deleteFile(String fileName) {
        try {
            if (fileName != null) {
                Path file = root.resolve(fileName);
                Files.deleteIfExists(file); // លុប file ចោលប្រសិនបើវាមានពិតប្រាកដ
            }
        } catch (IOException e) {
            throw new RuntimeException("មិនអាចលុបឯកសារនេះបានទេ: " + e.getMessage());
        }
    }
}
