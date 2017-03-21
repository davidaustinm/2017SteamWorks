
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
	XboxTrigger shiftHigh = new XboxTrigger(driver, XboxTrigger.DPADUP);		//Done -- Shift to high speed
	XboxTrigger shiftLow = new XboxTrigger(driver, XboxTrigger.DPADDOWN);		//Done -- Shift to low speed
	XboxTrigger switchDirection = new XboxTrigger(driver, XboxTrigger.A);		//Done
	XboxTrigger driveToTarget = new XboxTrigger(driver, XboxTrigger.B);			//Done
	XboxTrigger slowDriveOn = new XboxTrigger(driver, XboxTrigger.Y);
	XboxTrigger slowDriveOff = new XboxTrigger(driver, XboxTrigger.X);
	XboxTrigger bumpRight = new XboxTrigger(driver, XboxTrigger.DPADRIGHT);
	XboxTrigger bumpLeft = new XboxTrigger(driver, XboxTrigger.DPADLEFT);
	
	//XboxTrigger trackingOn = new XboxTrigger(driver, XboxTrigger.);
	//XboxTrigger trackingOff = new XboxTrigger(driver, XboxTrigger.B);
	//XboxTrigger intakeDivert = new XboxTrigger(operator, XboxTrigger.B);		//Done -- Changes flipper to low goal and opens hopper
	//XboxTrigger gearReleaseOpen = new XboxTrigger(operator, XboxTrigger.A);
	//XboxTrigger gearReleaseClose = new XboxTrigger(operator, XboxTrigger.Y);
	XboxTrigger homeClimbHorizontal = new XboxTrigger(operator, XboxTrigger.RB);//Done
	XboxTrigger homeClimbVertical = new XboxTrigger(operator, XboxTrigger.LB);	//Done
	XboxTrigger feedHopper = new XboxTrigger(operator, XboxTrigger.A);
	XboxTrigger feedLowBoiler = new XboxTrigger(operator, XboxTrigger.B);
	XboxTrigger shooterOn = new XboxTrigger(operator, XboxTrigger.LT);
	XboxTrigger gearRelease = new XboxTrigger(operator, XboxTrigger.RT);
	
	public OI() {
		
		//trackingOn.whenActive(new ToggleTracking(true));		Tracking
		//toggleCamera.whenActive(new ToggleCameraCommand());
		
		// driver
		
		shiftHigh.whenActive(new ShiftToggle(true));
		shiftLow.whenActive(new ShiftToggle(false));
		switchDirection.whenActive(new SwitchDirection());
		driveToTarget.whileActive(new TeleopDriveToTarget());
		backupDistance.whileActive(new BackupLoadGear());
		slowDriveOn.whenActive(new SlowDriveCommand(0.3));
		slowDriveOff.whenActive(new SlowDriveCommand(.8));
		bumpRight.whenActive(new ShortTurn(ShortTurn.RIGHT, 100, 0.5));
		bumpLeft.whenActive(new ShortTurn(ShortTurn.LEFT, 100, 0.5));
		// not needed any more, replaced by short turns
		//arcadeDrive.whenActive(new ArcadeDrive());
		//tankDrive.whenActive(new TankDrive());
		
		//operator
		
		
		homeClimbHorizontal.whenActive(new HomeClimbDrum(HomeClimbDrum.HORIZONTAL));
		homeClimbVertical.whenActive(new HomeClimbDrum(HomeClimbDrum.VERTICAL));
		
		//feedHopper.whenActive(new FeedHopper());
		feedLowBoiler.whileActive(new LoadHopper());
		shooterOn.whileActive(new TeleopShootHigh());
		gearRelease.whileActive(new PlaceGear());
		
		
		// don't worry about these
		//shooterOn.whenActive(new ShooterToggle(true));
		//shooterOn.whenInactive(new ShooterToggle(false));
		
	}

}
