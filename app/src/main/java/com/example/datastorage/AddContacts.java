package com.example.datastorage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class AddContacts extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    EditText name;
    EditText numbrer;
    EditText email;
    Button addimage;
    Button save;
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        imageView=findViewById(R.id.ivaddimage);
         name=findViewById(R.id.etaddname);
        numbrer=findViewById(R.id.etaddnum);
        email=findViewById(R.id.etaddemail);
        save=findViewById(R.id.submit);

        save.setOnClickListener(this);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();

            }
        });
    }

    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View view)
    {

        String username=name.getText().toString();
        String usernumber=numbrer.getText().toString();
        String useremail=email.getText().toString();
        String userimage=name.getText().toString();

        ContactTable contactTable=new ContactTable();
        contactTable.setName(username);
        contactTable.setNumber(usernumber);
        contactTable.setEmail(useremail);
//        contactTable.setImage(userimage);

        Home.contactDataBase.MyDao().addContact(contactTable);
        Toast.makeText(getApplicationContext(),"ContactAdded",Toast.LENGTH_SHORT).show();

        name.setText("");
        numbrer.setText("");
        email.setText("");

        Intent intent=new Intent(AddContacts.this,Home.class);
        startActivity(intent);
    }
}
