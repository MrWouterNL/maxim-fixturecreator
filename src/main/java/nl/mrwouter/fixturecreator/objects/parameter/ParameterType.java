package nl.mrwouter.fixturecreator.objects.parameter;

import java.util.Arrays;
import java.util.Optional;

public enum ParameterType {
	
	FOCUS("F"), COLOUR("C"), BEAM("B"), NO_STORE("N");
	
	
	private String abbrev;
	
	private ParameterType(String abbrev) {
		this.abbrev = abbrev;
	}
	
	public String getAbbreviation() {
		return abbrev;
	}
	
	public static ParameterType fromAbbreviation(String abbreviation) {
		Optional<ParameterType> matches = Arrays.stream(ParameterType.values()).filter(type->type.getAbbreviation().equalsIgnoreCase(abbreviation)).findFirst();
		if (!matches.isPresent()) {
			return null;
		}
		return matches.get();
	}
}
