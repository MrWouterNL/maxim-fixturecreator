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
		// (M 0:255),D,0,170,"Pan="
		// ( 32: 32),N,"Color 2"

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

	// General:
	// Mouseable
	// Wheel stop range
	// Value Display Format (D, N, 1, 2)
	// Parameter stop name

	// When VDF = D:
	// Min-max degree value

	// Non-degree based (color, gobo, etc.) :
	// Mouseable (M of ' ')
	// Wheel stop range (0:44)
	// Value Display Format (D, N, 1, 2)
	// Parameter stop name

	// E.g.: stop = ( 32: 32),N,"Color 2"
	// non mousable parameter (= "fixed value") stop with range 32-32, N = no
	// display (only showing text, which is color 2 in this case)

	// Would it make sense to have multiple degree based parameter stops..? I guess
	// not.
	// Degree based
	// Mouseable (M of ' ')
	// Wheel stop range (0:255)
	// Value Display Format (is D, duhh..)
	// Min,max degree value
	// Parameter stop name

	// E.g.: stop = (M 0:255),D,0,170,"Pan="
	// Mousable parameter stop from 0-255, in degrees, with degree range from 0 to
	// 170 degr. and the name is 'Pan'

	// When a parameter has only one wheel stop and it is mouseable, the text must
	// end with an equals (=) sign.

}
