package com.shadorc.shadbot.api.json.image.r34;

import com.fasterxml.jackson.annotation.JsonProperty;

public class R34Post {

    @JsonProperty("tags")
    private String tags;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("source")
    private String source;
    @JsonProperty("width")
    private int width;
    @JsonProperty("height")
    private int height;
    @JsonProperty("has_children")
    private boolean hasChildren;

    public String getTags() {
        return this.tags;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public String getSource() {
        return this.source;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean hasChildren() {
        return this.hasChildren;
    }

    @Override
    public String toString() {
        return "R34Post{" +
                "tags='" + this.tags + '\'' +
                ", fileUrl='" + this.fileUrl + '\'' +
                ", source='" + this.source + '\'' +
                ", width=" + this.width +
                ", height=" + this.height +
                ", hasChildren=" + this.hasChildren +
                '}';
    }
}
