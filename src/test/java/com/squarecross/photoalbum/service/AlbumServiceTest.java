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

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Test
    void testAlbumRepository() throws InterruptedException{
        Album album1 = new Album();
        Album album2 = new Album();
        album1.setAlbumName("aaaa");
        album2.setAlbumName("aaab");

        albumRepository.save(album1);
        TimeUnit.SECONDS.sleep(1); //시간차를 벌리기위해 두번째 앨범 생성 1초 딜레이
        albumRepository.save(album2);

        //최신순 정렬, 두번째로 생성한 앨범이 먼저 나와야한다.
        List<Album> resDate = albumRepository.findByAlbumNameContainingOrderByCreatedAtDesc("aaa");
        assertEquals("aaab", resDate.get(0).getAlbumName()); // 0번째 Index가 두번째 앨범명 aaab 인지 체크
        assertEquals("aaaa", resDate.get(1).getAlbumName()); // 1번째 Index가 첫번째 앨범명 aaaa 인지 체크
        assertEquals(2, resDate.size()); // aaa 이름을 가진 다른 앨범이 없다는 가정하에, 검색 키워드에 해당하는 앨범 필터링 체크

        //앨범명 정렬, aaaa -> aaab 기준으로 나와야한다.
        List<Album> resName = albumRepository.findByAlbumNameContainingOrderByAlbumNameAsc("aaa");
        assertEquals("aaaa", resName.get(0).getAlbumName()); // 0번째 Index가 두번째 앨범명 aaaa 인지 체크
        assertEquals("aaab", resName.get(1).getAlbumName()); // 1번째 Index가 두번째 앨범명 aaab 인지 체크
        assertEquals(2, resName.size()); // aaa 이름을 가진 다른 앨범이 없다는 가정하에, 검색 키워드에 해당하는 앨범 필터링 체크
    }

}