package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Linear Dirver TeleOp",group="TeleOp")
public class AManualD extends LinearOpMode {
    Hardware robot = new Hardware();
    public void runOpMode()throws InterruptedException{
        double left, right, max;
        robot.init(hardwareMap);
        telemetry.addData("Say", "Good morning");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            /**
            left=gamepad1.left_stick_y;
            right=gamepad1.right_stick_y;
            max=Math.max(Math.abs(left),Math.abs(right));
            if(max>1.0){
                left/=max;
                right/=max;
            }*/
            if(gamepad1.dpad_up) {
                telemetry.addData("Say","Forward");
                robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                robot.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                robot.frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                robot.backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                robot.frontLeftMotor.setPower(.99);
                robot.backLeftMotor.setPower(.99);
                robot.frontRightMotor.setPower(.99);
                robot.backRightMotor.setPower(.99);
            }
            if(gamepad1.dpad_down) {
                telemetry.addData("Say","Backward");
                robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                robot.backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                robot.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                robot.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                robot.frontLeftMotor.setPower(.99);
                robot.backLeftMotor.setPower(.99);
                robot.frontRightMotor.setPower(.99);
                robot.backRightMotor.setPower(.99);
            }
            robot.waitForTick(40);
        }
    }
}
