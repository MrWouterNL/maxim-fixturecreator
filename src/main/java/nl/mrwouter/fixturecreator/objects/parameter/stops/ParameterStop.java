package nl.mrwouter.fixturecreator.objects.parameter.stops;

public class ParameterStop {
	
	
	//General:
	// Mouseable
	// Wheel stop range
	// Value Display Format (D, N, 1, 2)
	// Parameter stop name
	
	//When VDF = D:
	// Min-max degree value
	
	
	
	//Non-degree based (color, gobo, etc.) :
	// Mouseable (M of ' ')
	// Wheel stop range (0:44)
	// Value Display Format (D, N, 1, 2) 
	// Parameter stop name
	
	// E.g.: stop = (   32:  32),N,"Color 2"
	// non mousable parameter (= "fixed value") stop with range 32-32, N = no display (only showing text, which is color 2 in this case)
	
	// Would it make sense to have multiple degree based parameter stops..? I guess not.
	//Degree based
	// Mouseable (M of ' ')
	// Wheel stop range (0:255)
	// Value Display Format (is D, duhh..)
	// Min,max degree value
	// Parameter stop name
	
	// E.g.: stop = (M 0:255),D,0,170,"Pan="
	// Mousable parameter stop from 0-255, in degrees, with degree range from 0 to 170 degr. and the name is 'Pan' 
	
	// When a parameter has only one wheel stop and it is mouseable, the text must end with an equals (=) sign.
	
}
