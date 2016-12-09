package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware {
    public DcMotor frontLeftMotor  = null;
    public DcMotor frontRightMotor  = null;
    public DcMotor backLeftMotor    = null;
    public DcMotor backRightMotor   = null;
    public DcMotor cannonMotor   = null;
    //public DcMotor capBallLift = null;

    public Servo spinner = null;
    public Servo lift = null;

    public ModernRoboticsI2cRangeSensor mainSensor = null;
    //public static final double driveForwardPower    =  0.45 ;
    //public static final double driveBackwardPower  = -0.45 ;

    HardwareMap hwMap           =  null;
    private ElapsedTime period  =  new ElapsedTime();

    public Hardware(){}

    public void init(HardwareMap ahwMap)
    {
        hwMap = ahwMap;

        InitComponents();
    }

    private void InitComponents()
    {
        GetMotors();

        InitMotorsDirection();

        SetMotorsInitPower();

        SetMotorInitMode();

        InitSpinner();

        InitLift();
    }

    private void InitLift() {
        lift = hwMap.servo.get("lift");
        lift.setPosition(0.5);
        lift.setDirection(Servo.Direction.REVERSE);
    }

    private void InitSpinner() {
        spinner = hwMap.servo.get("spinner");
        spinner.setPosition(0.5);
        spinner.setDirection(Servo.Direction.FORWARD);
    }

    private void SetMotorInitMode() {
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        cannonMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //capBallLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void SetMotorsInitPower() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        cannonMotor.setPower(0);
        //capBallLift.setPower(0);
    }

    private void InitMotorsDirection() {
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        cannonMotor.setDirection(DcMotor.Direction.FORWARD);
        //capBallLift.setDirection(DcMotor.Direction.FORWARD);
    }

    private void GetMotors() {
        frontLeftMotor = hwMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hwMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hwMap.dcMotor.get("backLeftMotor");
        backRightMotor = hwMap.dcMotor.get("backRightMotor");
        cannonMotor = hwMap.dcMotor.get("cannonMotor");
        //capBallLift      =  hwMap.dcMotor.get("capBallLift");
    }
        //mainSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class, "mainSensor");

    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        if (remaining > 0)
            Thread.sleep(remaining);

        period.reset();
    }
}
