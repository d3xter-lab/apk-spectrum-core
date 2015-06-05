package com.ApkInfo.UI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import com.ApkInfo.Core.MyDeviceInfo;
import com.ApkInfo.UIUtil.ButtonType;
import com.ApkInfo.UIUtil.StandardButton;
import com.ApkInfo.UIUtil.Theme;


class ADBDialog extends Dialog implements ActionListener 
{
	
	StandardButton btnshowDeiveInfo;
	StandardButton btnrefresh;
	Frame mainui;
	
	JTextArea AppInfo;
	JTextArea DeviceInfo;	
	Panel textPanel;
	
	MyDeviceInfo mMyDeviceInfo;
	ArrayList<MyDeviceInfo.Device> DeviceList;
	
    String[] petStrings = { "97920989(IM-G920S)", "31445989(SC-04F)"};
    
    //Create the combo box, select the item at index 4.
    //Indices start at 0, so 4 specifies the pig.
    JComboBox petList;
		
	public ADBDialog(Frame f1) {
		super(f1, "ADB Install", true);
		mainui = f1;
		setLayout(new BorderLayout());
		
		Panel ButtonPanel = new Panel(new GridLayout(1,2));
		
		petList = new JComboBox(petStrings);
		
        petList.setSelectedIndex(0);
        petList.addActionListener(this);		
	    	    
		ButtonPanel.add(btnshowDeiveInfo = new StandardButton("Refresh Device List",Theme.GRADIENT_LIGHTBLUE_THEME,ButtonType.BUTTON_ROUNDED));
		ButtonPanel.add(btnrefresh = new StandardButton("설치",Theme.GRADIENT_LIGHTBLUE_THEME,ButtonType.BUTTON_ROUNDED));
		
        WindowAdapter wa = new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	//setVisible(false);
                System.exit(0);
            }
        };
        this.addWindowListener(wa);
		
        textPanel = new Panel(new GridLayout(1,2));
        
		setResizable( false );
		btnshowDeiveInfo.addActionListener(this);
		btnrefresh.addActionListener(this);
		
		AppInfo = new JTextArea("-Source Apk\nPakage : Com.iloen.melon\nVersion 3.2.2\n"
				+ "\n-Target Apk\nPakage : Com.iloen.melon\nVersion : 3.1\nCodePath : /system/priv-app/Melon\nlegacyNativeLibDir : /system/priv-app/Melon/lib\n"
				+ "\n-Device\nModel : IM-G920S\n"+"build TAG : release-key\n Binary Version : SC04FOMU1WOEA\nbuild type : user");
		
		AppInfo.setEditable(false);
        Font font = new Font("helvitica", Font.BOLD, 15);
        AppInfo.setFont(font);        
		AppInfo.setBackground(textPanel.getBackground());
        
        
		textPanel.add(AppInfo);
		
		
		//textPanel.add(DeviceInfo = new JTextArea("Device Information"));
		
		
		add(petList,BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);
		add(ButtonPanel,BorderLayout.SOUTH);
		
		//add(scrollPane,BorderLayout.NORTH);
		
		setSize(400,380);
		this.setLocation(MainUI.nPositionX+100, MainUI.nPositionY+100);	
		
		mMyDeviceInfo = new MyDeviceInfo();
		
		DeviceList = mMyDeviceInfo.DeviceList;
		
				
		
	}
	public void actionPerformed(ActionEvent e)
	{		
		
		String str=e.getActionCommand();
		
		if(str == "Refresh Device List") {
			System.out.println("click  :" + str);
			setVisible(false);
		} else if(str == "설치") {
			System.out.println("click  :" + str);	
		}		
	}
	public void showPlease() {
		setVisible(true);
		
	}
  
		  
public static void main(final String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {	
				//args = file path					
				Frame window = null;
				ADBDialog dlg = new ADBDialog(window);
				
				dlg.showPlease();
				
			}
		});
	}
}

