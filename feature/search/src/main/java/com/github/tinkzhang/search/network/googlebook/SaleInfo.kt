package github.tinkzhang.readkeeper.search.model.googlebook

import com.github.tinkzhang.search.network.googlebook.ListPrice
import com.github.tinkzhang.search.network.googlebook.RetailPriceX

data class SaleInfo(
    val buyLink: String,
    val country: String,
    val isEbook: Boolean,
    val listPrice: ListPrice,
    val offers: List<Offer>,
    val retailPrice: RetailPriceX,
    val saleability: String
)
