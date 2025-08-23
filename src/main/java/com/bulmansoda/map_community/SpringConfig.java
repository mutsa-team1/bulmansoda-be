package com.bulmansoda.map_community;

import com.bulmansoda.map_community.repository.*;
import com.bulmansoda.map_community.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;

    private final MarkerRepository markerRepository;

    private final CenterMarkerRepository centerMarkerRepository;

    private final CenterMarkerLikeRepository centerMarkerLikeRepository;

    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;

    private final AiClusteringService aiClusteringService; // AI 서비스 의존성 추가

    @Autowired
    public SpringConfig(UserRepository userRepository, MarkerRepository markerRepository, CenterMarkerRepository centerMarkerRepository, CenterMarkerLikeRepository centerMarkerLikeRepository, CommentRepository commentRepository, CommentLikeRepository commentLikeRepository, AiClusteringService aiClusteringService) {
        this.userRepository = userRepository;
        this.markerRepository = markerRepository;
        this.centerMarkerRepository = centerMarkerRepository;
        this.centerMarkerLikeRepository = centerMarkerLikeRepository;
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.aiClusteringService = aiClusteringService; // 생성자를 통해 주입
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public MarkerService markerService() {
        // MarkerService를 생성할 때 AiClusteringService를 전달합니다.
        return new MarkerService(markerRepository, userRepository, centerMarkerRepository, aiClusteringService);
    }

    @Bean
    public CenterMarkerService centerMarkerService() {
        return new CenterMarkerService(userRepository, centerMarkerRepository, centerMarkerLikeRepository, commentRepository, commentLikeRepository);
    }

    @Bean
    public MapService mapService() {
        return new MapService(markerRepository, centerMarkerRepository);
    }
}
