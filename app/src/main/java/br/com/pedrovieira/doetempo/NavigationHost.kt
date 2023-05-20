package br.com.pedrovieira.doetempo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.pedrovieira.doetempo.navigation.ItemsMenu.Campaign
import br.com.pedrovieira.doetempo.navigation.ItemsMenu.Feed
import br.com.pedrovieira.doetempo.navigation.ItemsMenu.User
import br.com.pedrovieira.doetempo.screens.FeedActivity
import br.com.pedrovieira.doetempo.screens.HomeCampaigns
import br.com.pedrovieira.doetempo.screens.PerfilScreen

@Composable
fun NavigationHost(navController: NavHostController, campaigns: List<br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign>, idUser: String) {
    NavHost(
        navController = navController,
        startDestination = Campaign.route
    ) {
        composable(Campaign.route) {
            HomeCampaigns(campaigns)
        }
        composable(User.route) {
            PerfilScreen(idUser)
        }
        composable(Feed.route) {
            FeedActivity()
        }
    }
}