package nl.mrwouter.fixturecreator.menu.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class LimitDocumentFilter extends DocumentFilter {

	private int limit;

	public LimitDocumentFilter(int limit) {
		this.limit = limit;
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		if (text == null)
			return;
		
		int currentLength = fb.getDocument().getLength();
		int overLimit = (currentLength + text.length()) - limit - length;
		if (overLimit > 0) {
			text = text.substring(0, text.length() - overLimit);
		}
		if (text.length() > 0) {
			super.replace(fb, offset, length, text, attrs);
		}
	}
}