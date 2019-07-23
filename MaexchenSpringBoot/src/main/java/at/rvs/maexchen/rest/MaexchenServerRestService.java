package at.rvs.maexchen.rest;

import java.util.List;

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

	private boolean gameStarted;

	private long round = 0;

	@PostMapping("/join")
	public Team joinGame(@RequestBody Team team) {
		if (!gameStarted) {
			team.setPoints(5);
			maexchenService.addTeamToGame(team);
			return team;
		} else {
			throw new IllegalArgumentException("to late to join");
		}
	}

	@GetMapping("/startGame/{started}")
	public Boolean startGame(@PathVariable boolean started) {
		gameStarted = started;
		maexchenService.shuffleTeams();
		return started;
	}

	@GetMapping("/teams")
	public List<Team> getTeams() {
		return maexchenService.determineTeams();
	}

	@Scheduled(fixedDelay = 1000)
	public void runGameRound() {
		System.out.println("skedulte round");
		if (gameStarted) {
			System.out.println("Round" + round++);
			maexchenService.runGameRound();
		}

	}

}
