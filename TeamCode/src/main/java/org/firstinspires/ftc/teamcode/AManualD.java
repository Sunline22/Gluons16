package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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
        {
            motorPow(leftStickVert, rightStickVert);
        }
    }

    private String joyDir(double leftStickVert, double rightStickVert) {
        String joyDirTel = "";
        if (leftStickVert > 0)
        {
            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            joyDirTel += "Left Forward ";
        }
        else if (leftStickVert < 0)
        {
            robot.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            joyDirTel += "Left Backwards ";
        }
        if (rightStickVert > 0)
        {
            robot.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            joyDirTel += "Right Forward ";
        }
        else if (rightStickVert < 0)
        {
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
        } else if (gamepad1.dpad_down)
        {
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
        }
        else
        {
            robot.frontLeftMotor.setPower(0);
            robot.backLeftMotor.setPower(0);
            robot.frontRightMotor.setPower(0);
            robot.backRightMotor.setPower(0);
        }
    }

    private void motorPow(double leftStickVert, double rightStickVert)
    {
        robot.frontLeftMotor.setPower(leftStickVert);
        robot.backLeftMotor.setPower(leftStickVert);
        robot.frontRightMotor.setPower(rightStickVert);
        robot.backRightMotor.setPower(rightStickVert);
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