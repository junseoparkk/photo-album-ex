package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumServiceTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    AlbumService albumService;

    @Autowired
    PhotoRepository photoRepository;

    @Test
    void getAlbum(){
        //given
        Album album=new Album();

        //when
        album.setAlbumName("Test");
        Album saveAlbum=albumRepository.save(album);
        AlbumDto resAlbum=albumService.getAlbum(saveAlbum.getAlbumId());

        //then
        assertEquals("Test",resAlbum.getAlbumName());
    }

    @Test
    void getAlbumByAlbumName(){
        //given
        Album album=new Album();

        //when
        album.setAlbumName("album1");
        Album saveAlbum=albumRepository.save(album);
        Album resAlbum=albumService.getAlbumByAlbumName(saveAlbum.getAlbumName());

        //then
        assertThat(resAlbum).isEqualTo(saveAlbum);
    }

    @Test
    void getAlbumByAlbumNameException(){
        //given

        //when

        //then
        assertThrows(EntityNotFoundException.class,
                ()->albumService.getAlbumByAlbumName("album"));
    }

    @Test
    void testPhotoCount(){
        //given
        Album album=new Album();
        Photo photo1=new Photo();

        //when
        album.setAlbumName("album1");
        Album saveAlbum=albumRepository.save(album);

        photo1.setFileName("photo1");
        photo1.setAlbum(saveAlbum);
        photoRepository.save(photo1);

        //then
        assertThat(photoRepository.countByAlbum_AlbumId(saveAlbum.getAlbumId()))
                .isEqualTo(1);

    }

}