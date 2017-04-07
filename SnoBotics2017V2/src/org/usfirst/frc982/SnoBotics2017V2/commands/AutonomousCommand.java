// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc982.SnoBotics2017V2.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc982.SnoBotics2017V2.Robot;

/**
 *
 */
public class AutonomousCommand extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public AutonomousCommand() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveSystem);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    /**
     * Encoder logic:
     */
    
    public double leftEncoderValue;
    public double rightEncoderValue;
    
	// public static Timer autoTimer; // timer used by the Autonomous Command
    public static final double AngleCenterPoint  = 10.0;
    public static final double finalDistance     = 10.0;
    public static final double drive80Percent    =  0.80;
    public static final double drive60Percent    =  0.60;
    public static final double driveHalfSpeed    =  0.50;
    public static final double driveQuarterSpeed =  0.25;
    
    public static int counter = 0;
	public static enum AutoPhases {
		PAUSE_025_COUNT,
		TEST_USING_ENCODER,
		CROSS_THE_BASE_LINE,
		DEAD_RECKON_FORWARD,
		DEAD_RECKON_REVERSE,
		DEAD_RECKON_TURN_LEFT,
		DEAD_RECKON_TURN_RIGHT,
		DEAD_RECKON_TO_PEG,
		VISION_TURN,
		VISION_DRIVE_FORWARD,
		VISION_DRIVE_REVERSE,
		RELEASE_GEAR,
		WAIT_2_SECOND_AFTER_GEAR_RELEASE,
		BACK_AWAY_FROM_PEG,
		TERMINATION};
	AutoPhases phase = AutoPhases.DEAD_RECKON_FORWARD;
	NetworkTable table;
	
	/**
	 * Seems like the distanceTable belongs in the DriveSystem subsystem definition.
	 */
	
	public static int distanceTable[][] = new int [][] {
	/*   y    x  */
		{10, 4100},
		{12, 3800},
		{18, 2400},
		{20, 2000},
		{24, 1600},
		{30, 1150},
		{36,  900},
		{40,  750},
		{48,  600}
	};
	
	/**
	 * @param closeness
	 * @return distance
	 * 
	 * Uses linear interpolation of the table just above.
	 */
	
	public double distanceCalculation (double closeness) {
		double resultantDistance = -1;
		
		if (closeness <= 0) {
			return -1; // abort if range reported is <= 0
		}
		if (closeness > 4100) {
			resultantDistance = 10.0;
		}
		if (closeness < 600) {
			resultantDistance = 48.0;
		}
		
		/**
		 * Use a for loop to find the first value in the second index that is smaller that the
		 * closeness argument.
		 * 
		 * The two if statements above should guarentee that this loop will always find a value
		 * before the end of the array.
		 */
		
		for (int index = 1; index < 8; ++index) {
			if (closeness > distanceTable[index][1]) {
				
				/**
				 * So we have found a value in the array small than the closeness
				 * argument. I liberated this from the wikipedia article on Linear Interpolation.
				 */
				
				double x  = closeness;
				double y0 = distanceTable[index-1][0];
				double x0 = distanceTable[index-1][1];
				double y1 = distanceTable[index][0];
				double x1 = distanceTable[index][1];
				
				resultantDistance = y0 + (x - x0) * ((y1-y0)/(x1-x0));
			}
		}
		return resultantDistance;
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    	phase = AutoPhases.PAUSE_025_COUNT;
    	counter = 0;
    	
    	/**
    	 *  With these three statements commented out I was able to get
    	 *  and view the angle and closeness on the Dashboard.
    	 */
    	
    	//NetworkTable.setClientMode();
    	//NetworkTable.setIPAddress("raspberrypi.local");
    	//NetworkTable.initialize();
    	table = NetworkTable.getTable("navigation");
    	
    	leftEncoderValue = Robot.driveSystem.getLeftEncoderCount();
    	rightEncoderValue = Robot.driveSystem.getRightEncoderCount();
    }

    // Called repeatedly when this Command is scheduled to run
    /**
     *  This is simple state machine, currently used the states defined by
     *  enumeration type 'AutoPhases'. As things progress I want to use
     *  more meaningful names.
     *  
     *  The logic for using the vision to the goal, should include the
     *  following:
     *     - Stop when the target is x cm. away from the camera
     *     - If the angle is < -/- y don't turn
     *     - If the angle is > y turn left or right.
     *     - If the angle is < -y turn right or left.
     *     - If the distance is > x cm. move toward the target
     */
    
    protected void execute() {
    	
    	// First get the info from the camera
    	
    	double angle;
    	double closeness;
    	
		angle = table.getNumber("angle", -999);
		closeness = table.getNumber("closeness", -1);
		
		double distance = distanceCalculation (closeness);
		
		// table.putNumber("distance", distance);
		// table.putValue("phase", phase);
		
    	++counter; // timer wasn't working so I opted for a counter
    	
    	// should tickle the drive system every iteration
  
    	switch (phase) {
    	
    	// pause for 1/4 of a second to make sure the compressor is running

    	case PAUSE_025_COUNT:
    		Robot.driveSystem.autoDriveForward(0);
    		if (counter < 50) {
    			phase = AutoPhases.PAUSE_025_COUNT;
    		} else {
    			phase = AutoPhases.CROSS_THE_BASE_LINE;
    			// Robot.driveSystem.shiftToHigh();
    			// phase = AutoPhases.DEAD_RECKON_FORWARD;
    			// Robot.driveSystem.shiftToLow();
    			// phase = AutoPhases.TEST_USING_ENCODER;
    			Robot.driveSystem.shiftToHigh();
    			counter = 0;
    		}
    		break;
    	
    	// dead reckon from the starting location
    		
    	case TEST_USING_ENCODER:
    		Robot.driveSystem.autoDriveForward(driveHalfSpeed);
    		double averageEncoder = (Robot.driveSystem.getLeftEncoderCount() +
    				                 Robot.driveSystem.getRightEncoderCount())/ 2.0;
    		
    		if (averageEncoder > 1000) {
    			Robot.driveSystem.stop();
    			phase = AutoPhases.TERMINATION;
    			counter = 0;
    		}
    		break;
    		
    	case CROSS_THE_BASE_LINE:
    		Robot.driveSystem.autoDriveForward(drive80Percent);
    		if (counter > 125) {
    			Robot.driveSystem.stop();
    			phase = AutoPhases.TERMINATION;
    			counter = 0;
    		}
    		break;
    		
    	case DEAD_RECKON_FORWARD: // drive straight
    		Robot.driveSystem.autoDriveForward(driveHalfSpeed);
    		
    		/**
    		 * Each count is approximately 0.020 seconds
    		 */
    	
    		if (counter > 200) { // 4.0 seconds
    			phase = AutoPhases.DEAD_RECKON_TURN_LEFT;
    			counter = 0;
    		}
    		break;
    		
    	case DEAD_RECKON_REVERSE:
    		Robot.driveSystem.stop();
    		phase = AutoPhases.TERMINATION;
    		counter = 0;
    		break;
    		
    	// Turn left to face the peg
    		
    	case DEAD_RECKON_TURN_LEFT: // turn to goal
    		Robot.driveSystem.autoDriveTurnLeft(drive60Percent);
    		
    		// 3/17/17 1:56 PM was 55 = 1.1 seconds
    		//                 changing to 1.3 seconds, 65
    		// 3/17/17 9:30 PM changed to 1.2 seconds
    		
    		if (counter > 60) { // turn for 1.2 seconds
    			// phase = AutoPhases.VISION_TURN;
    			// phase = AutoPhases.TERMINATION;
    			// phase = AutoPhases.RELEASE_GEAR;
    			phase = AutoPhases.DEAD_RECKON_TO_PEG;
    			counter = 0;
    		}
    		break;
    		
    	case DEAD_RECKON_TURN_RIGHT: // turn to goal
    		Robot.driveSystem.autoDriveTurnRight(driveHalfSpeed);
    		
    		if (counter > 50) { // turn for 1 second
    			phase = AutoPhases.VISION_TURN;
    			counter = 0;
    		}
    		break;
    		
    	// Use the vision system to turn to the goal
    		
    	case VISION_TURN: // use vision to turn to goal
    		
    		// first need to check that the camera is giving valid looking data
    		
    		if (distance <= 0) { // if negative distance information is invalid
    			Robot.driveSystem.stop();
    			phase = AutoPhases.DEAD_RECKON_TO_PEG;
    			counter = 0;
    		} else if ((angle < AngleCenterPoint) && (angle > -AngleCenterPoint)) {
    			Robot.driveSystem.stop();
    			phase = AutoPhases.VISION_DRIVE_FORWARD;
    			counter = 0;
    		} else if (angle > AngleCenterPoint) {
    			Robot.driveSystem.autoDriveTurnLeft(driveQuarterSpeed);
    			phase = AutoPhases.VISION_TURN;
    		} else if (angle < -AngleCenterPoint) {
    			Robot.driveSystem.autoDriveTurnRight(driveQuarterSpeed);
    			phase = AutoPhases.VISION_TURN;
    		} else if (counter > 1000) {
    			Robot.driveSystem.stop();
    			phase = AutoPhases.VISION_DRIVE_FORWARD;
    			counter = 0;
    		}
    		break;
    		
    	case VISION_DRIVE_FORWARD: // drive toward the goal

    		// At each iteration check to make sure the camera is still working
    		
    		if (distance < -1) {
    			phase = AutoPhases.DEAD_RECKON_TO_PEG;
    			counter = 0;
    		} else if (distance < finalDistance) {
    			Robot.driveSystem.autoDriveForward(0);
    			phase = AutoPhases.RELEASE_GEAR;
    			counter = 0;
    		} else if ((angle > AngleCenterPoint) || (angle < -AngleCenterPoint)) {
    			Robot.driveSystem.stop();
    			phase = AutoPhases.VISION_TURN;
    			counter = 0;
     		} else if (counter < 500) { // give it 10 seconds to reach the goal
    			Robot.driveSystem.autoDriveForward(driveHalfSpeed);
    			phase = AutoPhases.VISION_DRIVE_FORWARD;
    		} else { // 10 secconds has expired back away from the peg
        		Robot.driveSystem.stop();
    			phase = AutoPhases.BACK_AWAY_FROM_PEG;
    			counter = 0;
    		}
    		break;
    		
    	case VISION_DRIVE_REVERSE:
    		Robot.driveSystem.stop();
    		phase = AutoPhases.TERMINATION;
    		counter = 0;
    		break;
    		
    	case RELEASE_GEAR:
    		Robot.driveSystem.stop();
    		Robot.gearManipulator.openPaddles();
    		phase = AutoPhases.WAIT_2_SECOND_AFTER_GEAR_RELEASE;
    		counter = 0;
    		break;
    		
    	case WAIT_2_SECOND_AFTER_GEAR_RELEASE:
    		Robot.driveSystem.stop();
    		
    		// 3/17/17 4:29 PM changed to 2 seconds pause
    		if (counter > 100) {
    			phase = AutoPhases.BACK_AWAY_FROM_PEG;
    			counter = 0;
    		}
    		break;
    		
    	case BACK_AWAY_FROM_PEG:
    		Robot.driveSystem.autoDriveReverse(driveHalfSpeed);
    		if (counter > 50) {
    			Robot.driveSystem.stop();
    			phase = AutoPhases.TERMINATION;
    			counter = 0;
    		}
    		break;
    		
    	case DEAD_RECKON_TO_PEG: // drive forward 3 seconds
    		Robot.driveSystem.autoDriveForward(driveHalfSpeed);
    		/**
    		 * Each count is approximately 0.020 seconds
    		 */
    		if (counter > 250) { // 50 is 1 seconds
    			phase = AutoPhases.RELEASE_GEAR;
    			counter = 0;
    		}
    		break;
    		
    	case TERMINATION:
    		Robot.driveSystem.stop();
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (phase == AutoPhases.TERMINATION);
     }

    // Called once after isFinished returns true
    
    protected void end() {
    	Robot.driveSystem.stop(); 
    	Robot.driveSystem.shiftToHigh();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    
    protected void interrupted() {
    	Robot.driveSystem.stop();
    	Robot.driveSystem.shiftToHigh();
     }
}
