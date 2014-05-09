package com.theeyetribe.client.data;

import com.google.gson.annotations.SerializedName;
import com.theeyetribe.client.Protocol;

/**
 * Contains eye tracking results of a single frame. It holds a state that defines 
 * the quality of the current tracking and fine grained tracking details down to eye level.
 */
public class GazeData
{
	/**
	 * Set when engine is calibrated and glint tracking successfully.
	 */
	public static final int STATE_TRACKING_GAZE = 1;

	/**
	 * Set when engine has detected eyes.
	 */
	public static final int STATE_TRACKING_EYES = 1 << 1;

	/**
	 * Set when engine has detected either face, eyes or glint.
	 */
	public static final int STATE_TRACKING_PRESENCE = 1 << 2;

	/**
	 * Set when tracking failed in the last process frame.
	 */
	public static final int STATE_TRACKING_FAIL = 1 << 3;

	/**
	 * Set when tracking has failed consecutively over a period of time defined by engine.
	 */
	public static final int STATE_TRACKING_LOST = 1 << 4;

	public Integer state;

	@SerializedName(Protocol.FRAME_TIME)
	public Long timeStamp = 0l;

	@SerializedName(Protocol.FRAME_TIMESTAMP)
	public String timeStampString;

	@SerializedName(Protocol.FRAME_RAW_COORDINATES)
	public Point2D rawCoordinates = new Point2D();

	@SerializedName(Protocol.FRAME_AVERAGE_COORDINATES)
	public Point2D smoothedCoordinates = new Point2D();

	@SerializedName(Protocol.FRAME_LEFT_EYE)
	public Eye leftEye = new Eye();

	@SerializedName(Protocol.FRAME_RIGHT_EYE)
	public Eye rightEye = new Eye();

	@SerializedName(Protocol.FRAME_FIXATION)
	public Boolean isFixated = false;

	public GazeData()
	{
		timeStamp = System.currentTimeMillis();
	}

	public GazeData(GazeData other)
	{
		this.state = other.state;
		this.timeStamp = other.timeStamp;

		this.rawCoordinates = new Point2D(other.rawCoordinates);
		this.smoothedCoordinates = new Point2D(other.smoothedCoordinates);

		this.leftEye = new Eye(other.leftEye);
		this.rightEye = new Eye(other.rightEye);

		this.isFixated = new Boolean(other.isFixated);
	}

	public GazeData clone()
	{
		return new GazeData(this);
	}

	@Override
	public boolean equals(Object o) 
	{	
		if(o instanceof GazeData)
		{
			GazeData other = (GazeData) o;

			return 
					this.state.intValue() == other.state.intValue() &&
					this.timeStamp.longValue() == other.timeStamp.longValue() &&
					this.rawCoordinates.equals(other.rawCoordinates) &&
					this.smoothedCoordinates.equals(other.smoothedCoordinates) &&
					this.leftEye.equals(other.leftEye) &&
					this.rightEye.equals(other.rightEye) &&
					this.isFixated.booleanValue() == other.isFixated.booleanValue();
		}

		return false;
	}

	public void set(GazeData other)
	{
		this.state = other.state;
		this.timeStamp = other.timeStamp;

		this.rawCoordinates.x = other.rawCoordinates.x;
		this.rawCoordinates.y = other.rawCoordinates.y;
		this.smoothedCoordinates.x = other.smoothedCoordinates.x;
		this.smoothedCoordinates.y = other.smoothedCoordinates.y;

		this.leftEye = new Eye(other.leftEye);
		this.rightEye = new Eye(other.rightEye);

		this.isFixated = new Boolean(other.isFixated);
	}

	/**
	 * Contains tracking results of a single eye.
	 */
	public class Eye
	{
		@SerializedName(Protocol.FRAME_RAW_COORDINATES)
		public Point2D rawCoordinates = new Point2D();

		@SerializedName(Protocol.FRAME_AVERAGE_COORDINATES)
		public Point2D smoothedCoordinates = new Point2D();

		@SerializedName(Protocol.FRAME_PUPIL_CENTER)
		public Point2D pupilCenterCoordinates = new Point2D();

		@SerializedName(Protocol.FRAME_PUPIL_SIZE)
		public Double pupilSize = 0d;

		public Eye() 
		{
		}

		public Eye(Eye other) 
		{
			this.rawCoordinates = new Point2D(other.rawCoordinates);
			this.smoothedCoordinates = new Point2D(other.smoothedCoordinates);
			this.pupilCenterCoordinates = new Point2D(other.pupilCenterCoordinates);
			this.pupilSize = new Double(other.pupilSize);
		}

		@Override
		public boolean equals(Object o) 
		{	
			if(o instanceof Eye)
			{
				Eye other = (Eye) o;

				return 
						this.rawCoordinates.equals(other.rawCoordinates) &&
						this.smoothedCoordinates.equals(other.smoothedCoordinates) &&
						this.pupilCenterCoordinates.equals(other.pupilCenterCoordinates) &&
						Double.doubleToLongBits(this.pupilSize) == Double.doubleToLongBits(other.pupilSize);
			}

			return false;
		}
	}
}
