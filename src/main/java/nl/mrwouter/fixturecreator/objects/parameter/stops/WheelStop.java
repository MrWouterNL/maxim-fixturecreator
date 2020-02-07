package nl.mrwouter.fixturecreator.objects.parameter.stops;

public class WheelStop {
	
	public static WheelStop parse(String string) {
		String[] stopRange = string.split("-");
		return new WheelStop(Integer.parseInt(stopRange[0]), Integer.parseInt(stopRange[1]));
	}
	
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
	
	@Override
	public String toString() {
		return start + "-" + end;
	}
}
