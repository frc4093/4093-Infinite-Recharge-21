// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
/**
 * self explanatory this can be a lot more advanced but this will do for now
 *
 */

public class driveForFeet extends Command {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_ft;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_speed;
    private double kP = .085;
    // private double output;
    private double error;
    private double driveVal;
    // double turnOffsetGyro;
    private double initGyroAngle;
    private double currentAngle;
    private double kPGyro = .01;
    private double turnPower;
    private boolean grabFromRobot;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public driveForFeet(double ft) {
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_ft = ft;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_speed = .5;
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drive);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    grabFromRobot = false;
    }
    public driveForFeet(double ft,double speed){
        m_ft = ft;
        m_speed = speed;
        
        grabFromRobot = false;
    }
    public driveForFeet(){
        m_ft = 0;
        m_speed = .92;
        grabFromRobot = true;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        if (grabFromRobot){
            m_ft = Robot.distance;
        }
        //Robot.drive.resetEnc();  
        Robot.drive.resetVirtualEnc();
        initGyroAngle = Robot.drive.getAngle();
        currentAngle = Robot.drive.getAngle() - initGyroAngle;
        // turnOffsetGyro = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        currentAngle = Robot.drive.getAngle() - initGyroAngle;
        error = Math.abs(m_ft) - Math.abs(Robot.drive.getFeetMovedFromVEnc());
        turnPower = -currentAngle*kPGyro; //should do the trick
        if (m_ft > 0){
            driveVal = -.15-kP*error;
            //Robot.drive.arcade(driveVal, 0,false);
            if (Math.abs(driveVal)>Math.abs(m_speed)){
                driveVal = -Math.abs(m_speed);
            }
            
        }
        if (m_ft < 0){
            driveVal = .15+kP*error;
            if (Math.abs(driveVal)>Math.abs(m_speed)){
                driveVal = Math.abs(m_speed);
            }
            //Robot.drive.arcade(driveVal, 0,false);
        }
        // Robot.dash.displayData("currentGyroFromDFF", turnOffsetGyro);
        // Robot.dash.displayData("Ft", Robot.drive.getFeetMovedFromVEnc());
        //for safe testing
        if (Math.abs(turnPower)>.5)
            turnPower = turnPower/Math.abs(turnPower)*.5; //took off .5 multiplier to test faster turning we need to be quicker 
        
        Robot.drive.arcade(driveVal, turnPower,false);
        // Robot.drive.pidDriveStraight(m_ft/Robot.drive.kDriveEncoderToFt);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished(){
        //doesnt matter what direction if we do it like this
        return (Math.abs(m_ft) <= Math.abs(Robot.drive.getFeetMovedFromVEnc()));
        // return (Robot.drive.getLeftPIDController().atSetpoint()); //idk
        // return false; //just testing
    // Called once after isFinished returns true
    }
    @Override
    protected void end() {
        Robot.drive.arcade(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
