package com.udacity.image.service;

import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Arrays;
import java.util.Optional;

import java.util.stream.Stream;

import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



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
			BufferedImage img = ImageIO.read(is);
					
			int w = img.getWidth();
			int h = img.getHeight();
					
			int[] rgb = img.getRGB(0, 0, w, h, null, 0, w);
					
			imgHash = Arrays.hashCode(rgb);
		}
		catch(Exception exp)
		{
			JOptionPane.showMessageDialog
				(
					null,
					"Unable To Find A Critical Resource, Application Will Now Terminate",
					"ERROR",
					JOptionPane.ERROR_MESSAGE
				);

			System.exit(1);
		}
		
		Logger log = LoggerFactory.getLogger(FakeImageService.class);
		log.info("Using: " + this.getClass() );
	}

    @Override
	public boolean imageContainsCat(BufferedImage image, float confidenceThreshhold)
	{
		//return (r.nextFloat()*confidenceThreshhold) > (0.7*confidenceThreshhold);
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		int[] rgb = image.getRGB(0, 0, w, h, null, 0, w);
		
		boolean confident = (r.nextFloat()*confidenceThreshhold) > (0.025*confidenceThreshhold);
		
		return (Arrays.hashCode(rgb) == imgHash) && confident;
    }
}
