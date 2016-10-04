package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware {
    public DcMotor frontLeftMotor  = null;
    public DcMotor  frontRightMotor  = null;
    public DcMotor  backLeftMotor    = null;
    public DcMotor backRightMotor   = null;
    public Servo spinner = null;
    //public static final double driveForwardPower    =  0.45 ;
    //public static final double driveBackwardPower  = -0.45 ;

    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    public Hardware(){

    }

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        frontLeftMotor   = hwMap.dcMotor.get("frontLeftDrive");
        frontRightMotor   = hwMap.dcMotor.get("frontRightDrive");
        backLeftMotor   = hwMap.dcMotor.get("backLeftDrive");
        backRightMotor   = hwMap.dcMotor.get("backRightDrive");
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        spinner = hwMap.servo.get("spinner");
        spinner.setPosition(0);
    }

    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        if (remaining > 0)
            Thread.sleep(remaining);

        period.reset();
    }
}

