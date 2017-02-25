package com.techsource.mycheck.domain;
// Generated 19 Sep, 2016 11:57:55 AM by Hibernate Tools 5.1.0.Beta1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EmailGroup generated by hbm2java
 */
@Entity
@Table(name = "email_group", catalog = "mycheck")
public class EmailGroup implements java.io.Serializable {

	private Integer id;
	private String name;
	private String status;
	private Date created;
	private Date modified;
	private Date deleted;
	private Set<EmailgpEmployee> emailgpEmployees = new HashSet<EmailgpEmployee>(0);
	private Set<EmailRelation> emailRelations = new HashSet<EmailRelation>(0);

	public EmailGroup() {
	}

	public EmailGroup(String name, String status, Date created, Date modified, Date deleted,
			Set<EmailgpEmployee> emailgpEmployees, Set<EmailRelation> emailRelations) {
		this.name = name;
		this.status = status;
		this.created = created;
		this.modified = modified;
		this.deleted = deleted;
		this.emailgpEmployees = emailgpEmployees;
		this.emailRelations = emailRelations;
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

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "status", length = 8)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "deleted", length = 45)
	public Date getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "emailGroup",cascade=CascadeType.ALL)
	public Set<EmailgpEmployee> getEmailgpEmployees() {
		return this.emailgpEmployees;
	}

	public void setEmailgpEmployees(Set<EmailgpEmployee> emailgpEmployees) {
		this.emailgpEmployees = emailgpEmployees;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "emailGroup",cascade=CascadeType.ALL)
	public Set<EmailRelation> getEmailRelations() {
		return this.emailRelations;
	}

	public void setEmailRelations(Set<EmailRelation> emailRelations) {
		this.emailRelations = emailRelations;
	}

}
