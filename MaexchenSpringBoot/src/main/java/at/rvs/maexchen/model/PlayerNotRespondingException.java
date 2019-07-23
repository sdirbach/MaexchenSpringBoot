package at.rvs.maexchen.model;

public class PlayerNotRespondingException extends IllegalArgumentException {

	private final Team team;

	public PlayerNotRespondingException(Team team) {
		this.team = team;
	}

	public Team getTeam() {
		return team;
	}

}
