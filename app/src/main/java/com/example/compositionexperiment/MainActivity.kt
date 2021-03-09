package com.example.compositionexperiment

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compositionexperiment.ui.theme.CompositionExperimentTheme

var outsideStatic = 0
var centerStatic = 0
var insideStatic = 0

var outsideDynamic = 0
var centerDynamic = 0
var insideDynamic = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var color by mutableStateOf(Color.Red)
        setContent {
            CompositionExperimentTheme {
                Column {
                    Text("staticCompositionLocalOf")
                    CompositionLocalProvider(ColorComposableLocalStatic provides color) {
                        outsideStatic++
                        MyBox(color = Color.Yellow, outsideStatic, centerStatic, insideStatic) {
                            centerStatic++
                            MyBox(color = ColorComposableLocalStatic.current, outsideStatic, centerStatic, insideStatic) {
                                insideStatic++
                                MyBox(color = Color.Yellow, outsideStatic, centerStatic, insideStatic) {
                                }
                            }
                        }
                    }

                    Text("compositionLocalOf")
                    CompositionLocalProvider(ColorComposableLocalDynamic provides color) {
                        outsideDynamic++
                        MyBox(color = Color.Yellow, outsideDynamic, centerDynamic, insideDynamic) {
                            centerDynamic++
                            MyBox(color = ColorComposableLocalDynamic.current, outsideDynamic, centerDynamic, insideDynamic) {
                                insideDynamic++
                                MyBox(color = Color.Yellow, outsideDynamic, centerDynamic, insideDynamic) {
                                }
                            }
                        }
                    }

                    Button(onClick = {
                        color = if (color == Color.Green) {
                            Color.Red
                        } else {
                            Color.Green
                        }
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Click Me")
                    }
                }
            }
        }
    }
}


@Composable
fun MyBox(color: Color,
          outside: Int,
          center: Int,
          inside: Int,
          content: @Composable BoxScope.() -> Unit) {
    Column (Modifier.background(color)) {
        Greeting("Compose $outside $center $inside")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            content = content
        )
    }
}

val ColorComposableLocalStatic = staticCompositionLocalOf<Color> {
    error("No Color provided")
}

val ColorComposableLocalDynamic = compositionLocalOf<Color> {
    error("No Color provided")
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CompositionExperimentTheme {
        Greeting("Android")
    }
}