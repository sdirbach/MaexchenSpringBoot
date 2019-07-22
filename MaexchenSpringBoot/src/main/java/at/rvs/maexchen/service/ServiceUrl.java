package at.rvs.maexchen.service;

public enum ServiceUrl {
	ROUND_ENDED("/roundended"), DICE_ROLL("/diceroll"), CURRENT_DICE_ROLL("/currentdiceroll");

	private String url;

	private ServiceUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
