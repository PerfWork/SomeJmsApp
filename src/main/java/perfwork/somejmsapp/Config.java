package perfwork.somejmsapp;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Queue;

@Configuration
public class Config {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(connectionFactory());
    }

    @Bean
    public Queue inboundQueue() {
        return new ActiveMQQueue("inboundQueue");
    }

    @Bean
    public Queue outboundQueue() {
        return new ActiveMQQueue("outboundQueue");
    }

    @Bean
    public MessageListener processingMessageListener() {
        return new ProcessingMessageListener(jmsTemplate(), outboundQueue());
    }

    @Bean
    public MessageListenerContainer inboundMessageListenerContainer() {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setDestination(inboundQueue());
        listenerContainer.setMessageListener(processingMessageListener());
        return listenerContainer;
    }
}
