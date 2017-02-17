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
    private int collectState = 0, liftState = 0, countShootChange = 0;
    private double shootPow = .65;
    private boolean shootTog = false;
    int motorTarget = robot.cannonMotor.getCurrentPosition();
    pos p = pos.in;

    private enum pos{
        neutral(.75) , in(1);

        private final double value;

        pos(double value){ this.value = value; }

        double getValue(){ return value; }
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        telemetry.addData("Say", "Good morning");
        telemetry.update();
        waitForStart();
        motorTarget = robot.cannonMotor.getCurrentPosition();
        while (opModeIsActive()) {
            control();
        }
    }

    public void control() throws java.lang.InterruptedException {
        //telem();
        try {
            drive();
        } catch (NullPointerException e){
            telemetry.addData("Say", "Exception caught in drive method.");
            telemetry.update();
        }
        try {
            collect();
        } catch (NullPointerException e){
            telemetry.addData("Say", "Exception caught in collect method.");
            telemetry.update();
        }
        try {
            lift();
        } catch (NullPointerException e){
            telemetry.addData("Say", "Exception caught in lift method.");
            telemetry.update();
        }
        try {
            power();
        } catch (NullPointerException e){
            telemetry.addData("Say", "Exception caught in power method.");
            telemetry.update();
        }
        try {
            shoot();
        } catch (NullPointerException e){
            telemetry.addData("Say", "Exception caught in shoot method.");
            telemetry.update();
        }
        robot.waitForTick(40);
    }

    private void power() throws NullPointerException {
        if (gamepad1.y && countShootChange <= 0 && shootPow + .05 < 1.0) {
            shootPow += .025;
            countShootChange = 5;
        } else if (gamepad1.x && countShootChange <= 0 && shootPow - .05 >= 0) {
            shootPow -= .025;
            countShootChange = 5;
        }
        //shootPow = Math.round(shootPow*100)/100;
        countShootChange--;
    }

    private void telem() throws NullPointerException{
        telemetry.clear();
        telemetry.addData("WidowMaker", ((shootTog) ? "On" : "Off") + " PL: " + shootPow);
        telemetry.addData("Lift", (liftState == 1) ? "Up" : (liftState == -1) ? "Down" : "Off");
        telemetry.addData("Spinner", (collectState == 1) ? "In" : (collectState == -1) ? "Out" : "Off");
        telemetry.addData("LeftStick", gamepad1.left_stick_y);
        telemetry.addData("RightStick", gamepad1.right_stick_y);
        telemetry.update();
    }

    private void drive() throws NullPointerException{
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

    private String joyDir(double leftStickVert, double rightStickVert) throws NullPointerException{
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

    private void dPad() throws NullPointerException{
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

    private void motorPow(double leftStickVert, double rightStickVert) throws NullPointerException{
        robot.frontLeftMotor.setPower(leftStickVert);
        robot.backLeftMotor.setPower(leftStickVert);
        robot.frontRightMotor.setPower(rightStickVert);
        robot.backRightMotor.setPower(rightStickVert);
    }

    private void collect() throws NullPointerException{
        if(p == pos.in && gamepad2.b) {
            robot.spinner.setPosition(p.getValue());
            p = pos.neutral;
        }
        else if (p == pos.neutral && gamepad2.b) {
            robot.spinner.setPosition(p.getValue());
            p = pos.in;
        }
    }

    private void lift() throws NullPointerException{
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

    private void shoot() throws NullPointerException{
        final double countsPerMotorRev = 1440 ;
        final double driveGearReduction = 2.0 ;
        final double wheelDiameterInches = 1.75 ;
        final double countsPerInch = (countsPerMotorRev * driveGearReduction) / (wheelDiameterInches * 3.1415);
        if(gamepad2.x) {
            motorTarget = robot.cannonMotor.getCurrentPosition() + (int)(5 * countsPerInch);
            robot.cannonMotor.setTargetPosition(motorTarget);
            robot.cannonMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.cannonMotor.setPower(shootPow);
        }
        if(robot.cannonMotor.getCurrentPosition() <= motorTarget){
            robot.cannonMotor.setPower(0);
            robot.cannonMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.cannonMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}