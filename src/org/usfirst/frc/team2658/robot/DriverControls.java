package org.usfirst.frc.team2658.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2658.robot.Robot;

public class DriverControls {
	
	static boolean usingStraightener = false;
	static double slowModifier = 1;
	static double drivePow = SmartDashboard.getNumber("Drive Power", 1.0);
	static double driveExp = SmartDashboard.getNumber("Drive Exponent", 1.0);
	static double turnPow = SmartDashboard.getNumber("Turn Power", 0.7);
	
	public static void driveRobot(){
    	drivePow = SmartDashboard.getNumber("Drive Power", 1.0);
    	driveExp = SmartDashboard.getNumber("Drive Exponent", 1.0);
		driveStraight();
		toggleSlowModes();
		if (usingStraightener == false) driveNormal();
	}
	
	public static void driveNormal(){
    	//These are to be filled in with the joystick values
    	//jvalue1: arcade drive -> forward motion, tank drive -> left wheel
    	//jvalue2: arcade drive -> rotation, tank drive -> right wheel
    	double jValue1 = 0;
    	double jValue2 = 0;
    	//This is just to check if the Drive Power value is below 0 or greater than 1.0
    	if (SmartDashboard.getNumber("Drive Power", 1.0) < 0){
    		SmartDashboard.putNumber("Drive Power", 0);
    	} else if (SmartDashboard.getNumber("Drive Power", 1.0) > 1.0){
    		SmartDashboard.putNumber("Drive Power", 1.0);
    	}
    	
    	//Fill in Joystick Values depending on controller type
    	if (Robot.controlChoice.getSelected().equals("Singular Joystick (XY)")){
    		jValue1 = Robot.leftJoystick.getRawAxis(1);
    		jValue2 = Robot.leftJoystick.getRawAxis(0);
    	}
    	if (Robot.controlChoice.getSelected().equals("Singular Joystick (XZ)")){
    		jValue1 = Robot.leftJoystick.getRawAxis(1);
    		jValue2 = Robot.leftJoystick.getRawAxis(2);
    	}
    	if (Robot.controlChoice.getSelected().equals("Double Joystick")){
    		if (Robot.driveChoice.getSelected().equals("Tank Drive")){
    			//J1 = vertical axis on left joystick, J2 = vertical axis on right joystick
	    		jValue1 = Robot.leftJoystick.getRawAxis(1);
	    		jValue2 = Robot.rightJoystick.getRawAxis(1);
    		}
    		if (Robot.driveChoice.getSelected().equals("Arcade Drive")){
    			//J1 = vertical axis on left joystick, J2 = horizontal axis on right joystick
	    		jValue1 = Robot.leftJoystick.getRawAxis(1);
	    		jValue2 = Robot.rightJoystick.getRawAxis(0);
    		}
    	}
    	if (Robot.controlChoice.getSelected().equals("Controller")){
    		if (Robot.driveChoice.getSelected().equals("Tank Drive")){
    			//J1 = vertical axis on left joystick, J2 = vertical axis on right joystick
	    		jValue1 = Robot.xBox.getRawAxis(1);
	    		jValue2 = Robot.xBox.getRawAxis(5);
    		}
    		if (Robot.driveChoice.getSelected().equals("Arcade Drive")){
    			//J1 = vertical axis on left joystick, J2 = horizontal axis on right joystick
	    		jValue1 = Robot.xBox.getRawAxis(1);
	    		jValue2 = Robot.xBox.getRawAxis(4);
    		}
    	}
    	
    	if (Robot.controlChoice.getSelected().equals("Operator Controller")){
    		if (Robot.driveChoice.getSelected().equals("Tank Drive")){
    			//J1 = vertical axis on left joystick, J2 = vertical axis on right joystick
	    		jValue1 = Robot.operatorController.getRawAxis(1);
	    		jValue2 = Robot.operatorController.getRawAxis(5);
    		}
    		if (Robot.driveChoice.getSelected().equals("Arcade Drive")){
    			//J1 = vertical axis on left joystick, J2 = horizontal axis on right joystick
	    		jValue1 = Robot.operatorController.getRawAxis(1);
	    		jValue2 = Robot.operatorController.getRawAxis(4);
    		}
    	}
    	//Modify Joystick Value 1 by flipping if needed and with the Driver Exponent, Drive Power, and Slow Buttons
    	/*if (Robot.axisFlip1.getSelected().equals("(L) Flip"))*/ jValue1 *= -1; //Flip the axis if needed
    	if ((driveExp < 1) && (jValue1 < 0)){
    		jValue1 = Math.pow(jValue1 * -1, driveExp) * -1;
    	}
    	else if ((driveExp % 2 == 0) && (jValue1 < 0)){
    		jValue1 = Math.pow(jValue1, driveExp) * -1;
    	}
    	else jValue1 = Math.pow(jValue1, driveExp);
    	jValue1 = (jValue1 * drivePow) / slowModifier;
    	
    	//Modify Joystick Value 2 by flipping if needed and with the Drive Exponent, Drive Power, and Slow Buttons
    	/*if (Robot.axisFlip2.getSelected().equals("(R) Flip"))*/ jValue2 *= -1; //Flip the axis if needed
    	if ((driveExp < 1) && (jValue2 < 0)){
    		jValue2 = Math.pow(jValue2 * -1, driveExp) * -1;
    	}
    	else if ((driveExp % 2 == 0) && (jValue2 < 0)){
    		jValue2 = Math.pow(jValue2, driveExp) * -1;
    	}
    	else jValue2 = Math.pow(jValue2, driveExp);
    	jValue2 = (jValue2 * drivePow) / slowModifier;
    	if (Robot.driveChoice.getSelected().equals("Arcade Drive")) jValue2 *= turnPow;
    	
    	//Let's move the robot, do it for each drive type
    	if (Robot.driveChoice.getSelected().equals("Arcade Drive")){
    		Robot.drive.arcadeDrive(jValue1, jValue2);
	
    	}if (Robot.driveChoice.getSelected().equals("Tank Drive")){
    		Robot.drive.tankDrive(jValue1, jValue2);
    	}
    }
	
	private static void driveStraight(){
		double jValue1 = 0;
		double jValue2 = 0;
		
		//This is just to check if the Drive Power value is below 0 or greater than 1.0
    	if (SmartDashboard.getNumber("Drive Power", 1.0) < 0){
    		SmartDashboard.putNumber("Drive Power", 0);
    	} else if (SmartDashboard.getNumber("Drive Power", 1.0) > 1.0){
    		SmartDashboard.putNumber("Drive Power", 1.0);
    	}
    	
		if ((Robot.leftJoystick.getRawButton(1)) && ((Robot.controlChoice.getSelected().equals("Singular Joystick (XY)")) ||
				(Robot.controlChoice.getSelected().equals("Singular Joystick (XZ)")) || (Robot.controlChoice.getSelected().equals("Double Joystick")))){
			
			if (Robot.lj01ButtonCheck == false){
			//	Robot.straightAngle = Robot.gyro.getAngle();
			}
			
			jValue1 = Robot.leftJoystick.getRawAxis(1);
			
			//Modify Joystick Value 1 by flipping if needed and with the Driver Exponent, Drive Power, and Slow Buttons
			/*if (Robot.axisFlip1.getSelected().equals("(L) Flip"))*/ jValue1 *= -1; //Flip the axis if needed
	    	if ((driveExp < 1) && (jValue1 < 0)){
	    		jValue1 = Math.pow(jValue1 * -1, driveExp) * -1;
	    	}
	    	else if ((driveExp % 2 == 0) && (jValue1 < 0)){
	    		jValue1 = Math.pow(jValue1, driveExp) * -1;
	    	}
	    	else jValue1 = Math.pow(jValue1, driveExp);
	    	jValue1 = (jValue1 * drivePow) / slowModifier;
	    	
	    	/*jValue2 = 0.01 * (Robot.gyro.getAngle() - Robot.straightAngle);
	    	if (jValue2 > 1.0) jValue2 = 1.0;
	    	if (jValue2 < -1.0) jValue2 = -1.0;*/
	    	
	    	Robot.drive.arcadeDrive(jValue1, jValue2);
	    	
	    	usingStraightener = true;
	    	SmartDashboard.putString("Drive Straightener", "ON");
		}
		
		/*else if ((Robot.xBox.getRawButton(6)) && (Robot.controlChoice.getSelected().equals("Controller"))){
			
			if (Robot.driverRBButtonCheck == false){
			//	Robot.straightAngle = Robot.gyro.getAngle();
			}
			
			jValue1 = Robot.xBox.getRawAxis(1);
			
			//Modify Joystick Value 1 by flipping if needed and with the Driver Exponent and Drive Power
			/*if (Robot.axisFlip1.getSelected().equals("(L) Flip"))*/ jValue1 *= -1; //Flip the axis if needed
	    	/*if ((driveExp < 1) && (jValue1 < 0)){
	    		jValue1 = Math.pow(jValue1 * -1, driveExp) * -1;
	    	}
	    	else if ((driveExp % 2 == 0) && (jValue1 < 0)){
	    		jValue1 = Math.pow(jValue1, driveExp) * -1;
	    	}
	    	else jValue1 = Math.pow(jValue1, driveExp);
	    	jValue1 = (jValue1 * drivePow) / slowModifier;
	    	
	    	/*jValue2 = 0.01 * (Robot.gyro.getAngle() - Robot.straightAngle);
	    	if (jValue2 > 1.0) jValue2 = 1.0;
	    	if (jValue2 < -1.0) jValue2 = -1.0;*/
	    	
	    	/*Robot.drive.arcadeDrive(jValue1, jValue2);
	    	
	    	usingStraightener = true;
	    	SmartDashboard.putString("Drive Straightener", "ON");
	    	
		} else {
			usingStraightener = false;
			SmartDashboard.putString("Drive Straightener", "OFF");
		}*/
		Robot.lj01ButtonCheck = Robot.leftJoystick.getRawButton(1);
		/*Robot.driverRBButtonCheck = Robot.xBox.getRawButton(6);*/
	}
	
	private static void toggleSlowModes(){
		if ((Robot.leftJoystick.getRawButton(7)) && (!Robot.lj07ButtonCheck)){
			slowModifier = 1;
		}
		if ((Robot.leftJoystick.getRawButton(8)) && (!Robot.lj08ButtonCheck)){
			slowModifier = 1.5;
		}
		if ((Robot.leftJoystick.getRawButton(9)) && (!Robot.lj09ButtonCheck)){
			slowModifier = 2;
		}
		if ((Robot.leftJoystick.getRawButton(10)) && (!Robot.lj10ButtonCheck)){
			slowModifier = 3;
		}
		Robot.lj07ButtonCheck = Robot.leftJoystick.getRawButton(7);
		Robot.lj08ButtonCheck = Robot.leftJoystick.getRawButton(8);
		Robot.lj09ButtonCheck = Robot.leftJoystick.getRawButton(9);
		Robot.lj10ButtonCheck = Robot.leftJoystick.getRawButton(10);
	}
}
