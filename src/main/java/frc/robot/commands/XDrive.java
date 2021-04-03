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
import edu.wpi.first.wpilibj.SlewRateLimiter;
// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 *
 */
public class XDrive extends Command {
    double lX;
    double lY;
    double rY;
    double rX;
    double turn;
    double drive;
    double triggerL;
    double triggerR;
    double highTrigger;
    double driveDir;
    double turnDir;
    double drivePlusTrigger;
    double turnPlusTrigger;
    double turnWithoutTrigger;
    boolean quickturn;
    boolean buttonL;
    boolean buttonR;
    boolean bothSticksTurnPlusReverseStick;
    SlewRateLimiter filter = new SlewRateLimiter(5);
    SlewRateLimiter filter2 = new SlewRateLimiter(5);
    SlewRateLimiter filter3 = new SlewRateLimiter(5);
    SlewRateLimiter filter4 = new SlewRateLimiter(5);
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public XDrive() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drive);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //switch direction
        lY = Robot.oi.getdriveGamepad().getRawAxis(1);
        lX = Robot.oi.getdriveGamepad().getRawAxis(0);
        rY = Robot.oi.getdriveGamepad().getRawAxis(5);
        rX = Robot.oi.getdriveGamepad().getRawAxis(4);

        //with filter
        // lY = filter.calculate(Robot.oi.getdriveGamepad().getRawAxis(1));
        // lX = filter2.calculate(Robot.oi.getdriveGamepad().getRawAxis(0));
        // rY = filter3.calculate(Robot.oi.getdriveGamepad().getRawAxis(5));
        // rX = filter4.calculate(Robot.oi.getdriveGamepad().getRawAxis(4));

        triggerL = Robot.oi.getdriveGamepad().getRawAxis(2);
        triggerR = Robot.oi.getdriveGamepad().getRawAxis(3);
        buttonL = Robot.oi.getdriveGamepad().getRawButton(4);
        buttonR = Robot.oi.getdriveGamepad().getRawButton(5);
        bothSticksTurnPlusReverseStick =  SmartDashboard.getBoolean("FancyRemote",true);
        
        if (triggerL>triggerR){
            highTrigger = triggerL;
        }else{
            highTrigger = triggerR;
        }
        if (bothSticksTurnPlusReverseStick){
            if (Math.abs(lY)>Math.abs(rY)){
                drive = lY;
            }else{
                drive = -rY;
            }
            if (Math.abs(lX)>Math.abs(rX)){
                turn = lX;
            }else{
                turn = rX;
            }
        }else{
            drive = lY;
            turn = rX;
        }
        if (drive>0.2){
            quickturn = false;
        }else{
            quickturn = true;
        }
        
            // quickturn =false;

        // if (Robot.oi.getdriveGamepad().getRawButton(5) || Robot.oi.getdriveGamepad().getRawButton(6)){
        //     Robot.drive.arcade(drive, turn*.6);
        //     //Robot.drive.drive.tankDrive(Robot.oi.getxBoxController().getRawAxis(1),Robot.oi.getxBoxController().getRawAxis(1));
        // }else{
        //     Robot.drive.arcade(drive*.5, turn*.3,false);
        // }
        //basically get -1 or 1
        driveDir = drive/Math.abs(drive);
        turnDir = turn/Math.abs(turn);
        drivePlusTrigger = drive*(.42+(.58*highTrigger));
        turnWithoutTrigger = turn*.4;
        turnPlusTrigger = turn*(.3+(.3*highTrigger));
        // if (drive<.1){
        //     turnWithoutTrigger=turn;
        // }
        //Robot.drive.tank(Robot.oi.getdriveGamepad().getRawAxis(1),Robot.oi.getdriveGamepad().getRawAxis(1));
        Robot.drive.arcade(drivePlusTrigger, turnPlusTrigger,false); //new more controllable drive(needs testing);
        // Robot.drive.curvature(drivePlusTrigger,turnWithoutTrigger,quickturn);
        Robot.dash.displayData("Trigger",highTrigger);
        Robot.dash.displayData("drive Dir", driveDir);
        Robot.dash.displayData("turn dir", turnDir);
        Robot.dash.displayData("DrivePlusTrigger", drivePlusTrigger);
        Robot.dash.displayData("TurnPlusTrigger", turnPlusTrigger);
        Robot.dash.displayData("normal drive", drive);
        Robot.dash.displayData("normal turn", turn);
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
