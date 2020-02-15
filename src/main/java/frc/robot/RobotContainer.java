/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AirCompressor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Feed;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.Rotate;
import net.bancino.robotics.swerveio.exception.SwerveException;
import net.bancino.robotics.swerveio.exception.SwerveRuntimeException;

import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.ElevatorWithJoystick;
import frc.robot.commands.IntakeWithJoystick;

import net.bancino.robotics.swerveio.gyro.NavXGyro;
import net.bancino.robotics.jlimelight.Limelight;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.PowerDistributionPanel;


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final XboxController xbox0 = new XboxController(0);
  private final XboxController xbox1 = new XboxController(1);

  /* The robot's subsystems and commands are defined here */
  private final AirCompressor compressor = new AirCompressor();
  private final DriveTrain drivetrain;
  private final Elevator elevator = new Elevator();
  private final Feed feed = new Feed();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();

  /* Additional global objects can go here. */
  private final PowerDistributionPanel pdp = new PowerDistributionPanel(Const.CAN.POWER_DISTRIBUTION_PANEL);
  private final NavXGyro gyro = new NavXGyro(SPI.Port.kMXP);
  private final Limelight limelight = new Limelight();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    /* Construct our subsystems here if they throw exceptions. */
    try {
      drivetrain = new DriveTrain(gyro);
    } catch (SwerveException e) {
      throw new SwerveRuntimeException(e);
    }

    // Configure the button bindings
    configureButtonBindings();
    configureCommands();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton xbox0A = new JoystickButton(xbox0, XboxController.Button.kA.value);
    xbox0A.whenPressed(new Rotate(drivetrain));
  }

  private void configureCommands() {
    intake.setDefaultCommand(new IntakeWithJoystick(intake, xbox0));
    drivetrain.setDefaultCommand(new DriveWithJoystick(drivetrain, gyro, xbox0));
    elevator.setDefaultCommand(new ElevatorWithJoystick(elevator, xbox1));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // This command will run in autonomous
    return null;
  }
}
