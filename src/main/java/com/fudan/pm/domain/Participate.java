package com.fudan.pm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "participate")
@IdClass(Participate.class)
public class Participate implements Serializable {
    private static final long serialVersionUID = -983100731383151320L;
    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "activity_id")
    private int activityId;
    
    @Column(name = "sign_up_time")
    private Date signUpTime;
    
    @Column(name = "present")
    private int present;
    
    @Column(name = "score")
    private int score;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "picture")
    private String picture;

    public Participate(int userId, int activityId) {
        this.userId = userId;
        this.activityId = activityId;
        this.signUpTime = new Date();
        this.score = -1;
        this.comment = "";
        this.picture = "";
    }
    public Participate() {
    }

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int user_id) {
        this.userId = user_id;
    }

    public int getActivity_id() {
        return activityId;
    }

    public void setActivity_id(int activity_id) {
        this.activityId = activity_id;
    }

    public Date getSign_up_time() {
        return signUpTime;
    }

    public void setSign_up_time(Date sign_up_time) {
        this.signUpTime = sign_up_time;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
