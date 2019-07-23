package at.rvs.maexchen.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.rvs.maexchen.model.Diceroll;
import at.rvs.maexchen.model.SeeOrRoll;
import at.rvs.maexchen.model.Team;

@Service
public class MaexchenService {

	private static final int FIRST = 0;

	private List<Team> teams = new ArrayList<Team>();

	private Team currentTeam;
	private Team previousTeam;

	private int index = 0;

	@Autowired
	public PlayerClientService playerClient;

	private Diceroll lastPlayerRealDiceRoll;
	private Diceroll lastPlayerDiceRollTold;

	public void addTeamToGame(Team team) {
		teams.add(team);
	}

	public void shuffleTeams() {
		Collections.shuffle(teams);
		currentTeam = teams.get(FIRST);
	}

	public void runGameRound() {
		if (Diceroll.MAEXCHEN.equals(lastPlayerRealDiceRoll.getDices())) {
			shameOnAllPlayersExcept(previousTeam);
			notifiyAllPlayersRoundEnded();
		}
		if (!firstPlayer()) {
			SeeOrRoll decision = playerClient.notifyPlayerSeeOrRoll(currentTeam);
			if (SeeOrRoll.ROLL.equals(decision)) {
				rollTheDicesTellEveryoneAndSetNextTeam();
			}
			if (SeeOrRoll.SEE.equals(decision)) {
				if (previousPlayerLied()) {
					shameOnPlayer(previousTeam);
				} else {
					shameOnPlayer(currentTeam);
				}
				notifiyAllPlayersRoundEnded();
			}
		}
		if (firstPlayer()) {
			rollTheDicesTellEveryoneAndSetNextTeam();
		}
	}

	private void rollTheDicesTellEveryoneAndSetNextTeam() {
		lastPlayerRealDiceRoll = new Diceroll();
		Diceroll currentDicerolltold = playerClient.notifyPlayer(currentTeam, lastPlayerRealDiceRoll);
		if (lastPlayerDiceRollTold.isHigherOrEqualThan(currentDicerolltold)) {
			shameOnPlayer(currentTeam);
			notifiyAllPlayersRoundEnded();
			return;
		}
		lastPlayerDiceRollTold = currentDicerolltold;
		playerClient.notifyAllPlayers(teams, lastPlayerDiceRollTold);
		setNextTeam();
	}

	private void shameOnAllPlayersExcept(Team winner) {
		for (Team team : teams) {
			if (!team.equals(winner)) {
				shameOnPlayer(team);
			}
		}
	}

	private void shameOnPlayer(Team player) {
		if (player.decreaseAndGetPoints() <= 0) {
			teams.remove(player);
			System.out.println("Removed player " + player.getName());
		} else {
			System.out.println(player.getName() + " decreased by 1 to " + player.getPoints());
		}

	}

	private boolean previousPlayerLied() {
		return lastPlayerDiceRollTold.isHigherOrEqualThan(lastPlayerRealDiceRoll);
	}

	private void notifiyAllPlayersRoundEnded() {
		playerClient.notifyAllPlayerRoundEnded(teams);
		previousTeam = null;
		System.out.println("Round Ended!");
	}

	private boolean firstPlayer() {
		return previousTeam == null;
	}

	private void setNextTeam() {
		index++;

		if (index >= teams.size() - 1) {
			index = 0;
		}
		previousTeam = currentTeam;
		currentTeam = teams.get(index);
	}

	public List<Team> determineTeams() {
		return teams;
	}

}
