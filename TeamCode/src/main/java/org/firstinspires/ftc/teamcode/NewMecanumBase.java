package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
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
    DcMotor ArmMotor;
    DcMotor LiftMotor;
    DcMotor RightCollectMotor;
    DcMotor LeftCollectMotor;

    Servo ElbowServo;
    Servo ClampServo;
    Servo TrayServo;


    ColorSensor JewelSensor;

    @Override
    public void init(){
        TopLeft = hardwareMap.dcMotor.get("topleft");
        TopRight = hardwareMap.dcMotor.get("topright");
        BotLeft = hardwareMap.dcMotor.get("botleft");
        BotRight = hardwareMap.dcMotor.get("botright");
        RightCollectMotor = hardwareMap.dcMotor.get("collectright");
        LeftCollectMotor = hardwareMap.dcMotor.get("collectleft");
        LiftMotor = hardwareMap.dcMotor.get("lift");
        ArmMotor = hardwareMap.dcMotor.get("ArmMotor");

        ElbowServo = hardwareMap.servo.get("elbow");
        ClampServo = hardwareMap.servo.get("clamp");
        TrayServo = hardwareMap.servo.get("tray");

        JewelSensor = hardwareMap.colorSensor.get("jewel");
    }

    @Override
    public void loop(){
        telemetry.addData("Jewel", JewelSensor.argb());
        telemetry.update();

        double power = 0;
        double ServoPos = .5;
        double ArmPos = .5;
        double ClampPos = .5;

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

        RightCollectMotor.setPower(gamepad2.right_stick_y);
        LeftCollectMotor.setPower(gamepad2.left_stick_y);

        if (gamepad2.right_trigger>0)
        {
            ArmMotor.setPower(1);
        }else{
            ArmMotor.setPower(0);
        }
        if(gamepad2.left_trigger>0)
        {
            ArmMotor.setPower(-1);
        }else{
            ArmMotor.setPower(0);
        }

        if (gamepad2.dpad_up)
        {
            LiftMotor.setPower(.5);
        }else {
            LiftMotor.setPower(0);
        }
        if (gamepad2.dpad_down)
        {
            LiftMotor.setPower(-.5);
        }else{
            LiftMotor.setPower(0);
        }

        if(gamepad2.dpad_up)
        {
            ServoPos = 1;
        }
        if(gamepad2.dpad_down)
        {
            ServoPos = 0;
        }
        if(gamepad2.dpad_right)
        {
            ServoPos = .5;
        }
        ElbowServo.setPosition(ServoPos);

        if(gamepad2.right_bumper)
        {
            ClampPos = 1;
        }
        if(gamepad2.left_bumper)
        {
            ClampPos = 0;
        }
        ClampServo.setPosition(ClampPos);
























































































































    }
}
