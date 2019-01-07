package org.usfirst.frc.team2658.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousChoices {
	
	final static double ENCODERS_PER_FT = 430;
	final static double ALLIANCE_WALL_FT = 253 / 12;
	final static double MAX_HORIZONTAL_DISTANCE = (131.925 * Math.tan(Math.PI / 3)) / 12;
	final static double MAX_DIAGONAL_DISTANCE = (131.925 / Math.cos(Math.PI / 3)) / 12;
	static Timer timer = new Timer();
	static double forwardDistance = convertFtToEncoderUnits((MAX_HORIZONTAL_DISTANCE - (ALLIANCE_WALL_FT / 2) - SmartDashboard.getNumber("(Auto) Distance from Side (Ft)", 0)) * Math.tan(Math.PI / 6)) - convertFtToEncoderUnits(38/12);
	static double diagonalDistance = convertFtToEncoderUnits(131.925 * Math.cos(Math.PI / 3) - ((MAX_HORIZONTAL_DISTANCE - (ALLIANCE_WALL_FT / 2) - SmartDashboard.getNumber("(Auto) Distance from Side (Ft)", 0)) / Math.cos(Math.PI / 3))) - convertFtToEncoderUnits(38/12);

	public static void doNothing(){
		//Just that, do exactly nothing for auto
	}
	
	public static void gunMiddleGear(){
		/* 
		 * -Make gear "arm" level with the airship
		 * -Go forward 7 feet 
		 */
		OperatorControls.closeGearGate();
		resetEncodersAndTimers();
		moveBot(convertFtToEncoderUnits(114.3 / 12), "Forward", 0.8);
	}
	
	public static void attackLeftGear(){
		/*(Go forward to pass the baseline for free points)
		 * -Make gear "arm" level with the airship
		 * -Go forward z distance
		 * -Rotate 60 degrees
		 * -Go forward x distance
		 * 
		 * sideDistance = distance from left side of arena
		 *
		 */
		OperatorControls.closeGearGate();
		resetEncodersAndTimers();
		moveBot(forwardDistance, "Forward", 0.8);
		
		resetEncodersAndTimers();
		moveBot(convertFtToEncoderUnits((21.5 * Math.PI) / 72), "Right ", 0.6);
		
		resetEncodersAndTimers();
		moveBot(diagonalDistance, "Forward", 0.8);
	}
	public static void destroyRightGear(){
		/*(Go forward to pass the baseline for free points)
		 * -Make gear "arm" level with the airship
		 * -Go forward z distance
		 * -Rotate -60 degrees
		 * -Go forward x distance
		 * 
		 * sideDistance = distance from right side of arena
		 *
		 */
		OperatorControls.closeGearGate();
		resetEncodersAndTimers();
		moveBot(forwardDistance, "Forward", 0.8);
		
		resetEncodersAndTimers();
		moveBot(convertFtToEncoderUnits((21.5 * Math.PI) / 72), "Left", 0.6);
		
		resetEncodersAndTimers();
		moveBot(diagonalDistance, "Forward", 0.8);
	}
	
	public static void dropBallsAndGoBlueSide(){
		resetEncodersAndTimers();
		OperatorControls.openBallGate();
		while (timer.get() < 2.0){
			
		}
		
		resetEncodersAndTimers();
		while ((Math.abs(Robot.rightEncoder.get()) < convertFtToEncoderUnits(7)) && (timer.get() < 5)){
			Robot.drive.arcadeDrive(0.7, 0.2);
		}
		
		/*
		resetEncodersAndTimers();
		while ((Robot.rightEncoder.getDistance() < convertFtToEncoderUnits((43 * Math.PI) / 96) * -1) && (timer.get() < 2.0)){
			Robot.drive.arcadeDrive(0, 0.6 * ((convertFtToEncoderUnits((43 * Math.PI) / 96) + Robot.rightEncoder.getDistance()) / convertFtToEncoderUnits((43 * Math.PI) / 96)));
		}
		
		resetEncodersAndTimers();
		while ((Robot.rightEncoder.getDistance() > -1 * convertFtToEncoderUnits(7)) && (timer.get() < 3)){
			Robot.drive.arcadeDrive(0.8 * ((convertFtToEncoderUnits(7) + Robot.rightEncoder.getDistance()) / convertFtToEncoderUnits(7)), 0);
		}*/
		OperatorControls.closeBallGate();
	}
	
	public static void dropBallsAndGoRedSide(){
		resetEncodersAndTimers();
		OperatorControls.openBallGate();
		while (timer.get() < 2.0){
			
		}
		
		resetEncodersAndTimers();
		while ((Math.abs(Robot.rightEncoder.get()) < convertFtToEncoderUnits(7)) && (timer.get() < 5)){
			Robot.drive.arcadeDrive(0.7, -0.2);
		}
		
		/*
		resetEncodersAndTimers();
		while ((Robot.rightEncoder.getDistance() < convertFtToEncoderUnits((43 * Math.PI) / 96) * -1) && (timer.get() < 2.0)){
			Robot.drive.arcadeDrive(0, 0.6 * ((convertFtToEncoderUnits((43 * Math.PI) / 96) + Robot.rightEncoder.getDistance()) / convertFtToEncoderUnits((43 * Math.PI) / 96)));
		}
		
		resetEncodersAndTimers();
		while ((Robot.rightEncoder.getDistance() > -1 * convertFtToEncoderUnits(7)) && (timer.get() < 3)){
			Robot.drive.arcadeDrive(0.8 * ((convertFtToEncoderUnits(7) + Robot.rightEncoder.getDistance()) / convertFtToEncoderUnits(7)), 0);
		}*/
		OperatorControls.closeBallGate();
	}
	
	public static double convertFtToEncoderUnits(double ft){
		double encoderUnits = ft * ENCODERS_PER_FT;
		return encoderUnits;
	}
	
	public static void resetEncodersAndTimers(){
		Robot.leftEncoder.reset();
		Robot.rightEncoder.reset();
		//Robot.gyro.reset();
		timer.stop();
		timer.reset();
		timer.start();
	}
	
	public static void moveBot(double distance, String direction, double startPower){
		if (direction.equals("Forward")){
			while ((Math.abs(Robot.rightEncoder.getDistance()) < distance) && (timer.get() < 3.0)){
				Robot.drive.arcadeDrive(startPower * ((distance - Math.abs(Robot.rightEncoder.getDistance())) / distance), 0);
			}
		}
		if (direction.equals("Backward")){
			while ((Math.abs(Robot.rightEncoder.getDistance()) < distance) && (timer.get() < 3.0)){
				Robot.drive.arcadeDrive(-1 * startPower * ((distance - Math.abs(Robot.rightEncoder.getDistance())) / distance), 0);
			}
		}
		if (direction.equals("Left")){
			while ((Math.abs(Robot.rightEncoder.getDistance()) < distance) && (timer.get() < 3.0)){
				Robot.drive.arcadeDrive(0, startPower * ((distance - Math.abs(Robot.rightEncoder.getDistance())) / distance));
			}
		}
		if (direction.equals("Right")){
			while ((Math.abs(Robot.rightEncoder.getDistance()) < distance) && (timer.get() < 3.0)){
				Robot.drive.arcadeDrive(0, -1 * startPower * ((distance - Math.abs(Robot.rightEncoder.getDistance())) / distance));
			}
		}
	}
}
