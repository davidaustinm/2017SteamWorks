
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
	//XboxTrigger intakeDivert = new XboxTrigger(operator, XboxTrigger.B);		//Done -- Changes flipper to low goal and opens hopper
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
		//intakeDivert.whenActive(new IntakeValveToggle(RobotMap.INTAKEFLIPPER, true));
		//intakeDivert.whenActive(new IntakeValveToggle(RobotMap.INTAKEREENTRY, true));
		homeClimbHorizontal.whenActive(new HomeClimbDrum(HomeClimbDrum.HORIZONTAL));
		homeClimbVertical.whenActive(new HomeClimbDrum(HomeClimbDrum.VERTICAL));
		switchDirection.whenActive(new SwitchDirection());
		driveToTarget.whileActive(new TeleopDriveToTarget());
		feedHopper.whenActive(new FeedHopper());
		feedLowBoiler.whenActive(new FeedLowBoiler());
		backupDistance.whileActive(new BackupLoadGear());
	}

}
