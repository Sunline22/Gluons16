package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Linear Driver TeleOp", group = "TeleOp")

//test the booty

public class AManualD extends LinearOpMode {
    Hardware robot = new Hardware();
    private int collectState = 0, liftState = 0, shootCount = 0, rpm=2550, spinCount=0, encSpeed = 0;
    private boolean shootTog = false, spinTog = false;
    private double spinnerPos;
    private ElapsedTime encodercalc = new ElapsedTime();
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        robot.cannonMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Say", "Good morning");
        telemetry.update();
        waitForStart();
        spinnerPos = robot.spinner.getPosition();
        while (opModeIsActive()) {
            control();
        }
    }

    public void control() throws java.lang.InterruptedException {
        telem();
        drive();
        collect();
        lift();
        shoot();
        robot.waitForTick(40);
    }

    private void calcSpeed(){
        int val1 = 0, val2 = 0;
        if(encodercalc.seconds() < .05)
            val1=(int)robot.spinner.getPosition();
        else if(encodercalc.seconds() > .95) {
            val2 = (int) robot.spinner.getPosition();
            encSpeed = val2 - val1;
            encodercalc.reset();
        }
    }

    private void telem() {
        calcSpeed();
        telemetry.clear();
        telemetry.addData("WidowMaker", ((shootTog) ? "On" : "Off") + " rpm: " + rpm);
        telemetry.addData("Encoder @ speed?", ((encSpeed > rpm-25 && encSpeed < rpm+25) ? "Yes" : "No"));
        telemetry.addData("Encoder @ speed? value", encSpeed);
        telemetry.addData("Lift", (liftState == 1) ? "Up" : (liftState == -1) ? "Down" : "Off");
        telemetry.addData("Spinner", (spinnerPos > .3) ? "In" : (collectState == -1) ? "Out" : "Off");
        telemetry.addData("LeftStick", gamepad1.left_stick_y);
        telemetry.addData("RightStick", gamepad1.right_stick_y);
        telemetry.update();
    }

    private void drive() throws NullPointerException {
        double leftStickVert = Range.clip(gamepad1.left_stick_y, -1.0, 1.0);
        double rightStickVert = Range.clip(gamepad1.right_stick_y, -1.0, 1.0);

        joyDir(leftStickVert, rightStickVert);

        leftStickVert = Math.abs(leftStickVert);
        rightStickVert = Math.abs(rightStickVert);

        if (leftStickVert <= .025 && rightStickVert <= .025)
            dPad();
        else {
            motorPow(leftStickVert, rightStickVert);
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

            robot.frontLeftMotor.setPower(.5);
            robot.backLeftMotor.setPower(.5);
            robot.frontRightMotor.setPower(.5);
            robot.backRightMotor.setPower(.5);
        } else if (gamepad1.dpad_down) {
            telemetry.addData("Say", "Backward");
            telemetry.update();

            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

            robot.frontLeftMotor.setPower(.5);
            robot.backLeftMotor.setPower(.5);
            robot.frontRightMotor.setPower(.5);
            robot.backRightMotor.setPower(.5);
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

        if(gamepad1.x)
            rpm = 20;
        if(gamepad1.y)
            rpm = 2550;
        if(gamepad1.b)
            rpm = 2600;
        if(gamepad1.a)
            rpm = 2650;
        if(gamepad1.right_trigger > .1)
            rpm--;
        else if(gamepad1.left_trigger > .1)
            rpm++;

        spinCount--;
        if (spinTog)
            robot.spinner.setPosition(-1.0);
        else
            robot.spinner.setPosition(1.0);
        if (gamepad2.b && spinCount<0){
            spinCount=50;
            spinTog=!spinTog;
        }
    }

    private void lift() {
        if (gamepad2.a)
            liftState = 0;
        else if (gamepad2.right_trigger != 0)
            liftState = 1;
        else if (gamepad2.left_trigger != 0)
            liftState = -1;

        if (liftState == 1)
            robot.lift.setPower(.75);
        else if (liftState == -1)
            robot.lift.setPower(-.75);
        else
            robot.lift.setPower(0);
    }

    private void shoot() {
        //robot.cannonMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.cannonMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shootCount--;

        if (gamepad2.x && shootCount < 0) {
            shootCount = 80;
            shootTog = !shootTog;
        }

        if (shootTog) {
            robot.cannonMotor.setMaxSpeed(rpm);
            robot.cannonMotor.setPower(.4);
        } else
            robot.cannonMotor.setPower(0);
    }
}