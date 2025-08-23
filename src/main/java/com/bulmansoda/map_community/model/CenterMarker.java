package com.bulmansoda.map_community.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "center_markers")
public class CenterMarker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy = "centerMarker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Marker> markers;

    @OneToMany(mappedBy = "centerMarker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CenterMarkerLike> centerMarkerLikes = new ArrayList<>();

    @OneToMany(mappedBy = "centerMarker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "center_marker_keywords", joinColumns = @JoinColumn(name = "center_id"))
    @Column(name = "keywords")
    private List<String> keywords;

}
