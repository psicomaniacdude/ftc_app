package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.Vector;
/**
 * Created by RoboPir8s on 1/26/2017
 */
@Autonomous(name = "AutoTest[v", group ="Autonomous")
public class AutoTest extends LinearOpMode
{
    DcMotor topLeft,topRight,botRight,botLeft,RightCollectMotor,LeftCollectMotor;
    Servo trayServo;
    I2cDevice JewelSensor;
    I2cDeviceSynch JewelReader;

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
        RightCollectMotor = hardwareMap.dcMotor.get("collectright");
        LeftCollectMotor = hardwareMap.dcMotor.get("collectleft");
        trayServo = hardwareMap.servo.get("tray");
        /*JewelSensor = hardwareMap.i2cDevice.get("jewel");
        JewelReader = new I2cDeviceSynchImpl(JewelSensor, I2cAddr.create8bit(0x3c), false);
*/
        while(!isStarted())
        {
            telemetry.addData("Start Delay: ", startDelay / 1000);
            telemetry.addData("Steps: ",stringSteps.toString());
            boolean justPressed = false;
            boolean autoPressed = false;

            trayServo.setPosition(.55);

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
            if (gamepad1.a) {
                steps.addElement(1);
                stringSteps.addElement("Forward");

                while (gamepad1.a) {
                    autoPressed = true;
                }
            }

            if (gamepad1.b && !autoPressed) {
                steps.addElement(2);
                stringSteps.addElement("Backward");

                while (gamepad1.b) {
                    autoPressed = true;
                }

            }
            if(gamepad1.y  && !autoPressed) {
                steps.addElement(3);
                stringSteps.addElement("Jewel Read Blue");

                while(gamepad1.y)
                {
                    autoPressed = true;
                }
            }
            if(gamepad1.dpad_left && !autoPressed)
            {
                steps.addElement(4);
                stringSteps.addElement("Dump");
                while(gamepad1.dpad_left) {
                    autoPressed = true;
                }
            }
            if (gamepad1.right_bumper && autoPressed)
            {
                steps.addElement(5);
                stringSteps.addElement("Spin Right");
                while(gamepad1.right_bumper)
                {
                    autoPressed = true;
                }

            }
            if(gamepad1.left_bumper && !autoPressed)
            {
                steps.addElement(6);
                stringSteps.addElement("Spin Left");
                while(gamepad1.left_bumper)
                {
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
                case 1://Drive Forward

                    go.forward(.5,1000);
                    break;
                case 2://Drive Backward
                    go.backward(1,1000);
                    break;
                case 3://Score the Blue Jewel
                    //go.Jewel_Read_Blue(1, 2000);
                case 4://Deposit the Glyph
                    go.Dump(1, 1000);
                    break;
                case 5://Turn to the Left
                    go.spinRight(1,500);
                    break;
                case 6://Turn to the Right
                    go.spinLeft(1,500);
                case 7:
                    step = steps.size();//needs to be updated will button push program do this last
                    break;
                case 8:
                    break;
                case 99:
                    step = steps.size();
                    break;
            }
        }

    }

    public class instructions {
        private double leftPower, rightPower, power = 1;

        public void buttonPush()// add button pusher here, we neeed to figure out how the bot is going to be position
        {
            //placeholder
        }

        public void Dump(double power, long duration) throws InterruptedException {
            trayServo.setPosition(1);
            Thread.sleep(duration);
        }

        /*public void Jewel_Read_Red(double power, long duration) throws InterruptedException {
            //TODO: 10/28/2017 Research how Vuforia works again, and create a VuforiaClass to replace the PlaceGlyph Class.


            //This is an array, and it is one byte long. We will be storing the values the color
            //sensor gets in here so that we can view them later.
            byte[] colorcache;
            //This saves the values that the JewelReader gives us, which will allow us to make decisions based off
            //those readings later.
            colorcache = JewelReader.read(0x04, 1);
            if (colorcache[0] >= 10 && colorcache[0] <= 12)
            {
                topLeft.setPower(power);
                topRight.setPower(power);
                botLeft.setPower(-power);
                botRight.setPower(-power);
            }else{
                topLeft.setPower(-power);
                topRight.setPower(-power);
                botLeft.setPower(power);
                botRight.setPower(power);

            }
        }
        public void Jewel_Read_Blue (double power, long duration) throws InterruptedException {
            byte[] colorcache;
            colorcache = JewelReader.read(0x04, 1);
            if (colorcache[0] >= 1 && colorcache[0] <= 3) {
                topLeft.setPower(power);
                topRight.setPower(power);
                botLeft.setPower(-power);
                botRight.setPower(-power);
            } else {
                topLeft.setPower(-power);
                topRight.setPower(-power);
                botLeft.setPower(power);
                botRight.setPower(power);
            }
        }*/

        public void forward(double power, long duration) throws InterruptedException
        {
            topLeft.setPower(-power);
            topRight.setPower(-power);
            botLeft.setPower(-power);
            botRight.setPower(-power);
            Thread.sleep(duration);
        }
        public void backward(double power,long duration) throws InterruptedException
        {
            topLeft.setPower(power);
            topRight.setPower(power);
            botLeft.setPower(power);
            botRight.setPower(power);
            Thread.sleep(duration);
        }
        public void spinLeft(double power, long duration) throws InterruptedException
        {
            topLeft.setPower(power);
            topRight.setPower(-power);
            botLeft.setPower(power);
            botRight.setPower(-power);
            Thread.sleep(duration);

        }

        public void spinRight(double power, long duration) throws InterruptedException
        {


            topLeft.setPower(-power);
            topRight.setPower(power);
            botLeft.setPower(-power);
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
