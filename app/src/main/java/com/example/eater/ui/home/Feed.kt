@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.eater.ui.home

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eater.model.Filter
import com.example.eater.model.SnackCollection
import com.example.eater.model.SnackRepo
import com.example.eater.ui.components.EaterDivider
import com.example.eater.ui.components.FilterBar
import com.example.eater.ui.components.EaterSurface
import com.example.eater.ui.components.SnackCollection
import com.example.eater.ui.theme.EaterTheme

@Composable
fun Feed(
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackCollections = remember { SnackRepo.getSnacks() }
    val filters = remember { SnackRepo.getFilters() }
    Feed(
        snackCollections,
        filters,
        onSnackClick,
        modifier
    )
}

@Composable
private fun Feed(
    snackCollections: List<SnackCollection>,
    filters: List<Filter>,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    EaterSurface(modifier = modifier.fillMaxSize()) {
        var filtersVisible by remember {
            mutableStateOf(false)
        }
        SharedTransitionLayout {
            Box {
                SnackCollectionList(
                    snackCollections,
                    onSnackClick = onSnackClick
                )
                DestinationBar()
                AnimatedVisibility(filtersVisible, enter = fadeIn(), exit = fadeOut()) {
                    FilterScreen(
                        animatedVisibilityScope = this@AnimatedVisibility,
                        sharedTransitionScope = this@SharedTransitionLayout
                    ) { filtersVisible = false }
                }
            }
        }
    }
}

@Composable
private fun SnackCollectionList(
    snackCollections: List<SnackCollection>,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            Spacer(
                Modifier.windowInsetsTopHeight(
                    WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                )
            )
        }
        itemsIndexed(snackCollections) { index, snackCollection ->
            if (index > 0) {
                EaterDivider(thickness = 2.dp)
            }

            SnackCollection(
                snackCollection = snackCollection,
                onSnackClick = onSnackClick,
                index = index
            )
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun HomePreview() {
    EaterTheme {
        Feed(onSnackClick = { _, _ -> })
    }
}
