package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by RoboPir8s on 1/26/2017.
 */
@Disabled
@Autonomous(name = "Yoga_Blue_Backup", group ="Autonomous")
public class Yoga_Blue_Backup extends LinearOpMode{
    DcMotor topLeft,topRight,botRight,botLeft,catapultMotor;
    Servo LL, LR, RL, RR, ballStop;
    int startDelay = 1000;
    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Start Delay: ", startDelay /1000);
        //Get hardware maps for the motors
        topLeft = hardwareMap.dcMotor.get("topleft");
        topRight = hardwareMap.dcMotor.get("topright");
        botLeft = hardwareMap.dcMotor.get("botleft");
        botRight = hardwareMap.dcMotor.get("botright");
        catapultMotor = hardwareMap.dcMotor.get("catapult");
        LL = hardwareMap.servo.get("leftl");
        LR = hardwareMap.servo.get("leftr");
        RL = hardwareMap.servo.get("rightl");
        RR = hardwareMap.servo.get("rightr");
        ballStop = hardwareMap.servo.get("ballstop");

        LR.setDirection(Servo.Direction.REVERSE);
        RR.setDirection(Servo.Direction.REVERSE);
        LL.setPosition(0);
        LR.setPosition(0);
        RL.setPosition(0);
        RR.setPosition(0);
        ballStop.setPosition(.75);
        while(!isStarted())
        {
            telemetry.update();
            boolean justPressed = false;
            if(gamepad1.dpad_up && !justPressed)
            {
                startDelay = Range.clip(startDelay,1000,10000);
                telemetry.addData("Start Delay: ", startDelay /1000);
                telemetry.update();
                startDelay += 1000;
                while(gamepad1.dpad_up)
                {
                    justPressed = true;
                }
            }

            if(gamepad1.dpad_down  && !justPressed)
            {
                startDelay = Range.clip(startDelay,1000,10000);
                telemetry.addData("Start Delay: ", startDelay /1000);
                telemetry.update();
                startDelay -= 1000;
                while(gamepad1.dpad_down)
                {
                    justPressed = true;
                }
            }
        }
        startDelay = Range.clip(startDelay,1000,10000);
        waitForStart();
        try
        {
           Thread.sleep(startDelay);
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        shoot(3000);
        shoot(1000);
        spinLeft(1,1200);
        forward(1,1000);
        spinRight(1,1200);
        forward(1,750);
    }

    public void forward(double power, long duration) throws InterruptedException
    {

        topLeft.setPower(power);
        topRight.setPower(-power);
        botLeft.setPower(power);
        botRight.setPower(-power);
        Thread.sleep(duration);

    }

    public void spinLeft(double power, long duration) throws InterruptedException
    {

        topLeft.setPower(-power);
        topRight.setPower(-power);
        botLeft.setPower(-power);
        botRight.setPower(-power);
        Thread.sleep(duration);

    }

    public void spinRight(double power, long duration) throws InterruptedException
    {

        topLeft.setPower(power);
        topRight.setPower(power);
        botLeft.setPower(power);
        botRight.setPower(power);
        Thread.sleep(duration);

    }

    public void stop(long duration) throws InterruptedException
    {

        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
        Thread.sleep(duration);

    }
    public void shoot(long duration) throws InterruptedException
    {
        catapultMotor.setPower(-1);
        Thread.sleep(500);
        catapultMotor.setPower(0);
        ballStop.setPosition(0);
        Thread.sleep(duration);
    }
    public void strafeRight(double power, long duration) throws InterruptedException
    {

        topLeft.setPower(power);
        topRight.setPower(power);
        botLeft.setPower(-power);
        botRight.setPower(-power);
    }

    public void strafeLeft(double power, long duration) throws InterruptedException
    {

        topLeft.setPower(-power);
        topRight.setPower(-power);
        botLeft.setPower(power);
        botRight.setPower(power);
    }
    public void rotateback(double power, long duration) throws  InterruptedException
    {
        botRight.setPower(power);
        botLeft.setPower(-power);
    }
}
