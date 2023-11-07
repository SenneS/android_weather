package be.senne.meerweer.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.senne.meerweer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResult() {
    Card(
        onClick = {

        },
        modifier = Modifier.padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(
                IntrinsicSize.Min)) {
                val icon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.be))
                Icon(
                    painter = icon,
                    contentDescription = "Flag",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(48.dp)
                )
                Text(
                    text = "Belgium, Leuven",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {

                    },
                ){
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Location")
                }
            }
            Text(
                text = "Flanders (99.99° E 99.99°N)",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
fun SearchResultExpanded() {

}

@Preview
@Composable
fun SearchResultPreview() {
    SearchResult()
}