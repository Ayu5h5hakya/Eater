@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.eater.ui.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eater.FilterSharedElementKey
import com.example.eater.R
import com.example.eater.model.Filter
import com.example.eater.ui.theme.EaterTheme

@Composable
fun FilterBar(
    filters: List<Filter>,
    onShowFilters: () -> Unit,
    filterScreenVisible: Boolean,
    sharedTransitionScope: SharedTransitionScope
) {
    with(sharedTransitionScope) {
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 8.dp),
            modifier = Modifier.heightIn(min = 56.dp)
        ) {
            item {
                AnimatedVisibility(visible = !filterScreenVisible) {
                    IconButton(
                        onClick = onShowFilters,
                        modifier = Modifier
                            .sharedBounds(
                                rememberSharedContentState(FilterSharedElementKey),
                                animatedVisibilityScope = this@AnimatedVisibility,
                                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            tint = EaterTheme.colors.brand,
                            contentDescription = stringResource(R.string.label_filters),
                            modifier = Modifier.diagonalGradientBorder(
                                colors = EaterTheme.colors.interactiveSecondary,
                                shape = CircleShape
                            )
                        )
                    }
                }
            }
            items(filters) { filter ->
                FilterChip(filter = filter, shape = MaterialTheme.shapes.small)
            }
        }
    }
}

@Composable
fun FilterChip(
    filter: Filter,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small
) {
    val (selected, setSelected) = filter.enabled
    val backgroundColor by animateColorAsState(
        if (selected) EaterTheme.colors.brandSecondary else EaterTheme.colors.uiBackground,
        label = "background color"
    )
    val border = Modifier.fadeInDiagonalGradientBorder(
        showBorder = !selected,
        colors = EaterTheme.colors.interactiveSecondary,
        shape = shape
    )
    val textColor by animateColorAsState(
        if (selected) Color.Black else EaterTheme.colors.textSecondary,
        label = "text color"
    )

    EaterSurface (
        modifier = modifier,
        color = backgroundColor,
        contentColor = textColor,
        shape = shape,
        elevation = 2.dp
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        val pressed by interactionSource.collectIsPressedAsState()
        val backgroundPressed =
            if (pressed) {
                Modifier.offsetGradientBackground(
                    EaterTheme.colors.interactiveSecondary,
                    200f,
                    0f
                )
            } else {
                Modifier.background(Color.Transparent)
            }
        Box(
            modifier = Modifier
                .toggleable(
                    value = selected,
                    onValueChange = setSelected,
                    interactionSource = interactionSource,
                    indication = null
                )
                .then(backgroundPressed)
                .then(border),
        ) {
            Text(
                text = filter.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 6.dp
                )
            )
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun FilterDisabledPreview() {
    EaterTheme {
        FilterChip(Filter(name = "Demo", enabled = false), Modifier.padding(4.dp))
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun FilterEnabledPreview() {
    EaterTheme {
        FilterChip(Filter(name = "Demo", enabled = true))
    }
}
