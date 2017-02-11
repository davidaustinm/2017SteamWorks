
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
	Trigger trackingOn = new TrackingOn();
	Trigger trackingOff = new TrackingOff();
	Trigger toggleCamera = new ToggleCamera(); 
	//Button toggleSpeed = new JoystickButton(driver,);
	Button toggleSolenoidOFF = new JoystickButton(driver,6);
	Button toggleSolenoidON = new JoystickButton(driver,5);
	
	public void buildTriggers() {
		trackingOn.whenActive(new ToggleTracking(true));
		trackingOff.whenActive(new ToggleTracking(false));
		toggleCamera.whenActive(new ToggleCameraCommand());
	}
	
	public OI() {
		//toggleSpeed.whenPressed(new GearChanger());
		toggleSolenoidOFF.whenPressed(new SolenoidActivator(true));
		toggleSolenoidON.whenPressed(new SolenoidActivator(false));
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
