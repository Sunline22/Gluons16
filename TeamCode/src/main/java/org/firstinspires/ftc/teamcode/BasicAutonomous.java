package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Sam on 16-Dec-16.
 */

public class BasicAutonomous extends LinearOpMode
{
    Hardware robot = new Hardware();
    private ElapsedTime stepTime = new ElapsedTime(), fullTime = new ElapsedTime();
    int Colour1, Colour2;

    public void runOpMode() throws InterruptedException {
        waitForStart();
        robot.frontLeftMotor.setPower(.9);
        robot.backLeftMotor.setPower(.9);
        robot.frontRightMotor.setPower(.9);
        robot.backRightMotor.setPower(.9);
        while (opModeIsActive() && (stepTime.seconds() < 3.0)) {
            idle();
        }
        robot.frontLeftMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);

    }

}
