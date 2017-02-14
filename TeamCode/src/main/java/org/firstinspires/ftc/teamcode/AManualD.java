package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Linear Driver TeleOp",group="TeleOp")

//test the booty

public class AManualD extends LinearOpMode
{
    Hardware robot = new Hardware();
    private int collectState = 0, liftState = 0, countShoot = 0, countShootChange = 0;
    private double shootPow = .65;
    private boolean shootTog = false;

    public void runOpMode() throws InterruptedException
    {

        robot.init(hardwareMap);
        telemetry.addData("Say", "Good morning");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()){
            control();
        }
    }

    public void control()throws java.lang.InterruptedException
    {
        telem();
        drive();
        collect();
        lift();
        power();
        shoot();
        robot.waitForTick(40);
    }

    private void power()
    {
        if(gamepad1.y && countShootChange <= 0 && shootPow + .05 < 1.0)
        {
            shootPow += .025;
            countShootChange = 5;
        }
        else if(gamepad1.x && countShootChange <= 0 && shootPow - .05 >= 0)
        {
            shootPow-=.025;
            countShootChange = 5;
        }
        //shootPow = Math.round(shootPow*100)/100;
        countShootChange--;
    }

    private void telem()
    {
        telemetry.clear();
        telemetry.addData("WidowMaker", ((shootTog)?"On":"Off") + " PL: " + shootPow);
        telemetry.addData("Lift", (liftState == 1)?"Up":(liftState == -1)?"Down":"Off");
        telemetry.addData("Spinner", (collectState == 1)?"In":(collectState == -1)?"Out":"Off");
        telemetry.addData("LeftStick", gamepad1.left_stick_y);
        telemetry.addData("RightStick", gamepad1.right_stick_y);
        telemetry.update();
    }

    private void drive()
    {
        double leftStickVert = Range.clip(gamepad1.left_stick_y,-1.0,1.0);
        double rightStickVert = Range.clip(gamepad1.right_stick_y,-1.0,1.0);

        joyDir(leftStickVert, rightStickVert);

        leftStickVert = Math.abs(leftStickVert);
        rightStickVert = Math.abs(rightStickVert);

        if (leftStickVert <= .025 && rightStickVert <= .025)
            dPad();
        else
            motorPow(leftStickVert, rightStickVert);
    }

    private void joyDir(double leftStickVert, double rightStickVert) {
        if (leftStickVert > 0)
        {
            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        else if (leftStickVert < 0)
        {
            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if (rightStickVert > 0)
        {
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else if (rightStickVert < 0)
        {
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    private void motorDirection(DcMotor.Direction dir){
        robot.frontLeftMotor.setDirection(dir);
        robot.backLeftMotor.setDirection(dir);
        robot.frontRightMotor.setDirection(dir.equals(DcMotor.Direction.FORWARD)?DcMotor.Direction.REVERSE:DcMotor.Direction.FORWARD);
        robot.backRightMotor.setDirection(dir.equals(DcMotor.Direction.FORWARD)?DcMotor.Direction.REVERSE:DcMotor.Direction.FORWARD);
    }

    private void dPad() {
        if (gamepad1.dpad_up) {
            telemetry.addData("Say", "Forward");
            telemetry.update();

            motorDirection(DcMotor.Direction.FORWARD);

            motorPow(.5);

        } else if (gamepad1.dpad_down) {
            telemetry.addData("Say", "Backward");
            telemetry.update();

            motorDirection(DcMotor.Direction.REVERSE);

            motorPow(.5);
        }
        else
            motorPow(0);
    }

    private void motorPow(double leftStickVert, double rightStickVert)
    {
        robot.frontLeftMotor.setPower(leftStickVert);
        robot.backLeftMotor.setPower(leftStickVert);
        robot.frontRightMotor.setPower(rightStickVert);
        robot.backRightMotor.setPower(rightStickVert);
    }

    private void motorPow(double pow){
        robot.frontLeftMotor.setPower(pow);
        robot.backLeftMotor.setPower(pow);
        robot.frontRightMotor.setPower(pow);
        robot.backRightMotor.setPower(pow);
    }

    private void collect()
    {
        if(gamepad2.b)
            collectState = 0;
        else if(gamepad2.right_bumper)
            collectState = 1;
        else if(gamepad2.left_bumper)
            collectState = -1;

        if (collectState == 1)
            robot.spinner.setPosition(.1);
        else if (collectState == -1)
            robot.spinner.setPosition(.9);
        else
            robot.spinner.setPosition(.5);
    }

    private void lift()
    {
        if(gamepad2.a)
            liftState = 0;
        else if(gamepad2.right_trigger != 0)
            liftState = 1;
        else if(gamepad2.left_trigger != 0)
            liftState = -1;

        if (liftState == 1)
            robot.lift.setPower(.75);
        else if (liftState == -1)
            robot.lift.setPower(-.75);
        else
            robot.lift.setPower(0);
    }

    private void shoot()
    {
        if (gamepad2.x && countShoot <= 0)
        {
            countShoot = 20;
            shootTog = !shootTog;
        }
        if (shootTog)
        {
            robot.cannonMotor.setPower(shootPow);
            countShoot--;
        }
        else
        {
            robot.cannonMotor.setPower(0);
            countShoot--;
        }
    }
}