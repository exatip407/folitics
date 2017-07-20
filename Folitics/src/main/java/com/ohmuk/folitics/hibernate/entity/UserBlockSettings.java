package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: UserBlockSettings
 * 
 * @author Mayank Sharma
 *
 */
@Entity
@Table(name="user_block_settings")
public class UserBlockSettings implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private BlockId id;

    public BlockId getId() {
        return id;
    }

    public void setId(BlockId id) {
        this.id = id;
    }
    
    
   
}
