package com.satriopndt.jetstreetfood.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.satriopndt.jetstreetfood.R
import com.satriopndt.jetstreetfood.ui.theme.JetStreetFoodTheme
import com.satriopndt.jetstreetfood.ui.theme.Shapes

@Composable
fun CartItem(
    foodId: Long,
    photoUrl: Int,
    title: String,
    totalPrice: Int,
    count: Int,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(photoUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(
                    R.string.required_price,
                    totalPrice),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )
        }
        ProductCounter(
            orderId = foodId,
            orderCount = count,
            onProductIncreased ={onProductCountChanged(foodId, count + 1)},
            onProductDescreased = {onProductCountChanged(foodId, count - 1)},
            modifier =Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview(){
    JetStreetFoodTheme {
        CartItem(
            foodId = 4,
            photoUrl = R.drawable.mango_sticky,
            title = "Mango Sticky Rice",
            totalPrice = 17000,
            count = 0,
            onProductCountChanged = { _, _ ->  },
        )
    }
}