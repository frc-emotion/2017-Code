package org.usfirst.frc.team2658.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors {
	////////////////////////////
	//**********GYRO**********//
	////////////////////////////
	/*public static void workGyro(){
		SmartDashboard.putNumber("Gyro Angle", Robot.gyro.getAngle());	
		if((Robot.operatorController.getRawButton(7)) && (!Robot.operatorSelectButtonCheck)){
			Robot.gyro.reset();
		}
		Robot.operatorSelectButtonCheck = Robot.operatorController.getRawButton(7);
	}*/
	
	////////////////////////////
	//*********SONAR**********//
	////////////////////////////
	/*public static void workSonar(){
		SmartDashboard.putNumber("Sonar Voltage", Robot.sonar.getVoltage());
	}*/
	
	////////////////////////////
	//********ENCODER*********//
	////////////////////////////
	public static void workEncoders(){
		SmartDashboard.putNumber("Left Encoder Units", Robot.leftEncoder.getDistance());
		SmartDashboard.putNumber("Right Encoder Units", Robot.rightEncoder.getDistance());
		if((Robot.operatorController.getRawButton(6)) && (!Robot.operatorStartButtonCheck)){
			Robot.leftEncoder.reset();
			Robot.rightEncoder.reset();
		}
		Robot.operatorStartButtonCheck = Robot.operatorController.getRawButton(7);
	}
}
