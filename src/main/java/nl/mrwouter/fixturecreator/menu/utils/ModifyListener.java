package nl.mrwouter.fixturecreator.menu.utils;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ModifyListener implements DocumentListener {

	public void onModify() {}

	@Override
	public void changedUpdate(DocumentEvent e) {
		onModify();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		onModify();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		onModify();
	}
}
