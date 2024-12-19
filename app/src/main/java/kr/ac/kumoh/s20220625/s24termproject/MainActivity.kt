package kr.ac.kumoh.s20220625.s24termproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
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
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp)
        ) {
            AsyncImage(
                model = author.imageurl,
                contentDescription = "작가 이미지 ${author.name}",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(percent = 10)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                TextName(author.name)
                TextBirth(author.birth)
            }
        }
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