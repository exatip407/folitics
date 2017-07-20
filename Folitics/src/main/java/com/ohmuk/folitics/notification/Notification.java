package com.ohmuk.folitics.notification;



import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author Harish Bagora
 */
public class Notification implements Serializable, Comparable<Notification> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String message;
    private Timestamp timeStamp;
    private String notificationType;
    private Set<Long> sendingUsers;

    

    /**
	 * @return the notificationType
	 */
	public String getNotificationType() {
		return notificationType;
	}

	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

  

   /* @Override
    public String toString() {
        return new StringBuffer()
                .append("id"+ id)
                .append("message"+ message)
                .append("timeStamp"+ timeStamp)
                .toString();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification appMsg = (Notification) o;

        return id.equals(appMsg.id);

    }

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Notification [id=" + id + ", message=" + message
				+ ", timeStamp=" + timeStamp + ", notificationType="
				+ notificationType + ", sendingUsers=" + sendingUsers + "]";
	}

	@Override
    public int hashCode() {
        return id.hashCode();
    }


    @Override
    public int compareTo(Notification o) {
        return this.getId().compareTo(o.getId());
    }

	/**
	 * @return the timeStamp
	 */
	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the sendingUsers
	 */
	public Set<Long> getSendingUsers() {
		return sendingUsers;
	}

	/**
	 * @param sendingUsers the sendingUsers to set
	 */
	public void setSendingUsers(Set<Long> sendingUsers) {
		this.sendingUsers = sendingUsers;
	}
}
