package org.sobotics.guttenberg.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.JarFile;

import org.sobotics.guttenberg.clients.Client;
import org.sobotics.guttenberg.clients.Guttenberg;
import org.sobotics.guttenberg.utils.ApiUtils;
import org.sobotics.guttenberg.utils.CommandUtils;
import org.sobotics.guttenberg.utils.FilePathUtils;
import org.sobotics.guttenberg.utils.StatusUtils;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;

public class Status implements SpecialCommand {
	
	private Message message;

    public Status(Message message) {
        this.message = message;
    }
    
	@Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"status");
    }

	@Override
	public void execute(Room room) {
		System.out.println("Checking status...");
		Properties prop = new Properties();
		Properties prop2 = new Properties();
		
        try{
            prop.load(new FileInputStream(FilePathUtils.loginPropertiesFile));
            //prop2.load(new FileInputStream("src/main/resources/guttenberg.properties"));
            InputStream is = Status.class.getResourceAsStream("/guttenberg.properties");
           	prop2.load(is);
        }
        catch (IOException e){
        	System.out.println("Could not load properties");
            e.printStackTrace();
        }
		
		
        StringBuilder status = new StringBuilder();
        status.append("Running since: "+StatusUtils.startupDate);
        
        if (room.getRoomId() == 111347) {
        	status.append("\nLast execution finished: "+StatusUtils.lastExecutionFinished);
        	status.append("\nLocation: "+prop.getProperty("location", "undefined"));
        }
        
        String version = prop2.getProperty("version", "undefined");
        status.append("\nVersion: "+version);
        status.append("\nChecked "+StatusUtils.numberOfCheckedTargets+" targets and reported "+StatusUtils.numberOfReportedPosts);
        
		
		room.replyTo(message.getId(), status.toString());
	}

	@Override
	public String description() {
		return "Returns statistics about the current status";
	}

	@Override
	public String name() {
		return "status";
	}

}