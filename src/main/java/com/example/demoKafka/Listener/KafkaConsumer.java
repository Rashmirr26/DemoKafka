package com.example.demoKafka.Listener;

import com.example.demoKafka.Dto.UserDto;
import com.example.demoKafka.Repo.UserRepository;
import com.example.demoKafka.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumer {
    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "Kafka_Example", group = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }


    @KafkaListener(topics = "Kafka_Example_json", group = "group_json",
            containerFactory = "userKafkaListenerFactory")
    public void consumeJson(User user) {
        UserDto userDto=new UserDto();
        userDto.setDept(user.getDept());
        userDto.setName(user.getName());
        userRepository.save(userDto);

        System.out.println("Consumed JSON Message: " + userDto);
    }
    public User getUsers(Long id){
        UserDto userDto= userRepository.getOne(id);
        User user=new User();
        user.setId(userDto.getId());
        user.setDept(userDto.getDept());
        user.setName(userDto.getName());
        return user;}

    public List<User> getAllUsers() {
        List<UserDto> userDtoList=userRepository.findAll();
        List<User> list=new ArrayList<>();
        for(UserDto userDto:userDtoList){
            User user=new User();
            user.setId(userDto.getId());
            user.setDept(userDto.getDept());
            user.setName(userDto.getName());
            list.add(user);
        }
        return list;
    }
}
