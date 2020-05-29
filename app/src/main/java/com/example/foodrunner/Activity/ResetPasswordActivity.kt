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

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var etOTP : TextInputLayout
    lateinit var etNewPass : TextInputLayout
    lateinit var etCnfrmPass : TextInputLayout
    lateinit var btnSubmit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        etOTP = findViewById(R.id.etOTP)
        etNewPass = findViewById(R.id.etNewPass)
        etCnfrmPass = findViewById(R.id.etCnfrmPass)
        btnSubmit = findViewById(R.id.btnSubmit)
        if(intent != null){
            val mobile = intent.getStringExtra("mobile_number")
            val email = intent.getStringExtra("email")

            btnSubmit.setOnClickListener {
                val otp = etOTP.editText?.text.toString()
                val pass = etNewPass.editText?.text.toString()
                val cnfrmPass = etCnfrmPass.editText?.text.toString()
                if(pass == cnfrmPass !=null){
                    val url = "http://13.235.250.119/v2/reset_password/fetch_result"
                    val queue = Volley.newRequestQueue(this@ResetPasswordActivity)
                    val params = JSONObject()
                    params.put("mobile_number", mobile)
                    params.put("password", pass)
                    params.put("otp", otp)

                    val resetReq = object : JsonObjectRequest(Request.Method.POST, url, params, Response.Listener {
                        val data = it.getJSONObject("data")
                        if(data.getBoolean("success")){
                            Toast.makeText(this, data.getString("successMessage"), Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                        }
                    }, Response.ErrorListener {
                        Toast.makeText(this, "Error- $it",Toast.LENGTH_LONG).show()
                    }){

                    }

                }else{

                }

            }

        }else{

        }
    }
}
