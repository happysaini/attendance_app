package com.attendanceApp;

import java.io.File;
import java.net.ServerSocket;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServerJava {
	
	public static AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	
	public void startServer(int port) {
		//Build the Appium service
		builder = new AppiumServiceBuilder();
		builder.usingDriverExecutable(new File("/usr/local/bin/node"));
		builder.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"));
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(port);
		builder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, String.valueOf(port+1));
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
		builder.withArgument(GeneralServerFlag.RELAXED_SECURITY);
		
		//Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
	}

	public boolean checkIfServerIsRunnning(int port) {
		
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (Exception e) {
			//If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}	


}
