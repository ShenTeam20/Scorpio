package org.usfirst.frc.team20.robot.Team20Libraries;

import java.util.Comparator;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Point;
import com.ni.vision.NIVision.Rect;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;

public class VisionTargeting2 {
	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport> {
		double PercentAreaToImageArea;
		double Area;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;
		double AreaRatio;

		public int compareTo(ParticleReport r) {
			// return (int) (r.Area - this.Area);
			return (int) (Math.abs(r.BoundingRectLeft - r.BoundingRectRight)
					- Math.abs(this.BoundingRectLeft + this.BoundingRectRight));
		}

		public int compare(ParticleReport r1, ParticleReport r2) {
			return (int) (Math.abs(r1.BoundingRectLeft - r1.BoundingRectRight)
					- Math.abs(r2.BoundingRectLeft - r2.BoundingRectRight));
		}
	};

	double AREA_MINIMUM;
	double LONG_RATIO;
	double SHORT_RATIO;
	double SCORE_MIN;
	double VIEW_ANGLE;
	Image frame;
	Image binaryFrame;
	int imaqError;
	int session;
	Image filteredImage;
	Image particleBinaryFrame;
	NIVision.StructuringElement box;
	NIVision.ParticleFilterCriteria2 criteria[];
	NIVision.ParticleFilterOptions2 filterOptions;
	int numParticles;
	double verticalImage;
	double horizontalImage;
	double distance;
	NIVision.Range TARGET_HUE_RANGE;
	NIVision.Range TARGET_SAT_RANGE;
	NIVision.Range TARGET_VAL_RANGE;
	double leftRec;
	double rightRec;
	double topRec;
	double bottomRec;
	double centerRec[] = new double[2];

	{
		centerRec[0] = (leftRec + rightRec) / 2;
		centerRec[1] = (topRec + bottomRec) / 2;
	}

	double boundingRight;
	double boundingLeft;
	double boundingTop;
	double boundingBottom;
	double Tft;
	double TftV;
	double horizontalPixel;
	double verticalPixel;
	double angle;
	double crosshair;
	boolean cameraStarted = true;
	boolean cameraWorks = true;
	public double width;
	double height;

	public boolean targetAquired = false;

	public boolean init() {
		cameraStarted = true;
		try {
			frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
			binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
			particleBinaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
			session = NIVision.IMAQdxOpenCamera("cam3", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
			box = new NIVision.StructuringElement(4, 4, 1);// 3,3,1
			criteria = new NIVision.ParticleFilterCriteria2[1];
			criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA,
					AREA_MINIMUM, 100.0, 0, 0);
			filterOptions = new NIVision.ParticleFilterOptions2(0, 0, 1, 1);
			NIVision.IMAQdxConfigureGrab(session);
			NIVision.IMAQdxStartAcquisition(session);
			// TARGET_HUE_RANGE = new NIVision.Range(54, 96);
			// TARGET_SAT_RANGE = new NIVision.Range(188, 255);
			// TARGET_VAL_RANGE = new NIVision.Range(69, 255);

			// 3:22

			TARGET_HUE_RANGE = new NIVision.Range(71, 132);
			TARGET_SAT_RANGE = new NIVision.Range(236, 255);
			TARGET_VAL_RANGE = new NIVision.Range(135, 238);

			// filteredImage = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
			width = 0;
		} catch (Exception e) {
			System.out.println("Print stack trace " + e.toString());
			cameraStarted = false;
		}
		return cameraStarted;
	}

	public VisionTargeting2() {
		System.out.println("Starting Init");
		AREA_MINIMUM = 0.012;
		LONG_RATIO = 2.22;
		SHORT_RATIO = 1.4;
		SCORE_MIN = 75.0;
		VIEW_ANGLE = 49.4;
		verticalImage = 180;
		horizontalImage = 240;
		distance = -1;
	}

	private boolean getImage() {
		cameraWorks = true;
		try {
			NIVision.IMAQdxGrab(session, frame, 1);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			cameraWorks = false;
		}
		return cameraWorks;
	}

	private void segmentImage() {
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE, TARGET_SAT_RANGE,
				TARGET_VAL_RANGE);
		imaqError = NIVision.imaqParticleFilter4(particleBinaryFrame, binaryFrame, criteria, filterOptions, null);
		numParticles = NIVision.imaqCountParticles(particleBinaryFrame, 1);
	}
	// test code jjb 02-21-2016 return just one rectangle.

	private void drawRectangle_test() {
		int testWidth = 0;
		Rect r = null;
		for (int particleIndex = 0; particleIndex < numParticles; particleIndex++) {
			ParticleReport par = new ParticleReport();
			par.Area = NIVision.imaqMeasureParticle(particleBinaryFrame, particleIndex, 0,
					NIVision.MeasurementType.MT_AREA);
			par.BoundingRectTop = NIVision.imaqMeasureParticle(particleBinaryFrame, particleIndex, 0,
					NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
			par.BoundingRectLeft = NIVision.imaqMeasureParticle(particleBinaryFrame, particleIndex, 0,
					NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
			par.BoundingRectBottom = NIVision.imaqMeasureParticle(particleBinaryFrame, particleIndex, 0,
					NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
			par.BoundingRectRight = NIVision.imaqMeasureParticle(particleBinaryFrame, particleIndex, 0,
					NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
			if ((Math.abs((int) (par.BoundingRectLeft - par.BoundingRectRight)) > testWidth)) {
				testWidth = Math.abs((int) (par.BoundingRectLeft - par.BoundingRectRight));
				r = new NIVision.Rect((int) par.BoundingRectTop, (int) par.BoundingRectLeft,
						Math.abs((int) (par.BoundingRectTop - par.BoundingRectBottom)),
						Math.abs((int) (par.BoundingRectLeft - par.BoundingRectRight)));
				// NIVision.imaqDrawShapeOnImage(binaryFrame, binaryFrame, r,
				// DrawMode.DRAW_VALUE,
				// ShapeMode.SHAPE_RECT, 150f);
				leftRec = par.BoundingRectLeft - horizontalImage;
				rightRec = par.BoundingRectRight - horizontalImage;
				topRec = -(par.BoundingRectTop - verticalImage);
				bottomRec = -(par.BoundingRectBottom - verticalImage);
				centerRec = new double[2];
				centerRec[0] = (leftRec + rightRec) / 2;
				centerRec[1] = (topRec + bottomRec) / 2;
				boundingRight = par.BoundingRectRight;
				boundingLeft = par.BoundingRectLeft;
				boundingTop = par.BoundingRectTop;
				boundingBottom = par.BoundingRectBottom;
				crosshair = par.BoundingRectTop;
			}
		}
		NIVision.imaqDrawShapeOnImage(binaryFrame, binaryFrame, r, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 150f);
	}

	private void drawRectangle() {
		width = 0;
		height = 0;
		boolean targetOk = false;
		double thisHeight;
		double thisWidth;
		double thisOnOffRatio = 0;
		double aspectRatio = 0;
		ParticleReport par[] = new ParticleReport[numParticles];

		for (int particleIndex = 0; particleIndex < numParticles; particleIndex++) {

			par[particleIndex].Area = NIVision.imaqMeasureParticle(particleBinaryFrame, particleIndex, 0,
					NIVision.MeasurementType.MT_AREA);
			par[particleIndex].BoundingRectTop = NIVision.imaqMeasureParticle(particleBinaryFrame, particleIndex, 0,
					NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
			par[particleIndex].BoundingRectLeft = NIVision.imaqMeasureParticle(particleBinaryFrame, particleIndex, 0,
					NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
			par[particleIndex].BoundingRectBottom = NIVision.imaqMeasureParticle(particleBinaryFrame, particleIndex, 0,
					NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
			par[particleIndex].BoundingRectRight = NIVision.imaqMeasureParticle(particleBinaryFrame, particleIndex, 0,
					NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
			par[particleIndex].AreaRatio = par[particleIndex].Area
					/ (Math.abs(par[particleIndex].BoundingRectLeft - par[particleIndex].BoundingRectRight)
							* Math.abs(par[particleIndex].BoundingRectBottom - par[particleIndex].BoundingRectTop));
		}

		for (int particleIndex = 0; particleIndex < numParticles; particleIndex++) {

			thisWidth = Math.abs(par[particleIndex].BoundingRectLeft - par[particleIndex].BoundingRectRight);
			thisHeight = Math.abs(par[particleIndex].BoundingRectBottom - par[particleIndex].BoundingRectTop);
			aspectRatio = thisHeight / thisWidth;
			if (thisWidth > 30 && thisWidth < 117 && par[particleIndex].PercentAreaToImageArea < .6
					&& par[particleIndex].AreaRatio < .25 && aspectRatio > .3 && aspectRatio < .9) {
				// Here we have a valid candidate.
				double tempLeftRec, tempRightRec, tempTopRec, tempBottomRec;
				tempLeftRec = par[particleIndex].BoundingRectLeft - horizontalImage;
				tempRightRec = par[particleIndex].BoundingRectRight - horizontalImage;
				tempTopRec = -(par[particleIndex].BoundingRectTop - verticalImage);
				tempBottomRec = -(par[particleIndex].BoundingRectBottom - verticalImage);

				double tempcenterRec[] = new double[2];
				double bestDistance = 0;
				double tempDistance;

				tempcenterRec[0] = (tempLeftRec + tempRightRec) / 2;
				tempcenterRec[1] = (tempTopRec + tempBottomRec) / 2;

				tempDistance = (tempcenterRec[1] * tempcenterRec[1] + tempcenterRec[0] * tempcenterRec[0]);

				if (!targetOk || tempDistance < bestDistance) {
					// System.out.println(onOffRatio + " " + thisWidth + " " +
					// aspectRatio);
					width = thisWidth;
					par[particleIndex].AreaRatio = thisOnOffRatio;
					Rect r = new NIVision.Rect((int) par[particleIndex].BoundingRectTop,
							(int) par[particleIndex].BoundingRectLeft,
							Math.abs(
									(int) (par[particleIndex].BoundingRectTop - par[particleIndex].BoundingRectBottom)),
							Math.abs((int) (par[particleIndex].BoundingRectLeft
									- par[particleIndex].BoundingRectRight)));
					NIVision.imaqDrawShapeOnImage(binaryFrame, binaryFrame, r, DrawMode.DRAW_VALUE,
							ShapeMode.SHAPE_RECT, 150f);
					targetOk = true;
					// centerRec = new double[2];
					bestDistance = tempDistance;
					boundingRight = par[particleIndex].BoundingRectRight;
					boundingLeft = par[particleIndex].BoundingRectLeft;
					boundingTop = par[particleIndex].BoundingRectTop;
					boundingBottom = par[particleIndex].BoundingRectBottom;
					leftRec = tempLeftRec;
					rightRec = tempRightRec;
					topRec = tempTopRec;
					bottomRec = tempBottomRec;
					centerRec[0] = tempcenterRec[0];
					centerRec[1] = tempcenterRec[1];
					height = Math.abs(par[particleIndex].BoundingRectBottom - par[particleIndex].BoundingRectTop);
				}
			}

		} // for
		targetAquired = targetOk;
	}

	private void drawCenterCrosshairs() {
		Point startH = new NIVision.Point((int) (horizontalImage - 10), (int) (verticalImage));
		Point endH = new NIVision.Point((int) (horizontalImage + 10), (int) verticalImage);
		NIVision.imaqDrawLineOnImage(binaryFrame, binaryFrame, DrawMode.DRAW_VALUE, startH, endH, 200f);
		Point startV = new NIVision.Point((int) (horizontalImage), (int) (verticalImage - 10));
		Point endV = new NIVision.Point((int) (horizontalImage), (int) (verticalImage + 10));
		NIVision.imaqDrawLineOnImage(binaryFrame, binaryFrame, DrawMode.DRAW_VALUE, startV, endV, 200f);
	}

	private void drawCenterRecCrosshairs() {
		Point startHrec = new NIVision.Point((int) (centerRec[0] + horizontalImage - 10), (int) (crosshair + 10));
		Point endHrec = new NIVision.Point((int) (centerRec[0] + horizontalImage + 10), (int) (crosshair - 10));
		NIVision.imaqDrawLineOnImage(binaryFrame, binaryFrame, DrawMode.DRAW_VALUE, startHrec, endHrec, 150f);
		Point startVrec = new NIVision.Point((int) (centerRec[0] + horizontalImage - 10), (int) (crosshair - 10));
		Point endVrec = new NIVision.Point((int) (centerRec[0] + horizontalImage + 10), (int) (crosshair + 10));
		NIVision.imaqDrawLineOnImage(binaryFrame, binaryFrame, DrawMode.DRAW_VALUE, startVrec, endVrec, 150f);
	}

	public void processImage() {
		if (getImage()) {
			segmentImage();
			drawRectangle();
			// drawCenterCrosshairs();
			// drawCenterRecCrosshairs();
			CameraServer.getInstance().setImage(binaryFrame);
		}
	}

	public double getDistance() {
		Tft = 1.625;
		horizontalPixel = horizontalImage * 2;
		double Tpixel = Math.abs(boundingLeft - boundingRight);
		double FOVpixel = (horizontalPixel * Tft) / (2 * Tpixel);
		distance = FOVpixel / Math.tan((44.10524987 * (Math.PI / 180)) / 2);
		System.out.println("Distance: " + distance);
		return distance;
	}

	public double getDistanceV() { // using vertical distance
		TftV = 14.0 / 12.0;
		verticalPixel = verticalImage * 2;
		double Tpixel = Math.abs(boundingTop - boundingBottom);
		double FOVpixel = (verticalPixel * TftV) / (2 * Tpixel);
		distance = FOVpixel / Math.tan((46 * (Math.PI / 180)) / 2);
		System.out.println("Vertical Distance: " + distance);
		return distance;
	}

	public double centerXCoordinate() {
		Point center = new NIVision.Point((int) (horizontalImage), (int) (verticalImage));
		return center.x;
	}

	public double centerYCoordinate() {
		Point center = new NIVision.Point((int) (horizontalImage), (int) (verticalImage));
		return center.y;
	}

	public double recCenterXCoordinate() {
		Point recCenter = new NIVision.Point((int) (centerRec[0]), (int) (topRec));
		return recCenter.x;
	}

	public double recCenterYCoordinate() {
		Point recCenter = new NIVision.Point((int) (centerRec[0]), (int) (topRec));
		return recCenter.y;
	}

	public double getAngle() {
		// double Tpixels = bottomRec;
		// double TpixelsV = leftRec;
		// double pixel = Math.abs(0 - recCenterXCoordinate());
		// double pixelV = Math.abs(0 - recCenterYCoordinate());
		// double feet = (Tft*pixel)/Tpixels;
		// double feetV = (Tft*pixelV)/TpixelsV; angle =
		// Math.atan2(feet/getDistance(),
		// feetV/getDistance())*(180/Math.PI);

		// double pixelsToMove = 0 - centerRec[0];
		// horizontalPixel = horizontalImage * 2;
		// double Tpixel = Math.abs(boundingLeft - boundingRight);
		// double DistanceToMove = pixelsToMove * Tft / Tpixel;
		// angle = Math.asin(DistanceToMove / getDistance()) * (180 / Math.PI);
		// System.out.println("angle: " + angle);
		double Tpixels = bottomRec;
		double pixel = Math.abs(0 - recCenterXCoordinate());
		double feet = (Tft * pixel) / Tpixels;
		angle = Math.atan(feet / getDistance()) * (180 / Math.PI);
		System.out.println("					" + angle);
		return angle;
	}

	public void processImageOriginal() {
		getImage();
		drawRectangle();
		// center crosshairs
		Point startH = new NIVision.Point((int) (horizontalImage - 10), (int) (verticalImage));
		Point endH = new NIVision.Point((int) (horizontalImage + 10), (int) verticalImage);
		NIVision.imaqDrawLineOnImage(frame, binaryFrame, DrawMode.DRAW_VALUE, startH, endH, 200f);
		Point startV = new NIVision.Point((int) (horizontalImage), (int) (verticalImage - 10));
		Point endV = new NIVision.Point((int) (horizontalImage), (int) (verticalImage + 10));
		NIVision.imaqDrawLineOnImage(frame, binaryFrame, DrawMode.DRAW_VALUE, startV, endV, 200f);
		// rectangle center crosshairs
		Point startHrec = new NIVision.Point((int) (centerRec[0] + horizontalImage - 10), (int) (crosshair + 10));
		Point endHrec = new NIVision.Point((int) (centerRec[0] + horizontalImage + 10), (int) (crosshair - 10));
		NIVision.imaqDrawLineOnImage(frame, binaryFrame, DrawMode.DRAW_VALUE, startHrec, endHrec, 150f);
		Point startVrec = new NIVision.Point((int) (centerRec[0] + horizontalImage - 10), (int) (crosshair - 10));
		Point endVrec = new NIVision.Point((int) (centerRec[0] + horizontalImage + 10), (int) (crosshair + 10));
		NIVision.imaqDrawLineOnImage(frame, binaryFrame, DrawMode.DRAW_VALUE, startVrec, endVrec, 150f);
		// TODO CameraServer.getInstance().setImage(frame);
	}
}