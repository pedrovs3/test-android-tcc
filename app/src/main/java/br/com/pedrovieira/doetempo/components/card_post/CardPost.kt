package br.com.pedrovieira.doetempo.components.card_post

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.models.NgoPostData
import br.com.pedrovieira.doetempo.models.Post
import br.com.pedrovieira.doetempo.models.UserDetails
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardPost(context: Context, post: Post, user: UserDetails?, ngo: NgoPostData?, ) {
    var image by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(true)
    }

    if (!user?.id.isNullOrEmpty()) {
        image = user?.photoURL.toString()
    } else {
        image = ngo?.photoURL.toString()
    }

    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    val zonedDateTime = ZonedDateTime.parse(post.createdAt.toString())
    val localDate = zonedDateTime.toLocalDateTime().minusHours(3)
    val formatterPattern = DateTimeFormatter.ofPattern("dd 'de' MMMM 'às' HH:mm", Locale("pt", "BR"))
    val formattedDateTime = localDate.format(formatterPattern)

    Card(onClick = { /*TODO*/ },
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(vertical = 5.dp, horizontal = 5.dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), verticalAlignment = Alignment.Top
            ) {
                Box(
                    Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                        .padding(end = 5.dp)
                        .border(
                            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(3.dp),
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = "Imagem do criador do post",
                        imageLoader = imageLoader,
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        placeholder = painterResource(id = R.drawable.logo_doe_tempo),
                        contentScale = ContentScale.Crop,
                        onError = { isLoading = false },
                        onSuccess = { isLoading = false },
                    )
                }
                if (!user?.id.isNullOrEmpty()) {
                    Column(Modifier.fillMaxSize()) {
                        Text(text = user?.name.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = formattedDateTime.toString(), fontSize = 12.sp)
                        Text(text = post.content.toString())
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                } else {
                    Column(Modifier.fillMaxSize()) {
                        Text(text = ngo?.name.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = formattedDateTime.toString(), fontSize = 12.sp)
                        Text(text = post.content.toString())
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
            LazyRow {
                post.postPhoto?.size?.let {
                    items(it) {
                        Box(modifier = Modifier.clip(RoundedCornerShape(12.dp))) {
                            AsyncImage(model = post.postPhoto[it].photoURL, contentDescription = "Foto do post", contentScale = ContentScale.Fit)
                        }
                    }
                }
            }
        }
    }
}