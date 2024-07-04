package com.blogzone.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class Comment {
    @Id
    private String commentId;
    private String userId;
    private String blogId;
    private String content;
    private Date date;
    private int likes;
    private int dislikes;
    
    public Comment() {
    }

    public Comment(String commentId, String userId, String blogId, String content, Date date, int likes, int dislikes) {
        this.commentId = commentId;
        this.userId = userId;
        this.blogId = blogId;
        this.content = content;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public String toString() {
        return "Comment [commentId=" + commentId + ", userId=" + userId + ", blogId=" + blogId + ", content=" + content
                + ", date=" + date + ", likes=" + likes + ", dislikes=" + dislikes + "]";
    }

}
