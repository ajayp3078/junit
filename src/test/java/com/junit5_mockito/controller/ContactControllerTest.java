package com.junit5_mockito.controller;

import com.junit5_mockito.entity.Contact;
import com.junit5_mockito.service.ContactService;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// To test controller methods -> when, thenReturn, mockMvc.perform(post(),contentType(),content()).andExpect(status().isOk())
// If request body is present we need to pass content
@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Mock
    ContactService contactService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void saveContact() throws Exception {
        Contact contact = new Contact(1L,"Ajay");
        when(contactService.saveContact(contact)).thenReturn(contact);
        mockMvc.perform(post("/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(contact)))
                .andExpect(status().isOk());
    }

    @Test
    void getContactById() throws Exception {
        Contact contact = new Contact(100L,"Ajay");
        when(contactService.getContactById(100L)).thenReturn(contact);
        mockMvc.perform(get("/contact/{id}",100L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ajay"));
    }

    @Test
    void getContactList() throws Exception{
        List<Contact> contacts = Arrays.asList(new Contact(1L,"ajay"),new Contact(2L,"jaya"));
        when(contactService.getContactsList()).thenReturn(contacts);
        mockMvc.perform(get("/contacts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ajay"));
    }

    @Test
    void deleteContact() throws Exception{
        Contact contact = new Contact(1L,"ajay");
        doNothing().when(contactService).deleteContact(contact);
        mockMvc.perform(delete("/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(contact)))
                .andExpect(status().isOk());
    }
}