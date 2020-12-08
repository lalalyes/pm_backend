package com.fudan.pm.domain;
import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author lanping
 */
@Entity
@Table(name="activityVenue")
@IdClass(ActivityVenue.class)
public class ActivityVenue implements Serializable {
    private static final long serialVersionUID = 5650166152833698529L;
    @Id
    @Column(name = "activity_id")
    private int activityId;

    @Id
    @Column(name = "venue_id")
    private int venueId;

    public ActivityVenue(int activityId, int venueId) {
        this.activityId = activityId;
        this.venueId = venueId;
    }

    public ActivityVenue() {

    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }
}
