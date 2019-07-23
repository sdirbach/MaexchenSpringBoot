package at.rvs.maexchen.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

	private List<Team> playingTeams = new ArrayList<Team>();

	@Autowired
	private transient Logger logger;

	private boolean gameRunning;

	private long round = 0;

	@PostMapping("/join")
	public Team joinGame(@RequestBody Team team) {
		if (!gameRunning) {
			logger.info("Team " + team.getName() + " joined game.");
			team.setPoints(5);

			teamIsNotAllowedToJoin(team);

			playingTeams.add(team);
			return team;
		} else {
			throw new IllegalArgumentException("to late to join");
		}
	}

	private void teamIsNotAllowedToJoin(Team team) {
		if (playingTeams.stream().filter(p -> p.getName().equals(team.getName())).findAny().isPresent()) {
			throw new IllegalArgumentException("already joined - don't cheat!");
		}
		if (StringUtils.isEmpty(team.getName())) {
			throw new IllegalArgumentException("No Teamname!!");
		}

		if (StringUtils.isEmpty(team.getUrl())) {
			throw new IllegalArgumentException("No Service url");
		}
	}

	@GetMapping("/startGame/{started}")
	public Boolean startGame(@PathVariable boolean started) {
		gameRunning = started;
		maexchenService.setPlayingTeams(new ArrayList<Team>(playingTeams));
		maexchenService.shuffleTeams();
		maexchenService.determineTeams().forEach(team -> logger.info(team.getName()));
		maexchenService.determineTeams().forEach(team -> team.setPoints(5));
		return started;
	}

	@GetMapping("/teams")
	public List<Team> getTeams() {
		return maexchenService.determineTeams();
	}

	@Scheduled(fixedDelay = 1)
	public void runGameRound() {
		if (gameRunning) {
			logger.info("Round" + round++);
			gameRunning = maexchenService.runGameRound();
		}

	}

}
