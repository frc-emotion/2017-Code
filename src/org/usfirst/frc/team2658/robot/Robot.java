
package org.usfirst.frc.team2658.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

//2017 JAVA
public class Robot extends IterativeRobot {
	/*
	 * The followings are port numbers and motors for the wheels
	 */
	final static int LF_PORT = 1;
	final static int LB_PORT = 0;
	final static int RF_PORT = 4;
	final static int RB_PORT = 3 ;
	static VictorSP leftFront;
	static VictorSP leftBack;
	static VictorSP rightFront;
	static VictorSP rightBack;
	
	/*
	 * The following is the drive, the combination of all the wheel motors
	 */
	static RobotDrive drive;
	
	/*
	 * The followings are port numbers and joysticks/controllers
	 */
	final static int LEFT_JOYSTICK_PORT = 0;
	final static int RIGHT_JOYSTICK_PORT = 1;
	final static int XBOX_PORT = 2;
	final static int OPERATOR_CONTROLLER_PORT = 3;
	static Joystick leftJoystick; //Driver's
	static Joystick rightJoystick; //Driver's
	static Joystick xBox; //Driver's
	static Joystick operatorController; //Operator's
	
	/*
	 * The followings are button checks on the Driver's buttons
	 * to counteract the driver holding down buttons
	 */
	static boolean driverRBButtonCheck = false; //used to prevent an infinite loop of button presses
	static boolean lj01ButtonCheck = false;
	static boolean lj07ButtonCheck = false;
	static boolean lj08ButtonCheck = false;
	static boolean lj09ButtonCheck = false;
	static boolean lj10ButtonCheck = false;
	
	/*
	 * The followings are button checks on the Operator's buttons
	 * to counteract the driver holding down buttons
	 */
	static boolean operatorAButtonCheck = false;
	static boolean operatorBButtonCheck = false;
	static boolean operatorXButtonCheck = false;
	static boolean operatorYButtonCheck = false;
	static boolean operatorStartButtonCheck = false;
	static boolean operatorSelectButtonCheck = false;
	static boolean operatorRBButtonCheck = false;
	static boolean operatorLBButtonCheck = false;
	static boolean operatorLJButtonCheck = false;
	
	/*
	 * The followings are the port number and climb motor
	 */
	final int CLIMB_MOTOR_ADDRESS = 6;
	static Spark climbMotor;
	static int climbToggle = 0;
	
	final int SECOND_CLIMB_MOTOR_PORT = 7;
	static Spark secondClimbMotor;
	
	/*
	 * The followings are the port numbers and motors for the ball mechanism
	 */
	final static int BALL_LIFT_PORT = 8;
	//final static int BALL_SHAKER_PORT = 7;
	static Spark ballLift;
	//static Spark ballShaker;
	
	/*
	 * The followings are the port numbers and solenoid for the ball gate
	 */
	final static int OPEN_BALL_MECH_PORT = 2;
	final static int CLOSE_BALL_MECH_PORT = 3;
	static DoubleSolenoid ballGate;
	
	/*
	 * The followings are the port numbers and solenoid for the gear gate
	 */
	final static int OPEN_GEAR_MECH_PORT = 0;
	final static int CLOSE_GEAR_MECH_PORT = 1;
	final static int DEFAULT_GEAR_OPEN_CLOSE_TIME = 1;
	static DoubleSolenoid gearMech;
	
	
	/*
	 * The followings are choice selectors on the SmartDashboard for
	 * flipping axes on various joysticks/controllers
	 */
	//static SendableChooser<String> axisFlip1;
	//static SendableChooser<String> axisFlip2;
	static SendableChooser<String> ropeAxisFlip;
	static SendableChooser<String> secondRopeAxisFlip;
	
	/*
	 * The following is a choice selector on the SmartDashboard for
	 * what kind of drive the driver would like (Arcade/Tank)
	 */
	static SendableChooser<String> driveChoice;
	
	/*
	 * The following is a choice selector on the SmartDashboard for
	 * what kind of controls the driver would like (Singular Joystick (XY),
	 * Singular Joystick (XZ), Double Joystick, Controller)
	 */
	static SendableChooser<String> controlChoice;
	
	/*
	 * The following is a choice selector on the SmartDashboard for
	 * what autonomous to use
	 */
	static SendableChooser<String> autoChoice;
	static boolean autoDone = false;
	
	/*
	 * The following is a default constant for the drive power, the
	 * maximum power the wheel motors can go
	 */
	final static double DEFAULT_DRIVE_POW = 1.0;
	final static double DEFAULT_DRIVE_TURN_POW = 0.5;
	
	/*
	 * The following is a default constant for the drive exponent, the
	 * power the raw joystick value is raised to
	 * 
	 * y(x) = P(x^E)
	 * y = actual power on motors [-1.0, 1.0]
	 * x = joystick value read [-1.0, 1.0]
	 * P = drive power constant [0, 1.0]
	 * E = drive exponent constant [0, +inf]
	 */
	final static double DEFAULT_DRIVE_EXP = 1.0;
	
	/*
	 * The following is a default constant for the ball shake power, the
	 * power that the ball shake motor goes at
	 */
	final static double DEFAULT_BALL_SHAKE_POW = 0.1;
	
	/*
	 * The following is a default constant for the rope motor
	 */
	final static double DEFAULT_CLIMBER_POW = 1.0;
	final static double DEFAULT_CLIMB_UP_TIME = 15;
	final static double DEFAULT_CLIMB_DOWN_TIME = 30;
	
	/*
	 * The following is the gyro
	 */
	//static ADXRS450_Gyro gyro;
	//static double straightAngle = 0;;
	
	/*
	 * The following is the sonar
	 */
	//static AnalogInput sonar;
	
	/*
	 * The following are the encoders and their respective ports
	 */
	public static Encoder leftEncoder, rightEncoder;
	
	final static int ENCODER_PORT_L1 = 0;
	final static int ENCODER_PORT_L2 = 1;
	final static int ENCODER_PORT_R1 = 3;
	final static int ENCODER_PORT_R2 = 4;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	/*
    	 * Initialize all the wheel motors
    	 */
    	leftFront = new VictorSP(LF_PORT);
    	leftBack = new VictorSP(LB_PORT);
    	rightFront = new VictorSP(RF_PORT);
    	rightBack = new VictorSP(RB_PORT);
    	
    	/*
    	 * Initialize the drive train with all the wheel motors
    	 */
    	drive = new RobotDrive(leftFront, leftBack, rightFront, rightBack);
    	
    	/*
    	 * Initialize all the joysticks and controllers
    	 */
    	leftJoystick = new Joystick(LEFT_JOYSTICK_PORT);
    	rightJoystick = new Joystick(RIGHT_JOYSTICK_PORT);
    	xBox = new Joystick(XBOX_PORT);
    	operatorController = new Joystick(OPERATOR_CONTROLLER_PORT);
    	
    	/*
    	 * Initialize the climb motor
    	 */
    	climbMotor = new Spark(CLIMB_MOTOR_ADDRESS);
    	secondClimbMotor = new Spark(SECOND_CLIMB_MOTOR_PORT);
    	/*
    	 * Initialize Right and Left Encoder
    	 */
    	leftEncoder = new Encoder(ENCODER_PORT_L1, ENCODER_PORT_L2);
    	rightEncoder = new Encoder(ENCODER_PORT_R1, ENCODER_PORT_R2);
    	SmartDashboard.putNumber("Left Encoder Units", leftEncoder.getDistance());
    	SmartDashboard.putNumber("Right Encoder Units", rightEncoder.getDistance());
    	
    	/*
    	 * Initialize all the ball mechanism motors and solenoid
    	 */
    	ballLift = new Spark(BALL_LIFT_PORT);
    	//ballShaker = new Spark(BALL_SHAKER_PORT);
    	ballGate = new DoubleSolenoid(OPEN_BALL_MECH_PORT, CLOSE_BALL_MECH_PORT);
    	SmartDashboard.putString("Ball Gate Position", "CLOSE");
    	OperatorControls.closeBallGate();
    	
    	/*
    	 * Initialize the gear mechanism, make sure its closed to begin with
    	 */
    	gearMech = new DoubleSolenoid(OPEN_GEAR_MECH_PORT, CLOSE_GEAR_MECH_PORT);
    	SmartDashboard.putString("Gear Gate Position", "CLOSE");
    	OperatorControls.closeGearGate();
    	
    	/*
    	 * Initialize and input the drive selector and place on Smart Dashboard
    	 */
    	driveChoice = new SendableChooser<String>();
    	driveChoice.addDefault("Arcade Drive", "Arcade Drive");
    	driveChoice.addObject("Tank Drive", "Tank Drive");
    	SmartDashboard.putData("Drive Choice", driveChoice);
    	
    	/*
    	 * Initialize and input the Control selector and place on Smart Dashboard
    	 */
    	controlChoice = new SendableChooser<String>();
    	controlChoice.addObject("Singular Joystick (XY)", "Singular Joystick (XY)");
    	controlChoice.addDefault("Singular Joystick (XZ)", "Singular Joystick (XZ)");
    	controlChoice.addObject("Double Joystick", "Double Joystick");
    	controlChoice.addObject("Controller", "Controller");
    	controlChoice.addObject("Operator Controller", "Operator Controller");
    	SmartDashboard.putData("Control Choice", controlChoice);
    	
    	/*
    	 * Initialize and input all the axis flips and place on the Smart Dashboard
    	 */
    	/*axisFlip1 = new SendableChooser<String>();
    	axisFlip1.addDefault("(L) Normal", "(L) Normal");
    	axisFlip1.addObject("(L) Flip", "(L) Flip");
    	SmartDashboard.putData("Left Axis", axisFlip1);
    	
    	axisFlip2 = new SendableChooser<String>();
    	axisFlip2.addDefault("(R) Normal", "(R) Normal");
    	axisFlip2.addObject("(R) Flip", "(R) Flip");
    	SmartDashboard.putData("Right Axis", axisFlip2);*/
    	
    	ropeAxisFlip = new SendableChooser<String>();
    	ropeAxisFlip.addDefault("(Rope) Normal", "(Rope) Normal");
    	ropeAxisFlip.addObject("(Rope) Flip", "(Rope) Flip");
    	SmartDashboard.putData("Rope Axis", ropeAxisFlip);
    	
    	secondRopeAxisFlip = new SendableChooser<String>();
    	secondRopeAxisFlip.addDefault("(Second Rope Motor) Normal", "(Second Rope Motor) Normal");
    	secondRopeAxisFlip.addObject("(Second Rope Motor) Flip", "(Second Rope Motor) Flip");
    	SmartDashboard.putData("2nd Rope Axis", secondRopeAxisFlip);
    	/*
    	 * Initialize and input the auto choices
    	 */
    	autoChoice = new SendableChooser<String>();
    	autoChoice.addDefault("(Auto) Nothing", "(Auto) Nothing");
    	autoChoice.addObject("(Auto) Middle Lane", "(Auto) Middle Lane");
    	autoChoice.addObject("(Auto) Left Lane", "(Auto) Left Lane");
    	autoChoice.addObject("(Auto) Right Lane", "(Auto) Right Lane");
    	autoChoice.addObject("(Auto) Balls and Go on Blue", "(Auto) Balls and Go on Blue");
    	autoChoice.addObject("(Auto) Balls and Go on Red", "(Auto) Balls and Go on Red");
    	SmartDashboard.putData("Auto Choice", autoChoice);
    	
    	/*
    	 * Put a number for Driver to put in for Auto off side
    	 */
    	SmartDashboard.putNumber("(Auto) Distance from Side (Ft)", 0);
    	
    	/*
    	 * Initialize all the constants and place on the Smart Dashboard
    	 */
    	SmartDashboard.putNumber("Drive Power", DEFAULT_DRIVE_POW);
    	SmartDashboard.putNumber("Turn Power", DEFAULT_DRIVE_TURN_POW);
    	SmartDashboard.putNumber("Drive Exponent", DEFAULT_DRIVE_EXP);
    	//SmartDashboard.putNumber("Ball Shaker Power", DEFAULT_BALL_SHAKE_POW);
    	SmartDashboard.putNumber("Rope Motor Power", DEFAULT_CLIMBER_POW);
    	SmartDashboard.putNumber("Rope Climb Up Time (Seconds)", DEFAULT_CLIMB_UP_TIME);
    	SmartDashboard.putNumber("Rope Climb Down Time (Seconds)", DEFAULT_CLIMB_DOWN_TIME);
    	SmartDashboard.putNumber("Seconds for Gear Mech", DEFAULT_GEAR_OPEN_CLOSE_TIME);
    	
    	
    	/*
    	 * Intialize the gyro and place angle on Smart Dashboard
    	 */
    	//gyro = new ADXRS450_Gyro();
    	//gyro.calibrate();
    	//SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
    	
    	/*
    	 * Add text to notify if using drive straightener
    	 */
    	SmartDashboard.putString("Drive Straightener", "OFF");
    	
    	/*
    	 * Initialize the camera and place on Smart Dashboard
    	 */
    	CameraServer camera = CameraServer.getInstance();
    	camera.startAutomaticCapture();
    	camera.startAutomaticCapture().setResolution(320,240);
    	
    	/*
    	 * Initialize the sonar and place voltage on Smart Dashboard
    	 */
    	//sonar = new AnalogInput(0);
    	//SmartDashboard.putNumber("Sonar Voltage", sonar.getVoltage());
    	
    	SmartDashboard.putString("HELLO", "WORLD");
    	
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	if (!autoDone){
	    	if (autoChoice.getSelected().equals("(Auto) Nothing")){
	    		AutonomousChoices.doNothing();
	    	}
	    	if(autoChoice.getSelected().equals("(Auto) Middle Lane")){
	    		AutonomousChoices.gunMiddleGear();
	    	}
	    	if(autoChoice.getSelected().equals("(Auto) Left Lane")){
	    		AutonomousChoices.attackLeftGear();
	    	}
	    	if(autoChoice.getSelected().equals("(Auto) Right Lane")){
	    		AutonomousChoices.destroyRightGear();
	    	}
	    	if(autoChoice.getSelected().equals("(Auto) Balls and Go on Blue")){
	    		AutonomousChoices.dropBallsAndGoBlueSide();
	    	}
	    	if(autoChoice.getSelected().equals("(Auto) Balls and Go on Red")){
	    		AutonomousChoices.dropBallsAndGoRedSide();
	    	}
	    	autoDone = true;
    	}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//The following method is for the Driver's controls
    	DriverControls.driveRobot();
    	
    	//The following methods are for the sensors
    	//Sensors.workGyro();
    	//Sensors.workSonar();
    	Sensors.workEncoders();
    	
    	//The following methods are for the Operator's controls
    	OperatorControls.useGearMech();
    	OperatorControls.useRopeMech();
    	//OperatorControls.shakeBalls();
    	OperatorControls.useBallGate();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
