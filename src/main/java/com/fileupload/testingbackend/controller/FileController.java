/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fileupload.testingbackend.controller;

import com.fileupload.testingbackend.service.FileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {
    private FileService fileService;
    
    @PostMapping
    public ResponseEntity<Object> fileUpload(@RequestPart String name, @RequestPart String description, MultipartFile fileTugas) throws FileNotFoundException, URISyntaxException {
        return new ResponseEntity(fileService.create(name, description, fileTugas), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws FileNotFoundException {
        String fileName = fileService.getFileName(id);
        Path path = Paths.get("src/main/resources/uploads/tugas/");
        File file = new File("src/main/resources/uploads/tugas/"+fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
    
}
