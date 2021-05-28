package com.example.sharingapp;

import android.content.Context;

/**
 * updated
 */

public class EditContactCommand  extends Command{
    private ContactList contactList;
    private Contact old_contact;
    private Contact new_contact;
    private Context context;


    public EditContactCommand(ContactList contactList, Contact old_contact, Contact new_contact, Context context) {
        this.contactList = contactList;
        this.old_contact = old_contact;
        this.new_contact = new_contact;
        this.context = context;
    }

    @Override
    public void execute() {

        contactList.deleteContact(old_contact);
        contactList.addContact(new_contact);
        setIsExecuted(contactList.saveContacts(context));
    }
}
