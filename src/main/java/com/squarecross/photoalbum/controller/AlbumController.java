package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @GetMapping(value = "/{albumId}")
    public ResponseEntity<AlbumDto>getAlbum(@PathVariable("albumId") final long albumId){
        AlbumDto album=albumService.getAlbum(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping(value = "/query")
    public ResponseEntity<AlbumDto>getAlbumByQuery(@RequestParam(value = "albumId") final long albumId){
        AlbumDto album=albumService.getAlbum(albumId);
        return new ResponseEntity<>(album,HttpStatus.OK);
    }

    @PostMapping(value = "/json_body")
    public ResponseEntity<AlbumDto>getAlbumByJson(@RequestBody final AlbumDto albumDto){
        Long albumId=albumDto.getAlbumId();
        AlbumDto album=albumService.getAlbum(albumId);
        return new ResponseEntity<>(album,HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<AlbumDto>createAlbum(@RequestBody final AlbumDto albumDto) throws IOException {
        AlbumDto savedAlbumDto=albumService.createAlbum(albumDto);
        return new ResponseEntity<>(savedAlbumDto,HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<AlbumDto>>getAlbumList(
            @RequestParam(value = "keyword",required = false,defaultValue = "")final String keyword,
            @RequestParam(value = "sort",required = false,defaultValue = "byDate")final String sort){
        List<AlbumDto>albumDtos=albumService.getAlbumList(keyword,sort);
        return new ResponseEntity<>(albumDtos,HttpStatus.OK);
    }

    @PutMapping(value="/{albumId")
    public ResponseEntity<AlbumDto>updateAlbum(@PathVariable("albumId")final long albumId,
                                               @RequestBody final AlbumDto albumDto){
        AlbumDto res=albumService.changeName(albumId,albumDto);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
