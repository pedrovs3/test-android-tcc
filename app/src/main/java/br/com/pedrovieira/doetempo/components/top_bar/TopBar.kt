package br.com.pedrovieira.doetempo.components.top_bar

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.pedrovieira.doetempo.MainActivity
import br.com.pedrovieira.doetempo.NavigationHost
import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.navigation.ItemsMenu
import br.com.pedrovieira.doetempo.screens.CampaignDetailsActivity
import br.com.pedrovieira.doetempo.screens.HomeCampaigns

@Composable
fun TopBar(title: String?) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (title != null) {
                        Row(
                            Modifier
                                .fillMaxWidth(0.95f),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = title, modifier = Modifier.padding(10.dp).verticalScroll(state = ScrollState(0)))
                        }
                    } else {
                        Row(
                            Modifier
                                .fillMaxWidth(0.95f),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "null")
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "voltar")
                    }
                },
                backgroundColor = MaterialTheme.colors.secondaryVariant,
                contentColor = Color.White,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "compartilhar")
                    }
                }
            )
        }
    ) {

    }
}