package br.com.pedrovieira.doetempo.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.pedrovieira.doetempo.navigation.ItemsMenu

@Composable
fun currentRoute(navController: NavHostController): String? {
    val entrada by navController.currentBackStackEntryAsState()
    return entrada?.destination?.route
}

@Composable
fun BottomBar(
    navController: NavHostController,
    menuItems: List<ItemsMenu>,
    typeUser: String,
) {
    if (typeUser == "USER") {
        BottomAppBar(backgroundColor = MaterialTheme.colors.secondaryVariant) {
            BottomNavigation(backgroundColor = MaterialTheme.colors.secondaryVariant) {
                val currentRoute = currentRoute(navController = navController)
                menuItems.forEach { item ->
                    if (isSystemInDarkTheme()) {
                        BottomNavigationItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.title)
                            },
                            unselectedContentColor = Color(0xFF8D8D99),
                            selectedContentColor = MaterialTheme.colors.primary,
                            label = { Text(text = item.title)},
                            alwaysShowLabel = false
                        )
                    } else {
                        BottomNavigationItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.title)
                            },
                            selectedContentColor = MaterialTheme.colors.background,
                            label = { Text(text = item.title)},
                            alwaysShowLabel = false
                        )
                    }
                }
            }
        }
    } else {
        BottomAppBar(
            cutoutShape = MaterialTheme.shapes.small.copy(
                CornerSize(percent = 50)
            ),
            backgroundColor = MaterialTheme.colors.secondaryVariant) {
            BottomNavigation(
                Modifier.padding(0.dp, 0.dp, 60.dp, 0.dp),
                backgroundColor = MaterialTheme.colors.secondaryVariant,
                elevation = 0.dp
            ) {
                val currentRoute = currentRoute(navController = navController)
                menuItems.forEach { item ->
                    if (isSystemInDarkTheme()) {
                        BottomNavigationItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.title)
                            },
                            unselectedContentColor = Color(0xFF8D8D99),
                            selectedContentColor = MaterialTheme.colors.primary,
                            label = { Text(text = item.title)},
                            alwaysShowLabel = false
                        )
                    } else {
                        BottomNavigationItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.title)
                            },
                            selectedContentColor = MaterialTheme.colors.background,
                            label = { Text(text = item.title)},
                            alwaysShowLabel = false
                        )
                    }
                }
            }
        }
    }
}