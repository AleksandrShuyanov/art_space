package com.shuyanov.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shuyanov.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ViewSlide()
                }
            }
        }
    }
}

@Composable
fun ViewSlide(
    modifier: Modifier = Modifier
) {
    val gallery: List<ArtItem> = listOf(
        ArtItem(R.drawable.viktor_mihajlovich_vasnecov_osnovatel_skazochno_bylinnogo_zhanra_5ffbc47589d93, "Алёнушка", "Виктор Васнецов", 1881),
        ArtItem(R.drawable.sery_volk, "Иван-царевич на сером волке", "Виктор Васнецов", 1889),
        ArtItem(R.drawable.bogatiry, "Богатыри", "Виктор Васнецов", 1898),
        ArtItem(R.drawable.tsarevna,"Сказка о спящей царевне", "Виктор Васнецов", 1926),
    )

    var itemIndex by remember {
        mutableIntStateOf(0)
    }

    val activeArtItem = gallery[itemIndex]

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(12.dp, 0.dp, 12.dp, 0.dp)
                .weight(1f)
                .width(IntrinsicSize.Max)
        ) {
            // Item content

            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp
            val screenWidth = configuration.screenWidthDp.dp

            var modifierImageCol = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth()

            if (screenWidth > screenHeight) {
                modifierImageCol = modifierImageCol.fillMaxHeight(0.7f)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifierImageCol
            ) {
                // Image
                Surface(
                    shadowElevation = 15.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(25.dp)
                    ) {
                        Image(
                            painter = painterResource(id = activeArtItem.imagePath),
                            contentDescription = activeArtItem.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                        )
                    }

                }

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Text block

                Column(
                    modifier = Modifier
                        .background(colorResource(id = R.color.label_bg))
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    TextLabel(
                        activeArtItem.title,
                        fontWeight = FontWeight.Normal,
                    )
                    Row(modifier = Modifier) {
                        TextLabel(
                            activeArtItem.author,
                            fontWeight = FontWeight.Bold,
                        )
                        TextLabel(
                            "(${activeArtItem.year})",
                            modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }


            }
        }

        Column(
            modifier = modifier
                .padding(25.dp, 0.dp, 25.dp, 10.dp)
        ) {
            // Buttons block

            val horizontalArrangement = when (itemIndex) {
                0 -> Arrangement.End
                in 1..<gallery.size -> Arrangement.SpaceBetween
                else -> Arrangement.Start
            }

            Row(
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (itemIndex > 0) {
                    NavigateButton(
                        btnText = stringResource(id = R.string.btn_prev),
                        onClick = { itemIndex-- },
                        callbackParam = "prev"
                    )
                }

                if (itemIndex < gallery.size - 1) {
                    NavigateButton(
                        btnText = stringResource(id = R.string.btn_next),
                        onClick = { itemIndex++ },
                        callbackParam = "prev",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }

            }
        }
    }
}


@Composable
fun TextLabel(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal
) {
    Text(
        text = text,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        modifier = modifier
    )
}

@Composable
fun NavigateButton(
    btnText: String,
    onClick: (String) -> Unit,
    callbackParam: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick(callbackParam) },
        modifier = modifier
    ) {
        Text(text = btnText)
    }
}

@Preview(
    showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ViewSlide()
    }
}