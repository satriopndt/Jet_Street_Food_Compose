package com.satriopndt.jetstreetfood.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.satriopndt.jetstreetfood.di.Injection
import com.satriopndt.jetstreetfood.ViewModelFactory
import com.satriopndt.jetstreetfood.model.OrderFood
import com.satriopndt.jetstreetfood.ui.component.FoodItem
import com.satriopndt.jetstreetfood.ui.component.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        ),
    ),
    navigateToDetail: (Long) -> Unit,
) {

    val groupFoods by viewModel.groupFoodies.collectAsState()
    val query by viewModel.query

    Column(
        modifier = modifier
    ) {
        SearchBar(
            query = query,
            onQueryChange = viewModel::search
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            groupFoods.forEach { _, food ->
                items(food) {
                    FoodItem(
                        photoUrl = it.photoUrl,
                        name = it.name,
                        price = it.price,
                        modifier.clickable { navigateToDetail(it.id) })
                }
            }
        }
    }

}

@Composable
fun HomeContent(
    orderFood: List<OrderFood>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(orderFood) { data ->
            FoodItem(photoUrl = data.streetFood.photoUrl,
                name = data.streetFood.name,
                price = data.streetFood.price,
                modifier = Modifier.clickable {
                    navigateToDetail(data.streetFood.id)
                })
        }
    }
}