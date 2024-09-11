@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.eater.ui.home

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.eater.LocalNavAnimatedVisibilityScope
import com.example.eater.LocalSharedTransitionScope
import com.example.eater.R
import com.example.eater.ui.components.EaterDivider
import com.example.eater.ui.components.eaterPreviewWrapper
import com.example.eater.ui.snackdetail.spatialExpressiveSpring
import com.example.eater.ui.theme.AlphaNearOpaque
import com.example.eater.ui.theme.EaterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationBar(modifier: Modifier = Modifier) {
    val sharedElementScope =
        LocalSharedTransitionScope.current ?: throw IllegalStateException("No shared element scope")
    val navAnimatedScope =
        LocalNavAnimatedVisibilityScope.current ?: throw IllegalStateException("No nav scope")
    with(sharedElementScope) {
        with(navAnimatedScope) {
            Column(
                modifier = modifier
                    .renderInSharedTransitionScopeOverlay()
                    .animateEnterExit(
                        enter = slideInVertically(spatialExpressiveSpring()) { -it * 2 },
                        exit = slideOutVertically(spatialExpressiveSpring()) { -it * 2 }
                    )
            ) {
                TopAppBar(
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    title = {
                        Row {
                            Text(
                                text = "Delivery to 1600 Amphitheater Way",
                                style = MaterialTheme.typography.titleMedium,
                                color = EaterTheme.colors.textSecondary,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = EaterTheme.colors.uiBackground
                            .copy(alpha = AlphaNearOpaque),
                        titleContentColor = EaterTheme.colors.textSecondary
                    ),
                )
                EaterDivider()
            }
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun PreviewDestinationBar() {
    eaterPreviewWrapper {
        DestinationBar()
    }
}
