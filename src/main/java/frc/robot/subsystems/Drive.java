// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.commands.XDrive;
import frc.robot.commands.tankDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.concurrent.atomic.DoubleAdder;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.util.Units;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 * Although there is no actual noticable problems with drive
 * We get output not updated enough because I likely coded drive
 * incorrectly for now safety is turned off*
 * 
 * 
 * *Error on autonomous not teleop
 */
public class Drive extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
    private int count;
    private static final double kGearRatio = 1/10.71;
    private static final int kSensorUnitsPerRotation = 2048;
    private static final double kWheelDiameter = 6;// 6" wheel
    private static final double kDriveEncoderToFt =((1.0/kSensorUnitsPerRotation)*(kGearRatio)*(kWheelDiameter*Math.PI)/12);//*.85; //1rotation/encoder ticks(2048)*gear ratio(1/10)*wheel diameter*PI/12 in
    private static ADIS16448_IMU imu = new ADIS16448_IMU();
    public static double virtualEncOffset;
    private static double xSpeed;
    private static double zRotation;
    private static boolean squaredInputs;
    private static double vAngle;
    private static double differenceAngle;
    private boolean quickTurn;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private WPI_TalonFX falconFL;
private WPI_TalonFX falconBL;
private SpeedControllerGroup leftSide;
private WPI_TalonFX falconFR;
private WPI_TalonFX falconBR;
private SpeedControllerGroup rightSide;
private DifferentialDrive drive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(28)); //TODO: check on wheel to wheel distance and update
    DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getHeading());

    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(.636, 2.35,.358); //ks kv ka

    PIDController leftPIDController = new PIDController(6.38, 0, 2.9); //TODO: GET from drive characterization + tune
    PIDController righPIDController = new PIDController(6.38, 0, 2.9); 

    Pose2d pose;
public Drive() {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
falconFL = new WPI_TalonFX(1);


        
falconBL = new WPI_TalonFX(4);


        


//Set SlaveSpeedControllers to Follow MasterSpeedController
falconBL.follow(falconFL);
        
        
        
falconFR = new WPI_TalonFX(3);


        
falconBR = new WPI_TalonFX(2);


        


//Set SlaveSpeedControllers to Follow MasterSpeedController
falconBR.follow(falconFR);
        
        
        
drive = new DifferentialDrive(falconFL, falconFR);
addChild("drive",drive);
drive.setSafetyEnabled(true);
drive.setExpiration(0.1);
drive.setMaxOutput(1.0);

        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    falconBL.setNeutralMode(NeutralMode.Brake);
    falconBR.setNeutralMode(NeutralMode.Brake);
    falconFL.setNeutralMode(NeutralMode.Brake);
    falconFR.setNeutralMode(NeutralMode.Brake);
    drive.setDeadband(.05); // Deadband
    // PIDController pid = new PIDController(kP, kI, kD);
    
}

@Override
public void initDefaultCommand() {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new XDrive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
}

public Rotation2d getHeading(){
    return Rotation2d.fromDegrees(-getAngle());
}
public DifferentialDriveWheelSpeeds getSpeeds(){
    return new DifferentialDriveWheelSpeeds(
        getMotor_RPM(falconFL)*kGearRatio*2*Math.PI*Units.inchesToMeters(3)/60, 
        getMotor_RPM(falconFR)*kGearRatio*2*Math.PI*Units.inchesToMeters(3)/60
    );
}
public SimpleMotorFeedforward getFeedForward(){
    return feedforward;
} 
public DifferentialDriveKinematics getKinematics(){
    return kinematics;
}
public void arcade(double xSpeed, double zRotation) {
    // drive.arcadeDrive(xSpeed, zRotation);
    Drive.xSpeed = xSpeed;
    Drive.zRotation = zRotation;
    Drive.squaredInputs = true;
}

public void arcade(double xSpeed, double zRotation, boolean squaredInputs) {
    // drive.arcadeDrive(xSpeed, zRotation,squaredInputs);
    Drive.xSpeed = xSpeed;
    Drive.zRotation = zRotation;
    Drive.squaredInputs = squaredInputs;
}

public void curvature(double xSpeed, double zRotation, boolean q) {
        quickTurn = q;
        Drive.xSpeed = xSpeed;
        Drive.zRotation = zRotation;

    }
    public void tank(double left, double right,boolean squaredInputs){
        drive.tankDrive(left, right,squaredInputs);
    }
    public ADIS16448_IMU getIMU(){
        return imu;
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        drive.arcadeDrive(xSpeed, zRotation,squaredInputs);
        pose = odometry.update(getHeading(), getDriveDistance(falconFL),getDriveDistance(falconFR));
        // drive.curvatureDrive(xSpeed, zRotation, quickTurn);
        // this should work
        // Robot.dash.displayData("RPM FL", getDriveRPM(falconFL));
        // Robot.dash.displayData("RPM FR", getDriveRPM(falconFR));
        // Robot.dash.displayData("RPM BL", getDriveRPM(falconBL));
        // Robot.dash.displayData("RPM BR", getDriveRPM(falconBR));
        // count++;
        // // For testing
        // Robot.dash.displayData("Drive left FPS", getDriveFPS(falconFL));
        // Robot.dash.displayData("Corrected val", getAngle360());
        Robot.dash.displayData("Gyro Value", getAngle());
        // Robot.dash.displayData("enc", getEnc());
        // Robot.dash.displayData("venc", getVirtualEnc());
        //why is there drift?
        Robot.dash.displayData("fake gyro",getFakeAngle());
        
        
    }

    public double getMotor_RPM(WPI_TalonFX motor) {
        return ((motor.getSelectedSensorVelocity() * 600) / kSensorUnitsPerRotation); // actual
    }

    public double getDriveRPM(WPI_TalonFX motor) {
        return getMotor_RPM(motor)*kGearRatio; // after gearbox
    }
    public double getDriveDistance(WPI_TalonFX motor){
        return Units.feetToMeters(motor.getSelectedSensorPosition()*kDriveEncoderToFt);// this is prob lazy
    }
    public Pose2d getPose(){
        return pose;
    }
    public void setOutput(double leftVolts,double rightVolts){
        falconFL.set(leftVolts/12);
        falconFR.set(rightVolts/12);
    }
    public PIDController getLeftPIDController(){
        return leftPIDController;
    }
    public PIDController getRightPIDController(){
        return righPIDController;
    }
    // lets get feet per second
    public double getDriveFPS(TalonFX motor){
        return Math.abs((motor.getSelectedSensorVelocity()*10)*kDriveEncoderToFt);
    }

    public void calGyro(){
        imu.calibrate();
    }
    public void resetGyro(){
        imu.reset();
    }

    public double getAngle(){
        return imu.getAngle();
    }
    public void setFakeAngle(double a){
        differenceAngle = a-getAngle();
        
    }
    public double getFakeAngle(){
        return getAngle() + differenceAngle;
    }
    
    public double getAngle360(){
        
        return convertTo360(imu.getAngle());
    }
    public double convertTo360(double a){
        double step1 = a%360;
        double step2;
        if (step1<0){
            step2 = 360+step1; //subtracting value from 360(its negative so technically adding)
        }else{
            step2 = step1;
        }
        return step2; 
    }
    // public double convertTo360(double a){
    //     double angle =a;
    //     double b = angle/360;
    //     if (angle> 0){
    //         while (b>1){
    //             angle -=360;
    //         }
    //     }else if (angle<0){
    //         while (b<1){
    //             angle -=360;
    //         } 
    //     }
        
    //     return angle; 
    // }


    //sort of rough methods for autonomous use
    public void resetEnc(){
        falconFL.setSelectedSensorPosition(0);
    }
    public double getEnc(){
         return falconFL.getSelectedSensorPosition();
    }
    //resetEnc() doesnt always work(unsure why)
    // simple solution was to create a virtual encoder
    public void resetVirtualEnc(){
        virtualEncOffset = falconFL.getSelectedSensorPosition();
    }
    public double getVirtualEnc(){
        return getEnc()-virtualEncOffset;
    }
    public double getFeetMovedFromVEnc(){
        return getVirtualEnc()*kDriveEncoderToFt;
    }
    public double getFeetMovedFromEnc(){
        return getEnc()*kDriveEncoderToFt;
    }
    // public void pidDriveStraight(double setPoint){
    //     falconFR.set(pid.calculate(getVirtualEnc(),setPoint));
    //     falconFL.set(pid.calculate(getVirtualEnc(),setPoint));
    // }

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

// Put methods for controlling this subsystem
// here. Call these from Commands.

}
