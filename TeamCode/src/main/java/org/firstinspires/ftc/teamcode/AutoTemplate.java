package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Sam on 18-Feb-17.
 */
@Autonomous(name="NewTemplate",group = "Hidden")
public class AutoTemplate extends AutoBlock{
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Say", "Waiting for start");
        telemetry.update();
        waitForStart();

        /*
         * This is the template for creating an Autonomous
         * Be sure to edit @Autonomous
         * Put code here ↓
         */

    }
}
