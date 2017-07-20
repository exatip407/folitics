package com.ohmuk.folitics.hibernate.entity.quickproblem;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@IdClass(QuickPersonalityId.class)
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class QuickProblemPersonality implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "quickpersonalityId", nullable = false, referencedColumnName = "id")
	private QuickPersonality quickpersonality;

	@Id
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
}