package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "User_connection")
public class UserConnection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 10, columnDefinition = "enum('Pending','Accepted')")
    @NotNull(message = "error.userConnection.connectionStatus.notNull")
    @Size(min = 1, max = 10, message = "error.userConnection.connectionStatus.size")
    private String connectionStatus;

    @Column(nullable = false)
    private Long connectionId;
    
    @Column(nullable = false)
    private Long userId;

  /*  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @NotNull(message = "error.userConnection.user.notNull")
    private User user;
*/
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the connectionStatus
     */
    public String getConnectionStatus() {
        return connectionStatus;
    }

    /**
     * @param connectionStatus the connectionStatus to set
     */
    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

/*    *//**
     * @return the user
     *//*
    public User getUser() {
        return user;
    }

    *//**
     * @param user the user to set
     *//*
    public void setUser(User user) {
        this.user = user;
    }*/

    /**
     * @return the connectionId
     */
    public Long getConnectionId() {
        return connectionId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @param connectionId the connectionId to set
     */
    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }

}
