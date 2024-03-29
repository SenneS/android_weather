package be.senne.meerweer.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.senne.meerweer.R
import be.senne.meerweer.ui.NavigationAlignment
import be.senne.meerweer.ui.NavigationType
import be.senne.meerweer.ui.nav.NavigationDestination

@Composable
fun WeatherAppBottomNavigation(
    selectedDestination : String,
    navAction: (NavigationDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        NavigationDestination.Items().forEach {
            NavigationBarItem(
                selected = selectedDestination == it.route,
                onClick = {
                    navAction(it)
                          },
                icon = {
                    Icon(
                        imageVector = it.selectedIcon,
                        contentDescription = stringResource(it.contentDescription)
                    )
                       },
                label = {
                    Text(stringResource(it.label))
                })
        }
    }
}
@Composable
fun WeatherAppNavigationDrawerContent(
    selectedDestination : String,
    navType : NavigationType,
    navAction: (NavigationDestination) -> Unit,
    onNavDrawerClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.app_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            if(navType != NavigationType.NAV_DRAWER) {
                IconButton(onClick = {
                    onNavDrawerClick()
                }) {
                    Icon(imageVector = Icons.Default.MenuOpen, contentDescription = stringResource(R.string.nav_menu_btn_description))
                }
            }
        }

        NavigationDestination.Items().forEach {
            NavigationDrawerItem(
                label = { Text(text = stringResource(it.label), modifier=Modifier.padding(horizontal = 16.dp)) },
                selected = it.route == selectedDestination,
                onClick = { navAction(it) },
                icon = {
                    Icon(imageVector = it.selectedIcon, contentDescription = stringResource(it.contentDescription))
                },
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
            )
        }


    }
}
@Composable
fun WeatherAppNavigationRail(
    selectedDestination : String,
    navAlignment: NavigationAlignment,
    navAction: (NavigationDestination) -> Unit,
    onNavDrawerClick: () -> Unit
) {
    NavigationRail(modifier = Modifier.fillMaxHeight()) {
        NavigationRailItem(
            selected = false,
            onClick = {
                onNavDrawerClick()
                      },
            icon = { Icon(imageVector = Icons.Default.Menu, contentDescription = stringResource(R.string.nav_menu_btn_description)) }
        )

        if(navAlignment == NavigationAlignment.CENTER) {
            Spacer(Modifier.weight(0.75f))
        }

        NavigationDestination.Items().forEach {
            NavigationRailItem(
                selected = it.route == selectedDestination,
                onClick = {
                    navAction(it)
                },
                icon = {
                    Icon(imageVector = it.selectedIcon, contentDescription = stringResource(it.contentDescription))
                }
            )
        }

        if(navAlignment == NavigationAlignment.CENTER) {
            Spacer(Modifier.weight(1f))
        }
    }
}