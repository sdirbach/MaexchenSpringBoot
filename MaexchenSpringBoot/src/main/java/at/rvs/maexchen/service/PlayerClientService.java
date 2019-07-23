package at.rvs.maexchen.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.rvs.maexchen.model.Diceroll;
import at.rvs.maexchen.model.PlayerNotRespondingException;
import at.rvs.maexchen.model.SeeOrRoll;
import at.rvs.maexchen.model.Team;

@Service
public class PlayerClientService {

	@Autowired
	private transient Logger logger;

	public void notifyAllPlayers(List<Team> teams, Diceroll playersDiceRoll) {
		for (Team currentTeam : teams) {
			notify(currentTeam, ServiceUrl.CURRENT_DICE_ROLL, playersDiceRoll.getDices());
		}
	}

	public void notifyAllPlayerRoundEnded(List<Team> teams) {
		for (Team currentTeam : teams) {
			notify(currentTeam, ServiceUrl.ROUND_ENDED, "ROUND_ENDED");
		}

	}

	public Diceroll notifyPlayer(Team team, Diceroll diceroll) {
		try {
			RestTemplate restTemplate = new RestTemplate();

			logger.info("notifyPlayer: " + team.getName() + " url: " + team.getUrl() + ServiceUrl.DICE_ROLL + " rolled:" + diceroll.getDices());

			HttpEntity<String> request = new HttpEntity<String>(diceroll.getDices(), new HttpHeaders());
			ResponseEntity<String> postForEntity = restTemplate.postForEntity(team.getUrl() + ServiceUrl.DICE_ROLL, request, String.class);

			String body = postForEntity.getBody();
			return new Diceroll(body);
		} catch (Exception httpException) {
			throw new PlayerNotRespondingException(team);
		}
	}

	public SeeOrRoll notifyPlayerSeeOrRoll(Team currentPlayer) {
		try {
			RestTemplate restTemplate = new RestTemplate();

			logger.info("notifyPlayerSeeOrRoll: " + currentPlayer.getName() + " url: " + currentPlayer.getUrl() + ServiceUrl.SEE_OR_ROLL + " See or Roll?");

			HttpEntity<String> request = new HttpEntity<String>("SEE OR ROLL", new HttpHeaders());
			ResponseEntity<String> postForEntity = restTemplate.postForEntity(currentPlayer.getUrl() + ServiceUrl.SEE_OR_ROLL, request, String.class);

			String body = postForEntity.getBody();

			logger.info(currentPlayer.getName() + " wants to " + body);
			return SeeOrRoll.getByName(body);
		} catch (Exception httpException) {
			throw new PlayerNotRespondingException(currentPlayer);
		}

	}

	private void notify(Team team, String url, String message) {
		RestTemplate restTemplate = new RestTemplate();

		try {
			logger.info("Notify Player:" + team.getName() + " " + message);
			restTemplate.postForLocation(team.getUrl() + url, message);
		} catch (Exception httpException) {
			logger.warn("Unable to Notify Player!");
		}
	}

}
