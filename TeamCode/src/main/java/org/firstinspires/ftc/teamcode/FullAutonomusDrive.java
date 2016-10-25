        package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.util.Range;

@TeleOp(name="FullAuto teleOp",group="TeleOp")

//test the booty

public class FullAutonomusDrive extends LinearOpMode {

    public Hardware robot = new Hardware();
    public AManualD drive = new AManualD();
    private int avgDistChange = 0;

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

    public boolean sense(){

    }

    public String decipher(){

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