package org.firstinspires.ftc.teamcode;

/**
 * Created by Edge Lord on 24-Oct-16.
 */
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class FullAutonomusDrive extends LinearOpMode {
    private Hardware robot = new Hardware();
    public void run(){
        robot.init(hardwareMap)
        while(!gamepad1.y)
            scan();
            decipher();
    }
}
