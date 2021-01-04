/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		GLabel msgLabel = new GLabel(msg);
		msgLabel.setFont(MESSAGE_FONT);
		add(msgLabel,getWidth()/2-msgLabel.getWidth()/2, getHeight()-BOTTOM_MESSAGE_MARGIN);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		addName(profile);
		addImage(profile);
		addStatus(profile);
		addFriends(profile);

	}
	
	
	private void addName(FacePamphletProfile profile) {
		String name = profile.getName();
		NameLabel = new GLabel(name);
		NameLabel.setColor(Color.BLUE);
		NameLabel.setFont(PROFILE_NAME_FONT);
		add(NameLabel, LEFT_MARGIN, TOP_MARGIN+NameLabel.getHeight());
	}
	
	private void addImage(FacePamphletProfile profile) {
		GImage image = profile.getImage();
		h = IMAGE_MARGIN+TOP_MARGIN+NameLabel.getHeight();
		if (image==null) {
			GRect imageSpace = new GRect(IMAGE_WIDTH,IMAGE_HEIGHT);			
			add(imageSpace,LEFT_MARGIN, h);
			GLabel NoImageLabel = new GLabel("No Image");
			NoImageLabel.setFont(PROFILE_IMAGE_FONT);
			add(NoImageLabel, LEFT_MARGIN+IMAGE_WIDTH/2-NoImageLabel.getWidth()/2, h+IMAGE_HEIGHT/2+NoImageLabel.getHeight()/2);
		}else {
		 	double sx = IMAGE_WIDTH/image.getWidth();
		 	double sy = IMAGE_HEIGHT/image.getHeight();
			image.scale(sx, sy);
			add(image,LEFT_MARGIN, h);
		}
	}
	
	
	private void addStatus(FacePamphletProfile profile) {		
		String status =  profile.getStatus();
		String statusMessage;
		
		if (status.equals("")) {
			statusMessage = "No Current Status";			
		}else {
			statusMessage = profile.getName()+" is "+status;			
		}
		GLabel statusLabel = new GLabel(statusMessage);
		statusLabel.setFont(PROFILE_STATUS_FONT);
		add(statusLabel,LEFT_MARGIN,STATUS_MARGIN+h+IMAGE_HEIGHT+statusLabel.getHeight());
		
	}
	
	
	private void addFriends(FacePamphletProfile profile) {
		GLabel friendsHeader = new GLabel("Friends:");
		friendsHeader.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendsHeader,getWidth()/2,h);
		Iterator<String> friendsIt = profile.getFriends();
		double verticalSpace = 1*friendsHeader.getHeight();
		double current_position = h;
		while (friendsIt.hasNext()) {
			current_position = current_position+verticalSpace;
			GLabel frindes = new GLabel(friendsIt.next(),getWidth()/2,current_position);
			verticalSpace = 1*frindes.getHeight();
			add(frindes);
		}
		
		
	}
	
	
	
	private GLabel NameLabel;
	private double h;

	
}
