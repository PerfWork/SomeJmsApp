package perfwork.somejmsapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SomeJmsApp {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Config.class);
    }
}
