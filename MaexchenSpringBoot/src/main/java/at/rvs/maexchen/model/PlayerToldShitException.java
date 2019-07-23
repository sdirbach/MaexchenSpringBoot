package at.rvs.maexchen.model;

public class PlayerToldShitException extends IllegalArgumentException implements FatalPlayerException {

	private final Team team;

	public PlayerToldShitException(Team team) {
		this.team = team;
	}

	public Team getTeam() {
		return team;
	}

}
