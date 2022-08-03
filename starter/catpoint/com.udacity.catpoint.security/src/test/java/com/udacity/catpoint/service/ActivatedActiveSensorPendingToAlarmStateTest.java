package com.udacity.catpoint.service;


import com.udacity.catpoint.data.*;
//import com.udacity.catpoint.service.*;
import com.udacity.catpoint.application.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import javax.swing.*;


/**
 * Unit test for Pending Alarm Status, when a sensor becomes active, and alarm armed.
 */

public class ActivatedActiveSensorPendingToAlarmStateTest 
{
	List<ArmingStatus> armingStatusList = List.of(ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
	
	List<SensorType> sensorTypeList = List.of(SensorType.DOOR, SensorType.WINDOW, SensorType.MOTION);

	@ParameterizedTest
	@EnumSource(SensorType.class)
	public void AlarmStatusTest(SensorType type)
    {
		SecurityRepository securityRepository = new MockSecurityRepository();
		SecurityService securityService = new SecurityService(securityRepository);
		SensorTestPanel panel = new SensorTestPanel(securityService);
		
		JButton addSensorBttn = panel.getAddSensorBttn();
		
			// create sensors
		for(SensorType T: sensorTypeList)
		{
			if( T != type )
			{
				panel.getSensorTypeDropdown().setSelectedItem(T);
				addSensorBttn.doClick();
			}
		}
		
		for(ArmingStatus S: armingStatusList)
		{
			securityService.setArmingStatus(S);
		
				// activate first sensor
			panel.getSensorToggleBttn(0).doClick();
		
				// activate second sensor
			panel.getSensorToggleBttn(1).doClick();
		
			assertEquals( AlarmStatus.ALARM, securityService.getAlarmStatus() );
			
				// reset alarm 
			securityService.setArmingStatus(ArmingStatus.DISARMED);
			
				// deactivate sensors
			panel.getSensorToggleBttn(0).doClick();
			panel.getSensorToggleBttn(1).doClick();
		}
	}
}
// No 5
