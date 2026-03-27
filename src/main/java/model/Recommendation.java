package model;

import java.time.LocalDateTime;
import java.util.List;

public class Recommendation {
    private String id;
    private String activityId;
    private String userId;
    private String activityType;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
