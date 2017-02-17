// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc982.SnoBotics2017V2;
import com.ctre.CANTalon.TalonControlMode;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static CANTalon driveSystemCANTalon1;
    public static CANTalon driveSystemCANTalon2;
    public static CANTalon driveSystemCANTalon4;
    public static CANTalon driveSystemCANTalon5;
    public static RobotDrive driveSystemRobotDrive4;
    public static CANTalon driveSystemCANTalon3;
    public static CANTalon driveSystemCANTalon6;
    public static DoubleSolenoid driveSystemShiftSolenoid;
    public static Encoder driveSystemLeftQuadratureEncoder;
    public static Encoder driveSystemRightQuadratureEncoder;
    public static DoubleSolenoid fuelDumperFuelDumperDoubleSolenoid;
    public static CANTalon ropeClimberCANTalon9;
    public static Encoder ropeClimberQuadratureEncoder1;
    public static PowerDistributionPanel healthMonitoringPowerDistributionPanel;
    public static AnalogInput healthMonitoringPressureSensorAnalogInput;
    public static DoubleSolenoid gearManipulatorPaddleSolenoid;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveSystemCANTalon1 = new CANTalon(1);
        LiveWindow.addActuator("Drive System", "CAN Talon 1", driveSystemCANTalon1);
        
        driveSystemCANTalon2 = new CANTalon(2);
        LiveWindow.addActuator("Drive System", "CAN Talon 2", driveSystemCANTalon2);
        
        driveSystemCANTalon4 = new CANTalon(4);
        LiveWindow.addActuator("Drive System", "CAN Talon 4", driveSystemCANTalon4);
        
        driveSystemCANTalon5 = new CANTalon(5);
        LiveWindow.addActuator("Drive System", "CAN Talon 5", driveSystemCANTalon5);
        
        driveSystemRobotDrive4 = new RobotDrive(driveSystemCANTalon1, driveSystemCANTalon2,
              driveSystemCANTalon4, driveSystemCANTalon5);
        
        driveSystemRobotDrive4.setSafetyEnabled(true);
        driveSystemRobotDrive4.setExpiration(0.1);
        driveSystemRobotDrive4.setSensitivity(0.5);
        driveSystemRobotDrive4.setMaxOutput(1.0);

        driveSystemRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveSystemRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        driveSystemCANTalon3 = new CANTalon(3);
        LiveWindow.addActuator("Drive System", "CAN Talon 3", driveSystemCANTalon3);
        
        driveSystemCANTalon6 = new CANTalon(6);
        LiveWindow.addActuator("Drive System", "CAN Talon 6", driveSystemCANTalon6);
        
        driveSystemShiftSolenoid = new DoubleSolenoid(0, 0, 1);
        LiveWindow.addActuator("Drive System", "Shift Solenoid", driveSystemShiftSolenoid);
        
        driveSystemLeftQuadratureEncoder = new Encoder(0, 1, true, EncodingType.k4X);
        LiveWindow.addSensor("Drive System", "Left Quadrature Encoder", driveSystemLeftQuadratureEncoder);
        driveSystemLeftQuadratureEncoder.setDistancePerPulse(1.0);
        driveSystemLeftQuadratureEncoder.setPIDSourceType(PIDSourceType.kRate);
        driveSystemRightQuadratureEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        LiveWindow.addSensor("Drive System", "Right Quadrature Encoder", driveSystemRightQuadratureEncoder);
        driveSystemRightQuadratureEncoder.setDistancePerPulse(1.0);
        driveSystemRightQuadratureEncoder.setPIDSourceType(PIDSourceType.kRate);
        fuelDumperFuelDumperDoubleSolenoid = new DoubleSolenoid(0, 2, 3);
        LiveWindow.addActuator("Fuel Dumper", "Fuel Dumper Double Solenoid", fuelDumperFuelDumperDoubleSolenoid);
        
        ropeClimberCANTalon9 = new CANTalon(9);
        LiveWindow.addActuator("Rope Climber", "CAN Talon 9", ropeClimberCANTalon9);
        
        ropeClimberQuadratureEncoder1 = new Encoder(4, 5, false, EncodingType.k4X);
        LiveWindow.addSensor("Rope Climber", "Quadrature Encoder 1", ropeClimberQuadratureEncoder1);
        ropeClimberQuadratureEncoder1.setDistancePerPulse(1.0);
        ropeClimberQuadratureEncoder1.setPIDSourceType(PIDSourceType.kRate);
        healthMonitoringPowerDistributionPanel = new PowerDistributionPanel(0);
        LiveWindow.addSensor("Health Monitoring", "PowerDistributionPanel", healthMonitoringPowerDistributionPanel);
        
        healthMonitoringPressureSensorAnalogInput = new AnalogInput(0);
        LiveWindow.addSensor("Health Monitoring", "PressureSensorAnalogInput", healthMonitoringPressureSensorAnalogInput);
        
        gearManipulatorPaddleSolenoid = new DoubleSolenoid(0, 4, 5);
        LiveWindow.addActuator("Gear Manipulator", "Paddle Solenoid", gearManipulatorPaddleSolenoid);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        

        /**
         *  Set up the Talons with address of 3 and 6 to following 1 and 4.
         */
        
        // should not be necessary: driveSystemRobotDrive4.setSafetyEnabled(false);
        
        CANTalon leftSlave = driveSystemCANTalon3;
        CANTalon rightSlave = driveSystemCANTalon6;
        
        leftSlave.changeControlMode(TalonControlMode.Follower);
        rightSlave.changeControlMode(TalonControlMode.Follower);
        leftSlave.set(1);
        rightSlave.set(4);
    }
}
