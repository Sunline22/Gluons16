package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Linear Dirver TeleOp",group="TeleOp")

//test the booty

public class AManualD extends LinearOpMode
{
    Hardware robot = new Hardware();
    //private int count = 0;
    //private boolean servoTog = false;
    public void runOpMode()throws InterruptedException{

        robot.init(hardwareMap);
        telemetry.addData("Say", "Good morning");
        telemetry.update();
        waitForStart();
        while(opModeIsActive())
        {
            drive();

            robot.waitForTick(40);
        }
    }

    private void drive()
    {

        double left   =  -gamepad1.left_stick_y;
        double right  =  -gamepad1.right_stick_y;
        double max    =  Math.max(Math.abs(left),Math.abs(right));
        //double spinPos = robot.spinner.getPosition();
        if(max>1.0)
        {
            left/=max;
            right/=max;
        }

        robot.frontLeftMotor.setPower(left);
        robot.backLeftMotor.setPower(left);
        robot.frontRightMotor.setPower(right);
        robot.backRightMotor.setPower(right);

        if(gamepad1.dpad_up)
        {
            telemetry.addData("Say","Forward");
            telemetry.update();

            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

            robot.frontLeftMotor.setPower(.99);
            robot.backLeftMotor.setPower(.99);
            robot.frontRightMotor.setPower(.99);
            robot.backRightMotor.setPower(.99);
        }
        else if(gamepad1.dpad_down)
        {
            telemetry.addData("Say","Backward");
            telemetry.update();

            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            robot.frontLeftMotor.setPower(.99);
            robot.backLeftMotor.setPower(.99);
            robot.frontRightMotor.setPower(.99);
            robot.backRightMotor.setPower(.99);
        }
        else
        {
            robot.frontLeftMotor.setPower(0);
            robot.backLeftMotor.setPower(0);
            robot.frontRightMotor.setPower(0);
            robot.backRightMotor.setPower(0);
        }
        //if(gamepad2.y && count == 0) {
            //count += 40;
            //servoTog=!servoTog;
        //}
        //if(servoTog){
            //spinPos+=.1d;
            //if(spinPos>=1.0d)
                //spinPos-=1.0d;
            //spinPos=Math.round(spinPos*10.0d)/10.0d;
            //robot.spinner.setPosition(spinPos);
            //count--;
        //}
    }
}