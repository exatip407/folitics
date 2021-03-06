package com.ohmuk.folitics.hibernate.entity.task;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Entity
@Table(name = "personality")
public class Personality {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
	@Column(length = 512)
    private String name;

    @Column(length = 512)
    @Size(min = 1, max = 512, message = "error.user.emailId.size")
    private String emailId;

    
    @Column(length = 50)
    private String desingnation;
    
    @Column
    private Long phoneNo;
    
    @Column(length = 512)
    private String fbAccount;
    
    @Column(length = 512)
    private String twAccount;
    
    @Column(length = 50)
    private String preferedCommunication;
    
    @Column(length = 50)
    private String organization;
    
    @OneToMany(mappedBy = "personality")
	private Set<TaskPersonality> taskPersonality;
    
    public Set<TaskPersonality> getTaskPersonality() {
		return taskPersonality;
	}

	public void setTaskPersonality(Set<TaskPersonality> taskPersonality) {
		this.taskPersonality = taskPersonality;
	}

	@Lob
    private byte[] image;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the desingnation
     */
    public String getDesingnation() {
        return desingnation;
    }

    /**
     * @param desingnation the desingnation to set
     */
    public void setDesingnation(String desingnation) {
        this.desingnation = desingnation;
    }

    /**
     * @return the phoneNo
     */
    public Long getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo the phoneNo to set
     */
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * @return the fbAccount
     */
    public String getFbAccount() {
        return fbAccount;
    }

    /**
     * @param fbAccount the fbAccount to set
     */
    public void setFbAccount(String fbAccount) {
        this.fbAccount = fbAccount;
    }

    /**
     * @return the twAccount
     */
    public String getTwAccount() {
        return twAccount;
    }

    /**
     * @param twAccount the twAccount to set
     */
    public void setTwAccount(String twAccount) {
        this.twAccount = twAccount;
    }

    /**
     * @return the preferedCommunication
     */
    public String getPreferedCommunication() {
        return preferedCommunication;
    }

    /**
     * @param preferedCommunication the preferedCommunication to set
     */
    public void setPreferedCommunication(String preferedCommunication) {
        this.preferedCommunication = preferedCommunication;
    }

    /**
     * @return the image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

}
