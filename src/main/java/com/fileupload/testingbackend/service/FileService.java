/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fileupload.testingbackend.service;

import com.fileupload.testingbackend.model.FileSiswa;
import com.fileupload.testingbackend.repository.FileRepository;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author user
 */
@Service
@AllArgsConstructor
public class FileService {

    private FileRepository fileRepository;

    public Object create(String name, String description, MultipartFile fileTugas) throws URISyntaxException {
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
            byte[] bytes = fileTugas.getBytes();
            File serverFile = new File(PATH + fileTugas.getOriginalFilename());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            
//            HashMap<String, String> hasil = new HashMap<>();
//            hasil.put("nameSiswa", name);
//            hasil.put("description", description);
//            hasil.put("fileTugas", Long.toString(fileTugas.getSize()));
//            hasil.put("nameDir", serverFile.getAbsolutePath());
            
            FileSiswa fileSiswa = new FileSiswa(null, fileTugas.getOriginalFilename(), description);
            
            return fileRepository.save(fileSiswa);
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    
    public String getFileName(Long id) {
        FileSiswa fileSiswa = fileRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File siswa not found"));
        return fileSiswa.getName();
    }

}
