package com.techsource.mycheck.vo;

public class TargetBoardVO {

	@Override
	public String toString() {
		return "TargetBoardVO [id=" + id + ", description=" + description + ", name=" + name + ", status=" + status
				+ ", state=" + state + "]";
	}

	private int id;
	private String description;
	private String name;
	private String status;
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public TargetBoardVO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
