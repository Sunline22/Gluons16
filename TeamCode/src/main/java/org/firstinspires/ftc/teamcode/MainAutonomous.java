package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Sam on 18-Feb-17.
 */
@Autonomous(name="Main Autonomous",group = "Autonomous")
public class MainAutonomous extends AutoBlock{
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData("Say", "Waiting for start");
        telemetry.update();
        waitForStart();

        /*
         * This is the template for creating an Autonomous
         * Be sure to edit @Autonomous
         * Put code here ↓
         */

        turnDeg(90, .75, .375);
        push();
        while(isBlue()){
            push();
            idle();
        }
    }
}
