package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: Response
 * @author Abhishek Patel
 */
@Entity
@Table(name = "response")
@PrimaryKeyJoinColumn(name = "id")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class Response extends Component implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false,length = 128, columnDefinition = "enum ('Agree','Disagree')")
    private String flag;
    
    @Column(nullable = false)
    private Timestamp edited;

	@Column(nullable = true, columnDefinition = "enum ('Active','Deleted','Updated')")
	private String state;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "userresponse", joinColumns = { @JoinColumn(name = "responseId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id") })
    private User _user;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "ComponentResponse", joinColumns = { @JoinColumn(name = "responseId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "componentId", referencedColumnName = "id") })
    private Component component;
	
    @Column(nullable = false, length = 512)
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="optionId" , referencedColumnName = "id")
    private Opinion opinion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId" , referencedColumnName = "id")
    private User user;

	@Column(nullable = false)
    private Timestamp timestamp;

    @OneToMany(mappedBy = "component")
    private List<Response> responses;

    public Response() {
        setTimestamp(DateUtils.getSqlTimeStamp());
    }

    public String getFlag() {
  		return flag;
  	}

    public String getState() {
		return state;
	}

	public void setState(String status) {
		this.state = status;
	}
    
  	public Opinion getOpinion() {
		return opinion;
	}

	public void setOpinion(Opinion opinion) {
		this.opinion = opinion;
	}

	public void setFlag(String flag) {
  		this.flag = flag;
  	}

  	public String getContent() {
  		return content;
  	}

  	public void setContent(String content) {
  		this.content = content;
  	}

  	public static long getSerialversionuid() {
  		return serialVersionUID;
  	}

    public Timestamp getEdited() {
        return this.edited;
    }

    public void setEdited(Timestamp edited) {
        this.edited = edited;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
}
