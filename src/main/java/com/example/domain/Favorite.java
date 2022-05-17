package com.example.domain;

public class Favorite {
    
    private Integer favoriteId;
    private Integer id;
    private Integer administratorId;
    private Quiz quiz;
    
    public Integer getFavoriteId() {
        return favoriteId;
    }
    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getAdministratorId() {
        return administratorId;
    }
    public void setAdministratorId(Integer administratorId) {
        this.administratorId = administratorId;
    }
    public Quiz getQuiz() {
        return quiz;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    @Override
    public String toString() {
        return "Favorite [administratorId=" + administratorId + ", favoriteId=" + favoriteId + ", id=" + id + ", quiz="
                + quiz + "]";
    }

    
}
