package com.Lacucaracha.musible.data.source.remote;

import com.lacucaracha.musible.data.source.remote.RetrofitClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RetrofitClientTest {
    private RetrofitClient client;
    @Before
    public void setUp() throws Exception {
        client = RetrofitClient.getInstance();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() {
    }

    @Test
    public void getTest() {
        String resString = client.getTest();
        assertThat(resString,is("Spring test"));
    }
}