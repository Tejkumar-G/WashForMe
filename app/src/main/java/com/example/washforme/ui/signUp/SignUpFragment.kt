package com.example.washforme.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.washforme.R
import com.example.washforme.component.GradientButton
import com.example.washforme.ui.theme.TextWhite

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SignUpView()
            }
        }
    }

    @Composable
    fun SignUpView() {

        var mobileNumber by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }

        var rememberMe by remember {
            mutableStateOf(false)
        }

        val transparentColor = Color.Transparent

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "back",
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        text = "SIGN UP",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h1
                    )
                }
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(TextWhite)
                                .padding(25.dp)
                        ) {
                            Column {
                                Text(text = "Enter your username")
                                TextField(value = mobileNumber, onValueChange = {
                                    mobileNumber = it
                                }, leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.person),
                                        contentDescription = ""
                                    )
                                }, colors = TextFieldDefaults.textFieldColors(
                                    unfocusedLabelColor = transparentColor,
                                    focusedLabelColor = transparentColor,
                                    unfocusedIndicatorColor = Color.Black,
                                    focusedIndicatorColor = Color.Black,
                                    backgroundColor = transparentColor,
                                    leadingIconColor = Color.Black,
                                    cursorColor = Color.Black,
                                    textColor = Color.Black,
                                )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "Enter your Email")
                                TextField(value = password, onValueChange = {
                                    password = it
                                }, leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.email),
                                        contentDescription = ""
                                    )
                                }, colors = TextFieldDefaults.textFieldColors(
                                    unfocusedLabelColor = transparentColor,
                                    focusedLabelColor = transparentColor,
                                    unfocusedIndicatorColor = Color.Black,
                                    focusedIndicatorColor = Color.Black,
                                    backgroundColor = transparentColor,
                                    leadingIconColor = Color.Black,
                                    cursorColor = Color.Black,
                                    textColor = Color.Black,
                                )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "Enter your Mobile Number")
                                TextField(value = password, onValueChange = {
                                    password = it
                                }, leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.phone),
                                        contentDescription = ""
                                    )
                                }, colors = TextFieldDefaults.textFieldColors(
                                    unfocusedLabelColor = transparentColor,
                                    focusedLabelColor = transparentColor,
                                    unfocusedIndicatorColor = Color.Black,
                                    focusedIndicatorColor = Color.Black,
                                    backgroundColor = transparentColor,
                                    leadingIconColor = Color.Black,
                                    cursorColor = Color.Black,
                                    textColor = Color.Black,
                                )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "Enter your Password")
                                TextField(value = password, onValueChange = {
                                    password = it
                                }, leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.lock),
                                        contentDescription = ""
                                    )
                                }, colors = TextFieldDefaults.textFieldColors(
                                    unfocusedLabelColor = transparentColor,
                                    focusedLabelColor = transparentColor,
                                    unfocusedIndicatorColor = Color.Black,
                                    focusedIndicatorColor = Color.Black,
                                    backgroundColor = transparentColor,
                                    leadingIconColor = Color.Black,
                                    cursorColor = Color.Black,
                                    textColor = Color.Black,
                                )
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = rememberMe,
                                        onCheckedChange = {
                                            rememberMe = it
                                        }
                                    )
                                    Text(
                                        text = "Remember Me",
                                        style = MaterialTheme.typography.body1,
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 45.dp),
                                    contentAlignment = Alignment.BottomEnd
                                ) {
                                    Button(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .background(
                                                brush = Brush.horizontalGradient(
                                                    listOf(Color.Blue, Color.Red)
                                                ),
                                                shape = RoundedCornerShape(20.dp)
                                            ),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color.Transparent,
                                            contentColor = Color.White
                                        ),
                                        onClick = {
                                            /*TODO*/
                                        }
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.arrow_next),
                                            contentDescription = ""
                                        )
                                    }
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 35.dp)
                                .fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 15.dp),
                                    text = "Have an account?",
                                    style = MaterialTheme.typography.body1,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                                GradientButton(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = "Log In",
                                    onClick = { /*TODO*/ },
                                    startColor = Color.Blue,
                                    endColor = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    @Preview
    fun SignUpPreview() {
        SignUpView()
    }
}
