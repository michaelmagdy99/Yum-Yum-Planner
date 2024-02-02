package com.example.yumyumplanner.model.data;

public class UserProfile {

    private String name;
    private String email;
    private String profileImageURL;

    public UserProfile(){

    }

    public UserProfile(String name, String email, String profileImageURL) {
        this.name = name;
        this.email = email;
        this.profileImageURL = profileImageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }
}
