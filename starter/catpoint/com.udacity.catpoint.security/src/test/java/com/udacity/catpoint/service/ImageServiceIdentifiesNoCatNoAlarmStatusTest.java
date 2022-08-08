package com.udacity.catpoint.service;


import com.udacity.catpoint.data.*;
import com.udacity.catpoint.application.*;
import com.udacity.image.service.*;

import java.util.*;
import java.util.stream.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.inject.Inject;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;


/*
* If the image service identifies an image that does not contain a cat,
* change the status to no alarm as long as the sensors are not active.
*/
public class ImageServiceIdentifiesNoCatNoAlarmStatusTest  // No. 8
{
	private static List<AlarmStatus> AlarmStatusList = List.of(AlarmStatus.NO_ALARM, AlarmStatus.PENDING_ALARM, AlarmStatus.ALARM);
	private static List<ArmingStatus> ArmingStatusList = List.of(ArmingStatus.DISARMED, ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
	private static List<SensorType> SensorTypeList = List.of(SensorType.DOOR, SensorType.WINDOW, SensorType.MOTION);
	private static List<Boolean> BoolList = List.of(Boolean.FALSE, Boolean.TRUE);
	
	private static Stream<Arguments> noCatNoSensors()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(AlarmStatus alarm: AlarmStatusList)
		{
			for(ArmingStatus arm: ArmingStatusList)
			{
				args.add( Arguments.of(alarm, arm) );
			}
		}
		
		return args.stream();
	}
	
	@ParameterizedTest
	@DisplayName("Unit Test 8, without sensors")
	@MethodSource("noCatNoSensors")
	public void ImageServiceIdentifiesNoCatNoSensors(AlarmStatus alarm, ArmingStatus arm)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();
		
		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.FALSE);
		
		securityService.setArmingStatus(arm);
		securityService.setAlarmStatus(alarm);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
		assertEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
	}
	
	private static Stream<Arguments> NoCatOneSensor()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(Boolean active: BoolList)
		{
			for(SensorType type: SensorTypeList)
			{
				for(AlarmStatus alarm: AlarmStatusList)
				{
					for(ArmingStatus arm: ArmingStatusList)
					{
						args.add( Arguments.of(active, type, alarm, arm) );
					}
				}
			}
		}
		
		return args.stream();
	}
	
	@ParameterizedTest
	@DisplayName("Unit Test 8, with just 1 sensor")
	@MethodSource("NoCatOneSensor")
	public void ImageServiceIdentifiesNoCatOneSensor(Boolean active, SensorType type, AlarmStatus alarm, ArmingStatus arm)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();

		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.FALSE);
		
		Sensor testSensor = new Sensor("test", type);
		
		securityService.addSensor(testSensor);
		
		testSensor.setActive(active);
		
		securityService.setArmingStatus(arm);
		securityService.setAlarmStatus(alarm);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
		if( testSensor.getActive() && alarm != AlarmStatus.NO_ALARM )
			assertNotEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
		else
			assertEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
	}
}
