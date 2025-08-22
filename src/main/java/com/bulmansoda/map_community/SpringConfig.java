package com.bulmansoda.map_community;

import com.bulmansoda.map_community.ai.OpenAIClient;
import com.bulmansoda.map_community.ai.PromptBuilder;
import com.bulmansoda.map_community.repository.*;
import com.bulmansoda.map_community.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final AiClusteringService aiClusteringService; // AI 서비스 의존성 추가

    @Autowired
    public SpringConfig(UserRepository userRepository, MarkerRepository markerRepository, CenterMarkerRepository centerMarkerRepository, LikeRepository likeRepository, CommentRepository commentRepository, AiClusteringService aiClusteringService) {
        this.userRepository = userRepository;
        this.markerRepository = markerRepository;
        this.centerMarkerRepository = centerMarkerRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
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
        return new CenterMarkerService(userRepository, centerMarkerRepository, likeRepository, commentRepository);
    }

    @Bean
    public MapService mapService() {
        return new MapService(markerRepository, centerMarkerRepository);
    }

    // 참고: AiClusteringService, OpenAIClient, PromptBuilder, ObjectMapper는
    // 각각 @Service, @Component 어노테이션이 있어 Spring이 자동으로 Bean으로 관리하므로
    // 이 설정 파일에 별도의 @Bean 메서드를 만들 필요가 없습니다.
}
