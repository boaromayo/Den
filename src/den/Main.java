/**====================================
/* THE EPIC OF DEN.
/* ------------------------------------
/* code by Nico Poblete
/* Copyright (c) 2014 OM Independent
/**====================================**/
package den;

import java.awt.*;

import javax.swing.*;

public class Main {
	/**=======================================
	/* MAIN METHOD.
	/*=========================================**/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame();
				} catch (Exception e) {
					System.err.println("ERROR: Unable to run Epic of Den.\nCAUSE: " 
							+ e.getClass() + " " + e.getMessage());
					e.printStackTrace();
					System.exit(1);
				}
			}
		});		
	}
	
	public static void frame() {
		// Draw main frame.
		JFrame frm = new JFrame();
		DenPanel dp = new DenPanel();
							
		frm.setTitle(DenConstants.TITLE);
		frm.add(dp);
							
		// Set frame settings.
		frm.setSize(DenConstants.WIDTH, DenConstants.HEIGHT);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setLocationRelativeTo(null);
		frm.setVisible(true);
		frm.setResizable(false);	
	}

}
