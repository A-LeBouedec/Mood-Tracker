package fr.lebouedec.moodtrack.ui.component

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.lebouedec.moodtrack.R
import fr.lebouedec.moodtrack.ui.component.slider.Slider
import fr.lebouedec.moodtrack.ui.theme.MoodTrackTheme
import kotlin.system.exitProcess

@Composable
fun MainScreen(innerPadding: PaddingValues) {
    val steps = 10
    var sliderValue by remember { mutableFloatStateOf(8f) }
    val activity = LocalContext.current as Activity
    Column(
        modifier = Modifier
            .padding(bottom = innerPadding.calculateBottomPadding())
            .paint(
                painterResource(id = if (sliderValue > 5) R.drawable.feel_1 else R.drawable.feel_2),
                contentScale = ContentScale.FillHeight
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.main_screen_title),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(
                    top = 30.dp + innerPadding.calculateTopPadding(),
                    start = 40.dp,
                    end = 40.dp
                )
                .align(Alignment.CenterHorizontally)
        )

        Column {
            Slider(
                modifier = Modifier.padding(horizontal = 20.dp),
                value = sliderValue,
                onValueChange = { newValue ->
                    sliderValue = newValue
                },
                steps = steps,
                valueRange = 0f..steps.toFloat(),
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.main_screen_feel_2),
                    fontSize = 8.sp
                )
                Text(
                    text = stringResource(id = R.string.main_screen_feel_1),
                    fontSize = 8.sp
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                onClick = {
                    activity.finish()
                    exitProcess(0)
                },
            ) {
                Text(text = stringResource(id = R.string.main_screen_continue))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MoodTrackTheme {
        MainScreen(PaddingValues())
    }
}
