package com.matteo.projects.algo_evaluation.view.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.matteo.projects.algo_evaluation.controller.RunController;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;

import java.awt.GridBagLayout;
import javax.swing.JList;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

public class AlgorithmSwingView extends JFrame {

	private static final String DIALOG = "Dialog";
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel errorLabel;

	private DefaultListModel<Algorithm> listAlgorithmsModel;
	private DefaultListModel<Dataset> listDatasetsModel;
	private DefaultListModel<Run> listRunsModel;

	private RunController runController;

	/**
	 * Create the frame.
	 */

	public AlgorithmSwingView() {
		setTitle("Sorting Algorithm evluation");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 100, 100, 0 };
		gbl_contentPane.rowHeights = new int[] { 110, 110, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JScrollPane algorithmScrollPane = new JScrollPane();
		GridBagConstraints gbc_algorithmScrollPane = new GridBagConstraints();
		gbc_algorithmScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_algorithmScrollPane.fill = GridBagConstraints.BOTH;
		gbc_algorithmScrollPane.gridx = 0;
		gbc_algorithmScrollPane.gridy = 0;
		contentPane.add(algorithmScrollPane, gbc_algorithmScrollPane);

		listAlgorithmsModel = new DefaultListModel<>();
		JList<Algorithm> algorithmList = new JList<>(listAlgorithmsModel);
		algorithmList.setFont(new Font(DIALOG, Font.BOLD, 20));
		algorithmList.setName("algorithmList");
		algorithmScrollPane.setViewportView(algorithmList);

		JLabel lblAlgorithmList = new JLabel("Algorithm list");
		lblAlgorithmList.setFont(new Font(DIALOG, Font.BOLD, 20));
		algorithmScrollPane.setColumnHeaderView(lblAlgorithmList);

		JScrollPane datasetScrollPane = new JScrollPane();
		GridBagConstraints gbc_datasetScrollPane = new GridBagConstraints();
		gbc_datasetScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_datasetScrollPane.fill = GridBagConstraints.BOTH;
		gbc_datasetScrollPane.gridx = 1;
		gbc_datasetScrollPane.gridy = 0;
		contentPane.add(datasetScrollPane, gbc_datasetScrollPane);

		listDatasetsModel = new DefaultListModel<>();
		JList<Dataset> datasetList = new JList<>(listDatasetsModel);
		datasetList.setFont(new Font(DIALOG, Font.BOLD, 20));
		datasetList.setName("datasetList");
		datasetScrollPane.setViewportView(datasetList);

		JLabel lblDatasetList = new JLabel("Dataset list");
		lblDatasetList.setFont(new Font(DIALOG, Font.BOLD, 20));
		datasetScrollPane.setColumnHeaderView(lblDatasetList);

		JScrollPane runScrollPane = new JScrollPane();
		GridBagConstraints gbc_runScrollPane = new GridBagConstraints();
		gbc_runScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_runScrollPane.gridwidth = 2;
		gbc_runScrollPane.fill = GridBagConstraints.BOTH;
		gbc_runScrollPane.gridx = 0;
		gbc_runScrollPane.gridy = 1;
		contentPane.add(runScrollPane, gbc_runScrollPane);

		listRunsModel = new DefaultListModel<>();
		JList<Run> runList = new JList<>(listRunsModel);
		runList.setFont(new Font(DIALOG, Font.BOLD, 20));
		runList.setName("runList");
		runScrollPane.setViewportView(runList);

		JLabel lblRunList = new JLabel("Run list");
		lblRunList.setFont(new Font(DIALOG, Font.BOLD, 20));
		runScrollPane.setColumnHeaderView(lblRunList);

		JButton runButton = new JButton("Run");
		runButton.setFont(new Font(DIALOG, Font.BOLD, 24));
		runButton.setEnabled(false);
		GridBagConstraints gbc_runButton = new GridBagConstraints();
		gbc_runButton.insets = new Insets(0, 0, 0, 5);
		gbc_runButton.gridx = 0;
		gbc_runButton.gridy = 2;
		contentPane.add(runButton, gbc_runButton);
		
		runButton.addActionListener(
				e -> runController.newRun(algorithmList.getSelectedValue(), datasetList.getSelectedValue()));

		errorLabel = new JLabel(" ");
		errorLabel.setFont(new Font(DIALOG, Font.BOLD, 14));
		errorLabel.setName("errorLabel");
		GridBagConstraints gbc_errorLabel = new GridBagConstraints();
		gbc_errorLabel.gridx = 1;
		gbc_errorLabel.gridy = 2;
		contentPane.add(errorLabel, gbc_errorLabel);

		datasetList.addListSelectionListener(e -> runButton
				.setEnabled(algorithmList.getSelectedIndex() != -1 && datasetList.getSelectedIndex() != -1));
		algorithmList.addListSelectionListener(e -> runButton
				.setEnabled(algorithmList.getSelectedIndex() != -1 && datasetList.getSelectedIndex() != -1));
	}

	public void showAllAlgorithms(List<Algorithm> algorithms) {
		algorithms.stream().forEach(listAlgorithmsModel::addElement);
	}

	public void showAllDatasets(List<Dataset> datasets) {
		datasets.stream().forEach(listDatasetsModel::addElement);
	}

	public void showAllRuns(List<Run> runs) {
		runs.stream().forEach(listRunsModel::addElement);
	}

	public DefaultListModel<Algorithm> getListAlgorithmsModel() {
		return listAlgorithmsModel;
	}

	public DefaultListModel<Dataset> getListDatasetsModel() {
		return listDatasetsModel;
	}
	
	public void setRunController(RunController runController) {
		this.runController = runController;
	}

	public void runAdded(Run run) {
		listRunsModel.addElement(run);
		errorLabel.setText(" ");
	}

	public void showAlgorithmError(String string, Algorithm algorithm) {
		errorLabel.setText(string + " for algorithm: " + algorithm.getName());
	}

	public void showDatasetError(String string, Dataset dataset) {
		errorLabel.setText(string + " for dataset: " + dataset.getName());
	}

}
