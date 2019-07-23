package at.rvs.maexchen.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.rvs.maexchen.service.ServiceUrl;

@RestController("/player")
public class MaexchenSpielerRestService {

	@PostMapping(ServiceUrl.DICE_ROLL)
	public String diceRoll(@RequestBody String dice) {
		System.out.println("DICE_ROLL:" + dice);
		return dice;
	}

	@PostMapping(ServiceUrl.ROUND_ENDED)
	public void roundEnded(@RequestBody String roundEnded) {
		System.out.println(roundEnded);
	}

	@GetMapping(ServiceUrl.CURRENT_DICE_ROLL)
	public void currentRoll(String dice) {
		System.out.println("CURRENT_DICE_ROLL" + dice);
	}

	@PostMapping(ServiceUrl.SEE_OR_ROLL)
	public String seeOrRoll(@RequestBody String seeOrRoll) {
		System.out.println(seeOrRoll);
		return "ROLL";
	}

}
