package nl.mrwouter.fixturecreator.objects.parameter.stops;

import java.util.Arrays;
import java.util.Optional;

public enum ParameterStopType {
	MOUSEABLE("M"), NON_MOUSEABLE(" "), READ_ONLY("R");

	private String abbreviaton;

	private ParameterStopType(String abbreviaton) {
		this.abbreviaton = abbreviaton;
	}

	public String getAbbreviation() {
		return abbreviaton;
	}

	public static ParameterStopType fromAbbreviation(String abbreviation) {
		Optional<ParameterStopType> matches = Arrays.stream(ParameterStopType.values())
				.filter(type -> type.getAbbreviation().equalsIgnoreCase(abbreviation)).findFirst();
		if (!matches.isPresent()) {
			return ParameterStopType.NON_MOUSEABLE;
		}
		return matches.get();
	}
}
