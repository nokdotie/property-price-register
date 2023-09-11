package ie.nok.ppr.common

import ie.nok.ppr.common.County.County

import java.time.LocalDate

object County extends Enumeration {
  type County = Value
  val CARLOW, CAVAN, CLARE, CORK, DONEGAL, DUBLIN, GALWAY, KERRY, KILDARE, KILKENNY, LAOIS, LEITRIM, LIMERICK, LONGFORD, LOUTH, MAYO, MEATH, MONAGHAN, OFFALY, ROSCOMMON, SLIGO, TIPPERARY, WATERFORD, WESTMEATH, WEXFORD, WICKLOW = Value
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

sealed abstract class PropertyType(val description: String)
object PropertyType {
  case object New
      extends PropertyType(description = "New Dwelling house /Apartment")
  case object SecondHand
      extends PropertyType(description =
        "Second-Hand Dwelling house /Apartment"
      )
  case object Unknown extends PropertyType(description = "Unknown")
}

case class PropertyPriceRegisterRecord(
    dateOfSale: LocalDate,
    address: Address,
    priceInEuro: Double,
    fullMarketPrice: Boolean,
    vatExclusive: Boolean,
    propertyType: PropertyType,
    sizeDescription: Option[String]
)
