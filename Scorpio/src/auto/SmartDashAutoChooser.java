package auto;

import org.usfirst.frc.team20.robot.Scorpio;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashAutoChooser extends Scorpio {

	public SmartDashAutoChooser() {

	}

	public void createAutoMode() {
		String tempString = SmartDashboard.getString("DB/String 0");
		if (tempString.length() > 0) {
			int tempInt;
			try {
				tempInt = Integer.parseInt(tempString);
			} catch (Exception e) {
				tempInt = 0;
			}
			Scorpio.autoModes.createMainAutoMode(tempInt, SmartDashboard.getBoolean("DB/Button 1"),
					SmartDashboard.getBoolean("DB/Button 3"));
		} else {

		}
	}
}
