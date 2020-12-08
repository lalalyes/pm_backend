package com.fudan.pm.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author lanping
 */
@Entity
@Table(name="launchActivity")
@IdClass(LaunchActivity.class)
public class LaunchActivity implements Serializable {
    private static final long serialVersionUID = -1822899102468052519L;
    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "activity_id")
    private int activityId;

    public LaunchActivity(int userId, int activityId) {
        this.userId = userId;
        this.activityId = activityId;
    }

    public LaunchActivity() {
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
