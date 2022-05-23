package com.example.demo.controller.admin;

import com.example.demo.entity.Image;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.UUID;

@RestController
public class ManageImageController {
    private static String UPLOAD_DIR = System.getProperty("user.home") + "/media/upload";

    @Autowired
    private ImageService imageService;

    @PostMapping("/api/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
        System.out.println("========== Đang call api upload ảnh =======");
        //Khởi tạo folder để lưu file nếu không tồn tại
        File uploadDir  = new File(UPLOAD_DIR);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }
        System.out.println("========== Đang call api upload ảnh2 =======");
        String originalFilename = file.getOriginalFilename();
        //Lấy phần đuôi mở rộng của file
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") +1);
        if(originalFilename != null && originalFilename.length() > 0) {
            if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("svg") && !extension.equals("jpeg")) {
                throw new BadRequestException("Không hỗ trợ định dạng file này");
            }
            System.out.println("========== Đang call api upload ảnh3 =======");
            try {
                System.out.println("========== Đang call api upload ảnh4 =======");
                Image image = new Image();
                image.setName(file.getName());
                image.setSize(file.getSize());
                image.setType(extension);
                image.setUploadedAt(new Timestamp(System.currentTimeMillis()));
                image.setUploadedBy(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());
                image.setId(UUID.randomUUID().toString());
                String link = "/media/static/" + image.getId() + "." + extension;
                image.setLink(link);

                //Create file
                File serverFile = new File(UPLOAD_DIR + "/" + image.getId() + "." + extension);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();
                imageService.save(image);
                return ResponseEntity.ok(link);
            } catch (Exception e) {
                throw new InternalServerException("Lỗi khi upload file");
            }
        }
        System.out.println("========== Đang call api upload ảnh6 =======");
        throw new BadRequestException("File không hợp lệ");
    }

    @GetMapping("/media/static/{filename:.+}")
    public ResponseEntity<?> download(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR + "/" + filename);
        if(!file.exists()) {
            throw new NotFoundException("File không tồn tại");
        }
        UrlResource resource;
        try {
            resource = new UrlResource(file.toURI());
        } catch (MalformedURLException e) {
            throw new NotFoundException("File không tồn tại");
        }
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getName() +"\"")
                            .body(resource);
    }


    @DeleteMapping("/api/delete-image/{filename:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename){
        System.out.println("======= Đang call api xoá ảnh =======");
        imageService.deleteImage(UPLOAD_DIR, filename);
        return ResponseEntity.ok("Xóa thành công");
    }

}
