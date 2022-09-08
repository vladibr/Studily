package com.example.Studili.classes;

public class Stats {
    Integer time;
    Integer totalDays;
    Integer tasksCompleted;
    Integer longerTimeStudied;
    Integer longestHoursStudied;
    Integer exp;

    public  Stats(){

    }
    public Stats(Integer time, Integer totalDays, Integer tasksCompleted,
                 Integer longerTimeStudied, Integer longestHoursStudied,Integer exp){

         setTime(time);
         setTotalDays(totalDays);
         setTasksCompleted(tasksCompleted);
         setLongerTimeStudied(longerTimeStudied);
         setLongestHoursStudied(longestHoursStudied);
         setExp(exp);
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Integer getTasksCompleted() {
        return tasksCompleted;
    }

    public void setTasksCompleted(Integer tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }

    public Integer getLongerTimeStudied() {
        return longerTimeStudied;
    }

    public void setLongerTimeStudied(Integer longerTimeStudied) {
        this.longerTimeStudied = longerTimeStudied;
    }

    public Integer getLongestHoursStudied() {
        return longestHoursStudied;
    }

    public void setLongestHoursStudied(Integer longestHoursStudied) {
        this.longestHoursStudied = longestHoursStudied;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }
}
