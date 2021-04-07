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
import frc.robot.subsystems.*;

/**
 *
 */
public class bouncePath extends CommandGroup {


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
    public bouncePath() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS
        addSequential(new SetStartPoint(1.5, 3));
        addSequential(new GoToGroup(2.2, 3));
        addSequential(new GoToGroup(2.5, 2.5));
        addSequential(new GoToGroup(3, 1));
        addSequential(new GoToGroup(4.5, 5.5));
        addSequential(new GoToGroup(6, 5));
        addSequential(new GoToGroup(6, 1));
        // addSequential(new GoToGroup(6.25, 5));
        addSequential(new GoToGroup(6.25, 5));
        // addSequential(new GoToGroup(8, 5));
        addSequential(new GoToGroup(9, 5.5));
        addSequential(new GoToGroup(9.25, 0.5)); //moved everything -1 cause it seems to slip
        addSequential(new GoToGroup(9, 3));
        addSequential(new GoToGroup(11, 3));
    } 
}
