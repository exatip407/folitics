package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: UserPrivacySettings
 * 
 * @author Mayank Sharma
 *
 */
@Entity
@Table(name = "User_Privacy_Data")
public class UserPrivacyData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    public UserPrivacyData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Column(nullable = false, length = 128)
    @NotNull(message = "error.UserPrivacySettings.userDataField.notNull")
    @Size(min = 1, max = 128, message = "error.UserPrivacySettings.userDataField.size")
    private String userDataField;

    @Column(nullable = false, length = 128)
    @Size(max = 128, message = "error.UserPrivacySettings.userDataValue.size")
    private String userDataValue;

    @Column(nullable = false, length = 10, columnDefinition = "enum('All','Friends','Foe','connection','none')")
    @NotNull(message = "error.UserPrivacySettings.notificationType.notNull")
    @Size(min = 1, max = 10, message = "error.UserPrivacySettings.notificationType.size")
    private String notificationType;

    @Column
    @NotNull(message = "error.UserPrivacySettings.userId.notNull")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserDataField() {
        return userDataField;
    }

    public void setUserDataField(String userDataField) {
        this.userDataField = userDataField;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

 /*   public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/

    /**
     * @return the userDataValue
     */
    public String getUserDataValue() {
        return userDataValue;
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
     * @param userDataValue the userDataValue to set
     */
    public void setUserDataValue(String userDataValue) {
        this.userDataValue = userDataValue;
    }

}
