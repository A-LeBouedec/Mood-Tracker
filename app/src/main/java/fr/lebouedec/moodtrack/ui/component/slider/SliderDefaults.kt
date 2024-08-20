package fr.lebouedec.moodtrack.ui.component.slider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderPositions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Stable
object SliderDefaults {
    @Composable
    fun colors(
        thumbColor: Color = MaterialTheme.colorScheme.primary,
        activeTrackColor: Color = MaterialTheme.colorScheme.primary,
        inactiveTrackColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        disabledThumbColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ): SliderColorsDefaults = SliderColorsDefaults(
        thumbColor = thumbColor,
        activeTrackColor = activeTrackColor,
        activeTickColor = activeTrackColor,
        inactiveTrackColor = inactiveTrackColor,
        inactiveTickColor = inactiveTrackColor,
        disabledThumbColor = disabledThumbColor,
        disabledActiveTrackColor = disabledThumbColor,
        disabledActiveTickColor = disabledThumbColor,
        disabledInactiveTrackColor = inactiveTrackColor,
        disabledInactiveTickColor = inactiveTrackColor
    )

    @Composable
    fun Thumb(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource,
        enabled: Boolean = true,
        colors: SliderColorsDefaults,
        thumbSize: DpSize = SliderSizeDefaults.ThumbSize
    ) {
        val interactions = remember { mutableStateListOf<Interaction>() }
        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> interactions.add(interaction)
                    is PressInteraction.Release -> interactions.remove(interaction.press)
                    is PressInteraction.Cancel -> interactions.remove(interaction.press)
                    is DragInteraction.Start -> interactions.add(interaction)
                    is DragInteraction.Stop -> interactions.remove(interaction.start)
                    is DragInteraction.Cancel -> interactions.remove(interaction.start)
                }
            }
        }

        Spacer(
            modifier
                .size(thumbSize)
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(
                        bounded = false,
                        radius = SliderSizeDefaults.TickRadiusRippleEffect
                    )
                )
                .hoverable(interactionSource = interactionSource)
                .background(Color.White, RoundedCornerShape(20.dp))

        )
    }

    @Composable
    fun Track(
        modifier: Modifier = Modifier,
        sliderPositions: SliderPositions,
        colors: SliderColorsDefaults,
        trackHeight: Dp = SliderSizeDefaults.TrackHeight,
        enabled: Boolean = true
    ) {
        val inactiveTrackColor = colors.trackColor(enabled, active = false)

        Canvas(
            modifier
                .fillMaxWidth()
                .height(trackHeight)
        ) {
            val isRtl = layoutDirection == LayoutDirection.Rtl
            val sliderLeft = Offset(0f, center.y)
            val sliderRight = Offset(size.width, center.y)
            val sliderStart = if (isRtl) sliderRight else sliderLeft
            val sliderEnd = if (isRtl) sliderLeft else sliderRight

            drawLine(
                inactiveTrackColor.value,
                sliderStart,
                sliderEnd,
                trackHeight.toPx(),
                StrokeCap.Round
            )

            val sliderValueEnd = Offset(
                sliderStart.x + (sliderEnd.x - sliderStart.x) * sliderPositions.activeRange.endInclusive,
                center.y
            )

            val sliderValueStart = Offset(
                sliderStart.x + (sliderEnd.x - sliderStart.x) * sliderPositions.activeRange.start,
                center.y
            )

            drawLine(
                Brush.linearGradient(
                    listOf(
                        Color.Red,
                        Color.Yellow,
                        Color.Green
                    )
                ),
                sliderValueStart,
                sliderValueEnd,
                trackHeight.toPx(),
                StrokeCap.Round
            )
        }
    }
}
