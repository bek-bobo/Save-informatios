package com.example.saveinformation.user.otp;

import com.example.saveinformation.user.otp.entity.Otp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OtpRepository extends CrudRepository<Otp,String> {

}
