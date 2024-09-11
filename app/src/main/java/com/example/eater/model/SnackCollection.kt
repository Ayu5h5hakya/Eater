package com.example.eater.model

import androidx.compose.runtime.Immutable
import kotlin.random.Random

@Immutable
data class SnackCollection(
    val id: Long,
    val name: String,
    val snacks: List<Snack>,
    val type: CollectionType = CollectionType.Normal
)

enum class CollectionType { Normal, Highlight }

/**
 * A fake repo
 */
object SnackRepo {
    fun getSnacks(): List<SnackCollection> = snackCollections
    fun getSnack(snackId: Long) = snacks.find { it.id == snackId }!!
    fun getRelated(@Suppress("UNUSED_PARAMETER") snackId: Long) = related
    fun getInspiredByCart() = inspiredByCart
    fun getFilters() = filters
    fun getPriceFilters() = priceFilters
    fun getCart() = cart
    fun getSortFilters() = sortFilters
    fun getCategoryFilters() = categoryFilters
    fun getSortDefault() = sortDefault
    fun getLifeStyleFilters() = lifeStyleFilters
}

/**
 * Static data
 */

private val tastyTreats = SnackCollection(
    id = 1L,
    name = "Android's picks",
    type = CollectionType.Highlight,
    snacks = snacks.subList(0, 13)
)

private val popular = SnackCollection(
    id = Random.nextLong(),
    name = "Popular on Eater",
    snacks = snacks.subList(14, 19)
)

private val wfhFavs = tastyTreats.copy(
    id = Random.nextLong(),
    name = "WFH favourites"
)

private val newlyAdded = popular.copy(
    id = Random.nextLong(),
    name = "Newly Added"
)

private val exclusive = tastyTreats.copy(
    id = Random.nextLong(),
    name = "Only on Eater"
)

private val also = tastyTreats.copy(
    id = Random.nextLong(),
    name = "Customers also bought"
)

private val inspiredByCart = tastyTreats.copy(
    id = Random.nextLong(),
    name = "Inspired by your cart"
)

private val snackCollections = listOf(
    tastyTreats,
    popular,
    wfhFavs,
    newlyAdded,
    exclusive
)

private val related = listOf(
    also.copy(id = Random.nextLong()),
    popular.copy(id = Random.nextLong())
)

private val cart = listOf(
    OrderLine(snacks[4], 2),
    OrderLine(snacks[6], 3),
    OrderLine(snacks[8], 1)
)

@Immutable
data class OrderLine(
    val snack: Snack,
    val count: Int
)
