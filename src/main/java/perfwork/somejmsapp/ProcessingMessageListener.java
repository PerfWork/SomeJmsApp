package perfwork.somejmsapp;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

public class ProcessingMessageListener implements MessageListener {

    private final JmsTemplate jmsTemplate;
    private final Queue outboundQueue;

    public ProcessingMessageListener(JmsTemplate jmsTemplate, Queue outboundQueue) {
        this.jmsTemplate = jmsTemplate;
        this.outboundQueue = outboundQueue;
    }

    @Override
    public void onMessage(Message message) {
        jmsTemplate.send(outboundQueue, session -> {
            String inboundMessageBody = ((TextMessage) message).getText();
            return session.createTextMessage(inboundMessageBody + "666");
        });
    }
}
