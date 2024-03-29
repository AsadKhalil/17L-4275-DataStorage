package com.example.datastorage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ContactModel> listOfContacts;

    public ContactAdapter(Context context, ArrayList<ContactModel> listOfContacts) {
        this.context = context;
        this.listOfContacts = listOfContacts;
    }

    @Override
    public int getCount() {
        return listOfContacts.size();
    }

    @Override
    public ContactModel getItem(int i) {
        return listOfContacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ContactModel model = listOfContacts.get(position);
        ViewHodler holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listnameimage, parent, false);
            holder = new ViewHodler();
            holder.contactImage = (ImageView) convertView
                    .findViewById(R.id.imageView_cimage);
            holder.contactName = (TextView) convertView
                    .findViewById(R.id.textView_cname);
//            holder.contactEmail = (TextView) convertView
//                    .findViewById(R.id.contactEmail);
//            holder.contactNumber = (TextView) convertView
//                    .findViewById(R.id.contactNumber);
//            holder.contactOtherDetails = (TextView) convertView
//                    .findViewById(R.id.contactOtherDetails);

            convertView.setTag(holder);
        } else {
            holder = (ViewHodler) convertView.getTag();
        }

        // Set items to all view
        if (!model.getContactName().equals("")
                && model.getContactName() != null) {
            holder.contactName.setText(model.getContactName());
        } else {
//            holder.contactName.setText("No Name");
        }
//        if (!model.getContactEmail().equals("")
//                && model.getContactEmail() != null) {
//            holder.contactEmail.setText("EMAIL ID - n"
//                    + model.getContactEmail());
//        } else {
//            holder.contactEmail.setText("EMAIL ID - n" + "No EmailId");
//        }

//        if (!model.getContactNo().equals("")
//                && model.getContactNo() != null) {
//            holder.contactNumber.setText("CONTACT NUMBER - n"
//                    + model.getContactNo());
//        } else {
//            holder.contactNumber.setText("CONTACT NUMBER - n"
//                    + "No Contact Number");
//        }

//        if (!model.getContactOtherDetails().equals("")
//                && model.getContactOtherDetails() != null) {
//            holder.contactOtherDetails.setText("OTHER DETAILS - n"
//                    + model.getContactOtherDetails());
//        } else {
//            holder.contactOtherDetails.setText("OTHER DETAILS - n"
//                    + "Other details are empty");
//        }

        // Bitmap for imageview
        Bitmap image = null;
        if (!model.getContactPhoto().equals("")
                && model.getContactPhoto() != null) {
            image = BitmapFactory.decodeFile(model.getContactPhoto());// decode
            // the
            // image
            // into
            // bitmap
            if (image != null)
                holder.contactImage.setImageBitmap(image);// Set image if bitmap
                // is not null
            else {
                image = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.p);// if bitmap is null then set
                // default bitmap image to
                // contact image
                holder.contactImage.setImageBitmap(image);
            }
        } else {
            image = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.p);
            holder.contactImage.setImageBitmap(image);
        }
        return convertView;
    }

    // View holder to hold views
    private class ViewHodler {
        ImageView contactImage;
        TextView contactName, contactNumber, contactEmail, contactOtherDetails;
    }
}
