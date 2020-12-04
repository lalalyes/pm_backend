package com.fudan.pm.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author lanping
 */
@Entity
@Table(name="launchActivity")
public class LaunchActivity implements Serializable {
    private static final long serialVersionUID = -1822899102468052519L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private int activityId;

    //问题：activity_id和user_id都是主键，可是两者都标记@id时，会显示实体没有define IdClass
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

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
