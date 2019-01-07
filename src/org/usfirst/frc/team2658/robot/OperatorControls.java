package org.usfirst.frc.team2658.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OperatorControls {
	static Timer timer = new Timer();
	static Timer ropeTimer = new Timer();
	static boolean ropeTime = false;
	////////////////////////////
	//*****GEAR MECHANISM*****//
	////////////////////////////
	public static void useGearMech(){
		if ((Robot.operatorController.getRawButton(1)) && (!Robot.operatorAButtonCheck)){
			openGearGate();
		}
		if ((Robot.operatorController.getRawButton(2)) && (!Robot.operatorBButtonCheck)){
			closeGearGate();
		}
		Robot.operatorAButtonCheck = Robot.operatorController.getRawButton(1);
		Robot.operatorBButtonCheck = Robot.operatorController.getRawButton(2);
		
		if ((Robot.operatorController.getRawButton(3)) && (!Robot.operatorXButtonCheck)){
			openGearGate();
		}
		if ((!Robot.operatorController.getRawButton(3)) && (Robot.operatorXButtonCheck)){
			closeGearGate();
		}
		Robot.operatorXButtonCheck = Robot.operatorController.getRawButton(3);
		
		if ((Robot.operatorController.getRawButton(6)) && (!Robot.operatorRBButtonCheck) && (timer.get() == 0)){
			openGearGate();
			timer.reset();
			timer.start();
		}
		if (timer.get() >= SmartDashboard.getNumber("Seconds for Gear Mech", Robot.DEFAULT_GEAR_OPEN_CLOSE_TIME)){
			closeGearGate();
			timer.stop();
			timer.reset();
		}
		
		Robot.operatorRBButtonCheck = Robot.operatorController.getRawButton(6);
		
	}
	
	public static void openGearGate(){
		Robot.gearMech.set(DoubleSolenoid.Value.kForward);
		SmartDashboard.putString("Gear Gate Position", "OPEN");
	}
	
	public static void closeGearGate(){
		Robot.gearMech.set(DoubleSolenoid.Value.kReverse);
		SmartDashboard.putString("Gear Gate Position", "CLOSE");
	}
	
	////////////////////////////
	//*****ROPE MECHANISM*****//
	////////////////////////////
	public static void useRopeMech(){
		if ((SmartDashboard.getNumber("Rope Motor Power", 1.0) < 0) ||
				(SmartDashboard.getNumber("Rope Motor Power", 1.0) > 1.0)){
			SmartDashboard.putNumber("Rope Motor Power", Robot.DEFAULT_CLIMBER_POW);
		}
		
		if (Robot.climbToggle == 0){
			if (Robot.ropeAxisFlip.getSelected().equals("(Rope) Normal")){
				Robot.climbMotor.set(SmartDashboard.getNumber("Rope Motor Power", Robot.DEFAULT_CLIMBER_POW) * ((Robot.operatorController.getRawAxis(2) * -1) + (Robot.operatorController.getRawAxis(3))));
			}
			else if (Robot.ropeAxisFlip.getSelected().equals("(Rope) Flip")){
				Robot.climbMotor.set(SmartDashboard.getNumber("Rope Motor Power", Robot.DEFAULT_CLIMBER_POW) * ((Robot.operatorController.getRawAxis(3) * -1) + (Robot.operatorController.getRawAxis(2))));
			}
			
			//Second Rope Motor
			/*if (Robot.secondRopeAxisFlip.getSelected().equals("(Second Rope Motor) Normal")){
				if (Robot.operatorController.getRawAxis(1) < 0){
					Robot.secondClimbMotor.set(SmartDashboard.getNumber("Rope Motor Power", Robot.DEFAULT_CLIMBER_POW) * Robot.operatorController.getRawAxis(1));
					
				} else {
					Robot.secondClimbMotor.set(SmartDashboard.getNumber("Rope Motor Power", Robot.DEFAULT_CLIMBER_POW) * Robot.operatorController.getRawAxis(1) * -1);
					
				}
			}
			else if (Robot.secondRopeAxisFlip.getSelected().equals("(Second Rope Motor) Flip")){
				if (Robot.operatorController.getRawAxis(1) < 0){
					Robot.secondClimbMotor.set(SmartDashboard.getNumber("Rope Motor Power", Robot.DEFAULT_CLIMBER_POW) * Robot.operatorController.getRawAxis(1) * -1);
					
				} else {
					Robot.secondClimbMotor.set(SmartDashboard.getNumber("Rope Motor Power", Robot.DEFAULT_CLIMBER_POW) * Robot.operatorController.getRawAxis(1));
					
				}
			}*/
		}
		else if (Robot.climbToggle == 1){
			if (Robot.ropeAxisFlip.getSelected().equals("(Rope) Normal")) Robot.climbMotor.set(-1);
			if (Robot.secondRopeAxisFlip.getSelected().equals("(Second Rope Motor) Normal")) Robot.secondClimbMotor.set(-1);
			if (Robot.ropeAxisFlip.getSelected().equals("(Rope) Flip")) Robot.climbMotor.set(1);
			if (Robot.secondRopeAxisFlip.getSelected().equals("(Second Rope Motor) Flip")) Robot.secondClimbMotor.set(1);
		}
		else if (Robot.climbToggle == 2){
			if ((!ropeTime) && (ropeTimer.get() > SmartDashboard.getNumber("Rope Climb Down Time (Seconds)", Robot.DEFAULT_CLIMB_DOWN_TIME))){
				ropeTimer.stop();
				ropeTimer.reset();
				ropeTime = true;
				ropeTimer.start();
			} else if ((ropeTime) && (ropeTimer.get() > SmartDashboard.getNumber("Rope Climb Up Time (Seconds)", Robot.DEFAULT_CLIMB_UP_TIME))){
				ropeTimer.stop();
				ropeTimer.reset();
				ropeTime = false;
				ropeTimer.start();
			}
			
			if ((ropeTimer.get() <= SmartDashboard.getNumber("Rope Climb Up Time (Seconds)", Robot.DEFAULT_CLIMB_UP_TIME)) && (ropeTime)){
				if (Robot.ropeAxisFlip.getSelected().equals("(Rope) Normal")) Robot.climbMotor.set(-1);
				if (Robot.secondRopeAxisFlip.getSelected().equals("(Second Rope Motor) Normal")) Robot.secondClimbMotor.set(-1);
				if (Robot.ropeAxisFlip.getSelected().equals("(Rope) Flip")) Robot.climbMotor.set(1);
				if (Robot.secondRopeAxisFlip.getSelected().equals("(Second Rope Motor) Flip")) Robot.secondClimbMotor.set(1);
			}
			
			if ((ropeTimer.get() <= SmartDashboard.getNumber("Rope Climb Down Time (Seconds)", Robot.DEFAULT_CLIMB_DOWN_TIME)) && (!ropeTime)){
				if ((ropeTimer.get() <= SmartDashboard.getNumber("Rope Climb Up Time (Seconds)", Robot.DEFAULT_CLIMB_UP_TIME))){
					if (Robot.ropeAxisFlip.getSelected().equals("(Rope) Normal")) Robot.climbMotor.set(0.7);
					if (Robot.secondRopeAxisFlip.getSelected().equals("(Second Rope Motor) Normal")) Robot.secondClimbMotor.set(0.7);
					if (Robot.ropeAxisFlip.getSelected().equals("(Rope) Flip")) Robot.climbMotor.set(-0.7);
					if (Robot.secondRopeAxisFlip.getSelected().equals("(Second Rope Motor) Flip")) Robot.secondClimbMotor.set(-0.7);
				}
				Robot.climbMotor.set(0);
				Robot.secondClimbMotor.set(0);
			}
		}
		
		if ((Robot.operatorController.getRawButton(9)) && (!Robot.operatorLJButtonCheck)){
			if (Robot.climbToggle != 1){
				Robot.climbToggle = 1;
				ropeTimer.stop();
				ropeTimer.reset();
				ropeTime = false;
			} else{
				Robot.climbToggle = 0;
				ropeTimer.stop();
				ropeTimer.reset();
				ropeTime = false;
			}
		}
		Robot.operatorLJButtonCheck = Robot.operatorController.getRawButton(9);
		
		if ((Robot.operatorController.getRawButton(5)) && (!Robot.operatorLBButtonCheck)){
			if (Robot.climbToggle != 2){
				Robot.climbToggle = 2;
				ropeTimer.reset();
				ropeTimer.start();
				ropeTime = true;
			}else {
				Robot.climbToggle = 0;
				ropeTimer.stop();
				ropeTimer.reset();
				ropeTime = false;
			}
		}
		Robot.operatorLBButtonCheck = Robot.operatorController.getRawButton(5);
		
	}
	
	////////////////////////////
	//*****BALL MECHANISM*****//
	////////////////////////////
	/*public static void shakeBalls(){
		if ((SmartDashboard.getNumber("Ball Shaker Power", 1.0) < 0) ||
				(SmartDashboard.getNumber("Ball Shaker Power", 1.0) > 1.0)){
			SmartDashboard.putNumber("Ball Shaker Power", Robot.DEFAULT_BALL_SHAKE_POW);
		}
		
		if (Robot.operatorController.getRawButton(6)){
			Robot.ballShaker.set(SmartDashboard.getNumber("Ball Shaker Power", 0.1));
		}
	}*/
	
	public static void useBallGate(){
		if ((Robot.operatorController.getRawButton(4)) && (!Robot.operatorYButtonCheck)){
			if (Robot.ballGate.get() == DoubleSolenoid.Value.kReverse){
				openBallGate();
			}
			else{
				closeBallGate();
			}
		}
		Robot.operatorYButtonCheck = Robot.operatorController.getRawButton(4);
	}
	
	public static void openBallGate(){
		Robot.ballGate.set(DoubleSolenoid.Value.kForward);
		SmartDashboard.putString("Ball Gate Position", "OPEN");
	}
	
	public static void closeBallGate(){
		Robot.ballGate.set(DoubleSolenoid.Value.kReverse);
		SmartDashboard.putString("Ball Gate Position", "CLOSE");
	}

}