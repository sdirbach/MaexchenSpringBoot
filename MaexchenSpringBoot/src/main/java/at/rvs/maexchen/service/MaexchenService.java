package at.rvs.maexchen.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.rvs.maexchen.model.Diceroll;
import at.rvs.maexchen.model.PlayerNotRespondingException;
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
	private PlayerClientService playerClient;
	@Autowired
	private transient Logger logger;

	private Diceroll lastPlayerRealDiceRoll;
	private Diceroll lastPlayerDiceRollTold;

	public void addTeamToGame(Team team) {
		teams.add(team);
	}

	public void shuffleTeams() {
		Collections.shuffle(teams);
		currentTeam = teams.get(FIRST);
	}

	public boolean runGameRound() {
		if (noTeamLeft()) {
			logger.warn("Game end without winner!");
			return false;
		}

		if (lastTeamStanding()) {
			logger.info("We have a Winner: " + teams.get(FIRST).getName());
			return false;
		}

		try {
			if (lastPlayerRealDiceRoll != null && Diceroll.MAEXCHEN.equals(lastPlayerRealDiceRoll.getDices())) {
				shameOnAllPlayersExcept(previousTeam);
				notifiyAllPlayersRoundEndedAndResetDices();
			}

			if (!firstPlayer()) {
				playTurn();
			}

			if (firstPlayer()) {
				rollTheDicesTellEveryoneAndSetNextTeam();
			}
			return true;
		} catch (PlayerNotRespondingException exception) {
			logger.warn("Player " + exception.getTeam().getName() + " did not respond!");
			shameOnPlayer(exception.getTeam());
			notifiyAllPlayersRoundEndedAndResetDices();
			if (exception.getTeam().getPoints() <= 0) {
				setNextTeam();
			}
			return true;
		}
	}

	private void playTurn() {
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
			notifiyAllPlayersRoundEndedAndResetDices();
		}
	}

	private void rollTheDicesTellEveryoneAndSetNextTeam() {
		Diceroll currentPlayerDiceRoll = new Diceroll();
		Diceroll currentPlayerDicerolltold = playerClient.notifyPlayer(currentTeam, currentPlayerDiceRoll);

		if (lastPlayerDiceRollTold != null && lastPlayerDiceRollTold.isHigherOrEqualThan(currentPlayerDicerolltold)) {
			logger.info("Last Player Roll was " + lastPlayerDiceRollTold + " was highter than" + currentPlayerDicerolltold);

			shameOnPlayer(currentTeam);
			notifiyAllPlayersRoundEndedAndResetDices();
			return;
		}

		lastPlayerDiceRollTold = currentPlayerDicerolltold;
		lastPlayerRealDiceRoll = currentPlayerDiceRoll;

		playerClient.notifyAllPlayers(teams, lastPlayerDiceRollTold);
		setNextTeam();
	}

	private void shameOnAllPlayersExcept(Team winner) {
		List<Team> tempTeams = new ArrayList<Team>(teams);

		for (Team team : tempTeams) {
			if (!team.equals(winner)) {
				shameOnPlayer(team);
			}
		}
	}

	private void shameOnPlayer(Team player) {
		if (player.decreaseAndGetPoints() <= 0) {
			teams.remove(player);
			logger.info("Player " + player.getName() + " decreased to 0. Player Removed!");
		} else {
			logger.info("Player " + player.getName() + " decreased by 1 to " + player.getPoints());
		}

	}

	private boolean previousPlayerLied() {
		return lastPlayerDiceRollTold.isHigherOrEqualThan(lastPlayerRealDiceRoll);
	}

	private void notifiyAllPlayersRoundEndedAndResetDices() {
		playerClient.notifyAllPlayerRoundEnded(teams);
		previousTeam = null;
		lastPlayerDiceRollTold = null;
		lastPlayerRealDiceRoll = null;
		logger.info("Round Ended!");
	}

	private boolean firstPlayer() {
		return previousTeam == null;
	}

	private void setNextTeam() {
		index++;

		if (index > teams.size() - 1) {
			index = 0;
		}
		previousTeam = currentTeam;
		currentTeam = teams.get(index);
	}

	private boolean lastTeamStanding() {
		return teams.size() == 1;
	}

	private boolean noTeamLeft() {
		return teams.isEmpty();
	}

	public List<Team> determineTeams() {
		return teams;
	}

}
