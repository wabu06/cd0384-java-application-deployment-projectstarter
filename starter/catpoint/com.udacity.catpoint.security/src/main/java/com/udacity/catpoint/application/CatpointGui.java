package com.udacity.catpoint.application;

import com.udacity.catpoint.data.PretendDatabaseSecurityRepositoryImpl;
import com.udacity.catpoint.data.SecurityRepository;
//import com.udacity.catpoint.service.FakeImageService;
//import com.udacity.catpoint.service.SecurityService;
import com.udacity.catpoint.service.*;

import com.udacity.image.service.*;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.inject.Inject;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is the primary JFrame for the application that contains all the top-level JPanels.
 *
 * We're not using any dependency injection framework, so this class also handles constructing
 * all our dependencies and providing them to other classes as necessary.
 */
public class CatpointGui extends JFrame
{
    private SecurityRepository securityRepository = new PretendDatabaseSecurityRepositoryImpl();
	
    //private FakeImageService imageService = new FakeImageService();
    
	//private SecurityService securityService = new SecurityService(securityRepository, imageService);
	
	Injector ssInj = Guice.createInjector( new ImageServiceModule(), b->b.bind(SecurityRepository.class).toInstance(securityRepository) );
	
	SecurityService securityService = ssInj.getInstance(SecurityService.class);
	
	//private SecurityService securityService = new SecurityService(securityRepository);
	
    private DisplayPanel displayPanel = new DisplayPanel(securityService);
    private SensorPanel sensorPanel = new SensorPanel(securityService);
	private ImagePanel imagePanel = new ImagePanel(securityService);
	private ControlPanel controlPanel = new ControlPanel(securityService, sensorPanel, imagePanel);
    //private ImagePanel imagePanel = new ImagePanel(securityService);

    public CatpointGui()
	{
        //setLocation(100, 100);
		setLocation(50, 50);
        //setSize(600, 850);
		setSize(600, 720);
        setTitle("Very Secure App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ImageIcon icon = new ImageIcon("no-cats.jpeg");
		
		try( InputStream is = getClass().getClassLoader().getResourceAsStream("no-cats.jpeg") )
		{
			ImageIcon icon = new ImageIcon( is.readAllBytes() );
			setIconImage( icon.getImage() );
		}
		catch(Exception e)
		{
			Logger log = LoggerFactory.getLogger(CatpointGui.class);
			log.info("No Icon", e);
		}
		
		//ImageIcon icon = new ImageIcon( getClass().getResource("no-cats.jpeg").toString() );
		
		//System.out.println( getClass().getClassLoader().getResource("no-cats.jpeg") );

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());
        mainPanel.add(displayPanel, "wrap");
        mainPanel.add(imagePanel, "wrap");
        mainPanel.add(controlPanel, "wrap");
        mainPanel.add(sensorPanel);

        getContentPane().add(mainPanel);

    }
}
