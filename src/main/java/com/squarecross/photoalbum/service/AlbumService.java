package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.Constants;
import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;

    public AlbumDto getAlbum(Long albumId){
        Optional<Album> res=albumRepository.findById(albumId);
        if(res.isPresent()){
            AlbumDto albumDto= AlbumMapper.convertToDto(res.get());
            albumDto.setCount(photoRepository.countByAlbum_AlbumId(albumId));
            return albumDto;
        }else{
            throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되지 않았습니다.",albumId));
        }
    }

    public Album getAlbumByAlbumName(String albumName){
        Optional<Album> res=albumRepository.findByAlbumName(albumName);
        if(res.isPresent()){
            return res.get();
        }else{
            throw new EntityNotFoundException();
        }
    }

    public AlbumDto createAlbum(AlbumDto albumDto) throws IOException {
        Album album=AlbumMapper.convertToModel(albumDto);
        this.albumRepository.save(album);
        this.createAlbumDirectories(album);
        return AlbumMapper.convertToDto(album);
    }

    public void createAlbumDirectories(Album album) throws IOException{
        Files.createDirectories(Paths.get(
                Constants.PATH_PREFIX+"/photos/original/"+album.getAlbumId()));
        Files.createDirectories(Paths.get(
                Constants.PATH_PREFIX+"/photos/thumb/"+album.getAlbumId()));
    }
}
