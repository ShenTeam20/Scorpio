package org.usfirst.frc.team20.robot.Team20Libraries;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;

public class AHRSGyro {
	public com.kauailabs.navx.frc.AHRS ahrs;

	public AHRSGyro() {
		try {
			ahrs = new AHRS(SerialPort.Port.kMXP);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
	}
}