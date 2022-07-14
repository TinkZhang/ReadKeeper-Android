package app.tinks.readkeeper.uicomponent.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.cellview.InfoText
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionSection(
    description: String,
    modifier: Modifier = Modifier,
) {
    if (description.isNotEmpty()) {
        OutlinedCard(
            onClick = {}, modifier = modifier,
        ) {
            InfoText(
                text = description,
                modifier = Modifier.padding(8.dp),
                maxLine = 100
            )
        }
    }
}

@PreviewAnnotation
@Composable
private fun DescriptionSection() {
    ReadKeeperTheme {
        Surface {
            DescriptionSection(
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In eu sapien dolor. In molestie, justo ac porta malesuada, odio massa bibendum nisl, id iaculis ipsum odio nec justo. Donec ullamcorper gravida augue id tristique. Praesent id neque quis dolor lacinia luctus vitae sed urna. Praesent viverra ligula eu mauris maximus, ac pretium risus faucibus. Nulla facilisi. Pellentesque quis vestibulum purus. ",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}