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


public class SetArmingStatusTest
{
	private SecurityRepository securityRepository;
	private SecurityService securityService;
	
	@ParameterizedTest
	@EnumSource(ArmingStatus.class)
	public void alarmArmedTest(ArmingStatus as)
	{
		securityRepository = new MockSecurityRepository();
		securityService = new SecurityService(securityRepository);
		
		securityService.setArmingStatus(as);
		
		assertEquals( as, securityService.getArmingStatus() );
	}
}
