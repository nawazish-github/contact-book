package com.plivo.contactbook.handlers;

import com.plivo.contactbook.models.Contact;
import com.plivo.contactbook.models.ContactBook;
import com.plivo.contactbook.models.EditContact;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/*
    Adding, editing, deleting
    Pagination
    testing: unit and integration.
 */

@RestController
public class ContactBookHandler {

    private static final String NAME = "name";

    private String prevCriterion;

    @RequestMapping(value = "/contactbook", method = RequestMethod.PUT)
    public void addContact(@RequestBody Contact contact){
        ContactBook.put(contact.getEmail(), contact);
    }

    @RequestMapping(value = "/contactbook/{criterion}", method = RequestMethod.GET)
    public Collection<Contact> getContact(@PathVariable String criterion,
                                          @RequestHeader("search-by") String searchBy,
                                          @RequestParam(defaultValue = "2") String size,
                                          @RequestParam (defaultValue = "1") String pageNo){

        HashMap<Integer, Collection<Contact>> pages = new HashMap<>();
        int pageSize = Integer.parseInt(size.trim());
        int pageNum = Integer.parseInt(pageNo.trim());

        if(prevCriterion == null || prevCriterion != criterion){
            prevCriterion = criterion;
            if (searchBy.equalsIgnoreCase(NAME)){
                ArrayList<Contact> contacts = (ArrayList)ContactBook.getContactsByName(criterion);
                int strength = contacts.size();
                int quotient = strength / pageSize;
                int remainder = strength % pageSize;

                int counter = 0;
                for (int i = 0; i < quotient; i++) {
                    Collection<Contact> collection = new ArrayList<>();
                    for (int j = 0; j < pageSize; j++) {
                        collection.add(contacts.get(counter++));
                    }
                    pages.put(i+1, collection);
                }
                if(remainder > 0){
                    int index = contacts.size()-1;
                    Collection<Contact> collection = new ArrayList<>();
                    for (int i = 0; i < remainder; i++) {
                        collection.add(contacts.get(index--));
                    }
                    pages.put(pages.size()+1, collection);
                }
            }
            Collection<Contact> collection = pages.get(pageNum);
            return collection;
        }else{
            return pages.get(pageNum);
        }

    }

    @RequestMapping(value = "/contactbook", method = RequestMethod.GET)
    public Collection<Contact> getAllContacts(){
        return ContactBook.getAllContacts();
    }

    @RequestMapping(value = "/contactbook/{email}", method = RequestMethod.PATCH)
    public void editContact(@PathVariable String email, @RequestBody EditContact editContact){
        Contact tmp = ContactBook.get(email);
        if(editContact.getAddress()!= null)
            tmp.setAddress(editContact.getAddress());
        if(editContact.getPhone() != null)
            tmp.setPhone(editContact.getPhone());
        ContactBook.put(email, tmp);
    }

    @RequestMapping(value = "/contactbook/{email}", method = RequestMethod.DELETE)
    public Contact deleteContact(@PathVariable String email){
        return ContactBook.delete(email);
    }
}
