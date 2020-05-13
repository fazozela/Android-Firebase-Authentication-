package com.example.articles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("Message", "Integración de Firebase COMPLETA")
        analytics.logEvent("InitScreen", bundle)

        setup()
    }

    private fun setup(){
        title = "Autenticación"

        btn_register.setOnClickListener{
            if (edit_text_email.text.isNotEmpty() && edit_text_password.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(edit_text_email.text.toString(), edit_text_password.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", HomeActivity.ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }

        btn_login.setOnClickListener{
            if (edit_text_email.text.isNotEmpty() && edit_text_password.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(edit_text_email.text.toString(), edit_text_password.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", HomeActivity.ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email:String, provider: HomeActivity.ProviderType){

        val homeIntent =  Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

}
