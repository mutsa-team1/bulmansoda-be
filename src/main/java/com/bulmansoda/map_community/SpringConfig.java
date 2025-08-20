package com.bulmansoda.map_community;

import com.bulmansoda.map_community.repository.*;
import com.bulmansoda.map_community.service.CenterMarkerService;
import com.bulmansoda.map_community.service.MapService;
import com.bulmansoda.map_community.service.MarkerService;
import com.bulmansoda.map_community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;

    private final MarkerRepository markerRepository;

    private final CenterMarkerRepository centerMarkerRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository, MarkerRepository markerRepository, CenterMarkerRepository centerMarkerRepository, LikeRepository likeRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.markerRepository = markerRepository;
        this.centerMarkerRepository = centerMarkerRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public MarkerService markerService() {
        return new MarkerService(markerRepository, userRepository, centerMarkerRepository);
    }

    @Bean
    public CenterMarkerService centerMarkerService() {
        return new CenterMarkerService(userRepository, centerMarkerRepository, likeRepository, commentRepository);
    }

    @Bean
    public MapService mapService() {
        return new MapService(markerRepository, centerMarkerRepository);
    }
}
