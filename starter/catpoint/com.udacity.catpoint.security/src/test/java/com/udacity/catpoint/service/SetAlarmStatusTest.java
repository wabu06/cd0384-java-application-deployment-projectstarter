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


public class SetAlarmStatusTest
{
	private SecurityRepository securityRepository = new MockSecurityRepository(); ;
	private SecurityService securityService = new SecurityService(securityRepository);
	
	@ParameterizedTest
	@EnumSource(AlarmStatus.class)
	public void isAlarmStatusSetCorrectly(AlarmStatus as)
	{
		securityService.setAlarmStatus(as);
		
		assertEquals( as, securityService.getAlarmStatus() );
	}
}
