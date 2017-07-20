package com.ohmuk.folitics.hibernate.entity.quickproblem;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class QuickPersonalityId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "quickpersonalityId", nullable = false, referencedColumnName = "id")
	private QuickPersonality quickpersonality;

	@ManyToOne
	@JoinColumn(name = "quickProblemId", nullable = false, referencedColumnName = "id")
	private QuickProblem quickProblem;

	
	public QuickPersonality getQuickpersonality() {
		return quickpersonality;
	}

	public void setQuickpersonality(QuickPersonality quickpersonality) {
		this.quickpersonality = quickpersonality;
	}

	public QuickProblem getQuickProblem() {
		return quickProblem;
	}

	public void setQuickProblem(QuickProblem quickProblem) {
		this.quickProblem = quickProblem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

