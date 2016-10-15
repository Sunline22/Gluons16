package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Linear Dirver TeleOp",group="TeleOp")

//test the booty

public class AManualD extends LinearOpMode{

    Hardware robot = new Hardware();
    private int count = 0;
    private boolean servoTog = false;
    double spinPos=0;

    public void runOpMode()throws InterruptedException{

        robot.init(hardwareMap);
        telemetry.addData("Say", "Good morning");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            drive();

            robot.waitForTick(40);
        }
    }

    private void drive(){
        double leftStickVert   =  gamepad1.left_stick_y;
        double rightStickVert  =  gamepad1.right_stick_y;
        //double spinPos = robot.spinner.getPsotition();

        leftStickVert = Range.clip(leftStickVert,-1.0,1.0);
        rightStickVert = Range.clip(rightStickVert,-1.0,1.0);

        joyDir(leftStickVert, rightStickVert);

        telemetry.addData("Say",joyDir(leftStickVert, rightStickVert));
        telemetry.update();

        leftStickVert=Math.abs(leftStickVert);
        rightStickVert=Math.abs(rightStickVert);

        motorPow(leftStickVert, rightStickVert);

        if(gamepad2.y && count == 0) {
            count += 40;
            servoTog=!servoTog;
        }
        if(servoTog){
            spinPos+=.1d;
            if(spinPos>=1.0d)
                spinPos-=1.0d;
            spinPos=Math.round(spinPos*10.0d)/10.0d;
            robot.spinner.setPosition(spinPos);
            count--;
        }

        if(leftStickVert==0 && rightStickVert==0){
            dPad();
        }
    }

    private String joyDir(double leftStickVert, double rightStickVert){
        String joyDirTel = "";
        if(leftStickVert>0){
            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            joyDirTel+="Left Forward ";
        }
        else if(leftStickVert<0){
            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            joyDirTel+="Left Backwards ";
        }
        if(rightStickVert>0){
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            joyDirTel+="Right Forward ";
        }
        else if(rightStickVert<0) {
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            joyDirTel+="Right Backwards ";
        }
        return joyDirTel;
    }

    private void dPad(){
        if (gamepad1.dpad_up) {
            telemetry.addData("Say", "Forward");
            telemetry.update();

            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            robot.frontLeftMotor.setPower(.99);
            robot.backLeftMotor.setPower(.99);
            robot.frontRightMotor.setPower(.99);
            robot.backRightMotor.setPower(.99);
        } else if (gamepad1.dpad_down) {
            telemetry.addData("Say", "Backward");
            telemetry.update();

            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

            robot.frontLeftMotor.setPower(.99);
            robot.backLeftMotor.setPower(.99);
            robot.frontRightMotor.setPower(.99);
            robot.backRightMotor.setPower(.99);
        } else {
            robot.frontLeftMotor.setPower(0);
            robot.backLeftMotor.setPower(0);
            robot.frontRightMotor.setPower(0);
            robot.backRightMotor.setPower(0);
        }
    }

    private void motorPow(double leftStickVert, double rightStickVert){
        robot.frontLeftMotor.setPower(leftStickVert);
        robot.backLeftMotor.setPower(leftStickVert);
        robot.frontRightMotor.setPower(rightStickVert);
        robot.backRightMotor.setPower(rightStickVert);

    }

    /**private void spin(double spinPos){
        spinPos+=.1d;
        if(spinPos>=1.0d)
            spinPos-=1.0d;
        spinPos=Math.round(spinPos*10.0d)/10.0d;
        robot.spinner.setPosition(spinPos);
    }*/
}