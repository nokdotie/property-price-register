package ie.nok.ppr.common

import java.time.LocalDate

enum County {
  case CARLOW, CAVAN, CLARE, CORK, DONEGAL, DUBLIN, GALWAY, KERRY, KILDARE,
    KILKENNY, LAOIS, LEITRIM, LIMERICK, LONGFORD, LOUTH, MAYO, MEATH, MONAGHAN,
    OFFALY, ROSCOMMON, SLIGO, TIPPERARY, WATERFORD, WESTMEATH, WEXFORD, WICKLOW
}

case class Address(
    raw: String,
    line1: String,
    line2: Option[String],
    line3: Option[String],
    town: String,
    county: County,
    eirCode: Option[String]
)

enum PropertyType {
  case New, SecondHand, Unknown
}

case class PropertyPriceRegisterRecord(
    soldAt: LocalDate,
    address: Address,
    priceInEuro: Double,
    fullMarketPrice: Boolean,
    vatExclusive: Boolean,
    propertyType: PropertyType,
    sizeDescription: Option[String]
)
