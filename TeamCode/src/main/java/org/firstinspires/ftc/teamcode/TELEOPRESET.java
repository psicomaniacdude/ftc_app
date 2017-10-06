package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by RoboPir8s on 9/19/2016.
 * latest iteration 11/10/16
 */
/*THIS ALL NEEDS TO BE CHANGED
*THIS ALL NEEDS TO BE CHANGED
* THIS ALL NEED TO BE CHANGED
* THIS IS NOT REFINED
*ALL NUBERS ARE GUESSES
 */
@Disabled
@TeleOp(name = "TELEOPRESET", group = "Loop OpMode")
public class TELEOPRESET extends OpMode
{
    //mmmmmmmmmmmmmmmmmmmm pie
//    static double Pi =  3.141592653589793238462643383279502884;

    //Declare motors
    DcMotor TopLeft;
    DcMotor TopRight;
    DcMotor BotLeft;
    DcMotor BotRight;
    DcMotor LiftMotor;
    DcMotor ArmMotor;
    DcMotor CatapultMotor;
    DcMotor PinchMotor;

    //Declare Servos
    Servo BallStop;

    //Declare Sensors
    OpticalDistanceSensor ODSSensorLeft;
    OpticalDistanceSensor ODSSensorRight;

    //Declare variables
    boolean toggleswitch;
    int pinchPos =1;
    @Override
    public void init()
    {
        //Get hardware maps for the motors
        TopLeft = hardwareMap.dcMotor.get("topleft");
        TopRight = hardwareMap.dcMotor.get("topright");
        BotLeft = hardwareMap.dcMotor.get("botleft");
        BotRight = hardwareMap.dcMotor.get("botright");
        LiftMotor = hardwareMap.dcMotor.get("liftmotor");
        ArmMotor = hardwareMap.dcMotor.get("ArmMotor");
        CatapultMotor = hardwareMap.dcMotor.get("catapult");
        PinchMotor = hardwareMap.dcMotor.get("pinchmotor");
        BallStop = hardwareMap.servo.get("ballstop");
        ODSSensorLeft = hardwareMap.opticalDistanceSensor.get("odsleft");
        ODSSensorRight = hardwareMap.opticalDistanceSensor.get("odsright");

        //Set motors to run using encoders
        LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        PinchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void loop()
    {
        //Get ODS value
       /* double distanceleft = ODSSensorLeft.getLightDetected();
        double distanceright = ODSSensorRight.getLightDetected();
        final double distance = 0.04;*/
        int pinchPower;
        //Show Motor Power Levels its over 9000!?!?!?!?!?!?!?!?!?!
        telemetry.addData("toggle switch" , toggleswitch);
        telemetry.addData("Pinch pos =", pinchPos);
        telemetry.addData("LiftPos", LiftMotor.getCurrentPosition());
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
      //  LiftMotor.setTargetPosition(0);
        LiftMotor.setPower(gamepad2.left_stick_y);
        ArmMotor.setPower(gamepad2.right_stick_y);
        //Pass power to the CatapultMotor from the GamePad
        if (gamepad1.left_bumper)
        {
            CatapultMotor.setPower(-1);
        }
        if (gamepad1.right_bumper)
        {
            CatapultMotor.setPower(1);
        }
        if  (gamepad1.right_trigger > 0)
        {
            CatapultMotor.setPower(0);
        }
        if(gamepad1.dpad_up)
        {
            pinchPower = 1;
        }
        else if(gamepad1.dpad_down)
        {
            pinchPower = -1;
        }
        else
        {
            pinchPower = 0;
        }
        PinchMotor.setPower(pinchPower);
        BallStop.setPosition(.25);

        BallStop.setDirection(Servo.Direction.REVERSE);
        while (gamepad1.a)
        {
            BallStop.setPosition(1);
        }
    }
}

