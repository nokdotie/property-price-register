package ie.nok.ppr.common

case class PropertyPriceRegisterRaw(
    dateOfSale: String,
    address: String,
    county: String,
    eirCode: Option[String],
    priceInEuro: Double,
    notFullMarketPrice: Boolean,
    vatExclusive: Boolean,
    description: String,
    sizeDescription: Option[String]
)
