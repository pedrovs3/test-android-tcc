package br.com.pedrovieira.doetempo.components

import android.util.Log
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
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
    menuItems: List<ItemsMenu>
) {
    BottomAppBar() {
        BottomNavigation() {
            val currentRoute = currentRoute(navController = navController)
            menuItems.forEach { item ->
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
                    label = { Text(text = item.title)},
                    alwaysShowLabel = false
                )
            }
        }
    }
}