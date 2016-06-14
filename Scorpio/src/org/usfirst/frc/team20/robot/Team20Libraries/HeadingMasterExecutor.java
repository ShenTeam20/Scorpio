package org.usfirst.frc.team20.robot.Team20Libraries;

public class HeadingMasterExecutor extends Thread {
	private double pv = 0;
	private double sp = 0;

	SynchronousPID headingMasterPID = new SynchronousPID(.1, 0, .6);

	public HeadingMasterExecutor() {
		headingMasterPID.setOutputRange(-0.5, 0.5); // In Feet per Second
		headingMasterPID.setInputRange(0, 360); // In Degrees
		headingMasterPID.setContinuous();
	}

	public void setPID(double p, double i, double d) {
		headingMasterPID.setPID(p, i, d);
	}

	public void setOPRange(double minimumOutput, double maximumOutput) {
		headingMasterPID.setOutputRange(minimumOutput, maximumOutput);
	}

	public void setPV(double pv) {
		this.pv = pv;
	}

	public void setSP(double sp) {
		this.sp = sp;
	}

	public double getOP() {
		return headingMasterPID.get();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			headingMasterPID.setSetpoint(sp);
			headingMasterPID.calculate(pv);
		}
	}

}