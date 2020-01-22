package nl.mrwouter.fixturecreator.objects.parameter.stops;

import java.util.Arrays;
import java.util.Optional;

import nl.mrwouter.fixturecreator.objects.parameter.ParameterType;

public enum ValueDisplayFormat {
	DEGREES("D"), NO_VALUE_DISPLAYED("N"), ONE_DIGIT_AS_PERCENTAGE("1"), TWO_DIGITS_AS_PERCENTAGE("2");

	private String abbreviaton;

	private ValueDisplayFormat(String abbreviaton) {
		this.abbreviaton = abbreviaton;
	}

	public String getAbbreviation() {
		return abbreviaton;
	}

	public static ValueDisplayFormat fromAbbreviation(String abbreviation) {
		Optional<ValueDisplayFormat> matches = Arrays.stream(ValueDisplayFormat.values())
				.filter(type -> type.getAbbreviation().equalsIgnoreCase(abbreviation)).findFirst();
		if (!matches.isPresent()) {
			return null;
		}
		return matches.get();
	}
}
