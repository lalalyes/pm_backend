package com.fudan.pm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lanping
 */
@Entity
@Table(name="venue")
public class Venue implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private int venueId;


    @Column(name = "venue_name")
    private String venueName;

    @Column(name = "campus")
    private String campus;

    public int getVenue_id() {
        return venueId;
    }

    public void setVenue_id(int venue_id) {
        this.venueId = venue_id;
    }

    public String getVenue_name() {
        return venueName;
    }

    public void setVenue_name(String venue_name) {
        this.venueName = venue_name;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }


}
