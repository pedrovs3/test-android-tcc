package br.com.pedrovieira.doetempo.components.new_post

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.core.net.toUri
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.datastore.models.enums.ButtonState
import br.com.pedrovieira.doetempo.models.CreatePostBody
import br.com.pedrovieira.doetempo.models.responses.PostResponse
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewPost(context: Context) {
    var selectedImage by remember {
        mutableStateOf("")
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) {
        selectedImage = it.toString()
    }
    val painter = rememberImagePainter(
        if (selectedImage.isEmpty())
           R.drawable.baseline_preview_24
        else
            selectedImage
    )
    var imageToSend by remember {
        mutableStateOf("")
    }

    val storageRef = FirebaseStorage.getInstance().reference.child("images/${selectedImage.toUri().lastPathSegment}")
    val uploadTask = storageRef.putFile(selectedImage.toUri())
    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let {
                throw it
            }
        }
        storageRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            imageToSend = task.result.toString()
        } else {
            // Handle failures
            // ...
        }
    }

    Log.i("teste", imageToSend)

    var content by remember {
        mutableStateOf("")
    }
    var typeUser by remember {
        mutableStateOf("")
    }
    var token by remember {
        mutableStateOf("")
    }
    var buttonState by remember {
        mutableStateOf(ButtonState.IDLE)
    }

    val dataStore = DataStoreAppData(context)

    typeUser = dataStore.getType.collectAsState(initial = "").value.toString()
    token = dataStore.getToken.collectAsState(initial = "").value.toString()

    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(MaterialTheme.colors.onPrimary)
        .padding(vertical = 10.dp, horizontal = 10.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.fillMaxWidth(0.8f)) {
                OutlinedTextField(
                    value = content,
                    onValueChange = {content = it},
                    Modifier.fillMaxWidth(),
                    label = { Text(text = "O que gostaria de compartilhar?") },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.primary,
                        unfocusedBorderColor = MaterialTheme.colors.primary,
                        textColor = MaterialTheme.colors.primary,
                    )
                )
                Button(onClick = { launcher.launch("image/*") }, shape = RoundedCornerShape(12.dp)) {
                    Icon(painter = painterResource(id = R.drawable.baseline_add_photo_alternate_24), contentDescription = "adicionar foto.")
                }
                if (selectedImage.isNotEmpty()) {
                    Box(modifier = Modifier.clip(RoundedCornerShape(12.dp))) {
                        Image(painter = painter, contentDescription = "Preview")
                    }
                }
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = {
                Log.i("teste", "aquii")
                buttonState = ButtonState.LOADING
                val postBody = CreatePostBody(
                    typeUser = typeUser,
                    content = content,
                    photos = listOf(imageToSend)
                )

                Log.i("body", postBody.toString())

                 val createPublicationCall =
                     RetrofitApiDoeTempo
                         .retrofitPublicationServices()
                         .createPost("Bearer $token", postBody)

                createPublicationCall.enqueue(object : Callback<PostResponse> {
                    override fun onResponse(
                        call: Call<PostResponse>,
                        response: Response<PostResponse>
                    ) {
                        Log.i("teste", response.message().toString())
                        Log.i("teste", response.code().toString())
                        Log.i("teste", response.body().toString())
                        if (response.isSuccessful) {
                            content = ""
                            buttonState = ButtonState.IDLE
                            Toast.makeText(context, "Post criado com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            },
                Modifier
                    .padding()
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "enviar post.")
            }
        }
    }
}