package nl.mrwouter.fixturecreator.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import nl.mrwouter.fixturecreator.menu.utils.CustomComboBoxEditor;
import nl.mrwouter.fixturecreator.menu.utils.LimitDocumentFilter;
import nl.mrwouter.fixturecreator.menu.utils.ModifyListener;
import nl.mrwouter.fixturecreator.objects.parameter.Parameter;
import nl.mrwouter.fixturecreator.objects.parameter.ParameterType;
import nl.mrwouter.fixturecreator.objects.parameter.stops.ValueDisplayFormat;

public class FixtureParameterGUI extends JPanel {

	private static final long serialVersionUID = 5180782518088711551L;
	// private FixtureGUI fixtureGui;
	private Parameter parameter = new Parameter("", -1, new int[] { -1 }, ParameterType.NO_STORE);
	private JPanel panel;
	private JComboBox<Integer> parameterDisplay;
	private JComboBox<String> comboBox;
	private JList<String> attributes;
	private JTextField parameterName;
	private JTable table;

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

		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(attributeListHint, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE).addGap(593))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(tablePane,
								GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
										.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(parameterNameHint, GroupLayout.DEFAULT_SIZE, 166,
														Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(parameterName,
														GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(parameterDisplayHint, GroupLayout.DEFAULT_SIZE, 166,
														Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
														.addComponent(parameterDisplay, 0, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(comboBox, 0, 225, Short.MAX_VALUE)
														.addComponent(scrollPane, Alignment.TRAILING,
																GroupLayout.PREFERRED_SIZE, 225,
																GroupLayout.PREFERRED_SIZE))))
										.addGap(142).addComponent(deleteParameterButton))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(parameterTypeHint, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
										.addGap(272)))))
						.addGap(36)));
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
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING,
								groupLayout.createSequentialGroup()
										.addComponent(attributeListHint, GroupLayout.PREFERRED_SIZE, 16,
												GroupLayout.PREFERRED_SIZE)
										.addGap(63))
						.addGroup(
								groupLayout.createSequentialGroup()
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 57,
												GroupLayout.PREFERRED_SIZE)
										.addGap(22)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(tablePane, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(86, Short.MAX_VALUE)));
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

		Object[][] data = new Object[2][];
		data[0] = new Object[] { "Yeeth", true, "0-127", ValueDisplayFormat.DEGREES, "0-170" };
		data[1] = new Object[] { "Yooth", true, "128-255", ValueDisplayFormat.ONE_DIGIT_AS_PERCENTAGE, "" };

		tablePane.setViewportView(getTable(2, data));

		panel.setLayout(groupLayout);
	}

	@SuppressWarnings("serial")
	private JTable getTable(int dataRows, Object[][] data) {
		String column[] = { "Name", "Mouseable", "Range (min-max)", "Value display", "Degree range (min-max)" };
		table = new JTable(data, column) {
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 1:
					return Boolean.class;
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
		table.getColumn("Value display").setCellEditor(new CustomComboBoxEditor());

		return table;
	}

	public JPanel getPanel() {
		return panel;
	}

	public Parameter getParameter() throws IllegalArgumentException {
		return parameter;
	}
}
