package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.commands.ArcadeDrive;
import org.usfirst.frc.team4003.robot.commands.TankDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FrisbeeTestDrive extends Subsystem {
	Talon left1, left2, right1, right2;
	double speedScale = .75;
	public FrisbeeTestDrive() {
		left1 = new Talon(0);
		left2 = new Talon(1);
		right1 = new Talon(2);
		right2 = new Talon(3);
		right1.setInverted(true);
		right2.setInverted(true);
	}
	
	public void setPower(double leftPower, double rightPower) {
		if(Math.abs(Robot.oi.driver.getTriggerAxis(Hand.kLeft)) > .5 || Robot.inAuton){
		
			leftPower *= maxSpeed;
			rightPower *= maxSpeed;
		}
		/*
		double tmp = leftPower;
		leftPower = rightPower;
		rightPower = tmp;
		*/
		/*
		leftPower *= -1;
		rightPower *= -1;
		*/
		left1.set(leftPower);
		left2.set(leftPower);
		right1.set(rightPower);
		right2.set(rightPower);
	}
	
	int switchCount = 0;
	double yLimit=1, xLimit=1;
	double maxSpeed = 1;
	
	public void setMaxSpeed(double speed){
		maxSpeed = speed;
	}
	
	public void setLimit(double xValue, double yValue){
		yLimit = yValue;
		xLimit = xValue;
	}
	
	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {

	    double leftMotorSpeed;
	    double rightMotorSpeed;
	    moveValue *= yLimit;
	    rotateValue *= xLimit;

	    if (squaredInputs) {
	      // square the inputs (while preserving the sign) to increase fine control
	      // while permitting full power
	      if (moveValue >= 0.0) {
	        moveValue = moveValue * moveValue;
	      } else {
	        moveValue = -(moveValue * moveValue);
	      }
	      if (rotateValue >= 0.0) {
	        rotateValue = rotateValue * rotateValue;
	      } else {
	        rotateValue = -(rotateValue * rotateValue);
	      }
	    }

	    if (moveValue > 0.0) {
	      if (rotateValue > 0.0) {
	        leftMotorSpeed = moveValue - rotateValue;
	        rightMotorSpeed = Math.max(moveValue, rotateValue);
	      } else {
	        leftMotorSpeed = Math.max(moveValue, -rotateValue);
	        rightMotorSpeed = moveValue + rotateValue;
	      }
	    } else {
	      if (rotateValue > 0.0) {
	        leftMotorSpeed = -Math.max(-moveValue, rotateValue);
	        rightMotorSpeed = moveValue + rotateValue;
	      } else {
	        leftMotorSpeed = moveValue - rotateValue;
	        rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
	      }
	    }
	    
	    if(Math.abs(Robot.oi.driver.getTriggerAxis(Hand.kLeft)) < .5){
			leftMotorSpeed *= maxSpeed;
			rightMotorSpeed *= maxSpeed;
		}
	    
	    //System.out.println(leftMotorSpeed + " " + rightMotorSpeed);
	    left1.set(leftMotorSpeed);
	    left2.set(leftMotorSpeed);
	    //left3.set(leftMotorSpeed);
	    right1.set(rightMotorSpeed);
	    right2.set(rightMotorSpeed);
	    //right3.set(rightMotorSpeed);
	}
	
	public void switchDirection(){
		Talon temp = left1;
		left1 = right1;
		right1 = temp;
		temp = left2;
		left2 = right2;
		right2 = temp;
		
		left1.setInverted(!left1.getInverted());
		left2.setInverted(!left2.getInverted());
		
		right1.setInverted(!right1.getInverted());
		right2.setInverted(!right2.getInverted());
		
		switchCount = 1-switchCount;
	}
	
	public int getSwitchCount() {
		return switchCount;
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TankDrive());
    	
    }
    
}

