package at.rvs.maexchen.model;

import java.io.Serializable;

public class Team implements Serializable {

	private String name;
	private String url;
	private int points;

	public Team() {
		this.points = 5;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}

	public int decreaseAndGetPoints() {
		return this.points--;
	}

}
