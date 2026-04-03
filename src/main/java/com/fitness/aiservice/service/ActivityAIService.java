package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
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
        processAiResponse(activity, aiResponse);
        return aiResponse;
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);

            // Navigate to: candidates[0].content.parts[0].text
            JsonNode textNode = rootNode
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            String aiText = textNode.asText();

            log.info("Extracted AI Text: {}", aiText);

            // OPTIONAL: If AI returns JSON string, parse it again
            JsonNode structuredJson = mapper.readTree(aiText);

            Recommendation recommendation = new Recommendation();
            recommendation.setActivityId(activity.getId());

            // Example mappings (adjust based on your Recommendation model)
            recommendation.setOverallAnalysis(
                    structuredJson.path("analysis").path("overall").asText()
            );

            recommendation.setPaceAnalysis(
                    structuredJson.path("analysis").path("pace").asText()
            );

            recommendation.setHeartRateAnalysis(
                    structuredJson.path("analysis").path("heartRate").asText()
            );

            recommendation.setCaloriesAnalysis(
                    structuredJson.path("analysis").path("caloriesBurned").asText()
            );

            return recommendation;

        } catch (Exception e) {
            log.error("Error processing AI response", e);
            return null;
        }
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