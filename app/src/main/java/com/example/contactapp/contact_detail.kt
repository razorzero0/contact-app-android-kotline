package com.example.contactapp
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide

class  contact_detail: AppCompatActivity() {
        @SuppressLint("SuspiciousIndentation")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_contact_detail)

            val contactName = intent.getStringExtra("name")
            val contactPhoneNumber = intent.getStringExtra("phone")
            val contactImel = intent.getStringExtra("email")

            val imageView = findViewById<ImageView>(R.id.imageView)

            // Mendapatkan ID contact dari intent
            val contactId = intent.getStringExtra("id")

            val contactUri = contactId?.let { ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, it.toLong()) }
            val photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
            // Menampilkan foto kontak menggunakan Glide
            Glide.with(this)
                .load(photoUri)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .circleCrop()
                .into(imageView)
            val tvContactName = findViewById<TextView>(R.id.contact_name)
            tvContactName.text = contactName

            val tvContactPhoneNumber = findViewById<TextView>(R.id.contact_phone_number)
            tvContactPhoneNumber.text = contactPhoneNumber

            val btnSendMessage = findViewById<Button>(R.id.btn_send_message)
            btnSendMessage.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", contactPhoneNumber, null))
                startActivity(intent)
            }

            val btnMakeCall = findViewById<Button>(R.id.btn_make_call)
            btnMakeCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contactPhoneNumber, null))
                startActivity(intent)
            }
            val btnSendEmail = findViewById<Button>(R.id.btn_send_email)
            btnSendEmail.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contactImel))
                startActivity(Intent.createChooser(emailIntent, "Send email..."))
            }
            val whatsappButton = findViewById<Button>(R.id.btn_send_wa)
            whatsappButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$contactPhoneNumber")
                    startActivity(intent)


            }
        }
    }





