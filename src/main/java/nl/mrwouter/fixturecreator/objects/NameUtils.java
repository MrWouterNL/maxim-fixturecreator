package nl.mrwouter.fixturecreator.objects;

public class NameUtils {
	
	private static char[] ILLEGAL_CHARACTERS = new char[] { '~', '&', '^', '|', '/', '\\', '*'};
	
	public static boolean containsIllegalCharacters(String string) {
		for (char character: ILLEGAL_CHARACTERS) {
			if (string.indexOf(character) != -1) {
				return true;
			}
		}
		return false;
	}
}
