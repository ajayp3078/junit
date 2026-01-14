package com.junit5_mockito.service;

import com.junit5_mockito.entity.Contact;
import com.junit5_mockito.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public Contact saveContact(Contact contact){
        return contactRepository.save(contact);
    }

    public Contact getContactById(Long id){
        return contactRepository.findById(id).orElse(null);
    }

    public List<Contact> getContactsList(){
        return contactRepository.findAll();
    }

    public void deleteContact(Contact contact){
        contactRepository.delete(contact);
    }

    public Contact updateContactById(Long id,Contact contact){
        Contact existingContact = contactRepository.findById(id).orElse(null);
        existingContact.setName(contact.getName());
            return contactRepository.save(existingContact);
    }

}
