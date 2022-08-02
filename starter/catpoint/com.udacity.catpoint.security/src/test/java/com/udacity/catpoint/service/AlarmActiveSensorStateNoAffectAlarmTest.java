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



/**
 * Unit test for Pending Alarm Status, when a sensor becomes active, and alarm armed.
 */

public class AlarmActiveSensorStateNoAffectAlarmTest 
{
	List<ArmingStatus> armingStatusList  = List.of(ArmingStatus.DISARMED, ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
	
    @ParameterizedTest
	@EnumSource(SensorType.class)
	public void AlarmStatusTest(SensorType type)
    {
		SecurityRepository securityRepository = new MockSecurityRepository();
		SecurityService securityService = new SecurityService(securityRepository);
		SensorTestPanel panel = new SensorTestPanel(securityService);
		
		panel.getSensorTypeDropdown().setSelectedItem(type);
		panel.getAddSensorBttn().doClick();
	
		for(ArmingStatus S: armingStatusList)
		{
			securityService.setArmingStatus(S);
			securityService.setAlarmStatus(AlarmStatus.ALARM);
			
			panel.getSensorToggleBttn(0).doClick();
		
			assertEquals( AlarmStatus.ALARM, securityService.getAlarmStatus() );
			
			//panel.getSensorToggleBttn(0).doClick();
		} 
	}
}
// No 4
