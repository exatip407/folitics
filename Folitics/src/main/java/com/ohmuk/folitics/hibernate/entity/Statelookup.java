package com.ohmuk.folitics.hibernate.entity;
import java.io.Serializable;

	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.Table;

	import com.fasterxml.jackson.annotation.JsonIdentityInfo;
	import com.voodoodyne.jackson.jsog.JSOGGenerator;

	@Entity
	@Table(name = "statelookup")
	@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")

	public class Statelookup implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(unique = true, nullable = false)
		private Long id;

		@Column(nullable = true, length = 255)
		private String state;

		

		@Column(nullable = true, length = 255)
		private String city;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}



	}


