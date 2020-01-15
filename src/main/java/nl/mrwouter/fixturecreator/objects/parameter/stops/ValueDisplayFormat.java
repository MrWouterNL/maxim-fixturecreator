package nl.mrwouter.fixturecreator.objects.parameter.stops;

public enum ValueDisplayFormat {
	DEGREES("D"), NO_VALUE_DISPLAYED("N"), ONE_DIGIT_AS_PERCENTAGE("1"), TWO_DIGITS_AS_PERCENTAGE("2");
	
	private String abbreviaton;
	private ValueDisplayFormat(String abbreviaton) {
		this.abbreviaton = abbreviaton;
	}
	
	public String getAbbreviaton() {
		return abbreviaton;
	}
}
