package at.rvs.maexchen.rest;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.rvs.maexchen.model.Team;
import at.rvs.maexchen.service.MaexchenService;

@RestController
public class MaexchenServerRestService {

	@Autowired
	private MaexchenService maexchenService;

	@Autowired
	private transient Logger logger;

	private boolean gameRunning;

	private long round = 0;

	@PostMapping("/join")
	public Team joinGame(@RequestBody Team team) {
		if (!gameRunning) {
			logger.info("Team " + team.getName() + " joined game.");
			team.setPoints(5);
			maexchenService.addTeamToGame(team);
			return team;
		} else {
			throw new IllegalArgumentException("to late to join");
		}
	}

	@GetMapping("/startGame/{started}")
	public Boolean startGame(@PathVariable boolean started) {
		gameRunning = started;
		maexchenService.shuffleTeams();
		maexchenService.determineTeams().stream().forEach(t -> logger.info(t.getName()));
		return started;
	}

	@GetMapping("/teams")
	public List<Team> getTeams() {
		return maexchenService.determineTeams();
	}

	@Scheduled(fixedDelay = 100)
	public void runGameRound() {
		if (gameRunning) {
			logger.info("Round" + round++);
			gameRunning = maexchenService.runGameRound();
		}

	}

}
