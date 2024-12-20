package kr.ac.kumoh.s20220625.s24termproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.W
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
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
fun MainScreen() {
    val viewModel: WebtoonViewModel = viewModel()
    val webtoonList by viewModel.webtoonList.observeAsState(emptyList())
    val authorList by viewModel.authorList.observeAsState(emptyList())
    val webtoonInfoList by viewModel.webtoonInfoList.observeAsState(emptyList())

    val navController = rememberNavController()

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheet(drawerState) {
                navController.navigate(it) {
                    launchSingleTop = true
                    popUpTo(it) { inclusive = true }
                }
            }
        },
        gesturesEnabled = true,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {TopBar(drawerState)},
            bottomBar = {
                BottomNavigationBar {
                    navController.navigate(it) {
                        launchSingleTop = true
                        popUpTo(it) { inclusive = true }
                    }
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = WebtoonScreen.Webtoon.name,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(route = WebtoonScreen.Webtoon.name) {
                    WebtoonList() {
                        navController.navigate(it) {
                            launchSingleTop = true
                            popUpTo(it) { inclusive = true }
                        }
                    }
                }
                composable(
                    route = WebtoonScreen.WebtoonInfo.name + "/{id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    val id = it.arguments?.getInt("id") ?: -1
                    val webtoon = webtoonList.find { webtoon -> webtoon.id == id }
                    val author = authorList.find { author -> author.id == webtoon!!.authorid }
                    val webtoonInfo =
                        webtoonInfoList.find { webtoonInfo -> webtoonInfo.webtoonid == id }
                    if (webtoon != null && author != null && webtoonInfo != null) {
                        WebtoonInfo(webtoon, author, webtoonInfo)
                    }
                }
                composable(route = WebtoonScreen.Author.name) {
                    AuthorList()
                }
            }
        }
    }
}

@Composable
fun DrawerSheet(
    drawerState: DrawerState,
    onNavigate: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {
        NavigationDrawerItem(
            label = { Text("웹툰 리스트") },
            selected = false,
            onClick = {
                onNavigate(WebtoonScreen.Webtoon.name)
                scope.launch {
                    drawerState.close()
                }
            },
            icon = {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = "웹툰 리스트 아이콘"
                )
            }
        )
        NavigationDrawerItem(
            label = { Text("작가 리스트") },
            selected = false,
            onClick = {
                onNavigate(WebtoonScreen.Author.name)
                scope.launch {
                    drawerState.close()
                }
            },
            icon = {
                Icon(
                    Icons.Filled.Face,
                    contentDescription = "작가 리스트 아이콘"
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = { Text("웹툰 정보") },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "메뉴 아이콘"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Composable
fun BottomNavigationBar(onNavigate: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            label = {
                Text("웹툰")
            },
            icon = {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = "웹툰 리스트 아이콘"
                )
            },
            selected = false,
            onClick = {
                onNavigate(WebtoonScreen.Webtoon.name)
            }
        )
        NavigationBarItem(
            label = {
                Text("작가")
            },
            icon = {
                Icon(
                    Icons.Filled.Face,
                    contentDescription = "작가 리스트 아이콘"
                )
            },
            selected = false,
            onClick = {
                onNavigate(WebtoonScreen.Author.name)
            }
        )
    }
}