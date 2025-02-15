package com.example.courtfinderlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class SignUpActivity : ComponentActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var signupBtn: Button

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.courtfindersignuplayout) // Use your signup layout

        // Initialize the views
        usernameInput = findViewById(R.id.username)
        emailInput = findViewById(R.id.email)
        passwordInput = findViewById(R.id.password)
        confirmPasswordInput = findViewById(R.id.confirmpassword)
        signupBtn = findViewById(R.id.signupButton) // Use your signup button

        dbHelper = DatabaseHelper(this)

        signupBtn.setOnClickListener { // No need for View.OnClickListener
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            } else if (password != confirmPassword) {
                Toast.makeText(this@SignUpActivity, "Passwords don't match", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Check if the username already exists
                val existingUser = dbHelper.getUser(username, "") // Check only by username
                if (existingUser != null) {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Username already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Add the new user to the database
                    val newUser = User(username, password, email)
                    dbHelper.addUser(newUser)
                    Toast.makeText(this@SignUpActivity, "Sign up successful!", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Close the SignUpActivity
                }
            }
        }
    }
}