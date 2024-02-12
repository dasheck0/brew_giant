package dr.achim.brewgiant.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import dr.achim.brewgiant.R
import dr.achim.brewgiant.common.mirror

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBarWithHistory(
    searchQuery: String,
    placeholderText: String,
    historySize: Int = 3,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    var searchBarActive by rememberSaveable { mutableStateOf(false) }
    val searchHistory = remember {
        mutableStateListOf("Buzz", "Pils")
    }
    val lastSearchItems by remember {
        derivedStateOf {
            searchHistory
                .distinct()
                .takeLast(historySize)
        }
    }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = searchQuery,
        onQueryChange = onQueryChange,
        onSearch = {
            searchHistory.add(it)
            onSearch(it)
            searchBarActive = false
        },
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        active = searchBarActive,
        onActiveChange = { searchBarActive = it },
        leadingIcon = {
            if (searchBarActive) {
                IconButton(onClick = { searchBarActive = false }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            } else {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        },
        trailingIcon = {
            if (searchBarActive && searchQuery.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChange("")
                    onSearch("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close_button)
                    )
                }

            }
        },
        placeholder = { Text(placeholderText) },
    ) {
        lastSearchItems.forEach { item ->
            SearchHistoryItem(item) {
                onQueryChange(it)
                onSearch(it)
                searchBarActive = false
            }
        }
    }
}

@Composable
private fun SearchHistoryItem(text: String, onSelect: (String) -> Unit) {
    ListItem(
        modifier = Modifier.clickable {
            onSelect(text)
        },
        leadingContent = {
            Icon(imageVector = Icons.Default.History, contentDescription = null)
        },
        headlineContent = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.mirror()
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}