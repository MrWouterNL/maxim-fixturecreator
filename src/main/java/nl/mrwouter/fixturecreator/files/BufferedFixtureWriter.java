package nl.mrwouter.fixturecreator.files;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class BufferedFixtureWriter extends BufferedWriter {

	public BufferedFixtureWriter(Writer out) {
		super(out);
	}
	
	public void writeln(String line) throws IOException {
		this.write(line);
		this.newLine();
	}	
}
