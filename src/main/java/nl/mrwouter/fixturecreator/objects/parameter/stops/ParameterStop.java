package nl.mrwouter.fixturecreator.objects.parameter.stops;

public class ParameterStop {

	private String name;
	private ParameterStopType type;
	private WheelStop wheelstop;
	private ValueDisplayFormat vdf;

	private WheelStop degreeRange;

	public ParameterStop(String name, ParameterStopType type, WheelStop stop, ValueDisplayFormat vdf) {
		this.name = name;
		this.type = type;
		this.wheelstop = stop;
		this.vdf = vdf;
	}

	public ParameterStop(String name, ParameterStopType type, WheelStop stop, ValueDisplayFormat vdf,
			WheelStop degreeRange) {
		this(name, type, stop, vdf);
		this.degreeRange = degreeRange;
	}

	public String getName() {
		return name;
	}

	public ParameterStopType getType() {
		return type;
	}

	/*
	 * Still needs an implementation for multiple wheelstops (page 22)
	 */
	public WheelStop getWheelStop() {
		return wheelstop;
	}

	public ValueDisplayFormat getValueDisplayFormat() {
		return vdf;
	}

	public WheelStop getDegreeRange() {
		return degreeRange;
	}

	@Override
	public String toString() {
		String degrees = vdf == ValueDisplayFormat.DEGREES
				? getDegreeRange().getStart() + "," + getDegreeRange().getEnd() + ","
				: "";
		return "(" + getType().getAbbreviation() + addZeros("" + getWheelStop().getStart()) + ":"
				+ addZeros("" + getWheelStop().getEnd()) + ")," + getValueDisplayFormat().getAbbreviation() + ","
				+ degrees + "\"" + getName() + "\"";
	}

	private String addZeros(String string) {
		return String.format("%1$3s", string);
	}
}
