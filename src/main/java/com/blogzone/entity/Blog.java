package com.blogzone.entity;

import java.util.Date;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "blogs")
public class Blog {
    @Id
    private String id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Content is required")
    private String content;
    @NotBlank(message = "Author is required")
    private String authorId;
    private String dateString;
    private List<String> categories;
    private Binary productImage;
    private transient String imageBase64; 
    private List<Comment> comments;
    private int likes;
    private int dislikes;
    private int views;

    public Blog() {
        
    }

    public Blog(String id, @NotBlank(message = "Title is required") String title,
            @NotBlank(message = "Content is required") String content,
            @NotBlank(message = "Author is required") String authorId, String dateString,
            List<String> categories, Binary productImage, String imageBase64, List<Comment> comments, int likes,
            int dislikes, int views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.dateString = dateString;
        this.categories = categories;
        this.productImage = productImage;
        this.imageBase64 = imageBase64;
        this.comments = comments;
        this.likes = likes;
        this.dislikes = dislikes;
        this.views = views;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Binary getProductImage() {
        return productImage;
    }

    public void setProductImage(Binary productImage) {
        this.productImage = productImage;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "Blog [id=" + id + ", title=" + title + ", content=" + content + ", authorId=" + authorId + ", dateString=" + dateString + ", categories=" + categories + ", productImage=" + productImage
                + ", comments=" + comments + ", likes=" + likes + ", dislikes=" + dislikes + ", views=" + views + "]";
    }
    
}