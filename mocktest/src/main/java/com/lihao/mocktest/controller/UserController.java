package com.lihao.mocktest.controller;

import com.lihao.mocktest.domain.UserDomain;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private List<UserDomain> getData() {
        List<UserDomain> list = new ArrayList<>();

        UserDomain userDO = new UserDomain();
        userDO.setUserId(1);
        userDO.setUserName("admin");
        list.add(userDO);

        userDO = new UserDomain();
        userDO.setUserId(2);
        userDO.setUserName("heike");
        list.add(userDO);

        userDO = new UserDomain();
        userDO.setUserId(3);
        userDO.setUserName("tom");
        list.add(userDO);

        userDO = new UserDomain();
        userDO.setUserId(4);
        userDO.setUserName("mac");
        list.add(userDO);

        return list;
    }

    @RequestMapping("/hello")
    public String hello(String name){
        return "Hello "+name;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Object getUsers(){
        List<UserDomain> list=new ArrayList<>();

        list=getData();

        return list;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getUser(@PathVariable("id") String id) {

        if (null == id) {
            return null;
        }

        List<UserDomain> list = getData();
        UserDomain userDO = null;
        for (UserDomain user : list
        ) {
            if (id.equals(user.getUserId().toString())) {
                userDO = user;
                break;
            }
        }

        return userDO;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addUser(@RequestBody UserDomain user){

        List<UserDomain> list= getData();
        list.add(user);//模拟向列表中增加数据
        return user;
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Object editUser(@PathVariable("id") String id,@RequestBody UserDomain user){
        List<UserDomain> list= getData();
        for (UserDomain u:list
        ) {
            if(id.equals(u.getUserId().toString())){
                u = user;
                break;
            }
        }

        return user;
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") String id){
        List<UserDomain> list= getData();

        for (UserDomain user:list) {
            if(id.equals(user.getUserId().toString())){
                //删除用户
                list.remove(user);
                break;
            }
        }
    }


}
