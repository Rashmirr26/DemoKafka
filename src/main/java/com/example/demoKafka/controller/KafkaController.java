package com.example.demoKafka.controller;

import com.example.demoKafka.Listener.KafkaConsumer;
import com.example.demoKafka.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("kafka")
public class KafkaController {
    @Autowired
    private KafkaConsumer kafkaService;

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    private static final String TOPIC = "Kafka_demo";

    @GetMapping("/publish/{id}")
    public String post(@PathVariable("id") final Long id) {
        User user=kafkaService.getUsers(id);
        kafkaTemplate.send(TOPIC, new User(user.getId(),user.getName(),user.getDept()));

        return "Published successfully";
    }
    @GetMapping("/publish")
    public String postAll() {
        List<User> userList=kafkaService.getAllUsers();
        for(User user:userList) {
            kafkaTemplate.send(TOPIC, new User(user.getId(),user.getName(), user.getDept()));
        }
        return "Published successfully";
    }}
