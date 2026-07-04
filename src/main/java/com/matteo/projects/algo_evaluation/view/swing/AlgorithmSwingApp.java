package com.matteo.projects.algo_evaluation.view.swing;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgorithmSwingApp {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				AlgorithmSwingView view = new AlgorithmSwingView();
				view.setVisible(true);
			} catch (Exception e) {
				Logger.getLogger(AlgorithmSwingApp.class.getName()).log(Level.SEVERE, "Exception", e);
			}
		});
	}
}
