package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by RoboPir8s on 9/27/2017.
 */
@TeleOp(name = "TeleOp", group = "Loop OpMode")
public class NewMecanumBase extends OpMode {


    //Drive Motors
    DcMotor TopLeft;
    DcMotor TopRight;
    DcMotor BotLeft;
    DcMotor BotRight;

    ColorSensor JewelSensor;

    /*//Conveyor Motors
    DcMotor Con1;
    DcMotor Con2; */
    @Override
    public void init(){
        TopLeft = hardwareMap.dcMotor.get("topleft");
        TopRight = hardwareMap.dcMotor.get("topright");
        BotLeft = hardwareMap.dcMotor.get("botleft");
        BotRight = hardwareMap.dcMotor.get("botright");

        JewelSensor = hardwareMap.colorSensor.get("jewelsensor");

        /*
        Con1 = hardwareMap.dcMotor.get("con1");
        Con2 = hardwareMap.dcMotor.get("con2");
        */
    }

    @Override
    public void loop(){
        telemetry.addData("Jewel", JewelSensor.argb());
        telemetry.update();

        //Set up Holonomic drive
        double Throttle = -gamepad1.left_stick_y;
        double Holonomic = gamepad1.left_stick_x;
        double Direction = gamepad1.right_stick_x;

        double TopL = Throttle + Holonomic + Direction;
        double TopR = -Throttle + Holonomic + Direction;
        double BotL = Throttle - Holonomic + Direction;
        double BotR = -Throttle - Holonomic + Direction;

        TopL = Range.clip(TopL, -1, 1);
        TopR = Range.clip(TopR,-1,1);
        BotL = Range.clip(BotL,-1,1);
        BotR = Range.clip(BotR,-1,1);
        //Set motor power to gamepad values
        TopLeft.setPower(TopL);
        TopRight.setPower(TopR);
        BotLeft.setPower(BotL);
        BotRight.setPower(BotR);
    }
}
