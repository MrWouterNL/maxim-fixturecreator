package nl.mrwouter.fixturecreator.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import nl.mrwouter.fixturecreator.objects.Attribute;
import nl.mrwouter.fixturecreator.objects.Fixture;
import nl.mrwouter.fixturecreator.objects.parameter.Parameter;
import nl.mrwouter.fixturecreator.objects.parameter.ParameterType;

public class FixtureParser {

	private static FixtureParser instance;

	public static FixtureParser getInstance() {
		if (instance == null) {
			instance = new FixtureParser();
		}
		return instance;
	}

	public void write(Fixture fixture, File targetFile) {
		try (BufferedFixtureWriter out = new BufferedFixtureWriter(new FileWriter(targetFile))) {
			DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

			out.writeln("// Autogenerated by FixtureCreator v1.0");
			if (fixture.getFullManufacturer() != null)
				out.writeln("// manufacturer = \"" + fixture.getFullManufacturer() + "\"");
			if (fixture.getFullName() != null)
				out.writeln("// name = \"" + fixture.getFullName() + "\"");

			out.writeln("// Last updated " + fmt.format(new Date()));

			out.newLine();

			out.writeln("Fixture");
			out.writeln("	manufacturer = \"" + fixture.getManufacturer().toUpperCase() + "\"");
			out.writeln("	name = \"" + fixture.getName() + "\"");
			out.writeln("	version = " + fixture.getVersion() + "");

			for (Attribute attr : fixture.getAttributes()) {
				out.newLine();
				out.writeln("	Attribute");
				out.writeln("		name = \"" + attr.getName() + "\"");
				out.writeln("		channel = " + attr.getChannel());
				if (attr.getFineChannel() != -1)
					out.writeln("		fineChan = " + attr.getFineChannel());
				if (attr.getMinVal() != -1) 
					out.writeln("		minval = " + attr.getMinVal());
				if (attr.getMaxVal() != -1) 
					out.writeln("		maxval = " + attr.getMinVal());
				
				out.writeln("		homeVal = " + attr.getHomeVal());
			}

			for (Parameter param : fixture.getParameters()) {
				System.out.println(param.toString());
				out.newLine();
				out.writeln("	Parameter");
				out.writeln("		name = \"" + param.getName() + "\"");
				out.writeln("		type = " + param.getType().getAbbreviation());
				out.writeln("		displayerNum = " + param.getDisplayerNum());
				out.writeln("		attribList = " + Arrays.stream(param.getAttributeList()).mapToObj(String::valueOf)
						.collect(Collectors.joining(",")));
			}
			out.write("end");
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Fixture parse(File file) {

		try (Scanner scanner = new Scanner(file)) {
			String fixtureManufacturer = null, fixtureName = null, fixtureVersion = null, fullManufacturer = null,
					fullName = null;
			List<Attribute> attributes = new ArrayList<>();
			List<Parameter> parameters = new ArrayList<>();

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if (line.replaceAll("\\s", "").isEmpty())
					continue;
				if (line.contains("//")) {
					if (line.contains("manufacturer") && line.contains("="))
						fullManufacturer = line.split("=")[1].replace("\"", "").replaceAll("\\s", "");
					if (line.contains("name") && line.contains("="))
						fullName = line.split("=")[1].replace("\"", "").replaceAll("\\s", "");
				}

				if (line.replaceAll("\\s", "").toLowerCase().equals("fixture")) {
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						if (line.replaceAll("\\s", "").isEmpty() || line.contains("//"))
							continue;

						if (line.contains("manufacturer") && line.contains("="))
							fixtureManufacturer = line.split("=")[1].replace("\"", "").replaceAll("\\s", "");
						else if (line.contains("name") && line.contains("="))
							fixtureName = line.split("=")[1].replace("\"", "").replaceAll("\\s", "");
						else if (line.contains("version") && line.contains("="))
							fixtureVersion = line.split("=")[1].replaceAll("\\s", "");
						else if (!line.toLowerCase().contains("fixture"))
							break;
					}
				}
				if (line.toLowerCase().contains("attribute")) {
					Entry<Attribute, NextFound> entry = parseAttribute(scanner);

					attributes.add(entry.getKey());
					while (entry.getValue() == NextFound.ATTRIBUTE) {
						entry = parseAttribute(scanner);
						attributes.add(entry.getKey());
					}
					
					Entry<Parameter, NextFound> parameterEntry = parseParameter(scanner);
					if (entry.getValue() == NextFound.PARAMETER) {
						parameters.add(parameterEntry.getKey());
						while (parameterEntry.getValue() == NextFound.PARAMETER) {
							parameterEntry = parseParameter(scanner);
							parameters.add(parameterEntry.getKey());
						}
						
					}
				}
			}

			return new Fixture(fixtureManufacturer, fixtureName, fixtureVersion, fullManufacturer, fullName, attributes,
					parameters);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Entry<Attribute, NextFound> parseAttribute(Scanner scanner) {
		String attributeName = null;
		int attributeChannel = 0, attributeFineChan = -1, attributeHomeVal = 0;
		NextFound next = NextFound.NOTHING;

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.replaceAll("\\s", "").isEmpty() || line.contains("//"))
				continue;

			if (line.contains("name") && line.contains("="))
				attributeName = line.split("=")[1].replace("\"", "").replaceAll("\\s", "");
			else if (line.contains("channel") && line.contains("="))
				attributeChannel = Integer.valueOf(line.split("=")[1].replaceAll("\\s", ""));
			else if (line.contains("fineChan") && line.contains("="))
				attributeFineChan = Integer.valueOf(line.split("=")[1].replaceAll("\\s", ""));
			else if (line.contains("homeVal") && line.contains("="))
				attributeHomeVal = Integer.valueOf(line.split("=")[1].replaceAll("\\s", ""));
			else {
				if (line.toLowerCase().contains("attribute")) {
					next = NextFound.ATTRIBUTE;
					break;
				}else if (line.toLowerCase().contains("parameter")) {
					next = NextFound.PARAMETER;
					break;
				}
			}
		}
		return new AbstractMap.SimpleEntry<Attribute, NextFound>(
				new Attribute(attributeName, attributeChannel, attributeHomeVal, attributeFineChan), next);
	}

	public Entry<Parameter, NextFound> parseParameter(Scanner scanner) {
		String parameterName = null, parameterType = null;
		int[] attribList = null;
		int displayerNum = -1;

		NextFound next = NextFound.NOTHING;

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.replaceAll("\\s", "").isEmpty() || line.contains("//"))
				continue;

			if (line.contains("name") && line.contains("="))
				parameterName = line.split("=")[1].replace("\"", "").replaceAll("\\s", "");
			else if (line.contains("type") && line.contains("="))
				parameterType = line.split("=")[1].replaceAll("\\s", "");
			else if (line.contains("displayerNum") && line.contains("="))
				displayerNum = Integer.valueOf(line.split("=")[1].replaceAll("\\s", ""));
			else if (line.contains("attribList") && line.contains("="))
				attribList = (int[]) Arrays.stream(line.split("=")[1].replaceAll("\\s", "").split(","))
						.mapToInt(i -> Integer.valueOf(i)).toArray();
			else {
				if (line.toLowerCase().contains("parameter")) {
					next = NextFound.PARAMETER;
					break;
				}
			}
		}
		return new AbstractMap.SimpleEntry<Parameter, NextFound>(
				new Parameter(parameterName, displayerNum, attribList, ParameterType.fromAbbreviation(parameterType)), next);
	}

	public enum NextFound {
		NOTHING, ATTRIBUTE, PARAMETER;
	}

}