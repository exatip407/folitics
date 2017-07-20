package com.ohmuk.folitics.enums;

public enum FactType {

	SUBMITTED("Submitted"), PENDING("Pending"), ACCEPTED("Accepted"), REJECTED(
			"Rejected"), SCHEME("Scheme"), LOCALGRAPH("LocalGraph"), GLOBALGRAPH(
			"GlobalGraph"), UPDATED("Updated"),DELETED("Deleted");

	private FactType(String value) {
		this.value = value;
	}

	private String value;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public static final FactType getFactStatus(String value) {
		if (SUBMITTED.getValue().equals(value)) {
			return SUBMITTED;
		}
		if (PENDING.getValue().equals(value)) {
			return PENDING;
		}
		if (ACCEPTED.getValue().equals(value))
			return ACCEPTED;

		if (REJECTED.getValue().equals(value))
			return REJECTED;

		if (UPDATED.getValue().equals(value))
			return UPDATED;

		if (SCHEME.getValue().equals(value))
			return SCHEME;

		if (LOCALGRAPH.getValue().equals(value))
			return LOCALGRAPH;

		if (GLOBALGRAPH.getValue().equals(value))
			return GLOBALGRAPH;
		
		if (DELETED.getValue().equals(value))
			return DELETED;

		return null;
	}
}
