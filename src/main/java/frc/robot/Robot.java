// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.
//test
package frc.robot;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.networktables.NetworkTableInstance;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Limelight.CamMode;
import frc.robot.subsystems.Limelight.LEDMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();
    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
public static Drive drive;
public static Shooter shooter;
public static Intake intake;
public static Limelight limelight;
public static PDP pDP;
public static Climb climb;
public static Indexer indexer;
public static ControlPanel controlPanel;
public static lights lights;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static boolean start_Shooter_Running = false;
    public static double mode;
public static Dash dash = new Dash();
    public int ballCounter = 0; //were not quite here yet but this will be needed
    public static boolean isAuto;
    public static double roughShotCounter; //this just helps jam code
    public final static InterpolatingTree distanceToRPM = new InterpolatingTree(40);
    public final static double rpmDistanceOffset = 0;
    public static double currentX = 1; //for autonav
    public static double currentY = 3;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        //dash.displayData("shoot RPM setter", 0);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
drive = new Drive();
shooter = new Shooter();
intake = new Intake();
limelight = new Limelight();
pDP = new PDP();
climb = new Climb();
indexer = new Indexer();
controlPanel = new ControlPanel();
lights = new lights();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        distanceToRPM.put(0.0,0.0);
        // distanceToRPM.put(60.0,3400.0); //3640
        // distanceToRPM.put(94.0,2910.0);
        // distanceToRPM.put(137.0,2950.0);
        // distanceToRPM.put(238.0,3200.0);
        //Redone 3/3/2020

        // So these worked on somewhat ok condition not so accurate(shoots high) on beat up ones
        
        distanceToRPM.put(47.0,0.0); //cant shoot
        distanceToRPM.put(54.999,0.0+rpmDistanceOffset);
        distanceToRPM.put(55.0, 4400.0+rpmDistanceOffset); //safe mininum
        distanceToRPM.put(61.0,3400.0+rpmDistanceOffset); 
        distanceToRPM.put(83.0,2910.0+rpmDistanceOffset);
        distanceToRPM.put(120.0, 2920.0+rpmDistanceOffset);
        distanceToRPM.put(130.0, 2925.0+rpmDistanceOffset);
        distanceToRPM.put(140.0,2930.0+rpmDistanceOffset);
        distanceToRPM.put(178.0,3100.0+rpmDistanceOffset); //3152 3030
        distanceToRPM.put(203.0,3080.0+rpmDistanceOffset);
        distanceToRPM.put(234.0,3500+rpmDistanceOffset);
        distanceToRPM.put(244.0,3550.0+rpmDistanceOffset);
        distanceToRPM.put(286.0, 3640.0+rpmDistanceOffset);
        
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_RobotBuilder);

        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        chooser.addOption("Pass line 5 Point", new passAutoLine());
        chooser.addOption("Six Ball LT", new SixBallAutoLT());
        chooser.addOption("Target pos", new TargetPosAuto());
        chooser.addOption("Rendezvous", new SixBallAutoRendezvous());
        SmartDashboard.putData("Auto mode", chooser);
        Robot.dash.displayData("Pause", 0); //to be flexible for other teams we could maybe pause our autos at start
        Robot.climb.setLeftEnc(0);
        Robot.climb.setRightEnc(0);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(2);
        Robot.dash.displayData("set rpm", 0);
        Robot.dash.displayData("RPM", 0); //makes a cool graph we can get an idea of the adjustments needed for my "proportional" control
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){
        Robot.limelight.setCameraMode(CamMode.DRIVER);
        Robot.limelight.setLight(LEDMode.OFF);
        Robot.shooter.stop();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        Robot.limelight.setLight(LEDMode.OFF);
        Robot.limelight.setCameraMode(CamMode.DRIVER);
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();
        isAuto = true;
        drive.resetGyro();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        isAuto = false;
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
    }
    public static boolean getAuto(){
        return isAuto;
    }

    @Override
    public void testPeriodic(){
        //Scheduler.getInstance().run();
        // Robot.limelight.setCameraMode(CamMode.DRIVER);
        // Robot.limelight.setLight(LEDMode.ON);
    }
}
