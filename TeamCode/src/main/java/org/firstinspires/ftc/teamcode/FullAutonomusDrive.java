package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="FullAuto teleOp",group="TeleOp")
@Disabled
//test the booty

public class FullAutonomusDrive extends LinearOpMode {

    public Hardware robot = new Hardware();
    public AManualD drive = new AManualD();
    private final int defaultDist = 13;
    private int distChange = defaultDist, senseDelay;

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        telemetry.addData("Say", "Full autonomous initiated");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()){
            //toggles manual control
            if(gamepad1.y) {
                sleep(125);
                while(!gamepad1.y){
                    drive.control();
                }
            }

            if(sense())
                action(decipher());
            }
        }

    public void search() {

    }

    public boolean sense(){
        if(senseDelay==0){
            senseDelay=40;
            //distChange-=robot.mainSensor.getDistance(DistanceUnit.CM);
        }
        else
            senseDelay--;
        if(Math.abs(distChange)>.5)
            decipher();
        else
            search();
        return false;
    }

    public String decipher(){
        return " ";
    }

    public void action(String object){
        switch(object){
            case "particle":{

            }

            case "capball":{

            }

            case "robot":{

            }

            case "unknown":{

            }

        }
    }
}