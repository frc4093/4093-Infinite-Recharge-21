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

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.subsystems.*;
import frc.robot.commands.*;

/**
 *
 */
public class GoTo extends CommandGroup {
   private double currentX;
   private double currentY;
   private double kSpacing = 30/12; //ft
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
    public GoTo(double x, double y) {
        
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
        currentX = Robot.currentX;
        currentY = Robot.currentY;
        double xLength = Math.abs(x-currentX)*kSpacing;
        double yLength = Math.abs(y-currentY)*kSpacing;
        double hyp = Math.sqrt(xLength+yLength);
        double angle = 0;
        double distance = createDistance(xLength,yLength,hyp);
        //this is the distance we need to go
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS
        addSequential(new TurnToGyroAngle(angle));
        addSequential(new driveForFeet(distance));
    } 
    public double createDistance(double xLength, double yLength,double hyp) {
        double distance;
        if (yLength == 0){
            distance = xLength;
        } else if (xLength == 0){
            distance = yLength;
        }else{
            distance = hyp;
        }
        return distance;
    }

    public double determineAngle(double xLength,double yLength,double hyp){
        double angle = 1/Math.sin(yLength/hyp);
        return 0;
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
    @Override
    protected boolean isFinished() {
        return false;
    }
    @Override
    protected void end() {

    }
    @Override
    protected void interrupted() {
    }
}
