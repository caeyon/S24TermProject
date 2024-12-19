package kr.ac.kumoh.s20220625.s24termproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        AuthorList(
            list = authorList,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AuthorList(list: List<Author>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp), //각 아이템 사이에 일정한 간격을 두고 배치
        contentPadding = PaddingValues(horizontal = 8.dp) //콘텐츠(각 아이템) 내부의 패딩
    ) {
        items(list) { author ->
            AuthorItem(author)
        }
    }
}

@Composable
fun AuthorItem(author: Author) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffffffcc))
            .padding(16.dp)
    ) {
        TextName(author.name)
        TextBirth(author.birth)
    }
}

@Composable
fun TextName(name: String) {
    Text(name, fontSize = 30.sp)
}

@Composable
fun TextBirth(birth: String) {
    Text(birth, fontSize = 20.sp)
}