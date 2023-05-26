package br.com.pedrovieira.doetempo.helpers
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts

@Component
fun pickImage(onPick: (uri: Uri?) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onPick,
    )
    launcher.launch("image/*")
}

