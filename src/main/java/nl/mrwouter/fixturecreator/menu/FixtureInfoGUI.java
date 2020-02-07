package nl.mrwouter.fixturecreator.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;

import nl.mrwouter.fixturecreator.files.FixtureParser;
import nl.mrwouter.fixturecreator.menu.utils.LimitDocumentFilter;
import nl.mrwouter.fixturecreator.menu.utils.ModifyListener;
import nl.mrwouter.fixturecreator.objects.Fixture;
import nl.mrwouter.fixturecreator.objects.NameUtils;
import nl.mrwouter.fixturecreator.objects.parameter.Parameter;

public class FixtureInfoGUI extends JPanel {

	private static final long serialVersionUID = 5180782518088711551L;
	// private FixtureGUI fixtureGui;
	private JPanel panel;
	private JTextField fullFixtureManufacturer;
	private JTextField fixtureManufacturer;
	private JTextField fullFixtureName;
	private JTextField fixtureName;
	private JTextField fixtureVersion;

	public FixtureInfoGUI(MainFixtureFrame fixtureGui) {
		// this.fixtureGui = fixtureGui;
		panel = new JPanel();
		panel.setBounds(100, 100, 700, 450);
		panel.setBackground(new Color(230, 230, 250));

		fullFixtureManufacturer = new JTextField();
		fullFixtureManufacturer.setColumns(12);
		((AbstractDocument) fullFixtureManufacturer.getDocument()).setDocumentFilter(new LimitDocumentFilter(15));
		fullFixtureManufacturer.getDocument().addDocumentListener(new ModifyListener() {
			@Override
			public void onModify() {
				fixtureGui.getFixture().setFullManufacturer(fullFixtureManufacturer.getText());
			}
		});

		fixtureManufacturer = new JTextField();
		fixtureManufacturer.setColumns(12);
		((AbstractDocument) fixtureManufacturer.getDocument()).setDocumentFilter(new LimitDocumentFilter(9));
		fixtureManufacturer.getDocument().addDocumentListener(new ModifyListener() {
			@Override
			public void onModify() {
				fixtureGui.getFixture().setManufacturer(fixtureManufacturer.getText());
			}
		});

		fullFixtureName = new JTextField();
		fullFixtureName.setColumns(12);
		((AbstractDocument) fullFixtureName.getDocument()).setDocumentFilter(new LimitDocumentFilter(15));
		fullFixtureName.getDocument().addDocumentListener(new ModifyListener() {
			@Override
			public void onModify() {
				fixtureGui.getFixture().setFullName(fullFixtureName.getText());
			}
		});

		fixtureName = new JTextField();
		fixtureName.setColumns(12);
		((AbstractDocument) fixtureName.getDocument()).setDocumentFilter(new LimitDocumentFilter(9));
		fixtureName.getDocument().addDocumentListener(new ModifyListener() {
			@Override
			public void onModify() {
				fixtureGui.getFixture().setName(fixtureName.getText());
			}
		});

		fixtureVersion = new JTextField();
		fixtureVersion.setColumns(12);
		((AbstractDocument) fixtureVersion.getDocument()).setDocumentFilter(new LimitDocumentFilter(9));
		fixtureVersion.getDocument().addDocumentListener(new ModifyListener() {
			@Override
			public void onModify() {
				fixtureGui.getFixture().setVersion(fixtureVersion.getText());
			}
		});

		JLabel statusmessage = new JLabel();
		statusmessage.setFont(new Font("DialogInput", Font.BOLD, 12));
		statusmessage.setText(" ");

		JButton openFileButton = new JButton("Open File");

		openFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("maXim Fixture Files (.fxt)", "fxt");
				chooser.setFileFilter(filter);

				int returnVal = chooser.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					Fixture fixture = FixtureParser.getInstance().parse(file);
					if (fixture == null) {
						statusmessage.setText("Couldn't read fixture file!");
						statusmessage.setForeground(Color.RED);
					} else {
						fullFixtureManufacturer.setText(fixture.getFullManufacturer());
						fixtureManufacturer.setText(fixture.getManufacturer());
						fullFixtureName.setText(fixture.getFullName());
						fixtureName.setText(fixture.getName());
						fixtureVersion.setText(fixture.getVersion());

						fixtureGui.setFixture(fixture);
						fixtureGui.fixtureAttributes.redrawTable();

						fixtureGui.clearParameters();
						for (Parameter param : fixture.getParameters()) {
							fixtureGui.addFixtureParameter(param);
						}

						statusmessage.setText("Succesfully loaded the fixture " + file.getName());
						statusmessage.setForeground(Color.BLACK);
					}
				}
			}
		});

		JButton createFixtureButton = new JButton("Save Fixture");

		createFixtureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("maXim Fixture Files (.fxt)", "fxt");
				chooser.setFileFilter(filter);

				int retrival = chooser.showSaveDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					if (!filePath.endsWith(".fxt"))
						filePath = filePath + ".fxt";
					File targetFile = new File(filePath);

					if (fixtureGui.getFixture().getManufacturer() == null) {
						statusmessage.setText("You need to enter a short manufacturer name.");
						statusmessage.setForeground(Color.RED);
						return;
					}
					if (fixtureGui.getFixture().getName() == null) {
						statusmessage.setText("You need to enter a short fixture name.");
						statusmessage.setForeground(Color.RED);
						return;
					}
					if (fixtureGui.getFixture().getVersion() == null) {
						statusmessage.setText("You need to enter a fixture version.");
						statusmessage.setForeground(Color.RED);
						return;
					}
					if (NameUtils.containsIllegalCharacters(fixtureGui.getFixture().getName())) {
						statusmessage.setText("Fixture name contains illegal characters.");
						statusmessage.setForeground(Color.RED);
						return;
					}
					if (NameUtils.containsIllegalCharacters(fixtureGui.getFixture().getManufacturer())) {
						statusmessage.setText("Fixture manufacturer contains illegal characters.");
						statusmessage.setForeground(Color.RED);
						return;
					}

					try {
						fixtureGui.getFixture().setAttributes(fixtureGui.fixtureAttributes.getAttributes());
					} catch (IllegalArgumentException ex) {
						ex.printStackTrace();
						statusmessage.setText("<html>" + ex.getMessage() + "</html>");
						statusmessage.setForeground(Color.RED);
						return;
					}
					int paramsChecked = 1;

					List<Parameter> parameters = new ArrayList<>();
					for (FixtureParameterGUI fixParam : fixtureGui.parameterTabs) {
						Parameter param = null;
						try {
							param = fixParam.getParameter();
						} catch (Exception ex) {
							ex.printStackTrace();
							statusmessage.setText("<html>" + ex.getMessage() + "</html>");
							statusmessage.setForeground(Color.RED);
							return;
						}

						if (param.getName().replaceAll("\\s", "").isEmpty()) {
							statusmessage.setText("Parameter " + paramsChecked + " doesn't have a name!");
							statusmessage.setForeground(Color.RED);
							return;
						}
						if (!param.getName().startsWith("-") || !param.getName().endsWith("-")) {
							statusmessage.setText(
									"Parameter " + paramsChecked + "'s name needs to start and end with the - char.");
							statusmessage.setForeground(Color.RED);
							return;
						}
						parameters.add(fixParam.getParameter());
						paramsChecked++;
					}
					fixtureGui.getFixture().setParameters(parameters);

					FixtureParser.getInstance().write(fixtureGui.getFixture(), targetFile);
					String prettyName = nullOrEmpty(fixtureGui.getFixture().getFullName())
							? fixtureGui.getFixture().getName()
							: fixtureGui.getFixture().getFullName();

					String prettyManufacturer = nullOrEmpty(fixtureGui.getFixture().getFullManufacturer())
							? fixtureGui.getFixture().getManufacturer()
							: fixtureGui.getFixture().getFullManufacturer();

					statusmessage.setText("Succesfully written " + prettyManufacturer + " " + prettyName + " to "
							+ targetFile.getName() + "!");
					statusmessage.setForeground(Color.BLACK);
				}
			}
		});

		JButton createParameterButton = new JButton("Create Parameter");
		createParameterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fixtureGui.getFixture().getAttributes().isEmpty()) {
					statusmessage.setText("You need atleast one attribute to setup a parameter!");
					statusmessage.setForeground(Color.RED);
				} else {
					fixtureGui.addFixtureParameter();
				}
			}
		});

		JLabel fullFixtureManufacturerHint = new JLabel();
		fullFixtureManufacturerHint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		fullFixtureManufacturerHint.setText("Full Fixture Manufacturer");

		JLabel fixtureManufacturerHint = new JLabel();
		fixtureManufacturerHint.setFont(new Font("Tahoma", Font.BOLD, 11));
		fixtureManufacturerHint.setText("Short Fixture Manufacturer*");

		JLabel fullFixtureNameHint = new JLabel();
		fullFixtureNameHint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		fullFixtureNameHint.setText("Full Fixture Name");

		JLabel fixtureNameHint = new JLabel();
		fixtureNameHint.setText("Short Fixture Name*");
		fixtureNameHint.setFont(new Font("Dialog", Font.BOLD, 11));

		JLabel fixtureVersionHint = new JLabel();
		fixtureVersionHint.setText("Fixture Version*");
		fixtureVersionHint.setFont(new Font("Dialog", Font.BOLD, 11));

		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(48)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(openFileButton, GroupLayout.PREFERRED_SIZE, 119,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(fixtureManufacturerHint, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(fullFixtureNameHint, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(fixtureVersionHint, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(fullFixtureManufacturerHint, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addComponent(fixtureNameHint, GroupLayout.PREFERRED_SIZE, 127,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(fixtureManufacturer, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(fullFixtureManufacturer, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(fixtureVersion, Alignment.LEADING)
												.addComponent(fullFixtureName, Alignment.LEADING)
												.addComponent(fixtureName))
										.addGroup(groupLayout.createSequentialGroup().addComponent(createFixtureButton)
												.addPreferredGap(ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
												.addComponent(createParameterButton))
										.addComponent(statusmessage, GroupLayout.PREFERRED_SIZE, 423,
												GroupLayout.PREFERRED_SIZE))
								.addGap(27)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap(85, Short.MAX_VALUE).addComponent(openFileButton).addGap(29)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(fullFixtureManufacturerHint)
						.addComponent(fullFixtureManufacturer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(fixtureManufacturer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(fixtureManufacturerHint))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(fullFixtureName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(fullFixtureNameHint))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(fixtureName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(fixtureNameHint, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
				.addGap(13)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(fixtureVersion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(fixtureVersionHint, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(statusmessage, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(createFixtureButton).addComponent(createParameterButton))
				.addGap(70)));
		panel.setLayout(groupLayout);
	}

	public JPanel getPanel() {
		return panel;
	}

	private boolean nullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}
}
