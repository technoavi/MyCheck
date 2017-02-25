package com.techsource.mycheck.domain;
// Generated 9 Sep, 2016 6:31:52 PM by Hibernate Tools 5.1.0.Beta1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.engine.internal.Cascade;

/**
 * Company generated by hbm2java
 */
@Entity
@Table(name = "company", catalog = "mycheck")
public class Company implements java.io.Serializable {

	private Integer id;
	private String name;
	private String location;
	private Date created;
	private Date modified;
	private Date deleted;
	private String status;
	private String code;
	private Set<DivisionCompany> divisionCompanies = new HashSet<DivisionCompany>(0);

	public Company() {
	}

	public Company(String name, String location, Date created, Date modified, Date deleted, String status, String code,
			Set<DivisionCompany> divisionCompanies) {
		this.name = name;
		this.location = location;
		this.created = created;
		this.modified = modified;
		this.deleted = deleted;
		this.status = status;
		this.code = code;
		this.divisionCompanies = divisionCompanies;
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

	@Column(name = "location", length = 45)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	@Column(name = "status", length = 8)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "code", length = 45)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade=CascadeType.ALL)
	public Set<DivisionCompany> getDivisionCompanies() {
		return this.divisionCompanies;
	}

	public void setDivisionCompanies(Set<DivisionCompany> divisionCompanies) {
		this.divisionCompanies = divisionCompanies;
	}

}
