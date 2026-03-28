package controller;

import lombok.RequiredArgsConstructor;
import model.Recommendation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.RecommendationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable String userId) {
        return ResponseEntity.ok((List<Recommendation>) recommendationService.getUserRecommendation(userId));
    }

    @GetMapping("/user/{activityId}")
    public ResponseEntity<List<Recommendation>> getActivityRecommendation(@PathVariable String activityId) {
        return ResponseEntity.ok((List<Recommendation>) recommendationService.getActivityRecommendation(activityId))
    }
}
