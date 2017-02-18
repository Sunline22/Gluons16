package org.firstinspires.ftc.teamcode;

import android.transition.ChangeBounds;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

/**
 * Created by Sam on 23-Jan-17.
 *
 *
 *
 *
 * Currently Programmed for Blue
 *
 *
 *
 */


@Autonomous(name = "VuforiaBlue", group = "Autonomous")
public class VuforiaBlue extends LinearOpMode {
    Hardware robot = new Hardware();
    private ElapsedTime runTime = new ElapsedTime();

    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addData("Say", "Waiting for start");
        telemetry.update();
        waitForStart();

        shootFirst();
        beaconNav(0);
        colorpusher();
        beaconNav(1);
        colorpusher();
    }

    private void shootFirst() throws InterruptedException {
        robot.frontLeftMotor.setPower(-.9);
        robot.backLeftMotor.setPower(-.9);
        robot.frontRightMotor.setPower(-.9);
        robot.backRightMotor.setPower(-.9);

        runTime.reset();

        while (opModeIsActive() && (runTime.seconds() < 0.8)) {
            telemetry.addData("RunTime: ", runTime);
            telemetry.addData("RunGoal: ", 0.8);
            telemetry.update();
            idle();
        }

        robot.frontLeftMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);

        runTime.reset();
        while (opModeIsActive() && (runTime.seconds() < 6.0)) {
            telemetry.addData("RunTime: ", runTime);
            telemetry.addData("RunGoal: ", 4.0);
            telemetry.update();

            robot.cannonMotor.setPower(.575);

            if(runTime.seconds()>1.9 && runTime.seconds()<2.0)
                robot.lift.setPower(.5);
            else if((runTime.seconds()>3.8 && runTime.seconds()<6.0))
                robot.lift.setPower(.5);
            else
                robot.lift.setPower(0);

            idle();
        }

    }

    private void beaconNav(int t) throws InterruptedException {
        VuforiaTrackableDefaultListener target = (VuforiaTrackableDefaultListener) robot.beacons.get(t).getListener();

        robot.beacons.activate();

        changeDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        changeDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftMotor.setPower(.05);
        robot.frontLeftMotor.setPower(.05);
        robot.backRightMotor.setPower(.4);
        robot.frontRightMotor.setPower(.4);

        while (opModeIsActive() && target.getRawPose() == null) {
            idle();
        }

        drive(0);

        //Analyze Beacon

        VectorF angles = anglesFromTarget(target);
        VectorF trans = navOffWall(target.getPose().getTranslation(), Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));

        do{
            if (trans.get(0) > 0)
                turn(.05);
            else
                turn(-.05);

            if(target.getPose() != null)
                trans = navOffWall(target.getPose().getTranslation(), Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));
            idle();
        } while (opModeIsActive() && Math.abs(trans.get(0)) > 30);

        drive(0);

        changeDriveMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeftMotor.setTargetPosition((int)(robot.frontLeftMotor.getCurrentPosition() + ((Math.hypot(trans.get(0), trans.get(2)) + robot.distanceFromCentermm) / robot.wheelCircumferencemm * robot.tickSpeed)));
        robot.frontRightMotor.setTargetPosition((int)(robot.frontRightMotor.getCurrentPosition() + ((Math.hypot(trans.get(0), trans.get(2)) + robot.distanceFromCentermm) / robot.wheelCircumferencemm * robot.tickSpeed)));
        robot.backLeftMotor.setTargetPosition((int)(robot.backLeftMotor.getCurrentPosition() + ((Math.hypot(trans.get(0), trans.get(2)) + robot.distanceFromCentermm) / robot.wheelCircumferencemm * robot.tickSpeed)));
        robot.backRightMotor.setTargetPosition((int)(robot.backLeftMotor.getCurrentPosition() + ((Math.hypot(trans.get(0), trans.get(2)) + robot.distanceFromCentermm) / robot.wheelCircumferencemm * robot.tickSpeed)));

        drive(0.3);
        while(opModeIsActive() && robot.frontLeftMotor.isBusy() && robot.frontRightMotor.isBusy() && robot.backLeftMotor.isBusy() && robot.backRightMotor.isBusy()){
            idle();
        }
        drive(0);

        changeDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while(opModeIsActive() && target.getPose() == null || Math.abs(target.getPose().getTranslation().get(0)) > 10){
            if (target.getPose() != null) {
                if(target.getPose().getTranslation().get(0) > 0)
                    turn(-.3);
                else
                    turn(.3);
            }
            else
                turn(-.3);
        }
        drive(0);
    }

    private void changeDriveMode(DcMotor.RunMode x){
        robot.frontLeftMotor.setMode(x);
        robot.frontRightMotor.setMode(x);
        robot.backLeftMotor.setMode(x);
        robot.backRightMotor.setMode(x);
    }

    private void drive(double power){
        robot.frontLeftMotor.setPower(power);
        robot.frontRightMotor.setPower(power);
        robot.backLeftMotor.setPower(power);
        robot.backRightMotor.setPower(power);
    }

    private void turn(double power){
        robot.frontLeftMotor.setPower(-power);
        robot.frontRightMotor.setPower(power);
        robot.backLeftMotor.setPower(-power);
        robot.backRightMotor.setPower(power);
    }
    private void colorpusher() throws InterruptedException{
        runTime.reset();
        while(opModeIsActive() && runTime.seconds()<0.2) {
            drive(.2);
            idle();
        }
        runTime.reset();
        while(opModeIsActive() && runTime.seconds()<0.2) {
            drive(-.2);
            idle();
        }
        drive(0);
        if(robot.beaconSensor.blue() < robot.beaconSensor.red()){
            colorpusher();
        }
    }

    private void vuforiaLocation() {
        for (VuforiaTrackable beac : robot.beacons) {
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();

            if (pose != null) {
                VectorF translation = pose.getTranslation();

                telemetry.addData(beac.getName() + "-Translation", translation);

                double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));

                telemetry.addData(beac.getName() + "-Degrees", degreesToTurn);
            }

            telemetry.update();
        }
    }

    public VectorF navOffWall(VectorF trans, double robotAngle, VectorF offWall)
    {
        return new VectorF((float) (trans.get(0) - offWall.get(0) * Math.sin(Math.toRadians(robotAngle)) - offWall.get(2) * Math.cos(Math.toRadians(robotAngle))), trans.get(1), (float) (trans.get(2) + offWall.get(0) * Math.cos(Math.toRadians(robotAngle)) - offWall.get(2) * Math.sin(Math.toRadians(robotAngle))));
    }

    public VectorF anglesFromTarget(VuforiaTrackableDefaultListener image)
    {
        float [] data = image.getRawPose().getData();
        float [] [] rotation = {{data[0], data[1]}, {data[4], data[5], data[6]}, {data[8], data[9], data[10]}};
        double thetaX = Math.atan2(rotation[2][1], rotation[2][2]);
        double thetaY = Math.atan2(-rotation[2][0], Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2]));
        double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]);
        return new VectorF((float)thetaX, (float)thetaY, (float)thetaZ);
    }
}
