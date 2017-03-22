package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToLeftLift extends CommandGroup {

    public DriveToLeftLift() {
    	if(Robot.sensors.isAllianceColorRed()){
    		System.out.println("Left lift red");
    		//addSequential(new DriveToPoint(62,0, new Acceleration(.1,.6,.02),true, 20,2000));
    		addSequential(new DriveToPoint(62,0, new Acceleration(.1,.6,.02),true, 20,2000));
        	addSequential(new RotateToHeading(55,0,0.5,false));
        	addSequential(new WaitForTime(100));
      
        	//addSequential(new DriveToPoint(100,26, new Acceleration(.2,.3,.04),false,10,1500));
        	
        	addSequential(new AutonDriveToTarget());
        	//addSequential(new DriveToPoint(91.5,30, new Acceleration(.2,.3,.04),false,10,1500));
        	addSequential(new GearReleaseToggle(true));
        	addSequential(new WaitForTime(1000));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(7, .4));
        	switch(Robot.autonSelector.getEndingPosition()) {
        		case "B": {
	        		addSequential(new DriveBackToPoint(12,-59, new Acceleration(.3,.5,0.02),false,20, 3000));
	        		addSequential(new ShootHighForTime(7000));
	        		break;
	        	}
        	
	        	case "H": {
	        		addSequential(new DriveBackToPoint(85, -73, new Acceleration(0.3,0.5, 0.02), false, 15, 3000));
	        		break;
	        	}
	        	case "G": {
	        		//addSequential(new DriveForwardForDistance(10,0.4));
	        		addSequential(new RotateToHeading(150,0,0.6,false));
	        		addSequential(new DriveBackToPoint(170, -20, new Acceleration(.3,0.5,0.02),false,20,10000));
	        		addSequential(new DriveBackToPoint(380, 120, new Acceleration(.3,0.5,0.02),true,20,10000));
	        		break;
	        	}
	        	default:
	        	case "L": {
	        		addSequential(new DriveBackToPoint(14, -61, new Acceleration(.3,.5,0.02),false,20, 3000));
	        		addSequential(new LowBoilerOnForTime(7000));
	        		break;
	        	}
        	}
        	
        	//addSequential(new SwitchDirection());
        	
    	}else{
    		System.out.println("Left lift blue");
    		//addSequential(new DriveToPoint(75,0, new Acceleration(.2,.6,.04),true, 20,2000));
    		addSequential(new DriveToPoint(63,0, new Acceleration(.2,.6,.04),true, 20,2000));
        	addSequential(new RotateToHeading(-55,.5,0,false));
        	addSequential(new WaitForTime(100));
        	
        	//addSequential(new DriveToPoint(109,-27, new Acceleration(.2,.3,.04),false,10,1500));
        	
        	addSequential(new AutonDriveToTarget());
        	//addSequential(new DriveToPoint(97,-27, new Acceleration(.2,.3,.04),false,10,1500));
        	addSequential(new GearReleaseToggle(true));
        	addSequential(new WaitForTime(1000));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(7, .4));
        	switch(Robot.autonSelector.getEndingPosition()) {
    			case "B": {
    				addSequential(new DriveBackToPoint(20,68, new Acceleration(.3,.5,0.02),false,20, 3000));
    				addSequential(new ShootHighForTime(7000));
	        		break;
	        	}
	    	
	        	case "H": {
	        		addSequential(new DriveBackToPoint(85, 70, new Acceleration(0.3,0.5, 0.02), false, 20, 3000));
	        		break;
	        	}
	        	case "G": {
	        		//addSequential(new DriveForwardForDistance(10,0.4));
	        		addSequential(new RotateToHeading(-150,0.6,0,false));
	        		addSequential(new DriveBackToPoint(170, 20, new Acceleration(.3,0.5,0.02),false,20,10000));
	        		addSequential(new DriveBackToPoint(380, -120, new Acceleration(.3,0.5,0.02),true,20,10000));
	        		
	        		break;
	        	}
	        	default:
	        	case "L": {
	        		addSequential(new DriveBackToPoint(12, 61, new Acceleration(.3,.5,0.02),false,20, 3000));
	        		addSequential(new LowBoilerOnForTime(7000));
	        		break;
	        	}
        	}
        	
        	//addSequential(new SwitchDirection());
        	
    	}
    	
    	
    	//addSequential(new RotateToHeading(20,.3,0,true));
    	/*
    	addSequential(new SwitchDirection());
    	addSequential(new DriveForwardForDistance(7,.2));
  		addSequential(new DriveBackToPoint(40, 8, new Acceleration(.2,.4,.01), true, 5));
    	addSequential(new DriveBackToPoint(22,24, new Acceleration(.3,.3,0), false, 5));
    	addSequential(new SwitchDirection());
    	*/
    	//addSequential(new DriveBackToPoint(-60,-28,.3));
    
    }
}
