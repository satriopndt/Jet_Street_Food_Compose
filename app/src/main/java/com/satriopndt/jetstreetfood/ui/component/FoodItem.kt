package com.satriopndt.jetstreetfood.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
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
import com.satriopndt.jetstreetfood.R
import com.satriopndt.jetstreetfood.ui.theme.JetStreetFoodTheme
import com.satriopndt.jetstreetfood.ui.theme.Shapes

@Composable
fun FoodItem(
    photoUrl: Int,
    name: String,
    price: Int,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        Image(painter = painterResource(photoUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = stringResource(R.string.required_price, price),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary)

    }
}

@Composable
@Preview(showBackground = true)
fun FoodItemPreview(){
    JetStreetFoodTheme {
        FoodItem(R.drawable.mango_sticky, "Mango Sticky Rice",17000)
    }
}