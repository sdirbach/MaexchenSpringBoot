package at.rvs.maexchen.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class Team implements Serializable {

	private String name;
	private String url;
	private int points;

	public Team() {
		this.points = 5;
	}

	@ApiModelProperty(example = "Hier könnte Ihr Team Name stehen.", required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ApiModelProperty(example = "Hier könnte Ihre Url stehen.", required = true)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPoints() {
		return points;
	}

	@ApiModelProperty(hidden = true)
	public void setPoints(int points) {
		this.points = points;
	}

	public int decreaseAndGetPoints() {
		return --this.points;
	}

}
