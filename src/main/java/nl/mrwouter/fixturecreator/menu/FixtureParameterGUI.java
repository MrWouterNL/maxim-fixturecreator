package nl.mrwouter.fixturecreator.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;

import nl.mrwouter.fixturecreator.menu.utils.CustomPSTEditor;
import nl.mrwouter.fixturecreator.menu.utils.CustomVDFEditor;
import nl.mrwouter.fixturecreator.menu.utils.LimitDocumentFilter;
import nl.mrwouter.fixturecreator.menu.utils.ModifyListener;
import nl.mrwouter.fixturecreator.objects.parameter.Parameter;
import nl.mrwouter.fixturecreator.objects.parameter.ParameterType;
import nl.mrwouter.fixturecreator.objects.parameter.stops.ParameterStop;
import nl.mrwouter.fixturecreator.objects.parameter.stops.ParameterStopType;
import nl.mrwouter.fixturecreator.objects.parameter.stops.ValueDisplayFormat;
import nl.mrwouter.fixturecreator.objects.parameter.stops.WheelStop;

public class FixtureParameterGUI extends JPanel {

	private static final long serialVersionUID = 5180782518088711551L;
	// private FixtureGUI fixtureGui;
	private Parameter parameter = new Parameter("", -1, new int[] { -1 }, ParameterType.NO_STORE, new ArrayList<>());
	private JPanel panel;
	private JComboBox<Integer> parameterDisplay;
	private JComboBox<String> comboBox;
	private JList<String> attributes;
	private JTextField parameterName;
	private JTable table;
	private JTextField stopCount;

	public FixtureParameterGUI(MainFixtureFrame fixtureGui, Parameter param) {
		// this.fixtureGui = fixtureGui;
		if (param != null) {
			parameter = param;
		}
		panel = new JPanel();
		panel.setBounds(100, 100, 700, 450);
		panel.setBackground(new Color(230, 230, 250));

		JButton deleteParameterButton = new JButton("Delete Parameter");

		final FixtureParameterGUI self = this;
		deleteParameterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fixtureGui.removeFixtureParameter(self);
			}
		});

		JLabel parameterNameHint = new JLabel();
		parameterNameHint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		parameterNameHint.setText("Parameter name");

		JLabel parameterDisplayHint = new JLabel();
		parameterDisplayHint.setFont(new Font("Dialog", Font.PLAIN, 11));
		parameterDisplayHint.setText("Parameter display");

		JLabel parameterTypeHint = new JLabel();
		parameterTypeHint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		parameterTypeHint.setText("Parameter Type");

		JLabel attributeListHint = new JLabel();
		attributeListHint.setText("Attributes");
		attributeListHint.setFont(new Font("Dialog", Font.PLAIN, 11));

		parameterName = new JTextField();
		parameterName.setColumns(12);
		((AbstractDocument) parameterName.getDocument()).setDocumentFilter(new LimitDocumentFilter(9));
		parameterName.getDocument().addDocumentListener(new ModifyListener() {
			@Override
			public void onModify() {
				parameter.setName(parameterName.getText());
			}
		});

		parameterDisplay = new JComboBox<Integer>();
		parameterDisplay.setModel(new DefaultComboBoxModel<Integer>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
				12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24 }));
		((JTextField) parameterDisplay.getEditor().getEditorComponent()).getDocument()
				.addDocumentListener(new ModifyListener() {
					@Override
					public void onModify() {
						parameter.setDisplayerNum(Integer.valueOf(parameterDisplay.getSelectedItem().toString()));
					}
				});

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(Arrays.asList(ParameterType.values()).stream()
				.map(ParameterType::name).collect(Collectors.toList()).toArray(new String[3])));
		((JTextField) comboBox.getEditor().getEditorComponent()).getDocument()
				.addDocumentListener(new ModifyListener() {
					@Override
					public void onModify() {
						parameter.setType(ParameterType.valueOf(comboBox.getSelectedItem().toString()));
					}
				});

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane tablePane = new JScrollPane();

		JLabel stopCountHint = new JLabel();
		stopCountHint.setText("Number of stops");
		stopCountHint.setFont(new Font("Dialog", Font.PLAIN, 11));

		stopCount = new JTextField();
		stopCount.setColumns(12);
		stopCount.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					int rowCount = Integer.parseInt(stopCount.getText());
					Object[][] tableContent = new Object[rowCount][];
					if (table != null && table.getRowCount() != 0 && table.getColumnCount() != 0) {
						for (int i = 0; i < rowCount; i++) {
							if (i < table.getRowCount()) {
								String row0 = table.getValueAt(i, 0) == null ? null : table.getValueAt(i, 0).toString();
								ParameterStopType row1 = table.getValueAt(i, 1) == null
										? ParameterStopType.NON_MOUSEABLE
										: (ParameterStopType) table.getValueAt(i, 1);

								String row2 = table.getValueAt(i, 2) == null ? "" : table.getValueAt(i, 2).toString();
								ValueDisplayFormat row3 = table.getValueAt(i, 3) == null ? null
										: (ValueDisplayFormat) table.getValueAt(i, 3);
								String row4 = table.getValueAt(i, 4) == null ? null : (String) table.getValueAt(i, 4);

								tableContent[i] = new Object[] { row0, row1, row2, row3, row4 };
							} else {
								int jump = 255 / rowCount;
								int rangeStart = jump * i;
								int rangeEnd = jump * (i + 1);
								tableContent[i] = new Object[] { "", ParameterStopType.NON_MOUSEABLE,
										rangeStart + "-" + rangeEnd, ValueDisplayFormat.NO_VALUE_DISPLAYED, "" };
							}
						}
						tablePane.setViewportView(getTable(rowCount, tableContent));
					} else {
						for (int i = 0; i < rowCount; i++) {
							int jump = 255 / rowCount;
							int rangeStart = jump * i;
							int rangeEnd = jump * (i + 1);
							tableContent[i] = new Object[] { "", ParameterStopType.NON_MOUSEABLE,
									rangeStart + "-" + rangeEnd, ValueDisplayFormat.NO_VALUE_DISPLAYED, "" };
						}
						tablePane.setViewportView(getTable(rowCount, tableContent));
					}

				} catch (NumberFormatException ex) {
					//
				}
			}
		});

		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(tablePane, GroupLayout.PREFERRED_SIZE, 654,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
								.addGroup(
										groupLayout.createParallelGroup(Alignment.TRAILING)
												.addGroup(groupLayout
														.createSequentialGroup()
														.addComponent(attributeListHint, GroupLayout.DEFAULT_SIZE, 97,
																Short.MAX_VALUE)
														.addGap(593))
												.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
														.createParallelGroup(Alignment.LEADING)
														.addGroup(groupLayout.createSequentialGroup()
																.addGroup(groupLayout
																		.createParallelGroup(Alignment.TRAILING)
																		.addGroup(groupLayout
																				.createSequentialGroup()
																				.addComponent(parameterNameHint,
																						GroupLayout.DEFAULT_SIZE, 136,
																						Short.MAX_VALUE)
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addComponent(
																						parameterName,
																						GroupLayout.PREFERRED_SIZE, 225,
																						GroupLayout.PREFERRED_SIZE))
																		.addGroup(groupLayout
																				.createSequentialGroup()
																				.addGroup(groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								parameterDisplayHint,
																								GroupLayout.DEFAULT_SIZE,
																								136, Short.MAX_VALUE)
																						.addComponent(
																								stopCountHint,
																								GroupLayout.PREFERRED_SIZE,
																								95,
																								GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addGroup(groupLayout
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(parameterDisplay,
																								0,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								comboBox, 0, 225,
																								Short.MAX_VALUE)
																						.addComponent(
																								scrollPane,
																								Alignment.TRAILING,
																								GroupLayout.PREFERRED_SIZE,
																								225,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(stopCount,
																								Alignment.TRAILING,
																								GroupLayout.PREFERRED_SIZE,
																								225,
																								GroupLayout.PREFERRED_SIZE))))
																.addGap(142).addComponent(deleteParameterButton))
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(parameterTypeHint,
																		GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
																.addGap(272)))
														.addGap(36))))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(parameterName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(parameterNameHint, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(deleteParameterButton))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(parameterDisplayHint)
						.addComponent(parameterDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout
						.createParallelGroup(Alignment.BASELINE).addComponent(parameterTypeHint).addComponent(comboBox,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(attributeListHint, GroupLayout.PREFERRED_SIZE, 16,
										GroupLayout.PREFERRED_SIZE)
								.addGap(63))
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(stopCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(stopCountHint, GroupLayout.PREFERRED_SIZE, 16,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addGap(26).addComponent(tablePane, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(88, Short.MAX_VALUE)));
		attributes = new JList<>();
		scrollPane.setViewportView(attributes);
		attributes.setModel(new DefaultComboBoxModel<String>(fixtureGui.getFixture().getAttributes().stream()
				.map(atr -> atr.getChannel() + ". " + atr.getName()).collect(Collectors.toList())
				.toArray(new String[fixtureGui.getFixture().getAttributes().size()])));
		attributes.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				parameter.setAttributeList(attributes.getSelectedValuesList().stream()
						.mapToInt(i -> Integer.valueOf(i.split("\\.")[0])).toArray());
			}
		});

		if (param != null) {
			parameterName.setText(param.getName());
			parameterDisplay.setSelectedItem(param.getDisplayerNum());
			stopCount.setText(param.getStops().size() == 0 ? "1" : "" + param.getStops().size());

			String importedParameterName = param.getType().name();
			for (int i = 0; i < comboBox.getItemCount(); i++) {
				if (comboBox.getItemAt(i).split("\\.")[0].equals(importedParameterName)) {
					comboBox.setSelectedIndex(i);
				}
			}

			int[] selectedAttributes = new int[param.getAttributeList().length];
			int lastInserted = 0;
			for (int attribute : param.getAttributeList()) {
				for (int i = 0; i < attributes.getModel().getSize(); i++) {
					String item = attributes.getModel().getElementAt(i);
					if (item.split("\\.")[0].equals("" + attribute)) {
						selectedAttributes[lastInserted] = i;
						lastInserted++;
					}
				}
			}
			attributes.setSelectedIndices(selectedAttributes);
		}

		Object[][] data = new Object[parameter.getStops().size()][];
		for (int i = 0; i < parameter.getStops().size(); i++) {
			ParameterStop stop = parameter.getStops().get(i);
			if (stop.getValueDisplayFormat() == ValueDisplayFormat.DEGREES) {
				data[i] = new Object[] { stop.getName(), stop.getType(), stop.getWheelStop().toString(),
						stop.getValueDisplayFormat(), stop.getDegreeRange().toString() };
			} else {
				data[i] = new Object[] { stop.getName(), stop.getType(), stop.getWheelStop().toString(),
						stop.getValueDisplayFormat(), "" };
			}
		}

		tablePane.setViewportView(getTable(2, data));

		panel.setLayout(groupLayout);
	}

	@SuppressWarnings("serial")
	private JTable getTable(int dataRows, Object[][] data) {
		String column[] = { "Name", "Type", "Range (min-max)", "Value display", "Degree range (min-max)" };
		table = new JTable(data, column) {
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 1:
					return ParameterStopType.class;
				case 3:
					return ValueDisplayFormat.class;
				default:
					return String.class;
				}
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				if (!table.getValueAt(row, 3).toString().equals(ValueDisplayFormat.DEGREES.name()))
					table.setValueAt("", row, 4);

				if (column == 4) {
					if (!table.getValueAt(row, 3).toString().equals(ValueDisplayFormat.DEGREES.name())) {
						return false;
					}
				}
				return true;
			}
		};
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumn("Value display").setCellEditor(new CustomVDFEditor());
		table.getColumn("Type").setCellEditor(new CustomPSTEditor());

		return table;
	}

	public JPanel getPanel() {
		return panel;
	}

	public Parameter getParameter() throws IllegalArgumentException {
		parameter.getStops().clear();

		for (int row = 0; row < table.getRowCount(); row++) {
			// "Name", "Type", "Range (min-max)", "Value display", "Degree range (min-max)"
			String name = isEmpty(table.getValueAt(row, 0)) ? null : table.getValueAt(row, 0).toString();
			ParameterStopType type = (ParameterStopType) table.getValueAt(row, 1);
			String range = isEmpty(table.getValueAt(row, 2)) ? null : table.getValueAt(row, 2).toString();
			ValueDisplayFormat vdf = (ValueDisplayFormat) table.getValueAt(row, 3);
			String degreerange = isEmpty(table.getValueAt(row, 4)) ? null : table.getValueAt(row, 4).toString();

			if (name == null)
				throw new IllegalArgumentException("Param " + parameter.getName() + " has an unnamed param stop.");
			if (range == null || !range.contains("-") || !isInteger(range.split("-")[0])
					|| !isInteger(range.split("-")[1]))
				throw new IllegalArgumentException(
						"Param stop " + name + " (" + parameter.getName() + ") has an invalid range.");
			if (vdf == ValueDisplayFormat.DEGREES) {
				if (degreerange == null || !degreerange.contains("-") || !isInteger(degreerange.split("-")[0])
						|| !isInteger(degreerange.split("-")[1])) {
					throw new IllegalArgumentException(
							"Param stop " + name + " (" + parameter.getName() + ") has an invalid degreerange.");
				}
			}

			WheelStop stoprange = WheelStop.parse(range);
			WheelStop degreeRange = vdf == ValueDisplayFormat.DEGREES ? WheelStop.parse(range) : null;

			parameter.getStops().add(new ParameterStop(name, type, stoprange, vdf, degreeRange));
		}
		return parameter;
	}

	private boolean isEmpty(Object obj) {
		return obj == null || obj.toString().isEmpty();
	}

	private boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
}
