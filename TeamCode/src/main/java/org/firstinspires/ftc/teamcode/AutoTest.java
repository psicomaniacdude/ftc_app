package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Vector;

/**
 * Created by RoboPir8s on 9/30/2017.
 */
public class AutoTest extends LinearOpMode {

    DcMotor TopLeft, TopRight, BotLeft, BotRight;
    ColorSensor JewelSensor;
    Servo JewelServo;
    @Override
    public void runOpMode() throws InterruptedException {
        Vector<Integer> steps = new Vector<>(1);
        Vector<String> stringSteps = new Vector<>(1);
        instructions go = new instructions();

        TopLeft = hardwareMap.dcMotor.get("topleft");
        TopRight = hardwareMap.dcMotor.get("topright");
        BotLeft = hardwareMap.dcMotor.get("botleft");
        BotRight = hardwareMap.dcMotor.get("botright");

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

            /*for(int step = 0; step < steps.size(); step++) //This is where we define each case
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
            }*/

        }

    }
    public class instructions
    {//This is where we define the steps for each case
    private double leftPower, rightPower,power = 1;

        public void Forward_Drive ( long duration)throws InterruptedException
        {
            TopLeft.setPower(power);
            TopRight.setPower(power);
            BotLeft.setPower(-power);
            BotRight.setPower(-power);
        }


        public void Jewel_Read (long duration) throws InterruptedException
    {
        telemetry.addData("Jewel", JewelSensor.argb());
        telemetry.update();
    }
    }
}
