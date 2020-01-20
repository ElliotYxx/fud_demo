package com.centerm.fud_demo.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

public class Listener implements HttpSessionListener {
    public static AtomicInteger userCount=new AtomicInteger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        userCount.getAndIncrement();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        userCount.getAndDecrement();
    }
}
