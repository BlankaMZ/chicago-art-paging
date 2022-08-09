package zuri.designs.artofchicago.screens.common

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import zuri.designs.artofchicago.R
import zuri.designs.artofchicago.model.ArtItem

@ExperimentalCoilApi
@Composable
fun ListContent(items: LazyPagingItems<ArtItem>) {
    Log.d("Error", items.loadState.toString())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = items,
            key = { artItem ->
                artItem.primaryId
            }
        ) { artItem ->
            artItem?.let { ArtItemComposable(artItem = it) }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ArtItemComposable(artItem: ArtItem) {
    val painter = rememberImagePainter(data = artItem.imageId) {
        crossfade(durationMillis = 1000)
        error(R.drawable.image_placeholder)
        placeholder(R.drawable.image_placeholder)
    }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .clickable {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(artItem.imageId)
                )
                startActivity(context, browserIntent, null)
            }
            .height(300.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "CAI Image",
            contentScale = ContentScale.Crop
        )
        Surface(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .alpha(ContentAlpha.medium),
            color = Color.Black
        ) {}
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        append(artItem.title)
                    }
                    if (artItem.artistName != null) {
                        append(" by ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append(artItem.artistName)
                        }
                    }
                },
                color = Color.White,
                fontSize = MaterialTheme.typography.caption.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
@Preview
fun ArtItemComposablePreview() {
    ArtItemComposable(
        artItem = ArtItem(
            primaryId = 0,
            id = 1,
            imageId = "",//"https://www.artic.edu/iiif/2/ec00751c-fc1f-76d9-321b-ca5f7712a6eb/full/843,/0/default.jpg",
            title = "Really Nothing",
            artistName = "Literally Nobody",
            styleTitle = "No-ness",
            placeOfOrigin = "Neverland"
        )
    )
}