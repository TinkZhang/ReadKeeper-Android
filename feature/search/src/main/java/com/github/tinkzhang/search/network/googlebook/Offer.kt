package github.tinkzhang.readkeeper.search.model.googlebook

import com.github.tinkzhang.search.network.googlebook.ListPriceX
import com.github.tinkzhang.search.network.googlebook.RetailPrice

data class Offer(
    val finskyOfferType: Int,
    val listPrice: ListPriceX,
    val retailPrice: RetailPrice
)
