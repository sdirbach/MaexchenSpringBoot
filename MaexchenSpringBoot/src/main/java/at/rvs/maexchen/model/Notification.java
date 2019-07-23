package at.rvs.maexchen.model;

public class Notification {
	private String dices;
	private String playerWhoToldDices;

	public Notification(String dices, String playerWhoToldDices) {
		this.dices = dices;
		this.playerWhoToldDices = playerWhoToldDices;
	}

	public String getDices() {
		return dices;
	}

	public void setDices(String dices) {
		this.dices = dices;
	}

	public String getPlayerWhoToldDices() {
		return playerWhoToldDices;
	}

	public void setPlayerWhoToldDices(String playerWhoToldDices) {
		this.playerWhoToldDices = playerWhoToldDices;
	}

}
