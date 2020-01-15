package nl.mrwouter.fixturecreator.objects.parameter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Parameter {

	private String name;
	private int displayerNum;
	private int[] attribList;
	private ParameterType type;

	public Parameter(String name, int displayerNum, int[] attribList, ParameterType type) {
		this.name = name;
		this.displayerNum = displayerNum;
		this.attribList = attribList;
		this.type = type;
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

	@Override
	public String toString() {
		return "{\"name\": \"" + getName() + "\", \"type:\"" + getType() + "\", \"displayerNum\": \""
				+ getDisplayerNum() + "\", \"attribList\":\""
				+ Arrays.stream(getAttributeList()).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "\"}";
	}
}
