package com.example.website.model;

import java.util.List;

public class Topic {
    private String id;
    private String title;
    private String category;
    private String description;
    private String videoFile;
    private String thumbnailColor;
    private String icon;
    private String[] tags;

    // Complexity fields
    private String complexityBest;
    private String complexityAvg;
    private String complexityWorst;
    private String complexityBestNote;
    private String complexityAvgNote;
    private String complexityWorstNote;
    private String spaceComplexity;
    private String spaceNote;

    // Code
    private String pseudocode;
    private String pythonCode;

    // Rich content
    private List<UseCase> useCases;
    private List<String> interviewQuestions;
    private List<String> questionDifficulties;

    // Visualizer flag
    private boolean hasVisualizer;

    public static class UseCase {
        private String emoji;
        private String title;
        private String desc;

        public UseCase(String emoji, String title, String desc) {
            this.emoji = emoji;
            this.title = title;
            this.desc = desc;
        }

        public String getEmoji() { return emoji; }
        public String getTitle() { return title; }
        public String getDesc() { return desc; }
    }

    public Topic(String id, String title, String category, String description,
                 String videoFile, String thumbnailColor, String icon, String[] tags) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.videoFile = videoFile;
        this.thumbnailColor = thumbnailColor;
        this.icon = icon;
        this.tags = tags;
        // defaults
        this.complexityBest = "O(n)";
        this.complexityAvg = "O(n)";
        this.complexityWorst = "O(n)";
        this.complexityBestNote = "";
        this.complexityAvgNote = "";
        this.complexityWorstNote = "";
        this.spaceComplexity = "O(1)";
        this.spaceNote = "";
        this.pseudocode = "";
        this.pythonCode = "";
        this.hasVisualizer = false;
        this.useCases = List.of();
        this.interviewQuestions = List.of();
        this.questionDifficulties = List.of();
    }

    // Full constructor with all fields
    public Topic withComplexity(String best, String avg, String worst,
                                String bestNote, String avgNote, String worstNote,
                                String space, String spaceNote) {
        this.complexityBest = best;
        this.complexityAvg = avg;
        this.complexityWorst = worst;
        this.complexityBestNote = bestNote;
        this.complexityAvgNote = avgNote;
        this.complexityWorstNote = worstNote;
        this.spaceComplexity = space;
        this.spaceNote = spaceNote;
        return this;
    }

    public Topic withCode(String pseudocode, String pythonCode) {
        this.pseudocode = pseudocode;
        this.pythonCode = pythonCode;
        return this;
    }

    public Topic withUseCases(List<UseCase> useCases) {
        this.useCases = useCases;
        return this;
    }

    public Topic withInterviewQuestions(List<String> questions, List<String> difficulties) {
        this.interviewQuestions = questions;
        this.questionDifficulties = difficulties;
        return this;
    }

    public Topic withVisualizer(boolean hasVisualizer) {
        this.hasVisualizer = hasVisualizer;
        return this;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getVideoFile() { return videoFile; }
    public String getThumbnailColor() { return thumbnailColor; }
    public String getIcon() { return icon; }
    public String[] getTags() { return tags; }
    public String getComplexityBest() { return complexityBest; }
    public String getComplexityAvg() { return complexityAvg; }
    public String getComplexityWorst() { return complexityWorst; }
    public String getComplexityBestNote() { return complexityBestNote; }
    public String getComplexityAvgNote() { return complexityAvgNote; }
    public String getComplexityWorstNote() { return complexityWorstNote; }
    public String getSpaceComplexity() { return spaceComplexity; }
    public String getSpaceNote() { return spaceNote; }
    public String getPseudocode() { return pseudocode; }
    public String getPythonCode() { return pythonCode; }
    public List<UseCase> getUseCases() { return useCases; }
    public List<String> getInterviewQuestions() { return interviewQuestions; }
    public List<String> getQuestionDifficulties() { return questionDifficulties; }
    public boolean isHasVisualizer() { return hasVisualizer; }
}