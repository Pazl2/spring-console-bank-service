package com.pazl2;

import com.pazl2.services.OperationConsoleListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static void main() {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.pazl2");

        OperationConsoleListener operationConsoleListener =
                context.getBean(OperationConsoleListener.class);

        operationConsoleListener.start();
    }

}
