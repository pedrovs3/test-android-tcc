package br.com.pedrovieira.doetempo.navigation

import br.com.pedrovieira.doetempo.R

sealed class ItemsMenu (
    val icon: Int,
    val title: String,
    val route: String
) {
    object Campaign: ItemsMenu(R.drawable.campaign_icon, title = "Campanhas", route = "campaign")
    object Search: ItemsMenu(R.drawable.baseline_search_24, title = "Pesquisar", route = "pesquisar")
    object Feed: ItemsMenu(R.drawable.feed_icon, title = "Feed", route = "feed")
}
