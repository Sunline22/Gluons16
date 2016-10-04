package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Linear Dirver TeleOp",group="TeleOp")
public class AManualD extends LinearOpMode {
    Hardware robot = new Hardware();
    public void runOpMode()throws InterruptedException{
        double left, right, max;
        robot.init(hardwareMap);
        telemetry.addData("Say", "Hello Driver");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            left=gamepad1.left_stick_y;
            right=gamepad1.right_stick_y;
            max=Math.max(Math.abs(left),Math.abs(right));
            if(max>1.0){
                left/=max;
                right/=max;
            }
            robot.frontLeftMotor.setPower(left);
            robot.backLeftMotor.setPower(left);
            robot.frontRightMotor.setPower(right);
            robot.backRightMotor.setPower(right);
            robot.waitForTick(40);
        }
    }
}
