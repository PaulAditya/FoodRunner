package com.example.foodrunner.Activity

import android.content.Context
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

class ForgotPassword : AppCompatActivity() {

    lateinit var btnNext : Button
    lateinit var etMob : TextInputLayout
    lateinit var etEmailadd : TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        btnNext = findViewById(R.id.btnNext)
        etEmailadd = findViewById(R.id.etEmailadd)
        etMob = findViewById(R.id.etMob)
        btnNext.setOnClickListener {

            val mobileNo = etMob.editText?.text.toString()
            val email = etEmailadd.editText?.text.toString()

            val url = "http://13.235.250.119/v2/forgot _password/fetch_result"
            val queue = Volley.newRequestQueue(this@ForgotPassword)
            if(mobileNo.length != null && email.length != null){
                val params = JSONObject()
                params.put("mobile_number", mobileNo)
                params.put("email", email)

                val OTPReq = object : JsonObjectRequest(Request.Method.POST, url, params, Response.Listener {
                    val data = it.getJSONObject("data")
                    if(data.getBoolean("success")){
                        val intent = Intent(this, ResetPasswordActivity::class.java)
                        intent.putExtra("mobile_number", mobileNo)
                        intent.putExtra("email", email)
                        Toast.makeText(this, "OTP has been sent", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }else{
                        //akdjlajdlka
                    }
                }, Response.ErrorListener {
                    Toast.makeText(this, "Volley Error", Toast.LENGTH_SHORT).show()
                }){
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "38ac8d48e87ad3"
                        return headers
                    }
                }
                queue.add(OTPReq)
            }else{
                //Invalid mob and email
                Toast.makeText(this, "Enter valid email and mobile no", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
