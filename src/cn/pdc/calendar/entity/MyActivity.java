package cn.pdc.calendar.entity;

import java.io.Serializable;

public class MyActivity implements Serializable {

	/**
	 * generated id
	 */
	private static final long serialVersionUID = 3474413207849757408L;
	private String title;
	private String start;
	private String end;
	private String description;
	private String location;

	public MyActivity() {
		super();
	}

	public MyActivity(String title, String start, String end,
			String description, String location) {
		super();
		this.title = title;
		this.start = start;
		this.end = end;
		this.description = description;
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
