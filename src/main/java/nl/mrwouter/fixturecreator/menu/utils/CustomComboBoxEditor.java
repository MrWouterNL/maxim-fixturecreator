package nl.mrwouter.fixturecreator.menu.utils;

import java.awt.Component;
import java.util.Arrays;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;

import nl.mrwouter.fixturecreator.objects.parameter.stops.ValueDisplayFormat;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomComboBoxEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;
	private DefaultComboBoxModel model;

	public CustomComboBoxEditor() {
		super(new JComboBox());
		this.model = (DefaultComboBoxModel) ((JComboBox) getComponent()).getModel();
		Arrays.asList(ValueDisplayFormat.values()).stream().forEach(vdf -> model.addElement(vdf.toString()));
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}
}