package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Sam on 16-Dec-16.
 */

public class AutoRedAlliance extends LinearOpMode
{
    Hardware robot = new Hardware();
    private ElapsedTime stepTime = new ElapsedTime(), fullTime = new ElapsedTime();
    int Colour1, Colour2;

    public void runOpMode() throws InterruptedException {
        while (opModeIsActive() && (stepTime.seconds() < 3.0)) {
            robot.frontLeftMotor.setPower(.9);
            robot.backLeftMotor.setPower(.9);
            robot.frontRightMotor.setPower(.7);
            robot.backRightMotor.setPower(.7);
        }
        while(color()){

        }
    }
    public boolean color(){
        //while(robot.beaconColour.blue >|| robot.beaconColour.red)
        return true;
    }

}
