module com.udacity.catpoint.security
{
	requires com.udacity.catpoint.image;
	requires com.miglayout.swing;
	requires java.desktop;
	requires java.prefs;
	requires com.google.gson;
	requires com.google.common;
	requires com.google.guice;
	requires javax.inject;
	
	opens com.udacity.catpoint.data;
	
	opens com.udacity.catpoint.service;
	
	requires org.slf4j;
}
