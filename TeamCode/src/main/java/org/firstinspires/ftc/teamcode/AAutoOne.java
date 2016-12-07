package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Pushbot: Auto Drive By Time", group="Pushbot")
@Disabled
public class AAutoOne extends LinearOpMode{

    public Hardware robot = new Hardware();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException{

        waitForStart();
        robot.frontRightMotor.setPower(1.0);
        robot.backRightMotor.setPower(1.0);
        robot.frontLeftMotor.setPower(1.0);
        robot.backLeftMotor.setPower(1.0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            idle();
        }
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);
        robot.frontLeftMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        runtime.reset();

    }
}
