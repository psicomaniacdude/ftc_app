package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.Vector;
/**
 * Created by RoboPir8s on 1/26/2017
 */
@Autonomous(name = "Yoga_Blue", group ="Autonomous")
public class Yoga_Blue extends LinearOpMode
{
    DcMotor topLeft,topRight,botRight,botLeft,catapultMotor;
    Servo LL, LR, RL, RR, ballStop;
    OpticalDistanceSensor ODSSensor;

    public void runOpMode() throws InterruptedException {
        int startDelay = 1000;
        Vector<Integer> steps = new Vector<>(1);
        Vector<String> stringSteps = new Vector<>(1);
        instructions go = new instructions();

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
        ODSSensor = hardwareMap.opticalDistanceSensor.get("ods");
        LR.setDirection(Servo.Direction.REVERSE);
        RR.setDirection(Servo.Direction.REVERSE);
        LL.setPosition(0);
        LR.setPosition(0);
        RL.setPosition(0);
        RR.setPosition(0);
        ballStop.setPosition(.75);
//        double LightReading = ODSSensor.getLightDetected();
//        double PowerLeft = ((.5-ODSSensor.getLightDetected())*.3);
//        double PowerRight = (ODSSensor.getLightDetected() -.255)*.3;
        while(!isStarted())
        {
            telemetry.addData("Start Delay: ", startDelay / 1000);
            telemetry.addData("Steps: ",stringSteps.toString());
            boolean justPressed = false;
            if(gamepad1.dpad_up)
            {
                startDelay = Range.clip(startDelay, 1000, 10000);
                startDelay += 1000;

                while(gamepad1.dpad_up)
                {
                    justPressed = true;
                }
            }

            if(gamepad1.dpad_down  && !justPressed)
            {
                startDelay = Range.clip(startDelay,1000,10000);
                startDelay -= 1000;

                while(gamepad1.dpad_down)
                {
                    justPressed = true;
                }
            }
            boolean autoPressed = false;
            if(gamepad1.x && !autoPressed)
            {
                steps.addElement(6);
                stringSteps.addElement("Skooch");
                while(gamepad1.x)
                {
                    autoPressed = true;
                }
            }
            if (gamepad1.y && autoPressed)
            {
                steps.addElement(5);
                stringSteps.addElement("Button Pushers");
                while(gamepad1.y)
                {
                    autoPressed = true;
                }

            }
            if(gamepad1.b  && !autoPressed) {
                steps.addElement(3);
                stringSteps.addElement("Pos 1");

                while(gamepad1.b)
                {
                    autoPressed = true;
                }
            }

            if (gamepad1.a) {
                steps.addElement(1);
                stringSteps.addElement("Shoot");

                while (gamepad1.a) {
                    autoPressed = true;
                }
            }
            if(gamepad1.dpad_left && !autoPressed)
            {
                steps.addElement(4);
                stringSteps.addElement("Line Follower");
                while(gamepad1.dpad_left) {
                    autoPressed = true;
                }
            }

            if (gamepad1.start &&!autoPressed) {//removes the last element from the instructions
                steps.removeElement(steps.lastElement());
                stringSteps.remove(stringSteps.lastElement());
                while(gamepad1.start)
                {
                    autoPressed = true;
                }
            }


            while(gamepad1.left_trigger > .5) {

                if (gamepad1.b && !autoPressed) {
                    steps.addElement(2);
                    stringSteps.addElement("Pos 2");

                    while (gamepad1.b) {
                        autoPressed = true;
                    }

                }
                if (gamepad1.a) {
                    steps.addElement(1);
                    stringSteps.addElement("Shoot");

                    while (gamepad1.a) {
                        autoPressed = true;
                    }
                }

                if (gamepad1.y && autoPressed)
                {
                    steps.addElement(5);
                    stringSteps.addElement("Button Pushers");
                    while(gamepad1.y)
                    {
                        autoPressed = true;
                    }

                }
                if(gamepad1.dpad_left && !autoPressed)
                {
                    steps.addElement(4);
                    stringSteps.addElement("Line Follower");
                    while(gamepad1.dpad_left) {
                        autoPressed = true;
                    }
                }

                if (gamepad1.left_trigger > .9 && gamepad1.left_bumper && gamepad1.right_trigger > .9 && gamepad1.right_bumper && !autoPressed) {
                    steps.addElement(99);
                    stringSteps.addElement("TESTING");

                    while (gamepad1.left_trigger > .9 && gamepad1.left_bumper && gamepad1.right_trigger > .9 && gamepad1.right_bumper) {
                        autoPressed = true;
                    }
                }
                telemetry.update();
            }
            telemetry.update();
        }

        startDelay = Range.clip(startDelay,1000,10000);
        waitForStart();
        boolean exit = false;
        try
        {
            Thread.sleep(startDelay);
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        for(int step = 0; step < steps.size(); step++)
        {
            switch (steps.get(step))
            {
                case 1://shoot

                    go.shoot(2500);
                    break;
                case 2://pos 2

                    go.spinLeft(1, 1200);
                    go.forward(1, 500);
                    go.spinRight(1, 1200);
                    go.forward(1, 1000);
                    break;
                case 3://pos 1

                    go.forward(-1,225);
                    go.spinLeft(1, 1500);
                    go.stop(1000);
                    go.forward(1, 2500);
                    go.stop(3000);
                    break;
                case 4:
                    long duration =System.currentTimeMillis();
                    while (!exit) {
                        go.lineFollow();
                        if (System.currentTimeMillis() - duration < 50000) exit =true;
                    }
                    break;
                case 5:
                    step = steps.size();//needs to be updated will button push program do this last
                    break;
                case 6:
                    go.Skooch(1200);
                    break;
                case 99:
                    step = steps.size();
                    break;
            }
        }

    }

    public class instructions {
        private double leftPower, rightPower,power = 1;
        public  void buttonPush()// add button pusher here, we neeed to figure out how the bot is going to be position
        {
            //placeholder
        }
        public void Skooch(long duration) throws InterruptedException
        {

            power *= -1;
            strafeLeft(power,duration);
            topRight.setPower(0);
            topLeft.setPower(0);
            botLeft.setPower(0);
            botRight.setPower(0);
        }
        public  void lineFollow()
        {
            telemetry.addData("light", ODSSensor.getLightDetected());
            telemetry.update();
            double motorMotifier = (ODSSensor.getLightDetected() -.255);
            if (motorMotifier <= 0)
            {
                leftPower = motorMotifier * .75;
                rightPower = .75;
            }
            else
            {
                leftPower = .75;
                rightPower = motorMotifier *.75;
            }
            Range.clip(leftPower,-1,1);
            Range.clip(rightPower,-1,1);
            topLeft.setPower(leftPower);
            botLeft.setPower(leftPower);
            topRight.setPower(rightPower);
            botRight.setPower(rightPower);
//            topLeft.setPower(.5 + ((ODSSensor.getLightDetected() - .255) * .3));
//            topRight.setPower(.5 + ((ODSSensor.getLightDetected()+ .255)*.3));
//            botRight.setPower(.5 + ((ODSSensor.getLightDetected()+ .255)*.3));
//            botLeft.setPower(.5 + ((ODSSensor.getLightDetected() - .255) * .3));
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
        public void strafeLeft(double power, long duration) throws InterruptedException
        {

            topLeft.setPower(power);
            topRight.setPower(power);
            botLeft.setPower(-power);
            botRight.setPower(-power);
            Thread.sleep(duration);
        }

        public void strafeRight(double power, long duration) throws InterruptedException
        {

            topLeft.setPower(-power);
            topRight.setPower(-power);
            botLeft.setPower(power);
            botRight.setPower(power);
            Thread.sleep(duration);
        }
    }
}
