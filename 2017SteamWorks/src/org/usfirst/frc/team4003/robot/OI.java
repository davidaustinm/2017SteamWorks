
package org.usfirst.frc.team4003.robot;

import org.usfirst.frc.team4003.robot.commands.*;
import org.usfirst.frc.team4003.robot.triggers.*;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public XboxController driver = new XboxController(0);
	public XboxController operator = new XboxController(1);
	
	XboxTrigger backupDistance = new XboxTrigger(driver, XboxTrigger.LB);
	//XboxTrigger trackingOn = new XboxTrigger(driver, XboxTrigger.);
	//XboxTrigger trackingOff = new XboxTrigger(driver, XboxTrigger.B);
	XboxTrigger toggleCamera = new XboxTrigger(driver, XboxTrigger.X);
	XboxTrigger shiftHigh = new XboxTrigger(driver, XboxTrigger.DPADUP);		//Done -- Shift to high speed
	XboxTrigger shiftLow = new XboxTrigger(driver, XboxTrigger.DPADDOWN);		//Done -- Shift to low speed
	XboxTrigger intakeDivert = new XboxTrigger(operator, XboxTrigger.B);		//Done -- Changes flipper to low goal and opens hopper
	//XboxTrigger gearReleaseOpen = new XboxTrigger(operator, XboxTrigger.A);
	//XboxTrigger gearReleaseClose = new XboxTrigger(operator, XboxTrigger.Y);
	XboxTrigger homeClimbHorizontal = new XboxTrigger(operator, XboxTrigger.RB);//Done
	XboxTrigger homeClimbVertical = new XboxTrigger(operator, XboxTrigger.LB);	//Done
	XboxTrigger switchDirection = new XboxTrigger(driver, XboxTrigger.A);		//Done
	XboxTrigger driveToTarget = new XboxTrigger(driver, XboxTrigger.B);			//Done
	XboxTrigger feedHopper = new XboxTrigger(operator, XboxTrigger.A);
	XboxTrigger feedLowBoiler = new XboxTrigger(operator, XboxTrigger.B);
	
	public OI() {
		
		//trackingOn.whenActive(new ToggleTracking(true));		Tracking
		toggleCamera.whenActive(new ToggleCameraCommand());
		
		shiftHigh.whenActive(new ShiftToggle(true));
		shiftLow.whenActive(new ShiftToggle(false));
		intakeDivert.whenActive(new IntakeValveToggle(RobotMap.INTAKEFLIPPER, true));
		intakeDivert.whenActive(new IntakeValveToggle(RobotMap.INTAKEREENTRY, true));
		//gearReleaseOpen.whenActive(new GearReleaseToggle(true));
		//gearReleaseClose.whenActive(new GearReleaseToggle(false));
		homeClimbHorizontal.whenActive(new HomeClimbDrum(HomeClimbDrum.HORIZONTAL));
		homeClimbVertical.whenActive(new HomeClimbDrum(HomeClimbDrum.VERTICAL));
		switchDirection.whenActive(new SwitchDirection());
		driveToTarget.whileActive(new TeleopDriveToTarget());
		feedHopper.whenActive(new FeedHopper());
		feedLowBoiler.whenActive(new FeedLowBoiler());
		backupDistance.whileActive(new BackupLoadGear());
	}
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
