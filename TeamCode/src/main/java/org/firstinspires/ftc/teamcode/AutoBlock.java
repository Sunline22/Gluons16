package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Sam on 18-Feb-17.
 */

public abstract class AutoBlock extends LinearOpMode {

    Hardware robot = new Hardware();
    final double TicktoInchConst = (2 / 3) * (1440 / (3 * Math.PI));

    /**
     * This method is intended to drive the robot at a power level indefinitely
     *
     * @param pow sets the desired power of all drive motors
     */
    public void drive(double pow) {
        setDrive(pow);
    }

    /**
     * Overrides drive
     * This method is intended to drive the robot until a set time
     *
     * @param pow  sets the desired power of all drive motors
     * @param time
     */
    public void drive(double pow, int time) throws InterruptedException {
        ElapsedTime runTime = new ElapsedTime();
        setDrive(pow);
        while (opModeIsActive() && runTime.seconds() < time)
            idle();
    }

    /**
     * This method is intended to drive the robot to a set position in inches
     *
     * @param toPos sets how long the encoder should run to in Inches
     */
    public void driveInches(double toPos) {
        setDrivePos((int) (TicktoInchConst * toPos));
    }

    /**
     * This method is intended to drive the robot to a set position in inches
     *
     * @param toPos    sets how long the encoder should run to in Inches
     * @param maxSpeed sets maximum speed the motor can move in encoder ticks per second
     */
    public void driveInches(double toPos, double maxSpeed) {

    }

    /**
     * This method will turn the robot around its center at a 40% power
     *
     * @param turnClockwise if true turns the robot clockwise else turns counter-clockwise
     */
    public void turn(boolean turnClockwise) {
        setDriveRight(((turnClockwise ? 1 : 0) * 2 - 1) * -.4);
        setDriveLeft(((turnClockwise ? 1 : 0) * 2 - 1) * -.4);
    }

    /**
     * This method will turn the robot around its center at a set power
     *
     * @param turnClockwise if true turns the robot clockwise else turns counter-clockwise
     * @param pow           sets drive power
     */
    public void turn(double pow, boolean turnClockwise) {
        setDriveRight(((turnClockwise ? 1 : 0) * 2 - 1) * -pow);
        setDriveLeft(((turnClockwise ? 1 : 0) * 2 - 1) * -pow);
    }

    /**
     * This method is intended to turn the robot at a power level indefinitely
     *
     * @param powLeft  sets the desired power of left drive motors
     * @param powRight sets the desired power of right drive motors
     */
    public void turn(double powLeft, double powRight) {
        setDriveRight(powRight);
        setDriveLeft(powLeft);
    }

    /**
     * This method is intended to turn the robot until a set time
     *
     * @param powLeft  sets the desired power of left drive motors
     * @param powRight sets the desired power of right drive motors
     * @param time     sets how long motors will run
     */
    public void turn(double powLeft, double powRight, int time) throws InterruptedException {
        ElapsedTime runTime = new ElapsedTime();
        setDriveRight(powRight);
        setDriveLeft(powLeft);
        while (opModeIsActive() && runTime.seconds() < time)
            idle();
        setDrive(0);
    }

    /**
     * This method will turn the robot around its center until it has reached a certain amount of degrees.
     * defaults 40% motor power
     *
     * @param degree sets how many degrees the robot will turn
     */
    public void turnDeg(int degree) throws InterruptedException {
        //CounterClockwise is +
        int currentHeading = robot.gyro.getIntegratedZValue(), targetHeading = currentHeading + degree;
        if (opModeIsActive() && currentHeading < targetHeading)
            while (currentHeading < targetHeading) {
                turn(false);
                idle();
            }
        else if (currentHeading > targetHeading) {
            while (currentHeading > targetHeading)
                turn(true);
            idle();
        }
        setDrive(0);
    }

    /**
     * This method will turn the robot around its center until it has reached a certain amount of degrees.
     *
     * @param degree sets how many degrees the robot will turn
     * @param pow    sets maxSpeed of turn in EncoderTicks per second
     */
    public void turnDeg(int degree, double pow) throws InterruptedException {
        //CounterClockwise is +
        int currentHeading = robot.gyro.getIntegratedZValue(), targetHeading = currentHeading + degree;
        if (opModeIsActive() && currentHeading < targetHeading)
            while (currentHeading < targetHeading) {
                turn(pow, false);
                idle();
            }
        else if (currentHeading > targetHeading) {
            while (currentHeading > targetHeading)
                turn(pow, true);
            idle();
        }
        setDrive(0);
    }

    public void turnDeg(int degree, double powL, double powR) throws InterruptedException {
        //CounterClockwise is +
        int currentHeading = robot.gyro.getIntegratedZValue();
        int targetHeading = currentHeading + degree;
        if (opModeIsActive() && currentHeading < targetHeading)
            while (currentHeading < targetHeading) {
                turn(powL, powR);
                idle();
            }
        else if (opModeIsActive() && currentHeading > targetHeading) {
            while (currentHeading > targetHeading) {
                turn(powL, powR);
                idle();
            }
        }
        setDrive(0);
    }

    /**
     * This method is intended to assist in loops. It will continue to return the same boolean value.
     *
     * @return true if the beacon is the set alliances color
     */
    public boolean isBlue() {
        if (robot.beaconSensor.blue() > robot.beaconSensor.red())
            return true;
        return false;
    }

    /**
     * This method is to be used when the robot is in front of a beacon attempting to score it
     * drives forward 2 inches then drives back 1.5 inches
     */
    public void push() throws InterruptedException {
        driveInches(2, .2);
        while (opModeIsActive() && robot.frontLeftMotor.isBusy())
            idle();
        driveInches(-1.5);
        while (opModeIsActive() && robot.frontLeftMotor.isBusy())
            idle();
    }

    /**
     * This method will start the wind up process of the robot
     *
     * @param state       desired state of the cannon
     * @param ticksPerSec the desired number of ticks per second for the robot to run at
     */
    public void toggleCannon(boolean state, int ticksPerSec) {
        robot.cannonMotor.setPower(.4);
        if (state) {
            robot.cannonMotor.setMaxSpeed(ticksPerSec);
        } else
            robot.cannonMotor.setMaxSpeed(0);
    }

    /**
     * private method sets all the drive motor's power to pow
     *
     * @param pow sets motor power
     */
    private void setDrive(double pow) {
        setDriveLeft(pow);
        setDriveRight(pow);
    }

    /**
     * private method sets the left  drive motor's power to pow
     *
     * @param pow sets motor power
     */
    private void setDriveLeft(double pow) {
        robot.frontLeftMotor.setPower(pow);
        robot.backLeftMotor.setPower(pow);
    }

    /**
     * private method sets the right drive motor's power to pow
     *
     * @param pow sets motor power
     */
    private void setDriveRight(double pow) {
        robot.frontRightMotor.setPower(pow);
        robot.backRightMotor.setPower(pow);
    }

    private void setDrivePos(int pos) {
        setDriveRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightMotor.setTargetPosition(robot.backRightMotor.getCurrentPosition() + pos);
        robot.backLeftMotor.setTargetPosition(robot.backLeftMotor.getCurrentPosition() + pos);
        robot.frontLeftMotor.setTargetPosition(robot.frontLeftMotor.getCurrentPosition() + pos);
        robot.frontRightMotor.setTargetPosition(robot.frontRightMotor.getCurrentPosition() + pos);
    }

    private void setDriveRunMode(DcMotor.RunMode runMode) {
        robot.backRightMotor.setMode(runMode);
        robot.backLeftMotor.setMode(runMode);
        robot.frontLeftMotor.setMode(runMode);
        robot.frontRightMotor.setMode(runMode);
    }

}