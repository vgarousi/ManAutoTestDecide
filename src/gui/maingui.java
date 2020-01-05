package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import jxl.write.biff.RowsExceededException;
import algorithms.BruteForceMain;
import algorithms.Fitness;
import algorithms.GAMain;
import algorithms.GAProfile;
import algorithms.UseCaseProfile;
import algorithms.Utility;

/*This class is generated for the UI of the app. Eclipse GUI designer add-in is used to generate this class*/
public class maingui {

	private JFrame frmTitle;
	private JTextField usecaseSize;
	private JTextField numberOfExecution;
	private JTextField manualDesignProductivity;
	private JTextField autoDesignProductivity;
	private JTextField designFixedCost;
	private JTextField manualScriptingProductivity;
	private JTextField AutoScriptProductivity_Simple;
	private JTextField AutoScriptProductivity_Medium;
	private JTextField AutoScriptProductivity_Complex;
	private JTextField ScriptingFixedCost;
	private JTextField NumberOfConfiguration;
	private JTextField executionFixedCost;
	private JTextField manualReportProductivity;
	private JTextField autoReportProductivity;
	private JTextField reportingFixedCost;
	private JTextField ComaintenanceRate;
	private JTextField QualityImprovementRate;
	private JTextField FileAddress;
	private JTextField populationSize;
	private JTextField crossoverProbability;
	private JTextField mutationProbability;
	private JTextField maximumIterations;
	private JRadioButton rdbtnGeneticAlgorithm;
	private JPanel genAlg_panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTabbedPane tabbedPane;
	private JButton btnStart;
	private JLabel status_lbl;
	private JLabel status_color;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					maingui window = new maingui();
					window.frmTitle.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public maingui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTitle = new JFrame();
		frmTitle.setTitle("Automation Decision Support System");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		frmTitle.setBounds(((screen.width - 600)/2), ((screen.height - 600)/2), 600, 600);
		frmTitle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTitle.getContentPane().setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 588, 566);
		frmTitle.getContentPane().add(tabbedPane);

		JPanel algSelect_panel = new JPanel();
		tabbedPane.addTab("Select Algorithm", null, algSelect_panel, null);
		algSelect_panel.setLayout(null);

		JButton btnNext = new JButton("Next");
		// btnNext.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// nextFunction();
		// }
		// });
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextFunction();
			}
		});
		btnNext.setBounds(444, 450, 117, 29);
		algSelect_panel.add(btnNext);

		FileAddress = new JTextField();
		//FileAddress.setText("D:/Eclipse-Workspace/geneticTest/InputData.xls");//**
		FileAddress.setBounds(24, 376, 408, 28);
		algSelect_panel.add(FileAddress);
		FileAddress.setColumns(10);

		JButton btn_Browse = new JButton("browse");
		btn_Browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create a file chooser
				final JFileChooser fc = new JFileChooser();

				// In response to a button click:
				int returnVal = fc.showOpenDialog(null);
				File file = fc.getSelectedFile();
				String path = file.getAbsolutePath();
				FileAddress.setText(path);
			}
		});
		btn_Browse.setBounds(444, 377, 117, 29);
		algSelect_panel.add(btn_Browse);

		genAlg_panel = new JPanel();
		genAlg_panel.setLayout(null);
		genAlg_panel.setToolTipText("");
		genAlg_panel.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "GA Parameters",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		genAlg_panel.setBounds(57, 107, 375, 165);
		algSelect_panel.add(genAlg_panel);

		JLabel lblPopulationSize = new JLabel("Population size");
		lblPopulationSize.setBounds(29, 30, 147, 16);
		genAlg_panel.add(lblPopulationSize);

		populationSize = new JTextField();
		//populationSize.setText("60");//**
		populationSize.setColumns(10);
		populationSize.setBounds(188, 24, 110, 28);
		genAlg_panel.add(populationSize);

		JLabel lblCrossoverProbability = new JLabel("Crossover probability");
		lblCrossoverProbability.setBounds(29, 64, 147, 16);
		genAlg_panel.add(lblCrossoverProbability);

		crossoverProbability = new JTextField();
		//crossoverProbability.setText("0.85");//**
		crossoverProbability.setColumns(10);
		crossoverProbability.setBounds(188, 58, 110, 28);
		genAlg_panel.add(crossoverProbability);

		JLabel lblMutationProbability = new JLabel("Mutation probability");
		lblMutationProbability.setBounds(29, 98, 147, 16);
		genAlg_panel.add(lblMutationProbability);

		mutationProbability = new JTextField();
		//mutationProbability.setText("0.2");//**
		mutationProbability.setColumns(10);
		mutationProbability.setBounds(188, 92, 110, 28);
		genAlg_panel.add(mutationProbability);

		JLabel lblFraction = new JLabel("Maximum iterations");
		lblFraction.setBounds(29, 132, 147, 16);
		genAlg_panel.add(lblFraction);

		maximumIterations = new JTextField();
		//maximumIterations.setText("14000");//**
		maximumIterations.setColumns(10);
		maximumIterations.setBounds(188, 126, 110, 28);
		genAlg_panel.add(maximumIterations);

		/* To add a logo to the start page of the app */

		// ImageIcon icon =
		// createImageIcon("D:\\eclipse_WS\\geneticTest\\icon.png", "");
		// JLabel lblIcon = new JLabel("", icon, JLabel.CENTER);
		// lblIcon.setBounds(454, 22, 94, 59);
		// algSelect_panel.add(lblIcon);

		rdbtnGeneticAlgorithm = new JRadioButton("Genetic Algorithm");
		rdbtnGeneticAlgorithm.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (rdbtnGeneticAlgorithm.isSelected()) {
					populationSize.setEnabled(true);
					crossoverProbability.setEnabled(true);
					mutationProbability.setEnabled(true);
					maximumIterations.setEnabled(true);
				} else {
					populationSize.setEnabled(false);
					crossoverProbability.setEnabled(false);
					mutationProbability.setEnabled(false);
					maximumIterations.setEnabled(false);
				}
			}
		});
		buttonGroup.add(rdbtnGeneticAlgorithm);
		rdbtnGeneticAlgorithm.setSelected(true);

		rdbtnGeneticAlgorithm.setBounds(24, 72, 171, 23);
		algSelect_panel.add(rdbtnGeneticAlgorithm);

		JRadioButton rdbtnBruteForceAlgrithm = new JRadioButton(
				"Brute Force Algrithm");
		buttonGroup.add(rdbtnBruteForceAlgrithm);
		rdbtnBruteForceAlgrithm.setBounds(24, 314, 212, 23);
		algSelect_panel.add(rdbtnBruteForceAlgrithm);
		
		JLabel lblInputFile = new JLabel("Input File");
		lblInputFile.setBounds(27, 356, 56, 16);
		algSelect_panel.add(lblInputFile);

		JPanel parameters_panel = new JPanel();
		tabbedPane.addTab("Common Parameters", null, parameters_panel, null);
		parameters_panel.setLayout(null);
		tabbedPane.setEnabledAt(1, false);

		JPanel panel = new JPanel();
		panel.setToolTipText("");
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null), "General", TitledBorder.LEFT, TitledBorder.TOP,
				null, new Color(0, 0, 0)));
		panel.setBounds(16, 16, 262, 101);
		parameters_panel.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Num. of use cases");
		lblNewLabel.setBounds(17, 30, 125, 16);
		panel.add(lblNewLabel);

		usecaseSize = new JTextField();
		//usecaseSize.setText("30");//**
		usecaseSize.setBounds(177, 24, 75, 28);
		panel.add(usecaseSize);
		usecaseSize.setColumns(10);

		JLabel lblNumberOfExecutions = new JLabel("Num. of executions");
		lblNumberOfExecutions.setBounds(17, 64, 125, 16);
		panel.add(lblNumberOfExecutions);

		numberOfExecution = new JTextField();
		//numberOfExecution.setText("3");//**
		numberOfExecution.setBounds(177, 58, 75, 28);
		panel.add(numberOfExecution);
		numberOfExecution.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setToolTipText("");
		panel_1.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Designing",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(305, 16, 243, 138);
		parameters_panel.add(panel_1);

		JLabel lblManualProductivity_1 = new JLabel("Manual productivity");
		lblManualProductivity_1.setBounds(17, 30, 136, 16);
		panel_1.add(lblManualProductivity_1);

		manualDesignProductivity = new JTextField();
		//manualDesignProductivity.setText("0.6756");//**
		manualDesignProductivity.setColumns(10);
		manualDesignProductivity.setBounds(152, 24, 72, 28);
		panel_1.add(manualDesignProductivity);

		JLabel lblAutomatedProductivity = new JLabel("Automated productivity");
		lblAutomatedProductivity.setBounds(17, 64, 136, 16);
		panel_1.add(lblAutomatedProductivity);

		autoDesignProductivity = new JTextField();
		//autoDesignProductivity.setText("6.25");//**
		autoDesignProductivity.setColumns(10);
		autoDesignProductivity.setBounds(152, 58, 72, 28);
		panel_1.add(autoDesignProductivity);

		designFixedCost = new JTextField();
		//designFixedCost.setText("10080"); // 3* 7 * 8 * 60//**
		designFixedCost.setColumns(10);
		designFixedCost.setBounds(152, 92, 72, 28);
		panel_1.add(designFixedCost);

		JLabel lblFixedCost_1 = new JLabel("Fixed cost");
		lblFixedCost_1.setBounds(17, 98, 136, 16);
		panel_1.add(lblFixedCost_1);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setToolTipText("");
		panel_3.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Execution",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(305, 170, 243, 113);
		parameters_panel.add(panel_3);

		JLabel lblNumberOfConfiguration = new JLabel("Num. of configurations");
		lblNumberOfConfiguration.setBounds(17, 32, 144, 16);
		panel_3.add(lblNumberOfConfiguration);

		NumberOfConfiguration = new JTextField();
		//NumberOfConfiguration.setText("3");//**
		NumberOfConfiguration.setColumns(10);
		NumberOfConfiguration.setBounds(161, 26, 63, 28);
		panel_3.add(NumberOfConfiguration);

		executionFixedCost = new JTextField();
		//executionFixedCost.setText("90");//**
		executionFixedCost.setColumns(10);
		executionFixedCost.setBounds(161, 65, 63, 28);
		panel_3.add(executionFixedCost);

		JLabel lblAutomationSetupCost = new JLabel("Automation setup cost");
		lblAutomationSetupCost.setBounds(17, 71, 144, 16);
		panel_3.add(lblAutomationSetupCost);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setToolTipText("");
		panel_4.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Reporting",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(305, 303, 243, 138);
		parameters_panel.add(panel_4);

		JLabel lblManualProductivity_2 = new JLabel("Manual productivity");
		lblManualProductivity_2.setBounds(17, 30, 134, 16);
		panel_4.add(lblManualProductivity_2);

		manualReportProductivity = new JTextField();
		//manualReportProductivity.setText("0.017");//**
		manualReportProductivity.setColumns(10);
		manualReportProductivity.setBounds(161, 24, 63, 28);
		panel_4.add(manualReportProductivity);

		JLabel lblAutomatedProductivity_1 = new JLabel("Automated productivity");
		lblAutomatedProductivity_1.setBounds(17, 64, 144, 16);
		panel_4.add(lblAutomatedProductivity_1);

		autoReportProductivity = new JTextField();
		//autoReportProductivity.setText("0.0335");//**
		autoReportProductivity.setColumns(10);
		autoReportProductivity.setBounds(161, 58, 63, 28);
		panel_4.add(autoReportProductivity);

		reportingFixedCost = new JTextField();
		//reportingFixedCost.setText("7200");//**
		reportingFixedCost.setColumns(10);
		reportingFixedCost.setBounds(161, 92, 63, 28);
		panel_4.add(reportingFixedCost);

		JLabel lblFixedCost_2 = new JLabel("Fixed cost");
		lblFixedCost_2.setBounds(17, 98, 134, 16);
		panel_4.add(lblFixedCost_2);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setToolTipText("");
		panel_5.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Maintenance",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(16, 341, 262, 101);
		parameters_panel.add(panel_5);

		JLabel lblComaintenanceRate = new JLabel("Co-maintenance rate");
		lblComaintenanceRate.setBounds(17, 30, 129, 16);
		panel_5.add(lblComaintenanceRate);

		ComaintenanceRate = new JTextField();
		//ComaintenanceRate.setText("0");//**
		ComaintenanceRate.setColumns(10);
		ComaintenanceRate.setBounds(177, 24, 75, 28);
		panel_5.add(ComaintenanceRate);

		JLabel lblQualityImprovementRate = new JLabel(
				"Quality improvement rate");
		lblQualityImprovementRate.setBounds(17, 64, 150, 16);
		panel_5.add(lblQualityImprovementRate);

		QualityImprovementRate = new JTextField();
		//QualityImprovementRate.setText("0.09");//**
		QualityImprovementRate.setColumns(10);
		QualityImprovementRate.setBounds(177, 58, 75, 28);
		panel_5.add(QualityImprovementRate);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startFunction();
			}
		});
		// btnStart.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// //String numberOfExecution_UI =
		// numberOfExecution.getText().toString();
		// startFunction();
		// }
		// });
		btnStart.setBounds(431, 470, 117, 29);
		parameters_panel.add(btnStart);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(16, 128, 262, 202);
		parameters_panel.add(panel_2);
		panel_2.setLayout(null);
		panel_2.setToolTipText("");
		panel_2.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Scripting",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JLabel lblManualProductivity = new JLabel("Manual productivity");
		lblManualProductivity.setBounds(17, 30, 110, 16);
		panel_2.add(lblManualProductivity);

		manualScriptingProductivity = new JTextField();
		//manualScriptingProductivity.setText("0.0665");//**
		manualScriptingProductivity.setColumns(10);
		manualScriptingProductivity.setBounds(177, 24, 75, 28);
		panel_2.add(manualScriptingProductivity);

		JLabel lblAutomatedProductivitysimple = new JLabel(
				"Simple script productivity");
		lblAutomatedProductivitysimple.setBounds(17, 64, 160, 16);
		panel_2.add(lblAutomatedProductivitysimple);

		AutoScriptProductivity_Simple = new JTextField();
		//AutoScriptProductivity_Simple.setText("0.0167");//**
		AutoScriptProductivity_Simple.setColumns(10);
		AutoScriptProductivity_Simple.setBounds(177, 58, 75, 28);
		panel_2.add(AutoScriptProductivity_Simple);

		AutoScriptProductivity_Medium = new JTextField();
		//AutoScriptProductivity_Medium.setText("0.0073");//**
		AutoScriptProductivity_Medium.setColumns(10);
		AutoScriptProductivity_Medium.setBounds(177, 92, 75, 28);
		panel_2.add(AutoScriptProductivity_Medium);

		JLabel lblMediumScriptProductivity = new JLabel(
				"Medium script productivity");
		lblMediumScriptProductivity.setBounds(17, 98, 160, 16);
		panel_2.add(lblMediumScriptProductivity);

		JLabel lblDifficultScriptProductivity = new JLabel(
				"Difficult script productivity");
		lblDifficultScriptProductivity.setBounds(17, 131, 160, 16);
		panel_2.add(lblDifficultScriptProductivity);

		AutoScriptProductivity_Complex = new JTextField();
		//AutoScriptProductivity_Complex.setText("0.0031");//**
		AutoScriptProductivity_Complex.setBounds(177, 125, 75, 28);
		panel_2.add(AutoScriptProductivity_Complex);
		AutoScriptProductivity_Complex.setColumns(10);

		JLabel lblFixedCost = new JLabel("Fixed cost");
		lblFixedCost.setBounds(17, 166, 110, 16);
		panel_2.add(lblFixedCost);

		ScriptingFixedCost = new JTextField();
		//ScriptingFixedCost.setText("9600");//**
		ScriptingFixedCost.setColumns(10);
		ScriptingFixedCost.setBounds(177, 160, 75, 28);
		panel_2.add(ScriptingFixedCost);

		status_lbl = new JLabel("Waiting for start");
		status_lbl.setBounds(53, 476, 215, 16);
		parameters_panel.add(status_lbl);

		status_color = new JLabel("");
		status_color.setOpaque(true);
		status_color.setBackground(Color.BLACK);
		status_color.setBounds(96, 455, 19, 16);
		parameters_panel.add(status_color);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(43, 455, 56, 16);
		parameters_panel.add(lblStatus);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path, String description) {
		// java.net.URL imgURL = getClass().getResource(path);
		// if (imgURL != null) {
		return new ImageIcon(path, description);
		// } else {
		// System.err.println("Couldn't find file: " + path);
		// return null;
		// }
	}

	public void nextFunction() {
		
		String addrStr = FileAddress.getText();
		if (rdbtnGeneticAlgorithm.isSelected()) {
			UseCaseProfile.setSelectedAlg(UseCaseProfile.GENETIC);
			String popSizeStr = populationSize.getText();
			String crossProbStr = crossoverProbability
					.getText();
			String mutateProbStr = mutationProbability
					.getText();
			String loopStr = maximumIterations.getText();

			if(addrStr.length()==0 || popSizeStr.length()==0 || crossProbStr.length() == 0 || mutateProbStr.length() == 0 || loopStr.length() == 0){
				JOptionPane.showMessageDialog(null, "One or more filed is empty!");
				return;
			}
			tabbedPane.setEnabledAt(1, true);
			UseCaseProfile.setExcelFilePath(addrStr);
			double crossProb = Double.parseDouble(crossProbStr);
			double mutateProb = Double.parseDouble(mutateProbStr);
			int loop = Integer.parseInt(loopStr);
			int popSize = Integer.parseInt(popSizeStr);
			GAProfile.setPOPULATION_SIZE(popSize);
			GAProfile.setCrossoverProbability(crossProb);
			GAProfile.setMutationProbability(mutateProb);
			GAProfile.setLOOP(loop);
		} else {
			UseCaseProfile.setSelectedAlg(UseCaseProfile.BRUTE_FORCE);
			if(addrStr.length()==0 ){
				JOptionPane.showMessageDialog(null, "One or more filed is empty!");
				return;
			}
			tabbedPane.setEnabledAt(1, true);
			UseCaseProfile.setExcelFilePath(addrStr);
		}
		tabbedPane.setSelectedIndex(1);
	}

	public void startFunction() {

		UseCaseProfile.setUseCaseNumber((int) toDouble(usecaseSize));
		UseCaseProfile.setNumberOfExecution((int) toDouble(numberOfExecution));
		// Design--------------------------------------------
		UseCaseProfile
				.setManual_Design_Per_Step(toDouble(manualDesignProductivity));
		UseCaseProfile
				.setAutomated_DESIGN_Per_Step(toDouble(autoDesignProductivity));
		UseCaseProfile.setAutomatedDesignFixedCost(toDouble(designFixedCost));
		// Scripting-----------------------------------------
		UseCaseProfile
				.setManualScripting_Per_Step(toDouble(manualScriptingProductivity));
		UseCaseProfile
				.setSimpleAutomatedScripting_Per_Step(toDouble(AutoScriptProductivity_Simple));
		UseCaseProfile
				.setAverageAutomatedScripting_Per_Step(toDouble(AutoScriptProductivity_Medium));
		UseCaseProfile
				.setDifficultAutomatedScripting_Per_Step(toDouble(AutoScriptProductivity_Complex));
		UseCaseProfile
				.setautomatedScriptingFixedCost(toDouble(ScriptingFixedCost));
		// Execution------------------------------------------
		// UseCaseProfile.setExecutionFactor(toDouble());
		UseCaseProfile
				.setNumberOfConfigurations(toDouble(NumberOfConfiguration));
		UseCaseProfile
				.setautomatedExecutionFixedCost(toDouble(executionFixedCost));
		// Reporting------------------------------------------
		UseCaseProfile
				.setManualReporting_Per_Defect(toDouble(manualReportProductivity));
		UseCaseProfile
				.setAutomatedReporting_Per_Defect(toDouble(autoReportProductivity));
		UseCaseProfile
				.setautomatedReportingFixedCost(toDouble(reportingFixedCost));
		// Maintenance------------------------------------------
		UseCaseProfile.setComaintenanceRate(toDouble(ComaintenanceRate));
		UseCaseProfile
				.setqualityImprovementRate(toDouble(QualityImprovementRate));
		if(correct == false){
			JOptionPane.showMessageDialog(null, "One or more filed is empty!");
			correct = true;
		}else{
			usecaseSize.setEditable(false);
			numberOfExecution.setEditable(false);
			manualScriptingProductivity.setEditable(false);
			AutoScriptProductivity_Simple.setEditable(false);
			AutoScriptProductivity_Medium.setEditable(false);
			AutoScriptProductivity_Complex.setEditable(false);
			ScriptingFixedCost.setEditable(false);
			ComaintenanceRate.setEditable(false);
			QualityImprovementRate.setEditable(false);
			manualDesignProductivity.setEditable(false);
			autoDesignProductivity.setEditable(false);
			designFixedCost.setEditable(false);
			NumberOfConfiguration.setEditable(false);
			executionFixedCost.setEditable(false);
			manualReportProductivity.setEditable(false);
			autoReportProductivity.setEditable(false);
			reportingFixedCost.setEditable(false);
			btnStart.setEnabled(false);
			status_lbl.setText("Algorithm is started!");
			status_color.setBackground(Color.GREEN);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
	
					if (UseCaseProfile.getSelectedAlg() == UseCaseProfile.BRUTE_FORCE) {
						System.err.println("BF Stared");
						BruteForceMain b = new BruteForceMain();
						int[][] result = new int[UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
						result = b.generateAllBinaryValues();
						Fitness fitness = new Fitness();
						fitness.fitnessCalculator(result,true);
						try {
							Utility.writeResultsToExecl(result, Fitness.getTotalROI());
						} catch (RowsExceededException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} else if (UseCaseProfile.getSelectedAlg() == UseCaseProfile.GENETIC) {		
						System.err.println("GA Stared");
						GAMain g = new GAMain();
						int[][] result = new int[UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
						result = g.GAAlgorithmBody();
						Utility.reusablityGraphEdgeCounter(result);
						Fitness fitness = new Fitness();
						fitness.fitnessCalculator(result,false);
						try {
							Utility.writeResultsToExecl(result, Fitness.getTotalROI());
						} catch (RowsExceededException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
					} else {
						// TODO: generate error for not selecting an algorithm!
					}
					usecaseSize.setEditable(true);
					numberOfExecution.setEditable(true);
					manualScriptingProductivity.setEditable(true);
					AutoScriptProductivity_Simple.setEditable(true);
					AutoScriptProductivity_Medium.setEditable(true);
					AutoScriptProductivity_Complex.setEditable(true);
					ScriptingFixedCost.setEditable(true);
					ComaintenanceRate.setEditable(true);
					QualityImprovementRate.setEditable(true);
					manualDesignProductivity.setEditable(true);
					autoDesignProductivity.setEditable(true);
					designFixedCost.setEditable(true);
					NumberOfConfiguration.setEditable(true);
					executionFixedCost.setEditable(true);
					manualReportProductivity.setEditable(true);
					autoReportProductivity.setEditable(true);
					reportingFixedCost.setEditable(true);
					btnStart.setEnabled(true);
					status_color.setBackground(Color.BLACK);
					status_lbl.setText("Algorithm is finished!");
				}
			});
		}
	}

	boolean correct = true;
	
	public double toDouble(JTextField field) {
		String text = field.getText();
		if(text.length()==0){
			correct = false;
			return 0;
		}
		double jtfv = Double.parseDouble(text);
		return jtfv;
	}
}
