package com.elh2ny.model.advicesResponceModel;

/**
 * Created by mostafa_anter on 2/4/17.
 */

public class AdviceModel {
    private Integer id;
    private String content;
    private String active;
    private String userId;
    private String createdAt;
    private String updatedAt;

    public AdviceModel() {
    }

    public AdviceModel(Integer id, String content, String active, String userId, String createdAt, String updatedAt) {
        this.id = id;
        this.content = content;
        this.active = active;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
