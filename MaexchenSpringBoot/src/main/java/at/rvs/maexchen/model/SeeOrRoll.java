package at.rvs.maexchen.model;

public enum SeeOrRoll {
	SEE, ROLL;

	public static SeeOrRoll getByName(String name) {
		for (SeeOrRoll value : SeeOrRoll.values()) {
			if (value.toString().equals(name)) {
				return value;
			}
		}

		throw new IllegalArgumentException("Unknown Action" + name);
	}
}
