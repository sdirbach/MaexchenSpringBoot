package at.rvs.maexchen.rest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.rvs.maexchen.service.ServiceUrl;

@RestController
public class MaexchenSpielerRestService {

	@Autowired
	private transient Logger logger;

	private String lastDice;

	@PostMapping(ServiceUrl.DICE_ROLL)
	public String diceRoll(@RequestBody String dice) {
		logger.info("DICE_ROLL:" + dice);

		if (dice.contains("3")) {
			return "21";
		}

		if (dice.contains("4")) {
			return "66";
		}

		return dice;
	}

	@PostMapping(ServiceUrl.ROUND_ENDED)
	public String roundEnded(@RequestBody String roundEnded) {
		logger.info(roundEnded);
		return roundEnded;
	}

	@PostMapping(ServiceUrl.CURRENT_DICE_ROLL)
	public String currentRoll(@RequestBody String dice) {
		logger.info(dice);
		lastDice = dice;
		return dice;
	}

	@PostMapping(ServiceUrl.SEE_OR_ROLL)
	public String seeOrRoll(@RequestBody String seeOrRoll) {
		logger.info(seeOrRoll);

		if (lastDice != null && lastDice.contains("21")) {
			return "I Mag nimma";
		}

		if (lastDice != null && lastDice.contains("66")) {
			return "SEE";
		}

		return "ROLL";
	}

}
