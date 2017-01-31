package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Sam on 24-Jan-17.
 */

@Autonomous(name="Encoder Test",group="Autonomous")
public class EncoderTest extends LinearOpMode {
    Hardware robot = new Hardware();
    final int TicksRotation = 120;

    private ElapsedTime runTime = new ElapsedTime();

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        telemetry.addData("Say", "Waiting for start");
        telemetry.update();
        waitForStart();

        robot.cannonMotor.setMaxSpeed(TicksRotation);
        while(opModeIsActive()){
            robot.cannonMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.cannonMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.cannonMotor.setTargetPosition(1444);
            idle();
        }
    }
}
