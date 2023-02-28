package com.example.washforme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.washforme.ui.theme.font

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                SignUp()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SignUp()
}

@Composable
fun SignUp() {
    Row (
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ){
        Text(text = "Softsuave", color = Color.Cyan, fontFamily = FontFamily.Cursive)
        Text(text = "Tech")
    }
}