package com.ohmuk.folitics.enums;

public enum NotificationReadStatus {

	READ("Read"), UNREAD("Unread");

	private String value;

	private NotificationReadStatus(String value) {

		setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static final NotificationReadStatus getNotificationReadStatus(
			String value) {

		if (READ.getValue().equals(value)) {
			return READ;
		}
		if (UNREAD.getValue().equals(value)) {
			return UNREAD;
		}

		return null;
	}
}
