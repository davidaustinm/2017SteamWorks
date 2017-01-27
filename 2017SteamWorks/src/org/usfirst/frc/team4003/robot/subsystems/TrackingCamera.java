package org.usfirst.frc.team4003.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TrackingCamera extends Subsystem implements Runnable {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CvSink cvSink;
	CvSource outputStream;
	Mat source, hsv, mask, hierarchy;
	Scalar lowerHSV, upperHSV;
	Thread tracking;
	Object visionLock = new Object();
	boolean trackingOn = true;
	UsbCamera camera;
	public TrackingCamera() {         
         source = new Mat();
         hsv = new Mat();
         mask = new Mat();
         hierarchy = new Mat();
         lowerHSV = new Scalar(0,108,94);
         upperHSV = new Scalar(32,206,255);
         tracking = new Thread(this);
         
	}
	public void trackingOn(boolean on) {
		synchronized(visionLock){
			trackingOn = on;
		}
		if(on){
			tracking = new Thread(this);
			tracking.start();
		}
	}
	public boolean isTrackingOn() {
		synchronized(visionLock){
			return trackingOn;
		}
	}
	public void startCamera() {
		tracking.start();
	}
	public void run() {
		camera = CameraServer.getInstance().startAutomaticCapture("Camera", 0);
        camera.setResolution(320,240);
        camera.setExposureManual(3);
        
        cvSink = CameraServer.getInstance().getVideo();
        outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
        while(isTrackingOn()) {
            camera.setExposureManual(3);
            cvSink.grabFrame(source);
            Imgproc.cvtColor(source, hsv, Imgproc.COLOR_BGR2HSV);
            Core.inRange(hsv, lowerHSV, upperHSV, mask);
            ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, 
            		Imgproc.CHAIN_APPROX_SIMPLE);
            for (int i = 0; i < contours.size(); i++) {
                Rect bbox = Imgproc.boundingRect(contours.get(i));
                Imgproc.rectangle(source, bbox.tl(), bbox.br(), new Scalar(0,255,0),2);
                //Imgproc.drawContours(source, contours, i, new Scalar(255,0,0),3);
                Imgproc.contourArea(contours.get(i));
            }
            outputStream.putFrame(source);
        }
        CameraServer.getInstance().removeCamera("Camera");
        CameraServer.getInstance().removeServer("Blur");
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

