package com.example.nutritionapp2ndedition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutritionapp2ndedition.ui.theme.NutritionApp2ndEditionTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutritionApp2ndEditionTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit = {}
) {
    // FONT UPLOAD FROM LOCAL COMPUTER
    val ralewayFontFamily = FontFamily(
        androidx.compose.ui.text.font.Font(R.font.raleway_regular)
    )
    // THE FOLLOWING LINES DICTATE THE ANIMATION OF THE GRADIENT
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")
    val gradientOffset by infiniteTransition.animateFloat(
        initialValue = -250f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )
    //THE FOLLOWING LINES CREATE A LOADING BAR & FADE OUT EFFECT
    var loadingProgress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = loadingProgress,
        animationSpec = tween(3000, easing = LinearEasing),
        label = "loading",
        finishedListener = { if (it == 1f) onFinished() }
    )

    //THIS FADES OUT THE HOME SCREEN
    var alpha by remember { mutableStateOf(1f)}
    val animatedAlpha by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(800),
        label = "alpha"
    )

    LaunchedEffect(Unit) {
        loadingProgress = 1f
        kotlinx.coroutines.delay(3200)
        alpha = 0f
        delay(900)
        onFinished()
    }

    Column( // THIS IS THE START OF THE MAIN SCREEN GRAPHIC.
        modifier = modifier
            .background(Color.Black)
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxSize()
            .graphicsLayer(alpha = animatedAlpha),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Text(
                text = "chameleon",
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xE2054106),
                fontFamily = ralewayFontFamily,
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
                    ),
                    shadow = Shadow(
                        color = Color(0xFF2E7D32),
                        blurRadius = 18f,
                        offset = Offset(0f,4f)
                    )
                )
            )
            Text( //CHAMELEON 'LOGO'
                text = "\uD83E\uDD8E",
                fontSize = 36.sp,
                modifier = Modifier.padding(start = 8.dp),
                color = Color(0xFF2E7D32)
            )


        }

        Text(
            text = "health guidance that adapts to you, for you.",
            fontSize = 16.sp,
            color = Color(0xFF98A235),
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp),
            fontFamily = ralewayFontFamily,
            letterSpacing = 3.7.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color(0xFF1A1A1A), RoundedCornerShape(2.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .height(4.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF2E7D32),
                                Color(0xFF98A235)
                            )
                        ),
                        RoundedCornerShape(2.dp)
                    )

            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NutritionApp2ndEditionTheme {
        HomeScreen()
    }
}
