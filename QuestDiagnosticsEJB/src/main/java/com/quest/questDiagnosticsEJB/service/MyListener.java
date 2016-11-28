package com.quest.questDiagnosticsEJB.service;

import javax.ejb.MessageDriven;
import javax.interceptor.Interceptors;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.ejb.ActivationConfigProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.stereotype.Service;

@Service
@MessageDriven(
		activationConfig = {
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") ,
				@ActivationConfigProperty(propertyName="connectionFactoryJndiName",propertyValue="jms/TestConnectionFactory"),
				@ActivationConfigProperty(propertyName="destinationJndiName", propertyValue="jms/TestJMSQueue")
				},
		mappedName = "jms/TestJMSQueue")

@Interceptors(SpringBeanAutowiringInterceptor.class)
public class MyListener implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(MyListener.class);

	@Autowired
	private PatientRecordsInserterService patientRecordsInserterService;
	
	
	
	@Override
	public void onMessage(Message msg) {
		TextMessage message = (TextMessage) msg;
		try {
			logger.info("message received: " + message.getText());
			
			patientRecordsInserterService.splitString(message.getText());
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
