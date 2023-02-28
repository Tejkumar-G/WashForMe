package com.example.washforme.ui.otpVerification

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.example.washforme.R
import com.example.washforme.ui.theme.WashForMeTheme
import dagger.Component

class OtpVerificationFragment : Fragment() {

    private val viewModel: OtpVerificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                WashForMeTheme {
                    OtpView()
                }
            }
        }
    }


    @Composable
    fun OtpView() {
        var mobileNumber by remember {
            mutableStateOf("")
        }

        val transparent = Color.Transparent
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.Gray)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)

            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.Center)
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.paper_plane),
                        contentDescription = "",
                        alignment = Alignment.Center
                    )
                }

                Text(
                    text = "OTP Verification",
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 15.dp),
                    text = "We will send you a One Time Password on this mobile number",
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    text = "Enter OTP",
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .width(220.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color.LightGray)
                            .padding(horizontal = 15.dp, vertical = 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = mobileNumber,
                            onValueChange = {
                                if (it.length <= 4)
                                    mobileNumber = it
                            },
                            textStyle = MaterialTheme.typography.h2,
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                disabledTextColor = transparent,
                                cursorColor = Color.Black,
                                errorCursorColor = transparent,
                                focusedIndicatorColor = transparent,
                                unfocusedIndicatorColor = transparent,
                                errorIndicatorColor = transparent,
                                disabledIndicatorColor = transparent,
                                leadingIconColor = transparent,
                                disabledLeadingIconColor = transparent,
                                errorLeadingIconColor = transparent,
                                trailingIconColor = transparent,
                                disabledTrailingIconColor = transparent,
                                errorTrailingIconColor = transparent,
                                backgroundColor = transparent,
                                focusedLabelColor = transparent,
                                unfocusedLabelColor = transparent,
                                disabledLabelColor = transparent,
                                errorLabelColor = transparent,
                                placeholderColor = transparent,
                                disabledPlaceholderColor = transparent
                            )
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        enabled = true,
                        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                        onClick = {}
                    ) {
                        Text(text = "Submit")
                    }
                }
            }
        }
    }

    @Composable
    @Preview
    fun OtpScreenPreview() {
        WashForMeTheme {
            OtpView()
        }
    }
}