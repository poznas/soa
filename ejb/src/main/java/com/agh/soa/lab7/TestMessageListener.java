package com.agh.soa.lab7;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * https://access.redhat.com/documentation/en-us/red_hat_jboss_a-mq/6.1/html/integrating_with_jboss_enterprise_application_platform/deployrar-installrar
 * https://blog.coffeebeans.at/archives/988
 */
@MessageDriven(
  name = "TestMessageListener",
  activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "AghSoaQueue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
  }
)
public class TestMessageListener implements MessageListener {

  @Override
  public void onMessage(Message message) {
    System.out.println(message);
  }
}
