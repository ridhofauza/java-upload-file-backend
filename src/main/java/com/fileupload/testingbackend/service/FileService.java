/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fileupload.testingbackend.service;

import com.fileupload.testingbackend.model.FileSiswa;
import com.fileupload.testingbackend.model.dto.request.FileMeta;
import com.fileupload.testingbackend.repository.FileRepository;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author user
 */
@Service
@AllArgsConstructor
public class FileService {

    private FileRepository fileRepository;

    public FileSiswa create(String description, FileMeta fileMeta) throws URISyntaxException {
        // if directory not exist create directory
        try {
            String PATH = "src/main/resources/uploads/tugas/";
            File directory = new File(PATH);
            if (!directory.exists()) {
                directory.mkdirs();
                directory.setReadable(true, false);
                directory.setWritable(true, false);
                directory.setExecutable(true, false);

                System.out.println("Can Readable: " + directory.canRead());
                System.out.println("Can Writable: " + directory.canWrite());
                System.out.println("Can Executable: " + directory.canExecute());
            }
            
            // uploading file
            byte[] bytes = fileMeta.getBytes();
            File serverFile = new File(PATH + fileMeta.getOriginalFilename());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            
            FileSiswa fileSiswa = new FileSiswa(null, fileMeta.getOriginalFilename(), description);
            
            return fileRepository.save(fileSiswa);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    public String getFileName(Long id) {
        FileSiswa fileSiswa = fileRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File siswa not found"));
        return fileSiswa.getName();
    }

}
