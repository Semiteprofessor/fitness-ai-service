package com.fitness.aiservice.service;

import lombok.RequiredArgsConstructor;
import com.fitness.aiservice.model.Recommendation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private  final com.fitness.aiservice.repository.RecommendationRepository recommendationRepository;


    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public List<Recommendation> getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId);
    }
}
