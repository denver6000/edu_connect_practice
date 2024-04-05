package com.denproj.educonnectv2.room.entity;

import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Resource.class, childColumns = "resourceId", parentColumns = "resourceId")})
public class ResourceFile {

    @PrimaryKey(autoGenerate = true)
    public int fileId;
    public String filePath;

    public long resourceId;

    @Ignore
    public Uri fileUri;

    public ResourceFile(String filePath, long resourceId) {
        this.filePath = filePath;
        this.resourceId = resourceId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        ResourceFile resourceFile = (ResourceFile) obj;
        assert resourceFile != null;
        return this.fileId == resourceFile.fileId;
    }


}
