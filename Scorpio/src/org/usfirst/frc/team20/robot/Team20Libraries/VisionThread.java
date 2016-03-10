package org.usfirst.frc.team20.robot.Team20Libraries;

public class VisionThread extends Thread {

	public VisionTargeting vision = new VisionTargeting();

	public VisionThread() {
		vision.init();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			vision.processImage();
		}
	}

}
