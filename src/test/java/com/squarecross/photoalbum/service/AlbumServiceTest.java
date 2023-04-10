package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumServiceTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    AlbumService albumService;

    @Test
    void getAlbum(){
        //given
        Album album=new Album();

        //when
        album.setAlbumName("Test");
        Album saveAlbum=albumRepository.save(album);
        Album resAlbum=albumService.getAlbum(saveAlbum.getAlbumId());

        //then
        assertEquals("Test",resAlbum.getAlbumName());
    }

}