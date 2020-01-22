package nl.mrwouter.fixturecreator.objects.parameter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nl.mrwouter.fixturecreator.objects.parameter.stops.ParameterStop;

public class Parameter {

	private String name;
	private int displayerNum;
	private int[] attribList;
	private ParameterType type;
	private List<ParameterStop> stops;

	public Parameter(String name, int displayerNum, int[] attribList, ParameterType type, List<ParameterStop> stops) {
		this.name = name;
		this.displayerNum = displayerNum;
		this.attribList = attribList;
		this.type = type;
		this.stops = stops;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setType(ParameterType type) {
		this.type = type;
	}

	public ParameterType getType() {
		return type;
	}

	public void setDisplayerNum(int displayerNum) {
		this.displayerNum = displayerNum;
	}

	public int getDisplayerNum() {
		return displayerNum;
	}

	public void setAttributeList(int[] attribList) {
		this.attribList = attribList;
	}

	public int[] getAttributeList() {
		return attribList;
	}
	
	public List<ParameterStop> getStops() {
		return stops;
	}

	@Override
	public String toString() {
		return "{\"name\": \"" + getName() + "\", \"type:\"" + getType() + "\", \"displayerNum\": \""
				+ getDisplayerNum() + "\", \"attribList\":\""
				+ Arrays.stream(getAttributeList()).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "\"}";
	}
}
