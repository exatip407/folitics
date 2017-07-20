package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Abhishek
 *
 */
@Embeddable
public class LikeId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    @NotNull(message = "error.likeId.componentId.notNull")
    private Long componentId;

    @Column(nullable = false)
    @NotNull(message = "error.likeId.userId.notNull")
    private Long userId;

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
