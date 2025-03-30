import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBar
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBarItem

@Composable
fun HomeScreen() {
    val labels = listOf(
        "홈", "퀘스트", "인증", "랭킹", "마이"
    )
    val icons = listOf(
        R.drawable.home,
        R.drawable.quest,
        R.drawable.approval,
        R.drawable.ranking,
        R.drawable.my
    )
    val selectedIcons = listOf(
        R.drawable.selected_home,
        R.drawable.selected_quest,
        R.drawable.selected_approval,
        R.drawable.selected_ranking,
        R.drawable.selected_my
    )
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = { ILSANGNavigationBar {
            labels.forEachIndexed { index, label ->
                ILSANGNavigationBarItem(
                    selected = selectedIndex == index,
                    icon = {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            tint = Color.Unspecified,
                            contentDescription = label,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            painter = painterResource(id = selectedIcons[index]),
                            tint = Color.Unspecified,
                            contentDescription = label,
                        )
                    },
                    label = label,
                    onClick = { selectedIndex = index }
                )
            }
        }}
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}