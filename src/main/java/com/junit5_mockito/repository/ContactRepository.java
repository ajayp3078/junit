package com.junit5_mockito.repository;

import com.junit5_mockito.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {

}
