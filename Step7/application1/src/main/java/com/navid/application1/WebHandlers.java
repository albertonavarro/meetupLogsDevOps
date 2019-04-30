package com.navid.application1;

import com.sun.net.httpserver.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
public class WebHandlers {

    Logger logger = LoggerFactory.getLogger(WebHandlers.class);

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private ConcurrentHashMap<String, Account> repository = new ConcurrentHashMap();

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(String name, @RequestHeader("rid") String rid) {

        MDC.put("requestId", rid);

        logger.info("Creating account {}", kv("name", name));

        Account newAccount = new Account(name, atomicInteger.incrementAndGet());
        Account result = repository.putIfAbsent(name, newAccount);

        if (result != null) {
            logger.info("Creation failed. An account with same name already exist {} {}", kv("name", name), kv("id", result.getId()));

            logger.info("Returning failure");
            MDC.clear();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        logger.info("Creation successful. An account has been created {} {}", kv("name", name), kv("id", newAccount.getId()));

        logger.info("Returning new account");

        MDC.clear();
        return ResponseEntity.status(HttpStatus.OK).body(newAccount);
    }


}
