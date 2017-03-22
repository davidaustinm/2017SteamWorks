
package org.usfirst.frc.team4003.robot;

import java.io.*;
import java.util.Enumeration;
import java.util.Set;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.networktables.*;

import org.usfirst.frc.team4003.robot.commands.*;
import org.usfirst.frc.team4003.robot.subsystems.*;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;
import org.usfirst.frc.team4003.robot.utilities.AutonInterface;
import org.usfirst.frc.team4003.robot.utilities.UDPServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//private static final int BEATERSUBSYSTEM = 0;
	// subsystems
	public static ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	// change this !
	public static TalonDriveTrain driveTrain;
	//public static FrisbeeTestDrive driveTrain;
	public static Sensors sensors;
	public static ShooterSubsystem shooter;
	public static Pneunamatics solenoid;;
	public static ShifterSubsystem gearShifter;
	public static IntakeValves intakeValves;
	public static GearReleaseSubsystem gearRelease;
	public static BeaterSubsystem beaters;
	public static ReentryFeedSubsystem intakeFeed;
	public static ClimbDrumSubsystem climbDrum;
	public static Agitator agitator;
	public static ShooterFeed shooterFeed;
	
	public static int DRIVETRAINSUBSYSTEM = 0;
	public static int BEATERSUBYSTEM = 1;
	public static int REENTRYFEEDSUBSYSTEM = 2;
	public static int CLIMBSUBSYSTEM = 3;
	public static int SHOOTERSUBSYSTEM = 4;
	public static int SHOOTERFEEDSUBSYSTEM = 5;
	public static int AGITATOR = 6;
	public static int SHIFTERSUBSYSTEM = 7;
	public static int INTAKEVALVESSYSTEM = 8;
	public static int GEARRELEASESUBSYSTEM = 9;
	
	public static boolean[] systemLoad = new boolean[10];
	
	public static GearReleaseCommand gearReleaseCommand;
	public static IntakeValueCommand intakeValveCommand;
	public static GearShiftCommand gearShiftCommand;
	public static ShooterCommand shooterCommand;
	
	public static OI oi;
	public static TrackingCamera trackingCamera;
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public static AutonSelector revAutonSelector = new AutonSelector();
	//public static AutonInterface autonSelector = new DashboardAutonSwitch();
	public static AutonInterface autonSelector = new AutonSwitch();
	
	public static ShooterState shooterState = null;
	public static LowBoilerState lowBoilerState = null;
	public static UDPServer udpServer = null;
	
	static {
		sensors = new Sensors();
		
		systemLoad[DRIVETRAINSUBSYSTEM] = true;
		systemLoad[BEATERSUBYSTEM] = true; // true
		systemLoad[REENTRYFEEDSUBSYSTEM] = true; // true
		systemLoad[CLIMBSUBSYSTEM] = true; // true
		systemLoad[SHOOTERSUBSYSTEM] = true; // true
		systemLoad[SHOOTERFEEDSUBSYSTEM] = false; // keep this one false
		systemLoad[AGITATOR] = true; // true
		systemLoad[SHIFTERSUBSYSTEM] = true; // true
		systemLoad[INTAKEVALVESSYSTEM] = true; // true
		systemLoad[GEARRELEASESUBSYSTEM] = true; // true
		
		// change this!
		if (systemLoad[DRIVETRAINSUBSYSTEM]) driveTrain = new TalonDriveTrain();
		//if (systemLoad[DRIVETRAINSUBSYSTEM]) driveTrain = new FrisbeeTestDrive();
		if (systemLoad[BEATERSUBYSTEM]) beaters = new BeaterSubsystem();
		if (systemLoad[REENTRYFEEDSUBSYSTEM]) intakeFeed = new ReentryFeedSubsystem();
		if (systemLoad[CLIMBSUBSYSTEM]) climbDrum = new ClimbDrumSubsystem();
		if (systemLoad[SHOOTERSUBSYSTEM]) {
			shooter = new ShooterSubsystem();
			shooterCommand = new ShooterCommand();
			//shooterCommand.start();
		}
		if (systemLoad[SHIFTERSUBSYSTEM]) {
			gearShifter = new ShifterSubsystem();
			gearShiftCommand = new GearShiftCommand();
			//gearShiftCommand.start();
		}
		if (systemLoad[INTAKEVALVESSYSTEM]) {
			intakeValves = new IntakeValves();
			intakeValveCommand = new IntakeValueCommand();
			//intakeValveCommand.start();
		}
		if (systemLoad[GEARRELEASESUBSYSTEM]) {
			gearRelease = new GearReleaseSubsystem();
			
			//gearReleaseCommand.start();
		}
		if (systemLoad[AGITATOR]) agitator = new Agitator();
		if (systemLoad[SHOOTERFEEDSUBSYSTEM]) shooterFeed = new ShooterFeed();
		
		
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		Compressor c = new Compressor(0);
		c.setClosedLoopControl(true);
		c.start();
		
		if (RobotMap.trackingLocal == false) udpServer = new UDPServer();
		
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", chooser);
		TrackingCamera.loadKeys();
		try {
			Process p1 = Runtime.getRuntime().exec("/usr/bin/v4l2-ctl --list-devices");
			BufferedReader output = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			String line = "";
			while((line = output.readLine()) != null) {
				System.out.println(line);

				if(line.indexOf("0-1.1") > 0) {
					// A line containing 0-1.1 indicates a camera plugged directly into the roboRio on a specific port.
					// This will be used as the tracking camera.  A physical indicator of said USB port should probably be
					// made on the unit.
					line = output.readLine();
					System.out.println(line);
					// We want the number immediately after /dev/video on this line and a nice trick to counting how many spaces
					// your literal strings consists of is the "literal".length() trick... better than magic numbers.
					int index = line.indexOf("/dev/video") + "/dev/video".length();
					TrackingCamera.cameraHash.put("Tracking", new Integer(line.substring(index, index + 1)));
				} else {
					// This camera is plugged into the non-tracking camera port or, more likely, into a hub on the non-tracking port.
					System.out.println("Key count: " + TrackingCamera.cameraHash.keySet().size());
					Set<String> keys = TrackingCamera.cameraHash.keySet();
					for(String key:keys) {
						if (line.indexOf(key) >= 0) {
							line = output.readLine();
							System.out.println(line);
							int index = line.indexOf("/dev/video") + "/dev/video".length();
							TrackingCamera.cameraHash.put(key, new Integer(line.substring(index, index + 1)));
						}
					}
				}
				
			}
			for (Enumeration<String> cameraKeys = TrackingCamera.cameraHash.keys(); cameraKeys.hasMoreElements();) {
				String key = cameraKeys.nextElement();
				System.out.println(key + " " + TrackingCamera.cameraHash.get(key));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    trackingCamera = new TrackingCamera();
	    trackingCamera.startCamera();
	    
	    
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		
		System.out.println(autonSelector.getAllianceColor() + " " + 
				autonSelector.getStartingPosition() + " " + 
				autonSelector.getEndingPosition());
				
		Scheduler.getInstance().run();
		//revAutonSelector.update();
		/*
		System.out.println(autonSelector.getAllianceColor()+autonSelector.getStartingPosition()+
				autonSelector.getEndingPosition());
				*/
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		//System.out.println("in autonomous init");
		sensors.resetEncoder();
		sensors.resetPosition();
		sensors.resetYaw();
		if (systemLoad[DRIVETRAINSUBSYSTEM]) driveTrain.setMaxSpeed(0.65); // was 0.65
		if (systemLoad[INTAKEVALVESSYSTEM]) (new FeedHopper()).start();
		
		//String allColor = autonSelector.getAllianceColor();
		System.out.println(autonSelector.getAllianceColor()+autonSelector.getStartingPosition()+
				autonSelector.getEndingPosition());
		/*
		System.out.println("Start: " + autonSelector.getStartingPosition()+"x");
		System.out.println("End: " + autonSelector.getEndingPosition()+"x");
		if (allColor != "R" && allColor != "B") {
			System.out.println("swapping auton selector");
			//autonSelector = revAutonSelector;
		}
		*/
		int color;
		if (autonSelector.getAllianceColor() == "R") color = Sensors.RED;
		else color = Sensors.BLUE;
		
		sensors.setAllianceColor(color);
		
		switch(autonSelector.getStartingPosition()) {
			case("L"): 
				if (color == Sensors.RED) autonomousCommand = new DriveToRightLift();
				else autonomousCommand = new DriveToLeftLift();
				break;	
			case("C"): 
				autonomousCommand = new DriveToMiddleLift();
				break;	
			case("R"):
				if (color == Sensors.RED) autonomousCommand = new DriveToLeftLift();
				else autonomousCommand = new DriveToRightLift();
				break;
			case("H"):
				//sensors.setBumperOffset(-3);
				//autonomousCommand = new DriveToRedBoilerHopper();
				autonomousCommand = new ShootThenGear();
				break;
			case("B"):
				autonomousCommand = new DriveToPoint(120, 0, new Acceleration(0.1, 0.5, 0.02), false, 20, 5000);
				break;
			default:
				autonomousCommand = new DoNothing();
		}
		/*
		if (color == Sensors.BLUE) System.out.println("Color: blue " + color);
		else System.out.println("Color: red " + color);
		*/
		//autonomousCommand = new DriveToLeftLift();
		System.out.println(autonomousCommand);
		if (systemLoad[GEARRELEASESUBSYSTEM]) {
			gearReleaseCommand = gearRelease.gearReleaseCommand;
		}
		if (systemLoad[SHOOTERSUBSYSTEM]) {
			shooterState = null;
			shooterState = new ShooterState();
			shooterState.start();
		}
		if (systemLoad[BEATERSUBYSTEM]) {
			lowBoilerState = null;
			lowBoilerState = new LowBoilerState();
			lowBoilerState.start();
		}
	    if (systemLoad[SHIFTERSUBSYSTEM]) {
			gearShiftCommand = gearShifter.gearShiftCommand;
		}
		if (systemLoad[INTAKEVALVESSYSTEM]) {
			intakeValveCommand = intakeValves.intakeCommand;
		}
			
		//autonomousCommand = new ShootThenGear();
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/** 
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		sensors.updatePosition();
		NetworkTable robotData = NetworkTable.getTable("robotData");
		robotData.putNumber("robotX", sensors.getXCoordinate());
		robotData.putNumber("robotY", sensors.getYCoordinate());
		robotData.putNumber("robotYaw", sensors.getYaw());
		SmartDashboard.putNumber("X Coordinate", sensors.getXCoordinate());
		SmartDashboard.putNumber("Y Coordinate", sensors.getYCoordinate());
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		shooter.resetSpeed();
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		if (systemLoad[CLIMBSUBSYSTEM]) (new HomeClimbDrum(HomeClimbDrum.VERTICAL)).start();
		sensors.resetEncoder();
		sensors.resetPosition();
		sensors.resetYaw();
		if (systemLoad[DRIVETRAINSUBSYSTEM]) {
			driveTrain.setMaxSpeed(.47);
			if (driveTrain.getSwitchCount() == 1) driveTrain.switchDirection();
		}
		if (systemLoad[GEARRELEASESUBSYSTEM]) {
			gearReleaseCommand = gearRelease.gearReleaseCommand;
			gearRelease.setState(false);
		}
		/*
		shooterState = null;
		shooterState = new ShooterState();
		shooterState.start();
		
		lowBoilerState = null;
		lowBoilerState = new LowBoilerState();
		lowBoilerState.start();
		*/
		
		if (systemLoad[SHOOTERSUBSYSTEM]) {
			shooterState = null;
			shooterState = new ShooterState();
			shooterState.start();
		}
		if (systemLoad[BEATERSUBYSTEM]) {
			lowBoilerState = null;
			lowBoilerState = new LowBoilerState();
			lowBoilerState.start();
		}
		
		
	    if (systemLoad[SHIFTERSUBSYSTEM]) {
			gearShiftCommand = gearShifter.gearShiftCommand;
		}
		if (systemLoad[INTAKEVALVESSYSTEM]) {
			intakeValveCommand = intakeValves.intakeCommand;
			(new FeedHopper()).start();
		}
		if (systemLoad[BEATERSUBYSTEM]) lowBoilerState.setIdle();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//oolean gearReleaseState = Math.abs(oi.operator.getTriggerAxis(Hand.kRight)) > .5;
		//boolean shooterState = Math.abs(oi.operator.getTriggerAxis(Hand.kLeft)) > .5;
		//if (systemLoad[SHOOTERSUBSYSTEM]) shooterCommand.set(shooterState);
		//if (systemLoad[GEARRELEASESUBSYSTEM]) gearReleaseCommand.set(gearReleaseState);
		sensors.updatePosition();
		NetworkTable robotData = NetworkTable.getTable("robotData");
		robotData.putNumber("robotX", sensors.getXCoordinate());
		robotData.putNumber("robotY", sensors.getYCoordinate());
		robotData.putNumber("robotYaw", sensors.getYaw());
		double[] target = trackingCamera.getTargetPosition();
		
		double targetX = target[0];
		double targetY = target[1];
		//SmartDashboard.putNumber("PiTargetX", targetX);
		//SmartDashboard.putNumber("PiTargetY", targetY);
		//System.out.println(targetX + " " + targetY);
		//if (Double.isNaN(targetX) == false) SmartDashboard.putNumber("PiTargetX", targetX);
		//if (Double.isNaN(targetY) == false) SmartDashboard.putNumber("PiTargetY", targetY);
		SmartDashboard.putNumber("X Coordinate", sensors.getXCoordinate());
		SmartDashboard.putNumber("Y Coordinate", sensors.getYCoordinate());
		//SmartDashboard.putNumber("shooterspeed", shooter.getSpeed());
		//SmartDashboard.putNumber("Closed Loop Error", shooter.getClosedLoopError());
		/*
		SmartDashboard.putNumber("Yaw", sensors.getYaw());
		SmartDashboard.putNumber("Right Encoder", sensors.getRightDriveEncoder());
		SmartDashboard.putNumber("Left Encoder", sensors.getLeftDriveEncoder());
		
		*/
		//double[] target = trackingCamera.getTargetPosition();
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
		LiveWindow.run();
	}
}
