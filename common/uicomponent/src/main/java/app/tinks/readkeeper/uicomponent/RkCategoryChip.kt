package app.tinks.readkeeper.uicomponent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RkCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit = {},
    onExecuteSearch: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        shape = MaterialTheme.shapes.medium,
        color = if(isSelected) MaterialTheme.colors.primary else Color.LightGray,
    ) {
        Row(modifier = Modifier.toggleable(
            value = isSelected,
            onValueChange = {
                onSelectedCategoryChanged(category)
                onExecuteSearch()
            }
        )) {
            Text(
                text = category,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

    }
}