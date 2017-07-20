package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Embeddable
public class ConnectionId implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private Long connectionUserId;

    @Column(nullable = false)
    private Long userId;

}
