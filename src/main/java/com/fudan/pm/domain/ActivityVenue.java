package com.fudan.pm.domain;
import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author lanping
 */
@Entity
@Table(name="activityVenue")
public class ActivityVenue implements Serializable {
    private static final long serialVersionUID = 5650166152833698529L;
    @Id
    //@ManyToOne(cascade= CascadeType.MERGE)
    @Column(name = "activity_id")
    private int activityId;
    //private Activity activity;



//    @Column(name = "venue_id")
//    private int venue_id;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    public int getActivity_id() {
        return activityId;
    }

    public void setActivity_id(int activity_id) {
        this.activityId = activity_id;
    }

//    public Activity getActivity() {
//        return activity;
//    }
//
//    public void setActivity(Activity activity) {
//        this.activity = activity;
//    }

//    public int getVenue_id() {
//        return venue_id;
//    }
//
//    public void setVenue_id(int venue_id) {
//        this.venue_id = venue_id;
//    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
