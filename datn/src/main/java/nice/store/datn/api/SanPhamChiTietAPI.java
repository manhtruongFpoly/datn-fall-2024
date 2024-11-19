package nice.store.datn.api;


import nice.store.datn.entity.HinhAnh;
import nice.store.datn.entity.SanPhamChiTiet;
import nice.store.datn.service.HinhAnhService;
import nice.store.datn.service.SanPhamCTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/san-pham-chi-tiet")
//public class SanPhamChiTietAPI {
//
//    @Autowired
//    SanPhamCTService sanPhamCTService;
//
//    @Autowired
//    HinhAnhService hinhAnhService;
//
//
//    @PostMapping("/saveSPCT")
//    public ResponseEntity<?> saveSanPhamChiTiet(@RequestBody List<SanPhamChiTiet> sanPhamChiTiets) {
//        return ResponseEntity.ok(sanPhamCTService.saveToDatabase(sanPhamChiTiets));
//    }
//
//
//    @PostMapping("/uploadImage")
//    public ResponseEntity<?> uploadImage(@RequestParam("files") List<MultipartFile> files, @RequestParam("spctId") Integer spctId) {
//        try {
//            // Kiểm tra xem sản phẩm chi tiết có tồn tại không
//            SanPhamChiTiet sanPhamChiTiet = sanPhamCTService.findById(spctId);
//            if (sanPhamChiTiet == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm chi tiết không tồn tại");
//            }
//
//            // Duyệt qua các file ảnh được upload
//            for (MultipartFile file : files) {
//                // Tạo tên file ngẫu nhiên
//                String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
//                String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
//
//                // Thay đổi đường dẫn lưu file về Desktop
//                String userHome = System.getProperty("user.home"); // Thư mục người dùng
//                Path uploadDir = Paths.get(userHome, "Desktop", "images"); // Thư mục Desktop/images
//                Files.createDirectories(uploadDir); // Tạo thư mục nếu chưa tồn tại
//                Path filePath = uploadDir.resolve(fileName);
//
//                // Lưu file vào thư mục
//                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//                // Lưu thông tin hình ảnh vào database
//                HinhAnh hinhAnh = new HinhAnh();
//                hinhAnh.setUrl(fileName); // Lưu tên file vào database
//                hinhAnh.setSanPhamChiTiet(sanPhamChiTiet); // Liên kết với sản phẩm chi tiết
//                hinhAnhService.addHinhAnh(hinhAnh); // Lưu thông tin ảnh vào database
//            }
//
//            return ResponseEntity.ok("Upload ảnh thành công!");
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Lỗi khi upload ảnh: " + e.getMessage());
//        }
//    }
//
//
//    @GetMapping("/desktop-images/{url}")
//    public ResponseEntity<Resource> getImage(@PathVariable String url) {
//        try {
//            String userHome = System.getProperty("user.home");
//            Path filePath = Paths.get(userHome, "Desktop", "images", url);
//            System.out.println("File path: " + filePath.toString());
//
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok(resource);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            System.out.println("Error loading image: " + e.getMessage());  // Log lỗi
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//}
@Controller
public class SanPhamChiTietAPI {

    @Autowired
    SanPhamCTService sanPhamCTService;

    @Autowired
    HinhAnhService hinhAnhService;

    @PostMapping("/api/san-pham-chi-tiet/saveSPCT")
    @ResponseBody
    public ResponseEntity<?> saveSanPhamChiTiet(@RequestBody List<SanPhamChiTiet> sanPhamChiTiets) {
        return ResponseEntity.ok(sanPhamCTService.saveToDatabase(sanPhamChiTiets));
    }

    @PostMapping("/api/san-pham-chi-tiet/uploadImage")
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("files") List<MultipartFile> files, @RequestParam("spctId") Integer spctId) {
        try {
            // Kiểm tra xem sản phẩm chi tiết có tồn tại không
            SanPhamChiTiet sanPhamChiTiet = sanPhamCTService.findById(spctId);
            if (sanPhamChiTiet == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm chi tiết không tồn tại");
            }

            // Duyệt qua các file ảnh được upload
            for (MultipartFile file : files) {
                // Tạo tên file ngẫu nhiên
                String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
                String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

                // Thay đổi đường dẫn lưu file về Desktop
                String userHome = System.getProperty("user.home"); // Thư mục người dùng
                Path uploadDir = Paths.get(userHome, "Desktop", "images"); // Thư mục Desktop/images
                Files.createDirectories(uploadDir); // Tạo thư mục nếu chưa tồn tại
                Path filePath = uploadDir.resolve(fileName);

                // Lưu file vào thư mục
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Lưu thông tin hình ảnh vào database
                HinhAnh hinhAnh = new HinhAnh();
                hinhAnh.setUrl(fileName); // Lưu tên file vào database
                hinhAnh.setSanPhamChiTiet(sanPhamChiTiet); // Liên kết với sản phẩm chi tiết
                hinhAnhService.addHinhAnh(hinhAnh); // Lưu thông tin ảnh vào database
            }

            return ResponseEntity.ok("Upload ảnh thành công!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi upload ảnh: " + e.getMessage());
        }
    }

    @GetMapping("/api/san-pham-chi-tiet/desktop-images/{url}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String url) {
        try {
            String userHome = System.getProperty("user.home");
            Path filePath = Paths.get(userHome, "Desktop", "images", url);
            System.out.println("File path: " + filePath.toString());

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());  // Log lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
