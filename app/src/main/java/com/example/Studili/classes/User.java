package com.example.Studili.classes;

import java.util.Map;


public class User {
    private String email;
    private String name;
    private Integer currentRank;
    private Stats stats;
    private Integer currentGif;
    private String registerDate;
    private Integer[] gifs;


    public User(String email, String name,Integer currentRank,Stats stats,String registerDate,Integer currentGif){
        setEmail(email);
        setName(name);
        setStats(stats);
        setCurrentRank(currentRank);
        setCurrentGif(currentGif);
        setRegisterDate(registerDate);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Integer[] getGifs() {
        return gifs;
    }

    public void setGifs(Integer[] gifs) {
        this.gifs = gifs;
    }

    public Integer getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(Integer currentRank) {
        this.currentRank = currentRank;
    }

    public Integer getCurrentGif() {
        return currentGif;
    }

    public void setCurrentGif(Integer currentGif) {
        this.currentGif = currentGif;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}
