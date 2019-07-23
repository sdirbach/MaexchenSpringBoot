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

	@PostMapping(ServiceUrl.DICE_ROLL)
	public String diceRoll(@RequestBody String dice) {
//		logger.info("DICE_ROLL:" + dice);
		return dice;
	}

	@PostMapping(ServiceUrl.ROUND_ENDED)
	public String roundEnded(@RequestBody String roundEnded) {
//		logger.info(roundEnded);
		return roundEnded;
	}

	@PostMapping(ServiceUrl.CURRENT_DICE_ROLL)
	public String currentRoll(@RequestBody String dice) {
//		logger.info(dice);
		return dice;
	}

	@PostMapping(ServiceUrl.SEE_OR_ROLL)
	public String seeOrRoll(@RequestBody String seeOrRoll) {
//		logger.info(seeOrRoll);
		return "ROLL";
	}

}
