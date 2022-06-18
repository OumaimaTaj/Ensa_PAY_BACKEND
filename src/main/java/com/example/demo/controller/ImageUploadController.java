package com.example.demo.controller;

import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;


@RestController
@RequestMapping(path = "image")
public class ImageUploadController {

    private final ImageService imageService;

    @Autowired
    ImageUploadController(ImageService imageService){
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public  ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String message = "";
        try {
            String filename = imageService.save(file);
            HashMap<String,String> res = new HashMap<>();
            res.put("filename",filename);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            message = "Impossible de charger le fichier: " + file.getOriginalFilename() + "!";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = imageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
