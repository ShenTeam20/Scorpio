package org.usfirst.frc.team20.robot.Team20Libraries;

/**
 * 
 * @author KNOX & Hiren Bhavsar
 * 
 */
import edu.wpi.first.wpilibj.CANTalon;

public class T20CANTalon extends CANTalon implements Runnable {

	protected double zero;
	protected double span;
	protected double scaleXDZero;
	protected double scaleXDSpan;
	protected double avgCurrent = 0;
	public double currentLimit = 100;
	private int polarity = 1;
	private String scaleXDUOM;
	public String subsystemName = "N/A";

	private double p, i, d;
	public boolean homed;

	public T20CANTalon(int deviceNumber) {
		super(deviceNumber);

	}

	public void setCurrentLimit(double currentLimit) {
		this.currentLimit = currentLimit;
	}

	public void disableControl() {
		super.setPID(0, 0, 0);
		super.disableControl();
	}

	public void enableControl() {
		super.enableControl();
		super.setPID(p, i, d);
	}

	// public void setP(double p) {
	// this.p = p;
	// super.setP(p);
	// }
	//
	// public void setI(double i) {
	// this.i = i;
	// super.setI(i);
	// }
	//
	// public void setD(double d) {
	// this.d = d;
	// super.setD(d);
	// }

	public void setPID(double p, double i, double d) {
		this.p = p;
		this.i = i;
		this.d = d;
		super.setPID(p, i, d);
	}

	/**
	 * setXDScale set the conversion between engineering units and turns for
	 * <br>
	 * servo drive. <br>
	 * 
	 * setX sets the set-point in turns. setXEU will set the set-point scaled
	 * <br>
	 * to engineering units. The scaling is performed by setting the XDScale.
	 * <br>
	 * <br>
	 * I.E. <br>
	 * With zero = 5 and span = 7 turns (set in constructor)<br>
	 * setXDScale(0, 360, "deg");<br>
	 * <br>
	 * A call to SetXEU would yield the following.<br>
	 * setXEU(0) would give 5 turns<br>
	 * setXEU(180) would give 6 turns<br>
	 * setXEU(360) would give 7 turns<br>
	 * <br>
	 * XDScale may also be inverted. so if zero and span are 5 to 7 turns <br>
	 * and XDScale is set zeroXD = 360 and spanXD = 0. <br>
	 * then following would be true<br>
	 * setXDScale(360, 0, "deg");<br>
	 * <br>
	 * A call to SetXEU would yield the following.<br>
	 * setXEU(0) would give 7 turns<br>
	 * setXEU(180) would give 6 turns<br>
	 * setXEU(360) would give 5 turns<br>
	 * <br>
	 * By default XDScale is 0-1.<br>
	 * Engineering units are informational only.<br>
	 * <br>
	 * 
	 * @param zeroXD
	 *            XDScale zero parameter<br>
	 * @param spanXD
	 *            XDScale span parameter<br>
	 * @param engineeringUnits
	 *            Text informational only.<br>
	 */
	public void setXDScale(double zeroXD, double spanXD, String engineeringUnits) {
		this.scaleXDZero = zeroXD;
		this.scaleXDSpan = spanXD;
		this.scaleXDUOM = engineeringUnits;
		if (spanXD < zeroXD && this.zero < this.span)
			this.polarity = -1;
		if (spanXD > zeroXD && this.zero > this.span)
			this.polarity = -1;
	}

	public void setXDScale(double zeroXD, double spanXD, double zero, double span, String engineeringUnits) {
		this.scaleXDZero = zeroXD;
		this.scaleXDSpan = spanXD;
		this.scaleXDUOM = engineeringUnits;
		this.span = span;
		this.zero = zero;
		if (spanXD < zeroXD && this.zero < this.span)
			this.polarity = -1;
		if (spanXD > zeroXD && this.zero > this.span)
			this.polarity = -1;
	}

	/**
	 * Sets the position of controller to setpoint scaled for engineering units.
	 * see setXDScale for details.
	 * 
	 * @param setPoint
	 *            Set-point in engineering units.
	 * 
	 */
	public void setXEU(double setPoint) {
		this.set(this.getEUToTicks(setPoint));
	}

	/**
	 * returns turns scaled from engineering units. see XD scale.
	 * 
	 * @param setPointEU
	 * @return
	 */
	public double getEUToTicks(double engUnits) {
		double rangeTicks;
		double rangeXD;
		double pctXdScale;

		rangeTicks = this.span - this.zero; // range in turns
		rangeXD = this.scaleXDSpan - this.scaleXDZero; // range in XD units
														// (Engineering units)
		pctXdScale = (engUnits - this.scaleXDZero) / rangeXD;
		return (pctXdScale * rangeTicks + this.zero);
	}

	public double getTicksToEU(double turns) {
		double rangeXD;
		double pctTurns;

		rangeXD = this.scaleXDSpan - this.scaleXDZero; // range in XD units
														// (Engineering units)
		pctTurns = (turns - this.zero) / this.span;
		return polarity * (pctTurns * rangeXD + this.scaleXDZero);

	}

	@Override
	public String getSmartDashboardType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		avgCurrent = (avgCurrent * .99) + (this.getOutputCurrent() * .01);
		if (avgCurrent >= currentLimit) {
			this.disableControl();
			System.out.println("The " + subsystemName + " has tripped on current!~~~" + "The " + subsystemName
					+ " has tripped on current!~~~" + "The " + subsystemName + " has tripped on current!");
		}
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
