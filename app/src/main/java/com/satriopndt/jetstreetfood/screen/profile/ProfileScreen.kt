package com.satriopndt.jetstreetfood.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.satriopndt.jetstreetfood.R
import com.satriopndt.jetstreetfood.ui.theme.JetStreetFoodTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val username = stringResource(R.string.username)
    val email = stringResource(R.string.email)
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .weight(1f),
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(
                        id = R.drawable.me
                    ),
                    contentDescription = stringResource(id = R.string.profile_screen),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .rotate(360f)
                        .padding(30.dp)
                )
                Text(
                    text = stringResource(id = R.string.username),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = stringResource(id = R.string.email),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
