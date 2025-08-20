package com.bulmansoda.map_community.model;

import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private CenterMarker centerMarker;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CenterMarker getCenterMarker() {
        return centerMarker;
    }

    public void setCenterMarker(CenterMarker centerMarker) {
        this.centerMarker = centerMarker;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
