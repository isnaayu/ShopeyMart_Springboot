package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.FileStorage;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class FileStorageService {
//    TODO : Inisialisasi lokasi file location
    private final Path fileStorageLocation = Paths.get("/home/user/Downloads/shopeymart/src/main/java/com/enigma/shopeymart/file");

    public FileStorageService() {
        try {
//            TODO : Jika belum ada directoeiws, maka akan membuat sebuah dorectory
            Files.createDirectories(this.fileStorageLocation);
        }catch (Exception e){
//            TODO :
            throw new RuntimeException("Could not create the directory where the upload file to storage");
        }
    }

    public FileStorage storageFile(MultipartFile file){
        String mimetype = file.getContentType();
        if (mimetype == null || (!mimetype.startsWith("image/"))){
            throw new RuntimeException("Invalid upload, Only Upload Image");
        }
        try {
            Path targetLocation = this.fileStorageLocation.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return FileStorage.builder()
                    .filename(file.getOriginalFilename())
                    .dateTime(LocalDateTime.now())
                    .build();
//            return file.getOriginalFilename();
        }catch (IOException e){
            throw new RuntimeException("Could not store "+file.getOriginalFilename()+ " please check again "+e);
        }
    }

    public Resource downloadFile(String newFile) throws FileNotFoundException{
        try {
            Path targetLocation = this.fileStorageLocation.resolve(newFile).normalize();
            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists()){
                return resource;
            }else {
                throw new FileNotFoundException("File not Found "+newFile);
            }

        }catch (MalformedURLException e){
            throw new FileNotFoundException("File not Found "+newFile);
        }
    }
}
