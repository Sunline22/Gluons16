package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Sam on 16-Dec-16.
 */


@Autonomous(name="Basic Autonomous",group="Autonomous")
public class BasicAutonomous extends LinearOpMode
{
    Hardware robot = new Hardware();
    private ElapsedTime runTime = new ElapsedTime();

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        telemetry.addData("Say", "Waiting for start");
        telemetry.update();
        waitForStart();

        robot.frontLeftMotor.setPower(.9);
        robot.backLeftMotor.setPower(.9);
        robot.frontRightMotor.setPower(.9);
        robot.backRightMotor.setPower(.9);

        runTime.reset();

        while (opModeIsActive() && (runTime.seconds() < 1.8)) {
            telemetry.addData("RunTime: ", runTime);
            telemetry.update();
            idle();
        }
        telemetry.addData("RunTime: ", runTime);
        telemetry.update();

        robot.frontLeftMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);

    }

}
