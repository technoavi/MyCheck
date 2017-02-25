package com.techsource.mycheck.domain;
// Generated 19 Sep, 2016 11:57:55 AM by Hibernate Tools 5.1.0.Beta1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EmailRelation generated by hbm2java
 */
@Entity
@Table(name = "email_relation", catalog = "mycheck")
public class EmailRelation implements java.io.Serializable {

	private Integer id;
	private Department department;
	private Division division;
	private EmailGroup emailGroup;
	private Date created;
	private Date modified;
	private Date deleted;

	public EmailRelation() {
	}

	public EmailRelation(Department department, Division division, EmailGroup emailGroup, Date created, Date modified,
			Date deleted) {
		this.department = department;
		this.division = division;
		this.emailGroup = emailGroup;
		this.created = created;
		this.modified = modified;
		this.deleted = deleted;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "depId")
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "businessdivId")
	public Division getDivision() {
		return this.division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emailgpId")
	public EmailGroup getEmailGroup() {
		return this.emailGroup;
	}

	public void setEmailGroup(EmailGroup emailGroup) {
		this.emailGroup = emailGroup;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", length = 0)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 0)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted", length = 0)
	public Date getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

}