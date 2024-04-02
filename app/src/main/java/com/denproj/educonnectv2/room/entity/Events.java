package com.denproj.educonnectv2.room.entity;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Events {

    @PrimaryKey(autoGenerate = true)
    public int eventId;
    public String eventName = "";
    public String eventDescription = "";
    public long eventStartDateInEpoch = -1;
    public long eventEndDateInEpoch = -1;

    public long eventTimeStartInMillis = -1;
    public long eventTimeEndInMillis = -1;



    public String posterPath = "";

    public Events(String eventName, String eventDescription, long eventStartDateInEpoch, long eventEndDateInEpoch, long eventTimeStartInMillis, long eventTimeEndInMillis, String posterPath) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventStartDateInEpoch = eventStartDateInEpoch;
        this.eventEndDateInEpoch = eventEndDateInEpoch;
        this.eventTimeStartInMillis = eventTimeStartInMillis;
        this.eventTimeEndInMillis = eventTimeEndInMillis;
        this.posterPath = posterPath;
    }

    public Events() {
    }

    @Ignore
    public boolean showInRcv = true;

    @Override
    public boolean equals(@Nullable Object obj) {
        Events events = (Events) obj;
        assert events != null;
        return events.eventId == this.eventId;
    }
}
