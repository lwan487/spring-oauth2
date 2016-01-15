package com.surendra.oauth.dao.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

/**
 * @author surendra.singh
 *
 */
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable {

    @Id
    private String id;

    private int version;
    
    private Date timeCreated;

    public BaseEntity() {
        this(UUID.randomUUID());
    }

    public BaseEntity(UUID guid) {
        Assert.notNull(guid, "UUID is required");
        id = guid.toString();
        this.timeCreated = new Date();
    }

    public BaseEntity(String guid) {
        Assert.notNull(guid, "UUID is required");
        id = guid;
        this.timeCreated = new Date();
    }

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }
}
