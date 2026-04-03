package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiService geminiService;

    public String generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("RESPONSE FROM AI: {}", aiResponse);
        return aiResponse;
    }

    private String createPromptForActivity(Activity activity) {

        return String.format("""
                Analyze the following fitness activity and provide a structured JSON response.

                Activity Details:
                - Type: %s
                - Duration: %d minutes
                - Calories Burned: %d
                - Start Time: %s
                - Device: %s
                - Source: %s

                Additional Metrics:
                %s

                Respond strictly in this JSON format:
                {
                  "analysis": {
                    "overall": "Overall analysis here",
                    "pace": "Pace analysis here",
                    "heartRate": "Heart rate analysis here",
                    "caloriesBurned": "Calories analysis here"
                  },
                  "improvements": [
                    {
                      "area": "Area name",
                      "recommendation": "Detailed recommendation"
                    }
                  ],
                  "suggestions": [
                    {
                        "workout": "Workout name",
                        "description": "Detailed workout description",
                    }
                  ],
                  "safety": [
                   "Safety point 1",
                   "Safety point 2",
                  ],
                }
                """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getStartTime(),
                activity.getDevice(),
                activity.getSource(),
                activity.getAdditionalMetrics()
        );
    }
}