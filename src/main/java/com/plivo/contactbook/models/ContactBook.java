package com.plivo.contactbook.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ContactBook {
    private static Map<String, Contact> contactBook = new HashMap<>(); //ToDo

    public static void put(String email, Contact contact){
        contactBook.put(email, contact);
    }

    public static Contact get(String email){
        return contactBook.get(email);
    }

    public static Contact delete(String email){
        return contactBook.remove(email);
    }

    public static Collection<Contact> getAllContacts(){
        return contactBook.values();
    }

    public static Collection<Contact> getContactsByName(String name) {
        Collection<Contact> collection = new ArrayList<>();
        for (Contact contact: contactBook.values()) {
            if(contact.getName().equalsIgnoreCase(name))
                collection.add(contact);
        }
        return collection;
    }
}
