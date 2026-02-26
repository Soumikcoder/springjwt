package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;
    private String token;
    @OneToOne
    private MyUserDetails user;
    private boolean isExpired;
    
    public RefreshToken() {
    }

    
    public RefreshToken(String token, MyUserDetails user) {
        this.token = token;
        this.user = user;
        this.isExpired = false;
    }


    public RefreshToken(Integer tokenId, String token, MyUserDetails user, boolean isExpired) {
        this.tokenId = tokenId;
        this.token = token;
        this.user = user;
        this.isExpired = isExpired;
    }


    public Integer getTokenId() {
        return tokenId;
    }
    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public MyUserDetails getUser() {
        return user;
    }
    public void setUser(MyUserDetails user) {
        this.user = user;
    }
    public boolean isExpired() {
        return isExpired;
    }
    public void setExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }
    
}
