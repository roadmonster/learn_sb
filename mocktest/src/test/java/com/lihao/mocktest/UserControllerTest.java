package com.lihao.mocktest;

import com.lihao.mocktest.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setUp(){

        mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void hello() throws  Exception{
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/api/hello")
                .param("name","fishpro")
                .accept(MediaType.TEXT_HTML_VALUE)) //perform 结束
                .andExpect(MockMvcResultMatchers.status().isOk()) //添加断言
                .andExpect(MockMvcResultMatchers.content().string("Hello fishpro"))//添加断言
                .andDo(MockMvcResultHandlers.print()) //添加执行
                .andReturn();
    }

    @Test
    public void getUsersTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .accept(MediaType.APPLICATION_JSON)) //perform 结束
                .andExpect(MockMvcResultMatchers.status().isOk()) //andExpect
                .andDo(MockMvcResultHandlers.print()) //andDo
                .andReturn();//andReturn

    }

    @Test
    public void getUserByIdTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    public void editUserTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/3")
        .)
    }



}
