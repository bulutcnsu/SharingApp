package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

/**
 * Editing a pre-existing contact consists of deleting the old contact and adding a new contact with the old
 * contact's id.
 * Note: You will not be able contacts which are "active" borrowers
 * updated
 */
public class EditContactActivity extends AppCompatActivity implements Observer {

    private ContactList contact_list = new ContactList();
    private  ContactListController  contactListController =new ContactListController(contact_list);//added

    private Contact contact;
    private ContactController  contact_controller; //added
    private EditText email;
    private EditText username;
    private Context context;

    private ItemList item_list = new ItemList();
    private ItemListController item_list_controller = new ItemListController(item_list);//added

    private ArrayAdapter<String> adapter;
    private boolean on_create_update = true;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);



        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        username.setText(contact.getUsername());
        email.setText(contact.getEmail());
        contact = contact_list.getContact(pos);

        Intent intent = getIntent();
        pos = intent.getIntExtra("position", 0);

        context = getApplicationContext();


        contactListController.addObserver(this);
        contactListController.loadContacts(context); // added

        on_create_update = false;

    }

    public void saveContact(View view) {

        String email_str = email.getText().toString();
        String username_str = username.getText().toString();
        String id = contact_controller.getId();

        if (email_str.equals("")) {
            email.setError("Empty field!");
            return;
        }

        if (!email_str.contains("@")){
            email.setError("Must be an email address!");
            return;
        }



       Contact updated_contact = new Contact(username_str, email_str, id);
       ContactController updated_contact_controller = new ContactController(updated_contact);//updated


        // Check that username is unique AND username is changed (Note: if username was not changed
        // then this should be fine, because it was already unique.)
        if (!contact_list.isUsernameAvailable(username_str) && !(contact.getUsername().equals(username_str))) {
            username.setError("Username already taken!");
            return;
        }



        boolean success = contactListController.editContact(contact, updated_contact, context); //updated
        if (!success) {
            return;
        }

       contactListController.removeObserver(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    public void deleteContact(View view) {

        // Delete item
        boolean success = contactListController.deleteContact(contact, context);
        if (!success) {
            return;
        }

        // End EditItemActivity
        contactListController.removeObserver(this);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void update() {

        if (on_create_update){

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
              contactListController.getAllUsernames());

            contact = contactListController.getContact(pos);

            contact_controller = new ContactController(contact);

            username = (EditText) findViewById(R.id.username);
            email = (EditText) findViewById(R.id.email);

            // Update the view
            username.setText(contact_controller.getUsername());
            email.setText(contact_controller.getEmail());
    }

}
}
