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

public class PendingAlarmSensorsInactiveToNoAlarmTest 
{
	@Test
	public void AlarmStatusTest()
    {
		SecurityRepository securityRepository = new MockSecurityRepository();
		SecurityService securityService = new SecurityService(securityRepository);
		SensorTestPanel panel = new SensorTestPanel(securityService);
		
		JButton addSensorBttn = panel.getAddSensorBttn();
		
		panel.getSensorTypeDropdown().setSelectedItem(SensorType.DOOR);
		addSensorBttn.doClick();
		
		panel.getSensorTypeDropdown().setSelectedItem(SensorType.WINDOW);
		addSensorBttn.doClick();
		
		panel.getSensorTypeDropdown().setSelectedItem(SensorType.MOTION);
		addSensorBttn.doClick();
		
			// activate sensors
		panel.getSensorToggleBttn(0).doClick();
		panel.getSensorToggleBttn(1).doClick();
		panel.getSensorToggleBttn(2).doClick();
		
		securityService.setAlarmStatus(AlarmStatus.PENDING_ALARM);
		
			// deactivate sensors
		panel.getSensorToggleBttn(0).doClick();
		panel.getSensorToggleBttn(1).doClick();
		panel.getSensorToggleBttn(2).doClick();
		
		assertEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
	}
}
// No 3
