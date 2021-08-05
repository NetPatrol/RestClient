package com.jm.restclient.controller;

import com.jm.restclient.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestTemplateClient {
    private static StringBuilder st = new StringBuilder();

    private static RestTemplate restTemplate;

    @Autowired
    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    private static String url;

    private static HttpHeaders httpHeaders = new HttpHeaders();
    private static HttpHeaders header = new HttpHeaders();

    private static User james = new User(3L, "James", "Brown", (byte) 25);
    private static User thomas = new User(3L, "Thomas", "Shelby", (byte) 25);

    private static void setJsessionid(HttpHeaders httpHeaders) {
        String cookie = "";
        if (httpHeaders.containsKey("Set-Cookie")) {
            cookie = httpHeaders.get("Set-Cookie").get(0);
        }
        String jsessionid = cookie.split(";")[0].split("=")[1];
        header.add("Cookie", "JSESSIONID=" + jsessionid + "; Path=/; HttpOnly");
    }

    private static ResponseEntity<User[]> getUsers() {
        ResponseEntity<User[]> users = restTemplate
                .getForEntity(url, User[].class);
        httpHeaders = users.getHeaders();
        setJsessionid(httpHeaders);
        System.out.println(users);
        return users;
    }

    private static void addUser(@RequestBody User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, header);
        System.out.println(entity);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        st.append(response.getBody());
    }

    private static void updateUser(@RequestBody User user){
        HttpEntity<User> entity = new HttpEntity<>(user, header);
        System.out.println(entity);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        st.append(response.getBody());
    }

    private static void deleteUsers(@RequestBody User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, header);
        System.out.println(entity);

        ResponseEntity<String> response = restTemplate.exchange(url + "/" + user.getId(), HttpMethod.DELETE, entity, String.class);
        st.append(response.getBody());
    }

    public static String getCode(String uri) {
        url = uri;
        getUsers();
        addUser(james);
        updateUser(thomas);
        deleteUsers(thomas);
        return String.valueOf(st);
    }
}
