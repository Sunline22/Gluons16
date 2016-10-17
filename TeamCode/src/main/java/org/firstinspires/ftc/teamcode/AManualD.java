package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Linear Dirver TeleOp",group="TeleOp")

//test the booty

public class AManualD extends LinearOpMode{

    private Hardware robot = new Hardware();
    private int countCollect = 0, countShoot = 0;
    private boolean shootTog = false, collectTog = false;

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        telemetry.addData("Say", "Good morning");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()){
            drive();
            spin();
            shoot();
            robot.waitForTick(40);
        }
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

    private void spin() {
        if (gamepad2.y && countCollect == 0) {
            countCollect += 40;
            collectTog = !collectTog;
        }
        spinSpinner();
        spinLift();
    }

    private void spinSpinner() {
        double spinPos = robot.spinner.getPosition();
        if (collectTog) {
            spinPos += .1d;
            if (spinPos >= 1.0d)
                spinPos -= 1.0d;
            spinPos = Math.round(spinPos * 10.0d) / 10.0d;
            robot.spinner.setPosition(spinPos);
            countCollect--;

        }
    }

    private void spinLift() {
        double liftPos = robot.lift.getPosition();
        if (collectTog) {
            liftPos += .1d;
            if (liftPos >= 1.0d)
                liftPos -= 1.0d;
            liftPos = Math.round(liftPos * 10.0d) / 10.0d;
            robot.lift.setPosition(liftPos);
            countCollect--;

        }
    }

    private void shoot(){
        if (gamepad2.x && countShoot == 0) {
            countShoot += 40;
            shootTog = !shootTog;
        }
        if (shootTog)
            robot.cannonMotor.setPower(1.0);
        else
            robot.cannonMotor.setPower(0);

        }
}
