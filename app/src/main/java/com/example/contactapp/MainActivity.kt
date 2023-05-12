package com.example.contactapp
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val REQUEST_PICK_CONTACT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPickContact = findViewById<Button>(R.id.btn_pick_contact)
        btnPickContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            startActivityForResult(intent, REQUEST_PICK_CONTACT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_PICK_CONTACT && resultCode == Activity.RESULT_OK) {
            val contactUri = data?.data ?: return
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID // tambahkan kolom contact_id
            )
            val cursor = contentResolver.query(contactUri, projection, null, null, null)
            cursor?.apply {
                if (moveToFirst()) {
                    val contactId = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                    val contactName = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val contactPhoneNumber = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    cursor.close()
                    val hapusSpasi= contactName.replace(" ", "")
                    val namaEmail = "${hapusSpasi}@gmail.com"
                    val intent = Intent(this@MainActivity, contact_detail::class.java)
                    intent.putExtra("id", contactId)
                    intent.putExtra("name", contactName)
                    intent.putExtra("phone", contactPhoneNumber)
                    intent.putExtra("email", namaEmail)
                    startActivity(intent)
                }
            }
        }
    }

}