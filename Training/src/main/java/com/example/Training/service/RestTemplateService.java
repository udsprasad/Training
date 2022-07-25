package com.example.Training.service;

import com.example.Training.model.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RestTemplateService {

   @Autowired
   RestTemplate restTemplate;

   String url ="http://localhost:8080";

   public ResponseEntity<PersonDTO> getUserResponse(){
       return restTemplate.getForEntity(url+"/user", PersonDTO.class);
   }

    public ResponseEntity<PersonDTO[]> getUsersResponse(){
        return restTemplate.getForEntity(url+"/users", PersonDTO[].class);
    }

    public ResponseEntity<PersonDTO> addPerson(PersonDTO person) {
       return restTemplate.postForEntity(url+"/addUser",person,PersonDTO.class);
    }

    public void updatePerson(String name, String phonenumber) {
        Map<String,Object> uriVariables = new HashMap<String,Object>();
        uriVariables.put("name",name);
        uriVariables.put("phonenumber",phonenumber);
        restTemplate.put(url + "/updateUser?name={name}&phonenumber={phonenumber}",null ,uriVariables);
    }
}
