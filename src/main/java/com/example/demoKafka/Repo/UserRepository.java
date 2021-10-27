package com.example.demoKafka.Repo;

import com.example.demoKafka.Dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDto,Long> {


}
