package service;

import lombok.RequiredArgsConstructor;
import model.Recommendation;
import org.springframework.stereotype.Service;
import repository.RecommendationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private  final RecommendationRepository recommendationRepository;
    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId)
    }

    public Object getActivityRecommendation(String activityId) {
    }

    private final RecommendationRepository
}
