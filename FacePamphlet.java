/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {
//public class FacePamphlet extends ConsoleProgram 
//implements FacePamphletConstants {
	
	
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		// You fill this in
		
		
		
		canvas = new FacePamphletCanvas();
		add(canvas);
		
		addInteractors();

//		FacePamphletProfile a = new FacePamphletProfile("Amin");
//		a.setStatus("Coding");
//		a.addFriend("Nima");
//		a.addFriend("Abol");
//		a.addFriend("Danny");
//		println(a.toString());
//		Iterator<String> it = a.getFriends();
//		println(a.getFriends());
		
		
		
    }
	
	

	
	
	
    
	private void addInteractors() {
		nameField = new JTextField(TEXT_FIELD_SIZE);
		
		statusField = new JTextField(TEXT_FIELD_SIZE);
		pictureField = new JTextField(TEXT_FIELD_SIZE);
		friendField = new JTextField(TEXT_FIELD_SIZE);
		
		//north interactors
		add(new JLabel("Name"), NORTH);	
		add(nameField,NORTH);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		//west interactors		
		add(statusField,WEST);
		add(new JButton("Change Status"), WEST);		
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);		
		add(pictureField,WEST);	
		add(new JButton("Change Picture"), WEST);			
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);		
		add(friendField,WEST);
		add(new JButton("Add Friend"), WEST);	
		
		
		//add action commands and listeners
		statusField.setActionCommand("Change Status");
		statusField.addActionListener(this);
		
		pictureField.setActionCommand("Change Picture");
		pictureField.addActionListener(this);
		
		friendField.setActionCommand("Add Friend");
		friendField.addActionListener(this);

		addActionListeners();
	}
	
	
	
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		// You fill this in as well as add any additional methods
    	String cmd = e.getActionCommand();
    	
    	if (cmd.equals("Add")) {
			String name = nameField.getText();			
			if (name.length()>0) {
				
				if (database.containsProfile(name)) {
					canvas.removeAll();
					canvas.displayProfile(database.getProfile(name));
					canvas.showMessage("A profile with the name "+name+" already exists");
				}else {					
					canvas.removeAll();
					database.addProfile(new FacePamphletProfile(name));
					canvas.displayProfile(database.getProfile(name));
					canvas.showMessage("New Profile Created");					
				}
				
				currentProfile = database.getProfile(name);
			}					
    	}
    	
    	
    	
    	if (cmd.equals("Delete")) {
			String name = nameField.getText();
			if (name.length()>0) {
				
				if (database.containsProfile(name)) {					
					database.deleteProfile(name);
					canvas.removeAll();
					canvas.showMessage("Profile of "+name+" deleted");
				}else {
					canvas.showMessage("A profile with the name "+name+" does not exists");
				}
				
				currentProfile = null;
			}
    	}
    	
    	
    	
    	if (cmd.equals("Lookup")) {
			String name = nameField.getText();
			if (name.length()>0) {
				
				if (database.containsProfile(name)) {					
					canvas.removeAll();
					canvas.displayProfile(database.getProfile(name));
					canvas.showMessage("Displaying "+name);					
					
					currentProfile = database.getProfile(name);
				}else {					
					canvas.removeAll();	
					canvas.showMessage("A profile with the name "+name+" does not exists");
					
					currentProfile = null;
				}
				
			}
    	}
    	
    	
    	
    	
    	if (cmd.equals("Change Status")) {
			String status = statusField.getText();
			if (status.length()>0) {
				
				if (currentProfile!=null) {										
					currentProfile.setStatus(status);
					
					canvas.removeAll();	
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Status updated to "+status);
				}else {					
					canvas.removeAll();	
					canvas.showMessage("Please select a profile to change status");
				}
				
			}
    	}   
    	
    	
    	
    	
    	if (cmd.equals("Change Picture")) {
			String filename = pictureField.getText();
			if (filename.length()>0) {
				
				if (currentProfile!=null) {	
					
					
					GImage image = null;
					try {
					image = new GImage(filename);
					} catch (ErrorException ex) {
					// Code that is executed if the filename cannot be opened.
						canvas.removeAll();	
						canvas.displayProfile(currentProfile);
						canvas.showMessage("Unable to open image file: "+filename);
					}
					
					if (image!=null) {
						currentProfile.setImage(image);
						canvas.removeAll();	
						canvas.displayProfile(currentProfile);
						canvas.showMessage("Picture updated");
					}

				}else {					
					canvas.removeAll();	
					canvas.showMessage("Please select a profile to change picture");
				}
				
			}
    	}  
    	
    	
    	
    	if (cmd.equals("Add Friend")) {
			String friend = friendField.getText();
			if (friend.length()>0) {
				
				if (currentProfile!=null) {										
					if (database.containsProfile(friend)) {
						
						if (friend.equals(currentProfile.getName())) {
							canvas.removeAll();	
							canvas.displayProfile(currentProfile);
							canvas.showMessage("The friend\'s name is the same as the current profile\'s name");
						}else {
							boolean friendExists = currentProfile.addFriend(friend);
							
							if (friendExists==false) {
								canvas.removeAll();	
								canvas.displayProfile(currentProfile);
								canvas.showMessage(currentProfile.getName()+" already has "+friend+"as a friend");
							}else {
		
								database.getProfile(friend).addFriend(currentProfile.getName());
								canvas.removeAll();	
								canvas.displayProfile(currentProfile);
								canvas.showMessage(friend+" added as a friend");
													
							}
						}
						
						
					}else {
						canvas.removeAll();	
						canvas.displayProfile(currentProfile);
						canvas.showMessage(friend+" does not exist");
					}
					
					
				}else {		
					canvas.removeAll();	
					canvas.showMessage("Please select a profile to add friend");
				}
				
			}
    	}  
    	
	}
    
    
    
    
    
    
    
    
    private JTextField nameField;
    private JTextField statusField;
    private JTextField pictureField;
    private JTextField friendField;
    private FacePamphletDatabase database = new FacePamphletDatabase();
    private FacePamphletProfile currentProfile;
    private FacePamphletCanvas canvas;

}
