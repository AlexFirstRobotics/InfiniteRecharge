/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.joystick;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Const;
import frc.robot.subsystems.Shooter;

public class ShooterWithJoystick extends CommandBase {

    private Shooter shooter;
    private XboxController.Button shooterButton;
    private XboxController.Axis hoodAxis;
    private XboxController xbox;

    private double positionRef = 0;

    /** Creates a new ShooterWithJoystick, of course. */
    public ShooterWithJoystick(Shooter shooter, XboxController xbox, XboxController.Button shooterButton, XboxController.Axis hoodAxis) {
        this.shooter = shooter;
        this.xbox = xbox;
        this.shooterButton = shooterButton;
        this.hoodAxis = hoodAxis;
        addRequirements(shooter);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double speedRef = -xbox.getRawAxis(hoodAxis.value);
        positionRef =  positionRef + (speedRef * Const.Shooter.HOOD_POSITION_INCREMENT);
        if (positionRef > Const.Shooter.MAX_HOOD_POSITION) {
            positionRef = Const.Shooter.MAX_HOOD_POSITION;
        } else if (positionRef < Const.Shooter.MIN_HOOD_POSITION) {
            positionRef = Const.Shooter.MIN_HOOD_POSITION;
        }
        shooter.setHoodPosition((int) positionRef);
        if (xbox.getRawButton(shooterButton.value)) {
            shooter.run();   
        } else {
            shooter.stop();
        }     
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}