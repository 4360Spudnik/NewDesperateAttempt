/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
    


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {

    RobotDrive chassis = new RobotDrive(2, 3);
    Joystick leftStick = new Joystick(1);
    Joystick rightStick = new Joystick(2);
    Jaguar LTRD = new Jaguar(1);
    Solenoid pistonOneOut = new Solenoid(1);
    Solenoid pistonOneIn = new Solenoid(2);
    Relay compressorRelay = new Relay(2);
    Compressor mainCompressor = new Compressor(1, 1);
    boolean buttonValueRight;
    boolean buttonValueRightDown;

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        chassis.setSafetyEnabled(false);
        chassis.drive(1, 0);
        Timer.delay(0.15);
        chassis.drive(0.0, 0.0);
        Timer.delay(0.15);
        chassis.drive(-0.5, 0.0);
        Timer.delay(3.0);
        chassis.drive(0.0, 0.0);
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        chassis.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
            Timer.delay(0.01);
            buttonValueRight = rightStick.getRawButton(3);
            buttonValueRightDown = rightStick.getRawButton(2);
            //This controls the mechanism for picking up the ball.
            if (buttonValueRight) {
                LTRD.set(1.0);
            } else if (buttonValueRightDown) {
                LTRD.set(-1.0);
            } else {
                LTRD.set(0.0);
            }
            //This removes any speed modifiers from the tank drive.
            //The loop within is a duplicate of the above code.
            //This allows the rotation to remain usable.
            if (rightStick.getRawButton(1)) {
                chassis.tankDrive(leftStick, rightStick);
                if (buttonValueRight) {
                    LTRD.set(1.0);
                } else if (buttonValueRightDown) {
                    LTRD.set(-1.0);
                } else {
                    LTRD.set(0.0);
                }
            } else {
                chassis.tankDrive(leftStick.getY() * .8, rightStick.getY() * .8);
            }

        }

    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {

    }
}
