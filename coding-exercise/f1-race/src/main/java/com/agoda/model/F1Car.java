package com.agoda.model;

import java.util.Comparator;

public class F1Car
{
	private Integer id;
	private Double currentSpeed;
	private Double currentPosition;
	private Double topSpeed;
	private Double acceleration;
	private Boolean isNitroUsed;
	private Double finishTiming;

	public F1Car(Integer id, Double currentSpeed, Double currentPosition, Double topSpeed, Double acceleration, Boolean isNitroUsed) {
		this.id = id;
		this.currentSpeed = currentSpeed;
		this.currentPosition = currentPosition;
		this.topSpeed = topSpeed;
		this.acceleration = acceleration;
		this.isNitroUsed = isNitroUsed;
	}

	public static Comparator<F1Car> sortByCurrentPosition = new Comparator<F1Car>() {
        public int compare(F1Car car1, F1Car car2) {
            return car1.getCurrentPosition().compareTo(car2.getCurrentPosition());
        }
    };

	public Double getCurrentSpeed()
	{
		return currentSpeed;
	}

	public void setCurrentSpeed(Double currentSpeed)
	{
		this.currentSpeed = currentSpeed;
	}

	public Double getCurrentPosition()
	{
		return currentPosition;
	}

	public void setCurrentPosition(Double currentPosition)
	{
		this.currentPosition = currentPosition;
	}

	public Double getTopSpeed()
	{
		return topSpeed;
	}

	public Double getAcceleration()
	{
		return acceleration;
	}

	void setAcceleration(Double acceleration)
	{
		this.acceleration = acceleration;
	}

	public void setIsNitroUsed(Boolean isNitroUsed)
	{
		this.isNitroUsed = isNitroUsed;
	}

	public Integer getId()
	{
		return id;
	}

	void setId(Integer id)
	{
		this.id = id;
	}

	public Double getFinishTiming()
	{
		return finishTiming;
	}

	public void setFinishTiming(Double finishTiming)
	{
		this.finishTiming = finishTiming;
	}

	public void setTopSpeed(Double topSpeed) {
		this.topSpeed = topSpeed;
	}

    public Boolean getNitroUsed() {
        return isNitroUsed;
    }

    public void setNitroUsed(Boolean nitroUsed) {
        isNitroUsed = nitroUsed;
    }
}
