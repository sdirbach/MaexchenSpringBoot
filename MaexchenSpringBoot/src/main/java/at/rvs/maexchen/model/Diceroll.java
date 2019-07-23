package at.rvs.maexchen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Diceroll {

	public static final String MAEXCHEN = "21";
	private static Random random = new Random();
	private String dices;
	private static List<String> diceValueTable;

	static {
		diceValueTable = new ArrayList<String>();
		diceValueTable.add("31");
		diceValueTable.add("32");
		diceValueTable.add("41");
		diceValueTable.add("42");
		diceValueTable.add("43");
		diceValueTable.add("51");
		diceValueTable.add("52");
		diceValueTable.add("53");
		diceValueTable.add("54");
		diceValueTable.add("61");
		diceValueTable.add("62");
		diceValueTable.add("63");
		diceValueTable.add("64");
		diceValueTable.add("65");
		diceValueTable.add("11");
		diceValueTable.add("22");
		diceValueTable.add("33");
		diceValueTable.add("44");
		diceValueTable.add("55");
		diceValueTable.add("66");
		diceValueTable.add("21");
	}

	public Diceroll() {
		int dice1 = (int) (1 + random.nextInt(6));
		int dice2 = (int) (1 + random.nextInt(6));
		dices = dice1 > dice2 ? "" + dice1 + dice2 : "" + dice2 + dice1;
	}

	public Diceroll(String dices) {
		if (!diceValueTable.contains(dices)) {
			throw new IllegalArgumentException("This is no a legal dices value: " + dices);
		}
		this.dices = dices;
	}

	public String getDices() {
		return dices;
	}

	public boolean isHigherOrEqualThan(Diceroll dices) {
		System.out.println("index this: " + diceValueTable.indexOf(this.dices));
		System.out.println("index para: " + diceValueTable.indexOf(dices.getDices()));
		return diceValueTable.indexOf(this.dices) >= diceValueTable.indexOf(dices.getDices());
	}

	public String toString() {
		return dices;
	}

	public boolean isMaexchen() {
		return dices.equals("21");
	}

	public boolean isHigher(Diceroll dices) {
		return diceValueTable.indexOf(this.dices) > diceValueTable.indexOf(dices.getDices());
	}

}
