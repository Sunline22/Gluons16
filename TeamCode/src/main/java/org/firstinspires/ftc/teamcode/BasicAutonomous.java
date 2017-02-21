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
    public int rpm=2550;

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        telemetry.addData("Say", "Waiting for start");
        telemetry.update();
        waitForStart();

        robot.frontLeftMotor.setPower(-.9);
        robot.backLeftMotor.setPower(-.9);
        robot.frontRightMotor.setPower(-.9);
        robot.backRightMotor.setPower(-.9);

        runTime.reset();

        while (opModeIsActive() && (runTime.seconds() < 0.7)) {
            telemetry.addData("RunTime: ", runTime);
            telemetry.addData("RunGoal: ", 0.7);
            telemetry.update();
            idle();
        }

        robot.frontLeftMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);

        runTime.reset();

        robot.cannonMotor.setMaxSpeed(rpm);
        robot.cannonMotor.setPower(.4);

        while (opModeIsActive() && (runTime.seconds() < 14.0)) {
            telemetry.addData("RunTime: ", runTime);
            telemetry.addData("RunGoal: ", 10.0);
            telemetry.update();

            if(runTime.seconds()>5.9 && runTime.seconds()<6.0)
                robot.lift.setPower(.5);
            else if((runTime.seconds()>11.8 && runTime.seconds()<14.0))
                robot.lift.setPower(.5);
            else
                robot.lift.setPower(0);

            idle();
        }

        robot.cannonMotor.setPower(0);

        robot.frontLeftMotor.setPower(-.9);
        robot.backLeftMotor.setPower(-.9);
        robot.frontRightMotor.setPower(-.9);
        robot.backRightMotor.setPower(-.9);


        runTime.reset();
        while (opModeIsActive() && (runTime.seconds() < 1.3)) {
            telemetry.addData("RunTime: ", runTime);
            telemetry.addData("RunGoal: ", 1.3);
            telemetry.update();
            idle();
        }

        robot.frontLeftMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);

    }

}
