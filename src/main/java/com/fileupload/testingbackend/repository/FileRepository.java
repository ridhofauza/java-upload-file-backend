/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fileupload.testingbackend.repository;

import com.fileupload.testingbackend.model.FileSiswa;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository
public interface FileRepository extends JpaRepository<FileSiswa, Long> {
    Optional<FileSiswa> findByName(String name);
}
