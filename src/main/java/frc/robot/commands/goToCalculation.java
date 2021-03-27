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
// import frc.robot.subsystems.*;
// import frc.robot.commands.*;
// import frc.robot.Dash.*;

/**
 *
 */
public class goToCalculation extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double currentX;
    private double currentY;
    private double kSpacing = 30.0/12.0; //ft
    private double distance;
    // private static double lastStep = 0;
    private double x;
    private double y;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public goToCalculation(double X, double Y) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
         x = X;
         y =Y;
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // lastStep = Robot.drive.getAngle360();
        currentX = Robot.currentX;
        currentY = Robot.currentY;
        double xOffsetFromRobot = (x-currentX)+.001; //if we think of robot at (0,0) then where is x.
        double yOffsetFromRobot = (y-currentY)+.001;//if we think of robot at (0,0) then where is y. 
        double hyp = Math.sqrt(Math.pow((xOffsetFromRobot*kSpacing),2)+Math.pow((yOffsetFromRobot*kSpacing),2));
        int q = determineQuadrant(xOffsetFromRobot, yOffsetFromRobot);
        distance = hyp;
        double angle = determineAngle(xOffsetFromRobot, yOffsetFromRobot, q);
        // System.out.println("GoTo angle is: " +angle);
        // System.out.println("distance is: "+ distance);
        // System.out.println("q is "+q);
        Robot.dash.displayData("GoTo Angle",angle);
        Robot.dash.displayData("distance",distance);
        Robot.currentX = x;
        Robot.currentY = y;
        Robot.angle = angle;
        Robot.distance = distance;
        Robot.dash.displayData("x",Robot.currentX);
        Robot.dash.displayData("y",Robot.currentY);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        
      //  end();
    }
    public double determineAngle(double xOffsetFromRobot,double yOffsetFromRobot, int q){
        double angle =0;
        switch (q) {
        case 1:
            angle = Math.atan(yOffsetFromRobot/xOffsetFromRobot)*(180/Math.PI);
        break;
        case 2:
            angle = 180 + Math.atan(yOffsetFromRobot/xOffsetFromRobot)*(180/Math.PI);
        break;
        case 3:
            angle = 180+Math.atan(yOffsetFromRobot/xOffsetFromRobot)*(180/Math.PI);
        break;
        case 4:
            angle = 360+Math.atan(yOffsetFromRobot/xOffsetFromRobot)*(180/Math.PI);
        break;
        
        }

        return angle;
    }
    public int determineQuadrant(double x, double y){
        int q;
        if (x>0 && y>0){
            q =1;
            
        }else if (x < 0 && y>0){
            q =2;
        }else if (x<0 && y<0){
            q = 3;
        }else{
            q =4;
        }
        return q;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // return false;
        return true;
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
