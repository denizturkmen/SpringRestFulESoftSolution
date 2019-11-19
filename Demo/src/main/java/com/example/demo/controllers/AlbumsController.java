package com.example.demo.controllers;

import com.example.demo.model.Album;
import com.example.demo.model.Photo;
import com.example.demo.repositories.AlbumRepository;
import com.example.demo.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AlbumsController {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private PhotoRepository photoRepository;

    @PutMapping(value = {"/userAlbums", "/userAlbumsWithDetails"})
    public Object getAlbumsOfUser() {
        return getAlbumsOfUser(null);
    }

    @PutMapping(value = "/userAlbums/{userId}")
    public Object getAlbumsOfUser(@PathVariable String userId) {
        try
        {
            long value = Long.parseLong(userId);
            return albumRepository.findAll().stream()
                    .filter((album) -> album.getUserId() == value)
                    .collect(Collectors.toList());
        }
        catch (NumberFormatException | NoSuchElementException ex)
        {
            Map map = new HashMap();
            map.put("errorMessage", "Lütfen geçerli bir userId yollayınız.");
            return map;
        }
    }

    @PutMapping(value = "/userAlbumsWithDetails/{userId}")
    public Object getAlbumsOfUserWithDetails(@PathVariable String userId) {
        try
        {
            long value = Long.parseLong(userId);
            Collection<Photo> photos = photoRepository.findAll();
            return albumRepository.findAll().stream()
                    .filter((album) -> album.getUserId() == value)
                    .map((album -> {
                        AlbumWithPhotos albumWithPhotos = new AlbumWithPhotos(album);
                        albumWithPhotos.setPhotos(photos.stream().filter(photo -> photo.getAlbumId() == album.getId()).collect(Collectors.toList()));
                        return albumWithPhotos;
                    }))
                    .collect(Collectors.toList());
        }
        catch (NumberFormatException | NoSuchElementException ex)
        {
            Map map = new HashMap();
            map.put("errorMessage", "Lütfen geçerli bir userId yollayınız.");
            return map;
        }
    }
}

class AlbumWithPhotos extends Album
{
    private Collection<Photo> photos;

    public AlbumWithPhotos(Album album){
        this.setId(album.getId());
        this.setUserId(album.getUserId());
        this.setTitle(album.getTitle());
    }

    public Collection<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Collection<Photo> photos) {
        this.photos = photos;
    }
}
