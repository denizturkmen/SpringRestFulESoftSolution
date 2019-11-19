package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repositories.AlbumRepository;
import com.example.demo.repositories.PhotoRepository;
import com.example.demo.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;
    private AlbumRepository albumRepository;
    private PhotoRepository photoRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, AlbumRepository albumRepository, PhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
    }

    public void run(ApplicationArguments args) {
        try {
            //Json to java object
            //Json read and write
            ObjectMapper mapper = new ObjectMapper();
            String usersJson = new String(Files.readAllBytes(Paths.get("users.json")));
            String albumsJson = new String(Files.readAllBytes(Paths.get("albums.json")));
            String photosJson = new String(Files.readAllBytes(Paths.get("photos.json")));
            //Tam tip çevrimi
            Collection<User> users = mapper.readValue(
                    usersJson, new TypeReference<Collection<User>>() { });
            Collection<Album> albums = mapper.readValue(
                    albumsJson, new TypeReference<Collection<Album>>() { });
            Collection<Photo> photos = mapper.readValue(
                    photosJson, new TypeReference<Collection<Photo>>() { });

            //h2 veritabani kayit
            users.forEach(user -> userRepository.save(user));
            albums.forEach(album -> albumRepository.save(album));
            photos.forEach(photo -> photoRepository.save(photo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}