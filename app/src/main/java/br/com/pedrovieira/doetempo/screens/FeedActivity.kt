package br.com.pedrovieira.doetempo.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.components.card_post.CardPost
import br.com.pedrovieira.doetempo.components.new_post.NewPost
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.models.Post
import br.com.pedrovieira.doetempo.models.responses.AllPostResponse
import br.com.pedrovieira.doetempo.screens.ui.theme.DoeTempoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FeedScreen()
                }
            }
        }
    }
}

@Composable
fun FeedScreen() {
    val context = LocalContext.current
    var posts by remember {
        mutableStateOf(listOf(Post()))
    }
    val dataStore = DataStoreAppData(context)
    val token = dataStore.getToken.collectAsState(initial = "").value.toString()
    val allPostCall = RetrofitApiDoeTempo.retrofitPublicationServices().getAll("Bearer $token")

    allPostCall.enqueue(object : Callback<AllPostResponse> {
        override fun onResponse(
            call: Call<AllPostResponse>,
            response: Response<AllPostResponse>
        ) {
            if (response.isSuccessful) {
                posts = response.body()?.allPosts!!
            } else {
                Toast.makeText(context, "Não foi possivel carregar os posts.", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<AllPostResponse>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })


    Column(Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
        NewPost(context = context)
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn{
            items(posts.size) {index ->
                if (!posts[index].postNgo.isNullOrEmpty()) {
                    posts[index].postNgo?.get(0)?.ngo?.let { CardPost(context = context, post = posts[index], user = null, ngo = it) }
                } else {
                    posts[index].postUser?.get(0)
                        ?.let { it.user?.let { it1 -> CardPost(context = context, post = posts[index], user = null , ngo = it1) } }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    DoeTempoTheme {
        FeedScreen()
    }
}