package com.example.website.model;

public class Topic {
    private String id;
    private String title;
    private String category;
    private String description;
    private String videoFile;
    private String thumbnailColor;
    private String icon;
    private String[] tags;

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
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getVideoFile() { return videoFile; }
    public String getThumbnailColor() { return thumbnailColor; }
    public String getIcon() { return icon; }
    public String[] getTags() { return tags; }
}
