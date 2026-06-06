package com.example.nutritionapp2ndedition

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


data class ChatMessage(val text: String, val isUser: Boolean)


@Composable
fun OnboardingScreen(onFinished: (UserProfile) -> Unit = {}, modifier: Modifier = Modifier) {
    val ralewayFontFamily = FontFamily(
        androidx.compose.ui.text.font.Font(R.font.raleway_regular)
    )
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Hey I am Chameleon!  What is a good name for you?", false)
        )
    }
    var userInput by remember {mutableStateOf("")}
    val listState = rememberLazyListState()
    val repository = remember { ChatRepository() }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.size - 1)
    }
    LaunchedEffect(isLoading) {
        if (isLoading) {
            val response = repository.sendMessage(messages)
            android.util.Log.d("CHAMELEON", "Response: $response")
            android.util.Log.d("CHAMELEON", "Contains check: ${response.lowercase().contains("onboarding complete")}")
            messages.add(ChatMessage(response, false))

            if (response.contains("ONBOARDING_COMPLETE") ||
                response.contains("Onboarding Complete:") ||
                response.lowercase().contains("onboarding complete")) {
                val profile = parseUserProfile(response)
                delay(5)
                onFinished(profile)
            } else {
                isLoading = false
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) {message ->
                ChatBubble(message = message, fontFamily = ralewayFontFamily)
            }
            if (isLoading) {
                item {
                    Text(
                        text = "Chameleon is thinking... 🦎",
                        color = Color(0xFF98A235),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it},
                placeholder = { Text("Type Here...", color = Color(0xFF555555))},
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(color = Color.White)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .background(Color(0xFF2E7D32))
                    .padding(12.dp)
                    .clickable {
                        if (userInput.isNotBlank() && !isLoading) {
                            val userMessage = userInput
                            messages.add(ChatMessage(userMessage, true))
                            userInput = ""
                            isLoading = true
                        }
                    }
            ) {
                Text("->", color = Color.White, fontSize = 20.sp)
            }
        }
    }

}

@Composable
fun ChatBubble(message: ChatMessage, fontFamily: FontFamily) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (message.isUser) Color(0xFF2E7D32) else Color(0xFF1A1A1A),
                    RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = fontFamily
            )
        }
    }
}
