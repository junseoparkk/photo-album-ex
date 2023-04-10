package com.squarecross.photoalbum.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="album",uniqueConstraints = {@UniqueConstraint(columnNames = "album_id")})
@Getter @Setter
public class Album {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="album_id",unique = true,nullable = false)
    private Long albumId;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    public Album() {
    }
}
