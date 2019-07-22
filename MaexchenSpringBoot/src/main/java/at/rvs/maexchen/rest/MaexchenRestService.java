package at.rvs.maexchen.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import at.rvs.app.Logger;
import at.rvs.maexchen.model.Team;
import at.rvs.maexchen.service.MaexchenService;

@RestController("/maexchen")
public class MaexchenRestService {

	@Autowired
	private Logger logger;

	@Autowired
	private MaexchenService maexchenService;

	private boolean gameStarted;

	@PostMapping(path = "/join")
	public void joinGame(Team team) {
		maexchenService.addTeamToGame(team);
	}

	@PostMapping(path = "/start")
	public void startGame(boolean started) {
		gameStarted = started;
		maexchenService.shuffleTeams();
	}

	@Scheduled(fixedDelay = 1000)
	public void runGameRound() {
		System.out.println("Round:");
		logger.info("Round");
		if (gameStarted) {
			maexchenService.runGameRound();
		}

	}

}
