package at.rvs.maexchen.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.rvs.maexchen.model.Diceroll;
import at.rvs.maexchen.model.SeeOrRoll;
import at.rvs.maexchen.model.Team;

@Service
public class PlayerClientService {

	public Diceroll notifyPlayer(Team team, Diceroll diceroll) {
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> request = new HttpEntity<String>(diceroll.getDices(), new HttpHeaders());
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(team.getUrl() + ServiceUrl.DICE_ROLL, request,
				String.class);

		String body = postForEntity.getBody();
		return new Diceroll(body);
	}

	public void notifyAllPlayers(List<Team> teams, Diceroll playersDiceRoll) {
		RestTemplate restTemplate = new RestTemplate();

		for (Team currentTeam : teams) {
			restTemplate.postForLocation(currentTeam.getUrl() + ServiceUrl.CURRENT_DICE_ROLL.getUrl(),
					playersDiceRoll.getDices());
		}
	}

	public void notifyAllPlayerRoundEnded(List<Team> teams) {
		RestTemplate restTemplate = new RestTemplate();

		for (Team currentTeam : teams) {
			restTemplate.postForLocation(currentTeam.getUrl() + ServiceUrl.ROUND_ENDED.getUrl(), "ROUND_ENDED");
		}
	}

	public SeeOrRoll notifyPlayerSeeOrRoll(Team currentPlayer) {
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> request = new HttpEntity<String>("SEE OR ROLL", new HttpHeaders());
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(currentPlayer.getUrl() + ServiceUrl.DICE_ROLL,
				request, String.class);

		String body = postForEntity.getBody();
		return SeeOrRoll.getByName(body);
	}

}
