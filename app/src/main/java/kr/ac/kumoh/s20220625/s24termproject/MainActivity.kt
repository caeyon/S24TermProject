package kr.ac.kumoh.s20220625.s24termproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.ac.kumoh.s20220625.s24termproject.ui.theme.S24TermProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S24TermProjectTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: WebtoonViewModel = viewModel()) {
    val authorList by viewModel.authorList.observeAsState(emptyList())

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        AuthorResult(
            list = authorList,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AuthorResult(list: List<Author>, modifier: Modifier) {
    Column(
        modifier
    ) {
        Text(list.toString())
    }
}