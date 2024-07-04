package com.blogzone.entity;

import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String fullName;
    private String username;
    private String email;
    private String password;
    private Binary productImage;
    private transient String imageBase64;
    private List<String> totalBlogIds;
    private List<String> savedBlogIds;
    private List<String> followingIds;
    private List<String> followersIds;
    private List<String> likedBlogsIds;
    private List<String> dislikedBlogIds;
    private List<String> likedCommentsIds;
    private List<String> dislikedCommentIds;
    
    public User() {
    }

    

    public User(String id, String fullName, String username, String email, String password, Binary productImage,
            String imageBase64, List<String> totalBlogIds, List<String> savedBlogIds, List<String> followingIds,
            List<String> followersIds, List<String> likedBlogsIds, List<String> dislikedBlogIds,
            List<String> likedCommentsIds, List<String> dislikedCommentIds) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.productImage = productImage;
        this.imageBase64 = imageBase64;
        this.totalBlogIds = totalBlogIds;
        this.savedBlogIds = savedBlogIds;
        this.followingIds = followingIds;
        this.followersIds = followersIds;
        this.likedBlogsIds = likedBlogsIds;
        this.dislikedBlogIds = dislikedBlogIds;
        this.likedCommentsIds = likedCommentsIds;
        this.dislikedCommentIds = dislikedCommentIds;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getTotalBlogIds() {
        return totalBlogIds;
    }

    public void setTotalBlogIds(List<String> totalBlogIds) {
        this.totalBlogIds = totalBlogIds;
    }

    public List<String> getSavedBlogIds() {
        return savedBlogIds;
    }

    public void setSavedBlogIds(List<String> savedBlogIds) {
        this.savedBlogIds = savedBlogIds;
    }

    public List<String> getFollowingIds() {
        return followingIds;
    }

    public void setFollowingIds(List<String> followingIds) {
        this.followingIds = followingIds;
    }

    public List<String> getFollowersIds() {
        return followersIds;
    }

    public void setFollowersIds(List<String> followersIds) {
        this.followersIds = followersIds;
    }

    public List<String> getLikedBlogsIds() {
        return likedBlogsIds;
    }

    public void setLikedBlogsIds(List<String> likedBlogsIds) {
        this.likedBlogsIds = likedBlogsIds;
    }

    public List<String> getDislikedBlogIds() {
        return dislikedBlogIds;
    }

    public void setDislikedBlogIds(List<String> dislikedBlogIds) {
        this.dislikedBlogIds = dislikedBlogIds;
    }

    public List<String> getLikedCommentsIds() {
        return likedCommentsIds;
    }

    public void setLikedCommentsIds(List<String> likedCommentsIds) {
        this.likedCommentsIds = likedCommentsIds;
    }

    public List<String> getDislikedCommentIds() {
        return dislikedCommentIds;
    }

    public void setDislikedCommentIds(List<String> dislikedCommentIds) {
        this.dislikedCommentIds = dislikedCommentIds;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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



    @Override
    public String toString() {
        return "User [id=" + id + ", fullName=" + fullName + ", username=" + username + ", email=" + email
                + ", password=" + password + ", productImage=" + productImage + ", totalBlogIds=" + totalBlogIds
                + ", savedBlogIds=" + savedBlogIds + ", followingIds=" + followingIds + ", followersIds=" + followersIds
                + ", likedBlogsIds=" + likedBlogsIds + ", dislikedBlogIds=" + dislikedBlogIds + ", likedCommentsIds="
                + likedCommentsIds + ", dislikedCommentIds=" + dislikedCommentIds + "]";
    }

   
}