package com.udacity.image.service;

import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Arrays;
import java.util.Optional;

import java.util.stream.Stream;

import java.io.*;
import javax.imageio.ImageIO;



// int[] dataBuffInt = image.getRGB(0, 0, w, h, null, 0, w);

/**
 * Service that tries to guess if an image displays a cat.
 */
public class FakeImageService implements ImageService
{
    private final Random r = new Random();
	
	int imgHash = 0;
	
	public FakeImageService()
	{
		try( InputStream is = getClass().getClassLoader().getResourceAsStream("camera/sample-cat.jpg") )
		{
			try
			{
				BufferedImage img = ImageIO.read(is);
					
				int w = img.getWidth();
				int h = img.getHeight();
					
				int[] rgb = img.getRGB(0, 0, w, h, null, 0, w);
					
				imgHash = Arrays.hashCode(rgb);
			}
			catch(Exception exp) { System.out.println(exp + ": File I/O Error"); }
		}
		catch(Exception exp) {}
		
		try( InputStream is = getClass().getClassLoader().getResourceAsStream("camera/manifest.img") )
		{
			Stream<String> entries = new BufferedReader( new InputStreamReader(is) ).lines();
			System.out.println(entries.findAny().get());
		}
		catch(Exception exp) {}
	}

    @Override
	public boolean imageContainsCat(BufferedImage image, float confidenceThreshhold)
	{
		//return (r.nextFloat()*confidenceThreshhold) > (0.7*confidenceThreshhold);
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		int[] rgb = image.getRGB(0, 0, w, h, null, 0, w);
		
		return Arrays.hashCode(rgb) == imgHash;
    }
}
