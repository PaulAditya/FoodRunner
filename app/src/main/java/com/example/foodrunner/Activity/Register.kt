package com.example.foodrunner.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner.R
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

class Register : AppCompatActivity() {

    lateinit var btnRegister : Button
    lateinit var etName : TextInputLayout
    lateinit var etEmail : TextInputLayout
    lateinit var etMobile : TextInputLayout
    lateinit var etDelivery : TextInputLayout
    lateinit var etPass : TextInputLayout
    lateinit var etCnfrmPass : TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnRegister = findViewById(R.id.btnRegister)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etMobile = findViewById(R.id.etMobile)
        etDelivery = findViewById(R.id.etDelivery)
        etPass = findViewById(R.id.etPass)
        etCnfrmPass = findViewById(R.id.etCnfrmPass)
        btnRegister.setOnClickListener {

            val name = etName.editText?.text.toString()
            val mobile = etMobile.editText?.text.toString()
            val email = etEmail.editText?.text.toString()
            val delivery = etDelivery.editText?.text.toString()
            val pass = etPass.editText?.text.toString()
            val cnfrmPass = etCnfrmPass.editText?.text.toString()

            if(name==null||mobile==null||pass==null||email==null||delivery==null||cnfrmPass==null){
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }else{
                if(pass == cnfrmPass){
                    val url = "http://13.235.250.119/v2/register/fetch_result"
                    val queue = Volley.newRequestQueue(this)
                    val params = JSONObject()
                    params.put("name", name)
                    params.put("mobile_number", mobile)
                    params.put("password", pass)
                    params.put("address", delivery)
                    params.put("email", email)

                    val registerPostReq = object : JsonObjectRequest(Request.Method.POST, url, params, Response.Listener {
                        val data = it.getJSONObject("data")
                        if(data.getBoolean("success")){
                            val intent = Intent(this@Register, HomeActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
                        }
                    }, Response.ErrorListener {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                    }){
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"
                            headers["token"] = "38ac8d48e87ad3"
                            return headers
                        }
                    }
                }else{
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
