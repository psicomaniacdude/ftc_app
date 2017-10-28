package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;

import java.util.Vector;

/**
 * Created by RoboPir8s on 9/30/2017.
 */
public class AutoTest extends LinearOpMode {

    DcMotor TopLeft, TopRight, BotLeft, BotRight;
    //We declare the Color Sensor as an I2c device so that we can set the I2c address, and then read the color
    //number value from the sensor. The color number is more useful than using the colorsensor.red command, because
    //it allows us to have to look at only one number to determine the color being viewed.
    I2cDevice JewelSensor;
    //The DeviceSynch makes it easier to read the values we want from the I2c Addresses.
    I2cDeviceSynch JewelReader;

    @Override
    public void runOpMode() throws InterruptedException {
        Vector<Integer> steps = new Vector<>(1);
        Vector<String> stringSteps = new Vector<>(1);
        instructions go = new instructions();

        TopLeft = hardwareMap.dcMotor.get("topleft");
        TopRight = hardwareMap.dcMotor.get("topright");
        BotLeft = hardwareMap.dcMotor.get("botleft");
        BotRight = hardwareMap.dcMotor.get("botright");

        JewelSensor = hardwareMap.i2cDevice.get("jewel");
        JewelReader = new I2cDeviceSynchImpl(JewelSensor, I2cAddr.create8bit(0x3c), false);


        while(!isStarted())
        {
            boolean autoPressed = false;
            if(gamepad1.a && !autoPressed)
            {
                steps.addElement(1);
                stringSteps.addElement("Forward Drive");

                while(gamepad1.a)
                {
                    autoPressed = true;
                }
            }
            waitForStart();

            for(int step = 0; step < steps.size(); step++) //This is where we define each case
            {
                switch (steps.get(step))
                {
                    case 1://shoot

                        go.Jewel_Read_Red(5000);
                        break;
                    /*case 2://pos 2

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
                        break;*/
                }
            }

        }

    }
    public class instructions
    {//This is where we define the steps for each case

        private double leftPower, rightPower,power = 1;

        public void Forward_Drive ( long duration, double power)throws InterruptedException
        {
            TopLeft.setPower(power);
            TopRight.setPower(power);
            BotLeft.setPower(-power);
            BotRight.setPower(-power);
            Thread.sleep(duration);
        }


        public void Jewel_Read_Red (long duration) throws InterruptedException
    {
        //TODO: 10/28/2017 Create a TurnRight, TurnLeft, and PlaceGlyph class.
        //TODO: 10/28/2017 Research how Vuforia works again, and create a VuforiaClass to replace the PlaceGlyph Class.

        //This is an array, and it is one byte long. We will be storing the values the color
        //sensor gets in here so that we can view them later.
        byte[] colorcache;
        //This saves the values that the JewelReader gives us, which will allow us to make decisions based off
        //those readings later.
        colorcache = JewelReader.read(0x04, 1);
        if (colorcache[0] >= 10 &&colorcache[0] <= 12)
        {
            TopLeft.setPower(power);
            TopRight.setPower(power);
            BotLeft.setPower(-power);
            BotRight.setPower(-power);
        }else{
            TopLeft.setPower(-power);
            TopRight.setPower(-power);
            BotLeft.setPower(power);
            BotRight.setPower(power);
        }
    }
        public void Jewel_Read_Blue (long duration) throws InterruptedException{
            byte[] colorcache;
            colorcache = JewelReader.read(0x04, 1);
            if (colorcache[0] >= 1 && colorcache[0] <= 3){
                TopLeft.setPower(power);
                TopRight.setPower(power);
                BotLeft.setPower(-power);
                BotRight.setPower(-power);
            }else {
                TopLeft.setPower(-power);
                TopRight.setPower(-power);
                BotLeft.setPower(power);
                BotRight.setPower(power);
            }
        }
    }
}
