package nl.mrwouter.fixturecreator.objects;

import java.util.ArrayList;
import java.util.List;

import nl.mrwouter.fixturecreator.objects.parameter.Parameter;

public class Fixture {

	private String manufacturer, name, fullManufacturer, fullName, version;
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private List<Parameter> parameters = new ArrayList<Parameter>();	

	public Fixture(String manufacturer, String name, String version) {
		this.manufacturer = manufacturer;
		this.name = name;
		this.version = version;
	}
	
	public Fixture(String manufacturer, String name, String version, String fullManufacturer, String fullName, List<Attribute> attributes, List<Parameter> parameters) {
		this(manufacturer, name, version);
		this.attributes = attributes;
		this.parameters = parameters;
		this.fullManufacturer = fullManufacturer;
		this.fullName = fullName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getFullManufacturer() {
		return fullManufacturer;
	}

	public void setFullManufacturer(String fullManufacturer) {
		this.fullManufacturer = fullManufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
}
