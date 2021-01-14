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
import frc.robot.subsystems.Limelight.CamMode;
import frc.robot.subsystems.Limelight.LEDMode;

/**
 *
 */
public class SetRPMVision extends Command {
    double rpm;
    double currentSetRPM;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public SetRPMVision() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.limelight.setCameraMode(CamMode.VISION);
        Robot.limelight.setLight(LEDMode.ON);
        setTimeout(1);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // if (!Robot.limelight.targetInSight()){
        //     rpm = 3000;
        // }
        currentSetRPM = Robot.shooter.getSetRPM();
        //Robot.shooter.powerLevel = .2; //give my fake "PID" loop a bit of a head start
        if (Robot.limelight.targetInSight()){
            rpm =Robot.limelight.getLimelightRPM();
        }else{
            rpm = 2920; //10 ft line value so that if it fails simple to find
        }
        Robot.shooter.setRPM(rpm);
        
        // Robot.dash.displayData("setRPM",Robot.limelight.getLimelightRPM());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.limelight.setCameraMode(CamMode.DRIVER);
        Robot.limelight.setLight(LEDMode.OFF);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
