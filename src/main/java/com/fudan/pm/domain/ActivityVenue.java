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
    @Id
    //@ManyToOne(cascade= CascadeType.MERGE)
    @Column(name = "activity_id")
    private int activity_id;
    //private Activity activity;



//    @Column(name = "venue_id")
//    private int venue_id;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
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
