package br.com.pedrovieira.doetempo.navigation

import br.com.pedrovieira.doetempo.R

sealed class ItemsMenu (
    val icon: Int,
    val title: String,
    val route: String
) {
    object Campaign: ItemsMenu(R.drawable.campaign_icon, title = "Campanhas", route = "campaign")
    object User: ItemsMenu(R.drawable.person_icon, title = "Perfil", route = "user")
    object Feed: ItemsMenu(R.drawable.feed_icon, title = "Feed", route = "feed")
}
