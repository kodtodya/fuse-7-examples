package com.kodtodya.practice.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class SQLProcessor implements Processor {

    @PropertyInject("{{insert.query}}")
    private String insertQuery;

    private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public void process(Exchange exchange) throws Exception {

        // fill up query values
        String query = String.format(insertQuery, this.getRandomNumber(), this.getRandomString(), this.getRandomString());

        // set query as an exchange body
        exchange.getIn().setBody(query);
    }

    // generates random string for name and location
    private String getRandomString() {
        StringBuffer randStr = new StringBuffer(8);
        SecureRandom secureRandom = new SecureRandom();
        for( int i = 0; i < 8; i++ )
            randStr.append( CHAR_LIST.charAt( secureRandom.nextInt(CHAR_LIST.length())));
        return randStr.toString();

    }

    // generates random number for ID
    private int getRandomNumber() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }
}
