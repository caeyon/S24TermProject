package kr.ac.kumoh.s20220625.s24termproject

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalContext

enum class WebtoonScreen {
    Webtoon,
    WebtoonInfo,
    Author
}

@Composable
fun WebtoonList(
    viewModel: WebtoonViewModel = viewModel(),
    onNavigate: (String) -> Unit
) {
    val webtoonList by viewModel.webtoonList.observeAsState(emptyList())
    val authorList by viewModel.authorList.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp), //각 아이템 사이에 일정한 간격을 두고 배치
        contentPadding = PaddingValues(horizontal = 8.dp) //콘텐츠(각 아이템) 내부의 패딩
    ) {
        items(webtoonList) { webtoon ->
            val author = authorList.find { it.id == webtoon.authorid } //작가 이름 찾기
            WebtoonItem(webtoon, author!!.name, onNavigate)
        }
    }
}

@Composable
fun WebtoonItem(
    webtoon: Webtoon,
    author: String,
    onNavigate: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable{
                onNavigate(WebtoonScreen.WebtoonInfo.name + "/${webtoon.id}")
            },
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp)
        ) {
            AsyncImage(
                model = webtoon.thumbnailurl,
                contentDescription = "웹툰 이미지 ${webtoon.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                TextTitle(webtoon.title)
                TextAuthor(author)
            }
        }
    }
}

@Composable
fun WebtoonInfo(webtoon: Webtoon, author: Author, webtoonInfo: WebtoonInfo) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            webtoon.title,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "작가 SNS",
            )
            Text(
                webtoonInfo.favorites.toString(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            model = webtoon.thumbnailurl,
            contentDescription = "웹툰 이미지",
            modifier = Modifier
                .size(400.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = author.imageurl,
                contentDescription = "작가 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Text(author.name, fontSize = 30.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                webtoonInfo.status,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
            Text("  |  ")
            Text(
                webtoonInfo.updateschedule,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        webtoonInfo.description?.let {
            Text(
                text = it.replace("\\n","\n"),
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(webtoonInfo.webtoonurl)
            )
            context.startActivity(intent)
        }) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "작가 SNS",
            )
            Text("웹툰 보러가기")
        }
    }
}

@Composable
fun TextTitle(title: String) {
    Text(title, fontSize = 30.sp)
}

@Composable
fun TextAuthor(author: String) {
    Text(author, fontSize = 20.sp)
}