package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by RoboPir8s on 10/5/2017.
 */

@TeleOp(name = "ColorTest", group = "LoopTeleOp")
public class ColorSensorTest extends OpMode {
    ColorSensor color;

    @Override
    public void init() {
        color = hardwareMap.colorSensor.get("color");
    }

    @Override
    public void loop() {
        telemetry.addData("Color", color.argb());
        telemetry.update();
        while(gamepad1.a){
            telemetry.addData("Red", color.red());
            telemetry.update();
        }
        while(gamepad1.b){
            telemetry.addData("Blue", color.blue());
            telemetry.update();
        }
        while(gamepad1.x){
            telemetry.addData("Green", color.green());
            telemetry.update();
        }
    }
}
