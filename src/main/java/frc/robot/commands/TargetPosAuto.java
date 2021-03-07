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
// import frc.robot.Robot;
// import frc.robot.subsystems.*;

/**
 *
 */
public class TargetPosAuto extends CommandGroup {


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
    public TargetPosAuto() {

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
        //addSequential(new PauseFor(Robot.dash.readNumber("Pause")));
        //kind of a work around to shoot 3 for rally until I figure out a better way
        addSequential(new PrepareBallForShoot(false));
        addParallel(new SetRPMVision());
        addParallel(new Start_Shooter(true));
        addSequential(new AutoAlign());
        addSequential(new WaitForShooterSpeed());
        addSequential(new AutomatedShoot());
        addSequential(new WaitForShooterSpeed());
        addSequential(new AutomatedShoot());
        addSequential(new WaitForShooterSpeed());
        addSequential(new AutomatedShoot());
        addSequential(new TurnToGyroAngle(0));
        addParallel(new StopShooter());
        addSequential(new passAutoLine());
       //update 3/11/2020 we could afford to 
       //slow down and if it increases chances of getting in inner port
       //

        

    } 
}
