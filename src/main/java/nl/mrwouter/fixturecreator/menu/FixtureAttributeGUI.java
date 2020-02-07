package nl.mrwouter.fixturecreator.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import nl.mrwouter.fixturecreator.menu.utils.LimitDocumentFilter;
import nl.mrwouter.fixturecreator.objects.Attribute;

public class FixtureAttributeGUI extends JPanel {

	private static final long serialVersionUID = 5180782518088711551L;
	private MainFixtureFrame fixtureGui;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JTextField attributeCount;
	private JTable table;

	public FixtureAttributeGUI(MainFixtureFrame fixtureGui) {
		this.fixtureGui = fixtureGui;
		panel = new JPanel();
		panel.setBounds(100, 100, 700, 450);
		panel.setBackground(new Color(230, 230, 250));

		JLabel statusmessage = new JLabel();

		attributeCount = new JTextField();
		attributeCount.setColumns(10);
		((AbstractDocument) attributeCount.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
					throws BadLocationException {
				fb.insertString(off, str.replaceAll("\\D++", ""), attr);
			}

			@Override
			public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)
					throws BadLocationException {
				fb.replace(off, len, str.replaceAll("\\D++", ""), attr);
			}
		});

		attributeCount.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					int rowCount = Integer.parseInt(attributeCount.getText());
					Object[][] tableContent = new Object[rowCount][];
					if (table != null && table.getRowCount() != 0) {
						for (int i = 0; i < rowCount; i++) {
							if (i < table.getRowCount()) {
								Integer row0 = table.getValueAt(i, 0) == null ? null : (Integer) table.getValueAt(i, 0);
								Integer row1 = table.getValueAt(i, 1) == null ? null : (Integer) table.getValueAt(i, 1);
								String row2 = table.getValueAt(i, 2) == null ? "" : table.getValueAt(i, 2).toString();
								Integer row3 = table.getValueAt(i, 3) == null ? null : (Integer) table.getValueAt(i, 3);
								Integer row4 = table.getValueAt(i, 4) == null ? null : (Integer) table.getValueAt(i, 4);
								Integer row5 = table.getValueAt(i, 5) == null ? null : (Integer) table.getValueAt(i, 5);
								Boolean row6 = (Boolean) table.getValueAt(i, 6);

								tableContent[i] = new Object[] { row0, row1, row2, row3, row4, row5, row6 };
							} else {
								tableContent[i] = new Object[] { null, null, "", null, null, null, false };
							}
						}
						scrollPane.setViewportView(getTable(rowCount, tableContent));
					} else {
						scrollPane.setViewportView(getTable(rowCount));
					}

				} catch (NumberFormatException ex) {
					statusmessage.setText("Invalid number of attributes!");
					statusmessage.setForeground(Color.RED);
				}
			}
		});

		JLabel attributeCountHint = new JLabel();
		attributeCountHint.setFont(new Font("Tahoma", Font.BOLD, 11));
		attributeCountHint.setText("Number of attributes");
		attributeCount.setText("");

		int rowCount = 0;
		try {
			rowCount = Integer.parseInt(attributeCount.getText());
		} catch (NumberFormatException ex) {
			if (!attributeCount.getText().isEmpty()) {
				statusmessage.setText("Invalid number of attributes!");
			}
		}
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(getTable(rowCount));

		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(48)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup().addComponent(attributeCountHint).addGap(85)
								.addComponent(attributeCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(202).addComponent(statusmessage,
								GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane))
				.addContainerGap(77, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addGap(44)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(attributeCountHint)
								.addComponent(attributeCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(statusmessage).addGap(124)));
		panel.setLayout(groupLayout);
	}

	public JPanel getPanel() {
		return panel;
	}

	private JTable getTable(int dataRows) {
		Object data[][] = new Object[dataRows][];
		for (int i = 0; i < dataRows; i++) {
			if (i < fixtureGui.getFixture().getAttributes().size()) {
				Attribute attrib = fixtureGui.getFixture().getAttributes().get(i);
				data[i] = (Object[]) Arrays.asList(attrib.getChannel(), getTableValue(attrib.getFineChannel()),
						attrib.getName(), getTableValue(attrib.getHomeVal()), getTableValue(attrib.getMinVal(), true),
						getTableValue(attrib.getMaxVal()), attrib.hasVirtualDimmer()).toArray();
			} else {
				data[i] = (Object[]) Arrays.asList(null, null, "", null, null, null, false).toArray();
			}
		}

		return getTable(dataRows, data);
	}

	public Integer getTableValue(int value) {
		return getTableValue(value, false);
	}

	public Integer getTableValue(int value, boolean showZero) {
		return ((value == 0 && !showZero) || value == -1) ? null : value;
	}

	@SuppressWarnings("serial")
	private JTable getTable(int dataRows, Object[][] data) {
		JTextField field = new JTextField();
		((AbstractDocument) field.getDocument()).setDocumentFilter(new LimitDocumentFilter(9));
		DefaultCellEditor dce = new DefaultCellEditor(field);

		String column[] = { "Channel", "FineChan", "Name", "Homevalue", "MinValue", "MaxValue", "Virtual Dimmer" };
		table = new JTable(data, column) {
			@Override
			public TableCellEditor getCellEditor(int row, int column) {
				int modelColumn = convertColumnIndexToModel(column);

				if (modelColumn == 2) {
					return dce;
				} else {
					return super.getCellEditor(row, column);
				}
			}

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 2:
					return String.class;
				case 6:
					return Boolean.class;
				default:
					return Integer.class;
				}
			}
		};
		table.getTableHeader().setReorderingAllowed(false);

		return table;
	}

	public void redrawTable() {
		scrollPane.setViewportView(getTable(fixtureGui.getFixture().getAttributes().size()));
	}

	public List<Attribute> getAttributes() throws IllegalArgumentException {
		List<Attribute> attributes = new ArrayList<>();
		for (int count = 0; count < table.getRowCount(); count++) {
			if (table.getValueAt(count, 0).toString().isEmpty() && table.getValueAt(count, 2).toString().isEmpty())
				continue;

			int channel = -1, fineChan = -1, homeVal = 0, minval = -1, maxval = -1;
			String name = "";
			boolean virtualDimmer = false;

			if (table.getValueAt(count, 0).toString().isEmpty()) {
				throw new IllegalArgumentException("No attribute channel given on row " + (count + 1) + ".");
			} else {
				channel = (Integer) table.getValueAt(count, 0);
			}
			name = table.getValueAt(count, 2).toString().replaceAll("\\s", "");
			if (name.isEmpty())
				throw new IllegalArgumentException(
						"Column 3 on row " + (count + 1) + " expected a name, but empty string was received.");

			if (!isEmpty(table.getValueAt(count, 1)))
				fineChan = (Integer) table.getValueAt(count, 1);
			if (!isEmpty(table.getValueAt(count, 3)))
				homeVal = (Integer) table.getValueAt(count, 3);
			if (!isEmpty(table.getValueAt(count, 4)))
				minval = (Integer) table.getValueAt(count, 4);
			if (!isEmpty(table.getValueAt(count, 5)))
				maxval = (Integer) table.getValueAt(count, 5);
			
			virtualDimmer = (boolean) table.getValueAt(count, 6);

			attributes.add(new Attribute(name, channel, homeVal, fineChan, minval, maxval, virtualDimmer));
		}

		if (!attributes.stream().filter(atr -> atr.getName().equals("INTENSITY") || atr.hasVirtualDimmer()).findFirst()
				.isPresent()) {
			// Required according to page 24 of the manual
			throw new IllegalArgumentException(
					"No attribute with the name 'INTENSITY' found. Please create one on channel 0 if there is none (see page 24 of the fixt. manual).");
		}

		return attributes;
	}

	private boolean isEmpty(Object obj) {
		return obj == null || obj.toString().isEmpty();
	}
}
