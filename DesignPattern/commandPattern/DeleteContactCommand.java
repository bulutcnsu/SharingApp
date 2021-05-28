package com.example.sharingapp;

import android.content.Context;

import java.util.List;

/***
 updated by me
 */
public class DeleteContactCommand extends Command{

    private ContactList contact_list; //aggregation
    private Contact contact;
    private Context context;

    public DeleteContactCommand(ContactList contact_list, Contact contact, Context context) {
        this.contact_list = contact_list;
        this.contact = contact;
        this.context = context;
    }


    @Override
    public void execute() {//methods added

        contact_list.deleteContact(contact);
        setIsExecuted(contact_list.saveContacts(context));

    }
}
