package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetails extends AppCompatActivity {

    Button delete;
    ImageView image;
    TextView name;
    TextView email;
    TextView number;
    Button addimage;
    String nmbertoDel;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Intent i = getIntent();
        ContactModel contactModel=(ContactModel) i.getSerializableExtra("contactModel");
        delete=findViewById(R.id.Delete);
        image=(ImageView)findViewById(R.id.imageView);
        name=findViewById(R.id.tv_contactName);
        email=findViewById(R.id.tv_email);
        number=findViewById((R.id.tv_contactNo));
        back=findViewById(R.id.Back);
        addimage=findViewById(R.id.addImage);
        name.setText(contactModel.getContactName());
        number.setText(contactModel.getContactNo());
        email.setText(contactModel.getContactEmail());
        Bitmap im= BitmapFactory.decodeFile(contactModel.getContactPhoto());

        if(im==null)
        {
            image.setImageResource(R.drawable.images);
            //image=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.p);
        }
        else
        image.setImageBitmap(im);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nmbertoDel=number.getText().toString();
                ContactTable contactTable=new ContactTable();
                contactTable.setNumber(nmbertoDel);
                Home.contactDataBase.MyDao().deleteContact(contactTable);
                Toast.makeText(getApplicationContext(),"Contact deleted..",Toast.LENGTH_LONG).show();

                //set fields empty??
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ContactDetails.this,Home.class);
                startActivity(intent);
            }
        });
    }
}
