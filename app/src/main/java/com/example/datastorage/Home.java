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
                //   List<ContactTable> contactList=MainActivity.ContactDataBase.class.MyDao();
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

//        // using native contacts selection
//        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
//       // startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
//
//        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
//        String selection = null; //where
//        String[] selectionArgs = null;
//        String sortOrder = null;
//
//        ContentResolver resolver = getContentResolver();
//        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
//        while (cursor.moveToNext())
//        {
//            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
////            String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//
////            ContactTable user = new ContactTable();
////            user.setName(name);
////            user.setNumber(num);
////            //user.setEmail(email);
////            Home.contactDataBase.MyDao().addContact((user));
//
//            ContactModel md=new ContactModel();
//            md.setContactName(name);
//            md.setContactNo(num);
//            //md.setContactEmail();
//            contactModels.add(md);
//
//            name = "";
//            num = "";
//        }

        contactModels=readContacts();
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
//            Log.d(TAG, "Response: " + data.toString());
//            uriContact = data.getData();
//
//
//            String cname=retrieveContactName();
//            String cno=retrieveContactNumber();
//            //retrieveContactPhoto();
//
//            ContactTable user = new ContactTable();
//            user.setName(cname);
//            user.setNumber(cno);
//            //user.setEmail(email);
//            Home.contactDataBase.MyDao().addContact((user));
//
//        }
//    }

    private void showContact() {
       // List<ContactTable> contacts = Home.contactDataBase.MyDao().getContacts();
//        for (ContactTable user : contacts) {
//            String name = user.getName();
//            String number = user.getNumber();
//            String email = user.getEmail();
//
//            listOfContacts.add(name + "\n" + number + "\n" + email);
//
//
//        }
//        for (ContactModel m:contactModels)
//        {
//            String n=m.getContactName();
//            String no=m.getContactNo();
//            listOfContacts.add(n +"\n" +no +"\n");
//        }
//        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listOfContacts);
//        lv.setAdapter(adapter);

        //contactAdapter=null;
        if(contactAdapter==null)
        {
            contactAdapter=new ContactAdapter(this,contactModels);
            lv.setAdapter(contactAdapter);

        }
        else
        {
            System.out.println("addapter is nill");
        }
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {

                    Intent intent=new Intent(Home.this,ContactDetails.class);
                    intent.putExtra("contactModel",contactModels.get(i));
                    startActivity(intent);

                }
            });

  //      adapter.notifyDataSetChanged();
     }
//
//    private void retrieveContactPhoto() {
//
//        Bitmap photo = null;
//
//        try {
//            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
//                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));
//
//            if (inputStream != null) {
//                photo = BitmapFactory.decodeStream(inputStream);
//     //           ImageView imageView = (ImageView) findViewById(R.id.img_contact);
//           //     imageView.setImageBitmap(photo);
//            }
//
//            assert inputStream != null;
//            inputStream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//    private String retrieveContactNumber() {
//
//        String contactNumber = null;
//
//        // getting contacts ID
//        Cursor cursorID = getContentResolver().query(uriContact,
//                new String[]{ContactsContract.Contacts._ID},
//                null, null, null);
//
//        if (cursorID.moveToFirst()) {
//
//            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
//        }
//
//        cursorID.close();
//
//        Log.d(TAG, "Contact ID: " + contactID);
//
//        // Using the contact ID now we will get contact phone number
//        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
//
//                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
//                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
//                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
//
//                new String[]{contactID},
//                null);
//
//        if (cursorPhone.moveToFirst()) {
//            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//        }
//
//        cursorPhone.close();
//
//        Log.d(TAG, "Contact Phone Number: " + contactNumber);
//        return contactNumber;
//    }
//
//    private String reteriveContactEmail()
//    {
//        String contactEmail=null;
//
//        // getting contacts ID
//        Cursor cursorID = getContentResolver().query(uriContact,
//                new String[]{ContactsContract.Contacts._ID},
//                null, null, null);
//
//        if (cursorID.moveToFirst()) {
//
//            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
//        }
//
//        cursorID.close();
//
//        Log.d(TAG, "Contact ID: " + contactID);
//
//        ContentResolver contactResolver = getContentResolver();
//
//        Cursor  emailCursor = contactResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                null,
//                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[] { contactID }, null);
//
//            String s=null;
//            while (emailCursor.moveToNext())
//        {
//            String phone = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//            int type = emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
//             s = (String) ContactsContract.CommonDataKinds.Email.getTypeLabel(getResources(), type, "");
//
//            Log.d("TAG", s + " email: " + phone);
//        }
//
//        emailCursor.close();
//
//            return s;
//    }
//
//    private String retrieveContactName() {
//
//        String contactName = null;
//
//        // querying contact data store
//        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//
//            // DISPLAY_NAME = The display name for the contact.
//            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.
//
//            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//        }
//
//        cursor.close();
//
//        Log.d(TAG, "Contact Name: " + contactName);
//
//        return contactName;
//    }


    private ArrayList<ContactModel> readContacts() {
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

                    contactList.add(new ContactModel(Long.toString(contctId),
                            displayName, contactNumbers, contactEmailAddresses,
                            photoPath));// Finally add
                    // items to
                    // array list
                }

            } while (contactsCursor.moveToNext());
        }
        return contactList;
    }
}
