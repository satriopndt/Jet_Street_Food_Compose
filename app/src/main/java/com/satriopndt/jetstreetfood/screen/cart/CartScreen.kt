package com.satriopndt.jetstreetfood.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satriopndt.jetstreetfood.di.Injection
import com.satriopndt.jetstreetfood.R
import com.satriopndt.jetstreetfood.ViewModelFactory
import com.satriopndt.jetstreetfood.ui.common.UiState
import com.satriopndt.jetstreetfood.ui.component.CartItem
import com.satriopndt.jetstreetfood.ui.component.OrderButton

@Composable
fun CartScreen(
    viewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClick: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrder()
            }

            is UiState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { foodId, count ->
                        viewModel.updateOrder(foodId, count)
                    },
                    onOrderButtonClick = onOrderButtonClick
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sharedMessage = stringResource(
        R.string.share_message,
        state.orderFood.count(),
        state.totalPrice
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.cart_screen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }

        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            if (state.orderFood.isNotEmpty()) {
                items(state.orderFood, key = { it.streetFood.id }) { item ->
                    CartItem(
                        foodId = item.streetFood.id,
                        photoUrl = item.streetFood.photoUrl,
                        title = item.streetFood.name,
                        totalPrice = item.streetFood.price * item.count,
                        count = item.count,
                        onProductCountChanged = onProductCountChanged,
                    )
                    Divider()
                }
            } else {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.cart_not_found),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

        }
        OrderButton(
            text = stringResource(R.string.required_price, state.totalPrice),
            enabled = state.orderFood.isNotEmpty(),
            onClick = {
                onOrderButtonClick(sharedMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
