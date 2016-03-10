package org.usfirst.frc.team20.robot.Team20Libraries;

public class GeneralPIDExecutor extends Thread {
	SynchronousPID generalPIDExecuter = new SynchronousPID(0, 0, 0);
	private double pv = 0;
	private double sp = 0;

	public GeneralPIDExecutor(double minOutputRange, double maxOutputRange, double minInputRange, double maxInputRange,
			boolean continuous) {
		generalPIDExecuter.setOutputRange(minOutputRange, maxOutputRange);
		generalPIDExecuter.setInputRange(minInputRange, maxInputRange);
		generalPIDExecuter.setContinuous(continuous);
	}

	public void setPID(double p, double i, double d) {
		generalPIDExecuter.setPID(p, i, d);
	}

	public void setPV(double pv) {
		this.pv = pv;
	}

	public void setSP(double sp) {
		this.sp = sp;
	}

	public double getOP() {
		return generalPIDExecuter.get();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			generalPIDExecuter.setSetpoint(sp);
			generalPIDExecuter.calculate(pv);
		}
	}

}