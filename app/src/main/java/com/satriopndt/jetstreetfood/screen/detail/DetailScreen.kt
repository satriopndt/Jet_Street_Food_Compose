package com.satriopndt.jetstreetfood.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.satriopndt.jetstreetfood.di.Injection
import com.satriopndt.jetstreetfood.R
import com.satriopndt.jetstreetfood.ViewModelFactory
import com.satriopndt.jetstreetfood.ui.common.UiState
import com.satriopndt.jetstreetfood.ui.component.OrderButton
import com.satriopndt.jetstreetfood.ui.component.ProductCounter
import com.satriopndt.jetstreetfood.ui.theme.JetStreetFoodTheme

@Composable
fun DetailScreen(
    foodId: Long,
    viewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFoodById(foodId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.streetFood.photoUrl,
                    data.streetFood.name,
                    data.streetFood.price,
                    data.count,
                    data.streetFood.description,
                    onBackClick = navigateBack,
                    addCart = { count ->
                        viewModel.addFoodToCart(data.streetFood, count)
                        navigateToCart()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes photoUrl: Int,
    name: String,
    basePrice: Int,
    count: Int,
    description: String,
    onBackClick: () -> Unit,
    addCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var totalPrice by rememberSaveable {
        mutableStateOf(0)
    }
    var orderCount by rememberSaveable {
        mutableStateOf(count)
    }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(photoUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp
                            )
                        )
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.btn_back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() })

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Text(
                    text = stringResource(R.string.required_price, basePrice),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.LightGray)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProductCounter(
                orderId = 1,
                orderCount = orderCount,
                onProductIncreased = { orderCount++ },
                onProductDescreased = { if (orderCount > 0) orderCount-- },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            totalPrice = basePrice * orderCount
            OrderButton(text = stringResource(
                R.string.add_to_cart,
                totalPrice
            ),
                enabled = orderCount > 0,
                onClick = { addCart(orderCount)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview(){
    JetStreetFoodTheme {
        DetailContent(
            photoUrl = R.drawable.mango_sticky,
            name = "Mango Sticky Rice",
            basePrice = 17000,
            count = 1,
            description = "mango enak",
            onBackClick = { },
            addCart = {}
        )
    }
}