package com.example.foodrunner.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner.R
import com.example.foodrunner.util.ConnectionManager
import kotlinx.android.synthetic.main.login_activity.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var etMobile: com.google.android.material.textfield.TextInputLayout
    lateinit var etPassword: com.google.android.material.textfield.TextInputLayout
    lateinit var btnLogin: Button
    lateinit var txtForgot: TextView
    lateinit var txtRegister: TextView


    lateinit var relative_layout: RelativeLayout
    lateinit var linear_layout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        etMobile = findViewById(R.id.etMobile)
        etPassword = findViewById(R.id.etPassword)
        txtForgot = findViewById(R.id.txtForgot)
        txtRegister = findViewById(R.id.txtRegister)
        btnLogin = findViewById(R.id.btnLogin)

        //LOGIN

        btnLogin.setOnClickListener {

            val queue = Volley.newRequestQueue(this@LoginActivity)
            val url = "http://13.235.250.119/v2/login/fetch_result/"

            val mobile = etMobile.editText?.text.toString()
            val password = etPassword.editText?.text.toString()

            if ((mobile.length >= 10) && (password.length >= 4)) {
                if (ConnectionManager().checkConnectivity(this@LoginActivity)) {
                    val jsonParams = JSONObject()
                    jsonParams.put("mobile_number", mobile)
                    jsonParams.put("password", password)
                    val loginPostReq = object :
                        JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
                            val data = it.getJSONObject("data")
                            if(data.getBoolean("success")){
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                            }

                        },
                            Response.ErrorListener {
                                Toast.makeText(this@LoginActivity, "Error $it", Toast.LENGTH_SHORT)
                                    .show()
                            }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"
                            headers["token"] = "38ac8d48e87ad3"
                            return headers
                        }
                    }

                    queue.add(loginPostReq)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        " No Internet Connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }

        }



        //FORGOT PASSWORD

        txtForgot.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPassword::class.java)
            startActivity(intent)
        }


        //REGISTER YOURSELF

        txtRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, Register::class.java)
            startActivity(intent)
        }

    }
}
