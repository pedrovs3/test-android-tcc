package br.com.pedrovieira.doetempo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.pedrovieira.doetempo.models.UserDetails
import br.com.pedrovieira.doetempo.models.responses.NgoDetailsResponse
import br.com.pedrovieira.doetempo.navigation.ItemsMenu
import br.com.pedrovieira.doetempo.navigation.ItemsMenu.Campaign
import br.com.pedrovieira.doetempo.navigation.ItemsMenu.Feed
import br.com.pedrovieira.doetempo.screens.FeedScreen
import br.com.pedrovieira.doetempo.screens.HomeCampaigns
import br.com.pedrovieira.doetempo.screens.SearchCampaignScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    campaigns: List<br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign>,
    idUser: String,
    userDetails: UserDetails?,
    ngoDetails: NgoDetailsResponse?) {
    NavHost(
        navController = navController,
        startDestination = Campaign.route
    ) {
        composable(Campaign.route) {
            HomeCampaigns(campaigns, userDetails, ngoDetails)
        }
        composable(ItemsMenu.Search.route) {
            SearchCampaignScreen()
        }
        composable(Feed.route) {
            FeedScreen()
        }
    }
}