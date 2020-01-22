package nl.mrwouter.fixturecreator.menu.utils;

import java.awt.Component;
import java.util.Arrays;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;

import nl.mrwouter.fixturecreator.objects.parameter.stops.ParameterStopType;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomPSTEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;
	private DefaultComboBoxModel model;

	public CustomPSTEditor() {
		super(new JComboBox());
		this.model = (DefaultComboBoxModel) ((JComboBox) getComponent()).getModel();
		Arrays.asList(ParameterStopType.values()).stream().forEach(pst -> model.addElement(pst.toString()));
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}
}