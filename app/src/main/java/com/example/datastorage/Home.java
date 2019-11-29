package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements  View.OnClickListener {
    ListView lv;
    Button addContact;
    Button loadCOntact;
    Button showConatct;
    ArrayAdapter<String> adapter;
    ArrayList<String> listOfContacts = new ArrayList<>();
    public static ContactDataBase contactDataBase;


    ArrayList<ContactModel> contactModels=new ArrayList<>();
    private static ContactAdapter contactAdapter;


    String photoPath = "" ; // Photo path
    String name;
    String id;
    String numb;
    String email;
    byte[] images;
//    private static final String TAG = Home.class.getSimpleName();
//    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
//    private Uri uriContact;
//    private String contactID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addContact = findViewById(R.id.addcontact);
        loadCOntact = findViewById(R.id.loadcontact);
        showConatct = findViewById(R.id.showcontact);

        loadCOntact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadContacts();
            }
        });
        showConatct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showContact();
            }
        });
        addContact.setOnClickListener(this);
        lv = findViewById(R.id.listOfcontacts);
        contactDataBase = Room.databaseBuilder(getApplicationContext(), ContactDataBase.class, "contactdb").allowMainThreadQueries().build();


    }
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(Home.this, AddContacts.class);
        startActivity(intent);

    }

    private void loadContacts()
    {
        readContacts();
    }

    private void showContact()
    {
        List<ContactTable> contactTable=contactDataBase.MyDao().getContacts();
        for(ContactTable user : contactTable)
        {
            photoPath = "" ; // Photo path
            name=user.getName();
            id=user.getId();
            numb=user.getNumber();
            email=user.getEmail();
            byte[] images=user.getImage();
            if (images != null)
            {

                // Now make a cache folder in file manager to
                // make cache of contacts images and save them
                // in .png
                Bitmap bitmap = BitmapFactory.decodeByteArray(
                        images, 0,images.length);
                File cacheDirectory = getBaseContext()
                        .getCacheDir();
                File tmp = new File(cacheDirectory.getPath()
                        + "/_androhub" + Long.parseLong(id) + ".png");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            tmp);
                    bitmap.compress(Bitmap.CompressFormat.PNG,
                            100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                photoPath = tmp.getPath();// finally get the
                // saved path of
                // image
            }

            contactModels.add(new ContactModel(id,name,email,numb,photoPath));
        }
        contactAdapter=new ContactAdapter(this,contactModels);
        lv.setAdapter(contactAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {

                    Intent intent=new Intent(Home.this,ContactDetails.class);
                    intent.putExtra("contactModel",contactModels.get(i));
                    startActivity(intent);

                }
            });

     }



    private void readContacts() {
        ArrayList<ContactModel> contactList = new ArrayList<ContactModel>();

        Uri uri = ContactsContract.Contacts.CONTENT_URI; // Contact URI
        Cursor contactsCursor = getContentResolver().query(uri, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC "); // Return
        // all
        // contacts
        // name
        // containing
        // in
        // URI
        // in
        // ascending
        // order
        // Move cursor at starting
        if (contactsCursor.moveToFirst()) {
            do {
                long contctId = contactsCursor.getLong(contactsCursor
                        .getColumnIndex("_ID")); // Get contact ID
                Uri dataUri = ContactsContract.Data.CONTENT_URI; // URI to get
                // data of
                // contacts
                Cursor dataCursor = getContentResolver().query(dataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + contctId,
                        null, null);// Retrun data cusror represntative to
                // contact ID

                // Strings to get all details
                String displayName = "";
                String nickName = "";
                String homePhone = "";
                String mobilePhone = "";
                String workPhone = "";
                String photoPath = "" + R.drawable.ic_person_black_24dp; // Photo path
                byte[] photoByte = null;// Byte to get photo since it will come
                // in BLOB
                String homeEmail = "";
                String workEmail = "";
                String companyName = "";
                String title = "";

                // This strings stores all contact numbers, email and other
                // details like nick name, company etc.
                String contactNumbers = "";
                String contactEmailAddresses = "";
                String contactOtherDetails = "";

                // Now start the cusrsor
                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor
                            .getString(dataCursor
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));// get
                    // the
                    // contact
                    // name
                    do {
                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)) {
                            nickName = dataCursor.getString(dataCursor
                                    .getColumnIndex("data1")); // Get Nick Name
                            contactOtherDetails += "NickName : " + nickName
                                    + "n";// Add the nick name to string

                        }

                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {

                            // In this get All contact numbers like home,
                            // mobile, work, etc and add them to numbers string
                            switch (dataCursor.getInt(dataCursor
                                    .getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    homePhone = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactNumbers += "Home Phone : " + homePhone
                                            + "n";
                                    break;

                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    workPhone = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactNumbers += "Work Phone : " + workPhone
                                            + "n";
                                    break;

                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    mobilePhone = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactNumbers += "Mobile Phone : "
                                            + mobilePhone + "n";
                                    break;

                            }
                        }
                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {

                            // In this get all Emails like home, work etc and
                            // add them to email string
                            switch (dataCursor.getInt(dataCursor
                                    .getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                    homeEmail = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactEmailAddresses += "Home Email : "
                                            + homeEmail + "n";
                                    break;
                                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                    workEmail = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactEmailAddresses += "Work Email : "
                                            + workEmail + "n";
                                    break;

                            }
                        }

                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
                            companyName = dataCursor.getString(dataCursor
                                    .getColumnIndex("data1"));// get company
                            // name
                            contactOtherDetails += "Coompany Name : "
                                    + companyName + "n";
                            title = dataCursor.getString(dataCursor
                                    .getColumnIndex("data4"));// get Company
                            // title
                            contactOtherDetails += "Title : " + title + "n";

                        }

                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                            photoByte = dataCursor.getBlob(dataCursor
                                    .getColumnIndex("data15")); // get photo in
                            // byte

                            if (photoByte != null) {

                                // Now make a cache folder in file manager to
                                // make cache of contacts images and save them
                                // in .png
                                Bitmap bitmap = BitmapFactory.decodeByteArray(
                                        photoByte, 0, photoByte.length);
                                File cacheDirectory = getBaseContext()
                                        .getCacheDir();
                                File tmp = new File(cacheDirectory.getPath()
                                        + "/_androhub" + contctId + ".png");
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(
                                            tmp);
                                    bitmap.compress(Bitmap.CompressFormat.PNG,
                                            100, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                                photoPath = tmp.getPath();// finally get the
                                // saved path of
                                // image
                            }

                        }

                    } while (dataCursor.moveToNext()); // Now move to next
                    // cursor
//
//                    contactList.add(new ContactModel(Long.toString(contctId),
//                            displayName, contactNumbers, contactEmailAddresses,
//                            photoPath));// Finally add
                    // items to
                    // array list
                    ContactTable ct=new ContactTable(Long.toString(contctId),
                           displayName, contactNumbers, contactEmailAddresses,
                           photoByte);
                  contactDataBase.MyDao().addContact(ct);
                }

            } while (contactsCursor.moveToNext());
        }

    }
}
