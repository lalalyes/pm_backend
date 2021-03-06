package com.fudan.pm.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author lanping
 */
@Entity
@Table(name="activity")
public class Activity implements Serializable {
    private static final long serialVersionUID = -7384679330427870404L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private int activityId;

    @OneToMany
    @JoinColumn(name = "activity_id")
    private List<ActivityVenue> activityVenues;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "type")
    private String type;

    @Column(name = "picture")
    private String picture;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "activity_start_time")
    private Date activityStartTime;

    @Column(name = "activity_end_time")
    private Date activityEndTime;

    @Column(name = "sign_up_start_time")
    private Date signUpStartTime;

    @Column(name = "sign_up_end_time")
    private Date signUpEndTime;

    @Column(name = "launch_time")
    private Date launchTime;

    @Column(name = "create_time")
    private Date createTime;

    public Activity(String activityName, String introduction, String type, String picture, int capacity, Date activityStartTime,
                    Date activityEndTime, Date signUpStartTime, Date signUpEndTime) {
        this.activityName = activityName;
        this.introduction = introduction;
        this.type = type;
        this.picture = picture;
        this.capacity = capacity;
        this.activityStartTime = activityStartTime;
        this.activityEndTime = activityEndTime;
        this.signUpStartTime = signUpStartTime;
        this.signUpEndTime = signUpEndTime;
        this.createTime = new Date();
    }

    public Activity() {

    }

    public int getActivity_id() {
        return activityId;
    }

    public void setActivity_id(int activity_id) {
        this.activityId = activity_id;
    }

    public String getActivity_name() {
        return activityName;
    }

    public void setActivity_name(String activity_name) {
        this.activityName = activity_name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getActivity_start_time() {
        return activityStartTime;
    }

    public void setActivity_start_time(Timestamp activity_start_time) {
        this.activityStartTime = activity_start_time;
    }

    public Date getActivity_end_time() {
        return activityEndTime;
    }

    public void setActivity_end_time(Timestamp activity_end_time) {
        this.activityEndTime = activity_end_time;
    }

    public Date getCreate_time() {
        return createTime;
    }

    public void setCreate_time(Timestamp create_time) {
        this.createTime = create_time;
    }

    public Date getLaunch_time() {
        return launchTime;
    }

    public void setLaunch_time(Timestamp launch_time) {
        this.launchTime = launch_time;
    }

    public Date getSign_up_start_time() {
        return signUpStartTime;
    }

    public void setSign_up_start_time(Timestamp sign_up_start_time) {
        this.signUpStartTime = sign_up_start_time;
    }

    public Date getSign_up_end_time() {
        return signUpEndTime;
    }

    public void setSign_up_end_time(Timestamp sign_up_end_time) {
        this.signUpEndTime = sign_up_end_time;
    }

    public List<ActivityVenue> getActivityVenues() {
        return activityVenues;
    }

    public void setActivityVenues(List<ActivityVenue> activityVenues) {
        this.activityVenues = activityVenues;
    }
}
