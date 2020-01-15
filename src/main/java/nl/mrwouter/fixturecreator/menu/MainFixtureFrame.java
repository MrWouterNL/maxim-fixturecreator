package nl.mrwouter.fixturecreator.menu;

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import nl.mrwouter.fixturecreator.objects.Fixture;
import nl.mrwouter.fixturecreator.objects.parameter.Parameter;

public class MainFixtureFrame {

	private JTabbedPane tabs;
	private Fixture fixture = new Fixture("", "", "");
	public List<FixtureParameterGUI> parameterTabs = new ArrayList<>();
	public FixtureInfoGUI fixtureInfo;
	public FixtureAttributeGUI fixtureAttributes;

	public static void setup() {
		MainFixtureFrame window = new MainFixtureFrame();
		JFrame jframe = new JFrame();
		jframe.getContentPane().setBackground(new Color(230, 230, 250));
		jframe.setTitle("maXim LSC Fixture Creator");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setBounds(100, 100, 700, 450);

		window.tabs = new JTabbedPane();
		window.tabs.setVisible(true);
		window.tabs.add("Fixture Info", (window.fixtureInfo = new FixtureInfoGUI(window)).getPanel());
		window.tabs.add("Attributes", (window.fixtureAttributes = new FixtureAttributeGUI(window)).getPanel());

		window.setupTabTraversalKeys(window.tabs);
		jframe.add(window.tabs);
		jframe.setVisible(true);
	}

	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public void addFixtureParameter() {
		FixtureParameterGUI fixtureParam = new FixtureParameterGUI(this, null);
		tabs.add("Parameter", fixtureParam.getPanel());
		parameterTabs.add(fixtureParam);
	}

	public void clearParameters() {
		parameterTabs.forEach(param -> tabs.remove(param.getPanel()));
		parameterTabs.clear();
	}

	public void addFixtureParameter(Parameter param) {
		FixtureParameterGUI fixtureParam = new FixtureParameterGUI(this, param);
		tabs.add("Parameter", fixtureParam.getPanel());
		parameterTabs.add(fixtureParam);
	}

	public void removeFixtureParameter(FixtureParameterGUI param) {
		tabs.remove(param.getPanel());
		parameterTabs.remove(param);
	}

	// http://www.davidc.net/programming/java/how-make-ctrl-tab-switch-tabs-jtabbedpane
	private void setupTabTraversalKeys(JTabbedPane tabbedPane) {
		KeyStroke ctrlTab = KeyStroke.getKeyStroke("ctrl TAB");
		KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke("ctrl shift TAB");

		// Remove ctrl-tab from normal focus traversal
		Set<AWTKeyStroke> forwardKeys = new HashSet<AWTKeyStroke>(
				tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
		forwardKeys.remove(ctrlTab);
		tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);

		// Remove ctrl-shift-tab from normal focus traversal
		Set<AWTKeyStroke> backwardKeys = new HashSet<AWTKeyStroke>(
				tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
		backwardKeys.remove(ctrlShiftTab);
		tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);

		// Add keys to the tab's input map
		InputMap inputMap = tabbedPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(ctrlTab, "navigateNext");
		inputMap.put(ctrlShiftTab, "navigatePrevious");
	}
}