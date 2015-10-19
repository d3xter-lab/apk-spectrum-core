package com.apkscanner.gui.dialog.install;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.apkscanner.core.AdbWrapper;
import com.apkscanner.gui.ApkInstaller;
import com.apkscanner.gui.ApkInstaller.InstallDlgFuncListener;
import com.apkscanner.gui.dialog.install.*;
import com.apkscanner.resource.Resource;
import com.apkscanner.test.ProgressBarTest;
import com.apkscanner.util.Log;

public class InstallDlg extends JDialog implements ActionListener{
	
	InstallCheckTable TestTable;
	DeviceListPanel deviceListDig;
	JScrollPane scrollPane;
	JPanel framelayout;
	JFrame f;
	JTextArea taskOutput;
	InstallDlgFuncListener CoreInstallLitener; 
	
	
	public InstallDlg() {
		createAndShowGUI();
		
		CoreInstallLitener = new InstallDlgFuncListener() {
			@Override
			public void Complete() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void AddCheckList() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int  ShowQuestion() {
				
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void AddLog(String str) {
				// TODO Auto-generated method stub
				
			}
		};		
		ApkInstaller.setDlgListener(CoreInstallLitener);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("showLogBox".equals(e.getActionCommand())) {
			if(scrollPane.isVisible()) {
				scrollPane.setVisible(false);
				this.pack();
			} else {
				//scrollPane.setVisible(true);
				//this.pack();
				//for test
				
				try {
					addCheckListForInstallDlg(AdbWrapper.class.getMethod("scanDevices", null));
				} catch (NoSuchMethodException | SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		} else if("Refresh".equals(e.getActionCommand())) {
			
		}
	}

	private void addCheckListForInstallDlg(Method methodObject) {
		 //methodObject.invoke(obj, args)
	}
	
	
    private void createAndShowGUI() {
        //Create and set up the window.
        
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
    	
        f = new JFrame();
        
        TestTable = new InstallCheckTable();
        deviceListDig = new DeviceListPanel();
        
        //TestTable.createAndShowGUI();
        
        this.setTitle(Resource.STR_APP_NAME.getString());
        this.setIconImage(Resource.IMG_TOOLBAR_INSTALL.getImageIcon().getImage());
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setBounds(0, 0, 700, 400);
        this.setPreferredSize(new Dimension(700,400));
        
         this.setMinimumSize(new Dimension(700, 400));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        //f.getContentPane().setLayout(new BorderLayout());
        //f.setLayout(new BorderLayout());
        JButton btnExit = new JButton("btnExit");
        JButton btnshowLogBox = new JButton("showLogBox");
        
        btnshowLogBox.addActionListener(this);
        
        
        JPanel framelayout = new JPanel(new BorderLayout());
        JPanel parent = new JPanel(new GridLayout(1,2));
        JPanel CheckListBox = new JPanel(new BorderLayout());
        JPanel MessageBox = new JPanel(new BorderLayout());
        JPanel ButtonBox = new JPanel(new BorderLayout());
        JPanel LogBox= new JPanel(new BorderLayout());
        
		taskOutput = new JTextArea();
		taskOutput.setText(Log.getLog());
		taskOutput.setEditable(false);
		taskOutput.setCaretPosition(0);
		scrollPane = new JScrollPane(taskOutput);
		//scrollPane.setPreferredSize(new Dimension(600, 400));
        
        ButtonBox.setBackground(Color.PINK);
        
        CheckListBox.add(TestTable);
        MessageBox.add(deviceListDig);
        
        parent.add(CheckListBox, BorderLayout.WEST);
        parent.add(MessageBox, BorderLayout.EAST);
        
        LogBox.add(scrollPane);
        
        ButtonBox.add(btnExit,BorderLayout.EAST );
        ButtonBox.add(LogBox, BorderLayout.SOUTH);
        ButtonBox.add(btnshowLogBox, BorderLayout.WEST);
                
        framelayout.add(parent,BorderLayout.CENTER);
        framelayout.add(ButtonBox,BorderLayout.SOUTH);
                
        scrollPane.setVisible(false);
        
        this.add(framelayout);        
    }
	
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	InstallDlg dlg = new InstallDlg();                
            }
        });
    }
}
