package fr.lebouedec.moodtrack.ui.component.slider

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderPositions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Slider as MaterialSlider
import androidx.compose.material3.SliderDefaults as MaterialSliderDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChangeFinished: (() -> Unit)? = null,
    colors: SliderColorsDefaults = SliderDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    thumb: @Composable (SliderPositions) -> Unit = {
        SliderDefaults.Thumb(
            interactionSource = interactionSource,
            colors = colors,
            enabled = enabled
        )
    },
    track: @Composable (SliderPositions) -> Unit = { sliderPositions ->
        SliderDefaults.Track(
            colors = colors,
            enabled = enabled,
            sliderPositions = sliderPositions
        )
    },
    steps: Int = 0,
) = MaterialSlider(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    valueRange = valueRange,
    enabled = enabled,
    onValueChangeFinished = onValueChangeFinished,
    interactionSource = interactionSource,
    colors = colors.toMaterialSliderColors(),
    steps = steps,
    thumb = thumb,
    track = track
)

@Composable
fun SliderColorsDefaults.toMaterialSliderColors(): SliderColors {
    return MaterialSliderDefaults.colors(
        thumbColor = thumbColor(enabled = true).value,
        disabledThumbColor = thumbColor(enabled = false).value,
        activeTrackColor = trackColor(enabled = true, active = true).value,
        inactiveTrackColor = trackColor(enabled = true, active = false).value,
        disabledActiveTrackColor = trackColor(enabled = false, active = true).value,
        disabledInactiveTrackColor = trackColor(enabled = false, active = false).value
    )
}

@Preview
@Composable
fun SliderPreview() {
    MaterialTheme {
        // Define the number of steps for the slider
        val steps = 10

        // Use remember to hold the current value of the slider
        var sliderValue by remember { mutableStateOf(0f) }

        // Display the slider
        Slider(
            value = sliderValue,
            onValueChange = { newValue ->
                sliderValue = newValue
            },
            steps = steps,
            valueRange = 0f..steps.toFloat(),
        )
    }
}