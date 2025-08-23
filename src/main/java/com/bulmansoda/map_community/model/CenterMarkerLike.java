package com.bulmansoda.map_community.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "likes")
public class CenterMarkerLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private CenterMarker centerMarker;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
