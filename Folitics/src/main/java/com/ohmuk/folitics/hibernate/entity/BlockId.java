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
public class BlockId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private Long blockUserId;

    @Column(nullable = false)
    private Long userId;

    public Long getBlockUserId() {
        return blockUserId;
    }

    public void setBlockUserId(Long blockUserId) {
        this.blockUserId = blockUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
