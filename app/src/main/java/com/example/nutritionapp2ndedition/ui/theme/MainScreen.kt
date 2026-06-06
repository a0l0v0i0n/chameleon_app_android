package com.example.nutritionapp2ndedition.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutritionapp2ndedition.ChatBubble
import com.example.nutritionapp2ndedition.ChatMessage
import com.example.nutritionapp2ndedition.ChatRepository
import com.example.nutritionapp2ndedition.R
import com.example.nutritionapp2ndedition.UserProfile


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(userProfile: UserProfile, modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableStateOf(0) }


    val infiniteTransition = rememberInfiniteTransition(label = "gradient")
    val gradientOffset by infiniteTransition.animateFloat(
        initialValue = -250f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )
    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        containerColor = Color.Black,
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "chameleon",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.raleway_regular)),
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF2E7D32),
                                        Color(0xFF98A235),
                                        Color(0xFF4CAF50),
                                        Color(0xFF2E7D32)
                                    ),
                                    start = Offset(gradientOffset, 0f),
                                    end = Offset(gradientOffset + 500f, 0f)
                                )
                            )
                        )
                        Text(
                            text = " 🦎",
                            fontSize = 32.sp
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        bottomBar = {
            NavigationBar(//these are the four icons on the botton of the screen.
                containerColor = Color(0xFF1A1A1A)
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = {selectedTab = 0},
                    icon = {Icon(Icons.Default.Home, contentDescription = "Home")},
                    label = {Text("Home")},
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2E7D32),
                        selectedTextColor = Color(0xFF2E7D32),
                        unselectedIconColor = Color(0xFF555555),
                        unselectedTextColor = Color(0xFF555555),
                        indicatorColor = Color(0xFF1A1A1A)
                    )

                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Restaurant, contentDescription = "Nutrition") },
                    label = { Text("Nutrition") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2E7D32),
                        selectedTextColor = Color(0xFF2E7D32),
                        unselectedIconColor = Color(0xFF555555),
                        unselectedTextColor = Color(0xFF555555),
                        indicatorColor = Color(0xFF1A1A1A)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Daily") },
                    label = { Text("Daily") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2E7D32),
                        selectedTextColor = Color(0xFF2E7D32),
                        unselectedIconColor = Color(0xFF555555),
                        unselectedTextColor = Color(0xFF555555),
                        indicatorColor = Color(0xFF1A1A1A)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Chat, contentDescription = "Chat") },
                    label = { Text("Chat") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2E7D32),
                        selectedTextColor = Color(0xFF2E7D32),
                        unselectedIconColor = Color(0xFF555555),
                        unselectedTextColor = Color(0xFF555555),
                        indicatorColor = Color(0xFF1A1A1A)
                    )
                )
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> HomeTab(userProfile = userProfile, modifier = Modifier.padding(innerPadding))
            1 -> NutritionTab(userProfile = userProfile, modifier = Modifier.padding(innerPadding))
            2 -> DailyTab(userProfile = userProfile, modifier = Modifier.padding(innerPadding))
            3 -> ChatTab(userProfile = userProfile, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun HomeTab(userProfile: UserProfile, modifier: Modifier = Modifier) {
    val ralewayFontFamily = FontFamily(Font(R.font.raleway_regular))
    //personalized ai suggestion box
    var aiSummary by remember { mutableStateOf("Figuring out your best path...") }

    LaunchedEffect(Unit) {
        val repository = ChatRepository()
        val prompt = """
        Based on this user's profile, calculate their daily nutritional targets but do not 
        show it in text.:
        Name: ${userProfile.name}
        Age: ${userProfile.age}
        Weight: ${userProfile.weight}
        Height: ${userProfile.height}
        Goals: ${userProfile.goals}
        Context: ${userProfile.context}
        
        Respond with a summary of the essential things they need to hit for the day based on what 
        the user said for their goals with the context and all the other data included in the 
        creation of a response. Do not make it too excessive.
    """.trimIndent()

        val response = repository.sendOneOffMessage(prompt)
        aiSummary = response
    }
    // rest of the home screen ui
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Hey, ${userProfile.name} 👋",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = ralewayFontFamily
        )

        ProfileCard(title = "Daily Target", fontFamily = ralewayFontFamily, glow = true) {
            Text(
                text = aiSummary,
                color = Color(0xFFCCCCCC),
                fontSize = 14.sp,
                fontFamily = ralewayFontFamily,
                lineHeight = 22.sp
            )
        }

        ProfileCard(title = "Stats", fontFamily = ralewayFontFamily) {
            ProfileRow("Age", userProfile.age, ralewayFontFamily)
            ProfileRow("Weight", userProfile.weight, ralewayFontFamily)
            ProfileRow("Height", userProfile.height, ralewayFontFamily)
        }

        ProfileCard(title = "Goals", fontFamily = ralewayFontFamily) {
            Text(
                text = userProfile.goals,
                color = Color(0xFFCCCCCC),
                fontSize = 14.sp,
                fontFamily = ralewayFontFamily
            )
        }

        ProfileCard(title = "Context", fontFamily = ralewayFontFamily) {
            Text(
                text = userProfile.context,
                color = Color(0xFFCCCCCC),
                fontSize = 14.sp,
                fontFamily = ralewayFontFamily
            )
        }
    }
}

@Composable
fun ProfileCard(title: String, fontFamily: FontFamily, glow: Boolean = false, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "border")
    val borderOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "borderOffset"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (glow) Modifier.border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.6f),
                            Color.White.copy(alpha = 0.1f),
                            Color.White.copy(alpha = 0.6f)
                        ),
                        start = Offset(borderOffset,0f),
                        end = Offset(borderOffset + 300f, 0f)
                    ),

                    shape = RoundedCornerShape(12.dp)
                ) else Modifier
            )
            .background(Color(0xFF1A1A1A), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color(0xFF2E7D32),
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            letterSpacing = 2.sp
        )
        content()
    }
}
@Composable
fun ProfileRow(label: String, value: String, fontFamily: FontFamily) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color(0xFF555555), fontSize = 14.sp, fontFamily = fontFamily)
        Text(value, color = Color.White, fontSize = 14.sp, fontFamily = fontFamily)
    }
}
@Composable
fun NutritionTab(userProfile: UserProfile, modifier: Modifier = Modifier) {
    val ralewayFontFamily = FontFamily(Font(R.font.raleway_regular))
    var calorieTarget by remember { mutableStateOf("Calculating...") }

    var totalCalories by remember { mutableStateOf(0) }
    var mealInput by remember { mutableStateOf("") }
    var meals by remember { mutableStateOf(listOf<Pair<String, Int>>()) }
    var isCalculating by remember { mutableStateOf(false) }

    LaunchedEffect(isCalculating) {
        if (isCalculating) {
            val repository = ChatRepository()
            val response = repository.sendOneOffMessage("""
            How many calories are in: ${mealInput}?
            Respond with ONLY a number. Example: 450
        """.trimIndent())
            val calories = response.trim().filter { it.isDigit() }.toIntOrNull() ?: 0
            meals = meals + Pair(mealInput, calories)
            totalCalories += calories
            mealInput = ""
            isCalculating = false
        }
    }

    LaunchedEffect(Unit) {
        val repository = ChatRepository()
        val response = repository.sendOneOffMessage("""
            Based on this profile, calculate ONLY the daily calorie target:
            Age: ${userProfile.age}
            Weight: ${userProfile.weight}
            Height: ${userProfile.height}
            Goals: ${userProfile.goals}
            Context: ${userProfile.context}
            
            Respond with ONLY a number. Example: 2100 -> then add calorie notation.
        """.trimIndent())
        calorieTarget = response.trim()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Nutrition",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = ralewayFontFamily
        )

        ProfileCard(title = "Daily Calorie Target", fontFamily = ralewayFontFamily, glow = true) {
            Text(
                text = "$calorieTarget kcal",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32),
                fontFamily = ralewayFontFamily
            )
            Text(
                text = "based on your goals",
                fontSize = 12.sp,
                color = Color(0xFF555555),
                fontFamily = ralewayFontFamily
            )
        }
        ProfileCard(title = "Log a Meal", fontFamily = ralewayFontFamily) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = mealInput,
                    onValueChange = { mealInput = it },
                    placeholder = { Text("e.g. 2 eggs and toast", color = Color(0xFF555555)) },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(color = Color.White, fontFamily = ralewayFontFamily),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2E7D32),
                        unfocusedBorderColor = Color(0xFF444444),
                        cursorColor = Color(0xFF2E7D32)
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFF2E7D32), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                        .clickable {
                            if (mealInput.isNotBlank() && !isCalculating) {
                                isCalculating = true
                            }
                        }
                ) {
                    Text("Add", color = Color.White, fontSize = 14.sp, fontFamily = ralewayFontFamily)
                }
            }
        }

        ProfileCard(title = "Daily Summary", fontFamily = ralewayFontFamily) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total Calories", color = Color(0xFF555555), fontSize = 14.sp, fontFamily = ralewayFontFamily)
                Text("$totalCalories kcal", color = Color.White, fontSize = 14.sp, fontFamily = ralewayFontFamily, fontWeight = FontWeight.Bold)
            }
            if (meals.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                meals.forEach { (meal, calories) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(meal, color = Color(0xFFCCCCCC), fontSize = 12.sp, fontFamily = ralewayFontFamily)
                        Text("$calories kcal", color = Color(0xFF98A235), fontSize = 12.sp, fontFamily = ralewayFontFamily)
                    }
                }
            }
        }
    }
}

@Composable
//consists of a checklist of ai-generated tasks  which the user can add to and also
//features a notes section.
fun DailyTab(userProfile: UserProfile, modifier: Modifier = Modifier) {
    val ralewayFontFamily = FontFamily(Font(R.font.raleway_regular))
    var tasks by remember { mutableStateOf<List<String>>(emptyList()) }
    var completedTasks by remember { mutableStateOf(setOf<Int>()) }
    var isLoading by remember { mutableStateOf(true) }
    var customTaskInput by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val repository = ChatRepository()
        val response = repository.sendOneOffMessage("""
        Generate 6 specific daily nutrition and health tasks for this exact person:
        Name: ${userProfile.name}
        Age: ${userProfile.age}
        Weight: ${userProfile.weight}
        Height: ${userProfile.height}
        Goals: ${userProfile.goals}
        Context: ${userProfile.context}
        
         Make each task SPECIFIC to their goals and situation.
        If they want to lose weight, give weight loss tasks.
        If they have a tight budget, suggest cheap food options.
        If they eat dining hall food, give dining hall specific advice.
        Respond ONLY with a numbered list, one task per line. Example:
        1. Drink 8 glasses of water
        2. Eat a protein with every meal
        3. Avoid sugary drinks today
    """.trimIndent())

        tasks = response.lines()
            .filter { it.isNotBlank() }
            .map { it.replace(Regex("^\\d+\\.\\s*"), "") }
        isLoading = false
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Daily Tasks",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = ralewayFontFamily
        )

        if (isLoading) {
            Text(
                text = "Generating your tasks... 🦎",
                color = Color(0xFF98A235),
                fontSize = 14.sp,
                fontFamily = ralewayFontFamily
            )
        } else {
            tasks.forEachIndexed { index, task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1A1A1A), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                        .clickable {
                            completedTasks = if (completedTasks.contains(index)) {
                                completedTasks - index
                            } else {
                                completedTasks + index
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                if (completedTasks.contains(index)) Color(0xFF2E7D32) else Color.Black,
                                RoundedCornerShape(4.dp)
                            )
                            .border(1.dp, Color(0xFF2E7D32), RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (completedTasks.contains(index)) {
                            Text("✓", color = Color.White, fontSize = 14.sp)
                        }
                    }
                    Text(
                        text = task,
                        color = if (completedTasks.contains(index)) Color(0xFF555555) else Color.White,
                        fontSize = 14.sp,
                        fontFamily = ralewayFontFamily,
                        textDecoration = if (completedTasks.contains(index))
                            androidx.compose.ui.text.style.TextDecoration.LineThrough
                        else
                            androidx.compose.ui.text.style.TextDecoration.None
                    )
                }
            }
        }

        ProfileCard(title = "Add your Own", fontFamily = ralewayFontFamily) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = customTaskInput,
                    onValueChange = { customTaskInput = it },
                    placeholder = { Text("Add a custom task...", color = Color(0xFF555555)) },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(color = Color.White, fontFamily = ralewayFontFamily),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2E7D32),
                        unfocusedBorderColor = Color(0xFF444444),
                        cursorColor = Color(0xFF2E7D32)
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFF2E7D32), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                        .clickable {
                            if (customTaskInput.isNotBlank()) {
                                tasks = tasks + customTaskInput
                                customTaskInput = ""
                            }
                        }
                ) {
                    Text("→", color = Color.White, fontSize = 20.sp)
                }
            }
        }
        ProfileCard(title = "Notes", fontFamily = ralewayFontFamily) {
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                placeholder = { Text("Jot something down...", color = Color(0xFF555555)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                textStyle = TextStyle(
                    color = Color.White,
                    fontFamily = ralewayFontFamily
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2E7D32),
                    unfocusedBorderColor = Color(0xFF444444),
                    cursorColor = Color(0xFF2E7D32)
                )
            )
        }
    }


}

@Composable
fun ChatTab(userProfile: UserProfile, modifier: Modifier = Modifier) {
    val ralewayFontFamily = FontFamily(Font(R.font.raleway_regular))
    val repository = remember { ChatRepository() }
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Hey ${userProfile.name}! 🦎 I know your profile. Ask me anything about nutrition!", false)
        )
    }
    var userInput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.size - 1)
    }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            val response = repository.sendChatMessage(messages, userProfile)
            messages.add(ChatMessage(response, false))
            isLoading = false
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
            items(messages) { message ->
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

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                placeholder = { Text("Ask Chameleon...", color = Color(0xFF555555)) },
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(color = Color.White, fontFamily = ralewayFontFamily),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2E7D32),
                    unfocusedBorderColor = Color(0xFF444444),
                    cursorColor = Color(0xFF2E7D32)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .background(Color(0xFF2E7D32), RoundedCornerShape(8.dp))
                    .padding(12.dp)
                    .clickable() {
                        if (userInput.isNotBlank() && !isLoading) {
                            messages.add(ChatMessage(userInput, true))
                            userInput = ""
                            isLoading = true
                        }
                    }
            ) {
                Text("→", color = Color.White, fontSize = 20.sp)
            }
        }
    }
}
        
