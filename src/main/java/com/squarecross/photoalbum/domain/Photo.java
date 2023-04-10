package com.squarecross.photoalbum.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "photo",uniqueConstraints = {@UniqueConstraint(columnNames = "photo_id")})
@Getter @Setter
public class Photo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="photo_id",unique = true,nullable = false)
    private Long photoId;
    @Column(name="file_name")
    private String fileName;
    @Column(name="thumb_url")
    private String thumbUrl;
    @Column(name = "original_url")
    private String originalUrl;
    @Column(name = "file_size")
    private String fileSize;
    @Column(name = "uploaded_at")
    @CreationTimestamp
    private LocalDateTime uploadedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;
}
