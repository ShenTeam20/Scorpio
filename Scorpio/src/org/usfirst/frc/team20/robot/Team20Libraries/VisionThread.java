package org.usfirst.frc.team20.robot.Team20Libraries;

public class VisionThread extends Thread {

	public VisionTargeting2 vision = new VisionTargeting2();

	public VisionThread() {
		vision.init();
	}
	public void getProcessImage() {
		try {
			vision.processImage();
			
		} catch (Exception e) {
			System.out.println("getProcessImage() failed: " + e.toString());
		}
	}

	/*public void run() {
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			vision.processImage();
		}
	}*/

}
