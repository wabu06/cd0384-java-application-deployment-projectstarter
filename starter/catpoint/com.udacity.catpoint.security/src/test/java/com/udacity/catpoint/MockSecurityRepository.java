package com.udacity.catpoint.data;

import java.util.*;

public class MockSecurityRepository implements SecurityRepository
{
	AlarmStatus alarm = AlarmStatus.NO_ALARM;
	ArmingStatus armed;
	Set<Sensor> sensors = new TreeSet<>();
	
	@Override
    public void setAlarmStatus(AlarmStatus alarmStatus) { alarm = alarmStatus; }
	
	@Override
    public AlarmStatus getAlarmStatus() { return alarm; }
	
	@Override
    public ArmingStatus getArmingStatus() { return armed; }
	
	@Override
    public void setArmingStatus(ArmingStatus armingStatus) { armed = armingStatus; }
	
	@Override
    public void addSensor(Sensor sensor) { sensors.add(sensor); }
	
	@Override
    public void updateSensor(Sensor sensor)
	{
		sensors.remove(sensor);
        sensors.add(sensor);
	}
	
	@Override
    public Set<Sensor> getSensors() { return null; }
	
	@Override
	public void removeSensor(Sensor sensor) {}
}
