package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Linear Dirver TeleOp",group="TeleOp")

//test the booty

public class AManualD extends LinearOpMode{

    public Hardware robot = new Hardware();
    private int countLift = 0, countCollect = 0, countShoot = 0;
    private boolean shootTog = false;

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        telemetry.addData("Say", "Good morning");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()){
            control();
        }
    }

    public void control()throws java.lang.InterruptedException{
        drive();
        collect();
        lift();
        shoot();
        robot.waitForTick(40);

    }

    private void drive() {
        double leftStickVert = Range.clip(gamepad1.left_stick_y,-1.0,1.0);
        double rightStickVert = Range.clip(gamepad1.right_stick_y,-1.0,1.0);

        joyDir(leftStickVert, rightStickVert);

        telemetry.addData("Say", joyDir(leftStickVert, rightStickVert));
        telemetry.update();

        leftStickVert = Math.abs(leftStickVert);
        rightStickVert = Math.abs(rightStickVert);

        motorPow(leftStickVert, rightStickVert);

        if (leftStickVert == 0 && rightStickVert == 0) {
            dPad();
        }
    }

    private String joyDir(double leftStickVert, double rightStickVert) {
        String joyDirTel = "";
        if (leftStickVert > 0) {
            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            joyDirTel += "Left Forward ";
        } else if (leftStickVert < 0) {
            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            joyDirTel += "Left Backwards ";
        }
        if (rightStickVert > 0) {
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            joyDirTel += "Right Forward ";
        } else if (rightStickVert < 0) {
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            joyDirTel += "Right Backwards ";
        }
        return joyDirTel;
    }

    private void dPad() {
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

    private void motorPow(double leftStickVert, double rightStickVert) {
        robot.frontLeftMotor.setPower(leftStickVert);
        robot.backLeftMotor.setPower(leftStickVert);
        robot.frontRightMotor.setPower(rightStickVert);
        robot.backRightMotor.setPower(rightStickVert);

    }

    private void collect() {
        if (gamepad2.dpad_right && countCollect == 0)
            countCollect = 20;
        else if (gamepad2.dpad_right && countCollect == 1)
            countCollect--;

        if (gamepad2.dpad_left && countCollect == 0)
            countCollect = -20;
        else if (gamepad2.dpad_left && countCollect == -1)
            countCollect++;

        if(countCollect < -1)
            countCollect++;
        else if(countCollect > 1)
            countCollect--;

        if (countCollect > 0)
            robot.spinner.setPosition(.1);
        else if (countCollect < 0)
            robot.spinner.setPosition(.9);
        else
            robot.spinner.setPosition(.5);
    }

    private void lift() {
        if (gamepad2.dpad_up && countLift == 0)
            countLift = 20;
        else if (gamepad2.dpad_up && countLift == 1)
            countLift--;

        if (gamepad2.dpad_down && countLift == 0)
            countLift = -20;
        else if (gamepad2.dpad_down && countLift == -1)
            countLift++;

        if(countLift < -1)
            countLift++;
        else if(countLift > 1)
            countLift--;

        if (countLift > 0)
            robot.lift.setPosition(.1);
        else if (countLift < 0)
            robot.lift.setPosition(.9);
        else
            robot.lift.setPosition(.5);
    }

    private void shoot() {
        if (gamepad2.x && countShoot <= 0) {
            countShoot = 20;
            shootTog = !shootTog;
        }
        if (shootTog) {
            robot.cannonMotor.setPower(1.0);
            countShoot--;
        }
        else{
            robot.cannonMotor.setPower(0);
            countShoot--;
            }
        }
}
