package com.fudan.pm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "participate")
public class Participate {
    @Id
    @Column(name = "user_id")
    private int user_id;

    @Id
    @Column(name = "activity_id")
    private int activity_id;
    
    @Column(name = "sign_up_time")
    private Date sign_up_time;
    
    @Column(name = "present")
    private int present;
    
    @Column(name = "score")
    private int score;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "picture")
    private String picture;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public Date getSign_up_time() {
        return sign_up_time;
    }

    public void setSign_up_time(Date sign_up_time) {
        this.sign_up_time = sign_up_time;
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
