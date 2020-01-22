package nl.mrwouter.fixturecreator.objects.parameter.stops;

public class WheelStop {
	
	private int start, end;
	
	public WheelStop(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public String toString() {
		return start + "-" + end;
	}

}
