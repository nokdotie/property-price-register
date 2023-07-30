package ie.nok.ppr.common

import ie.nok.ppr.common.County.County

import java.time.LocalDate

object County extends Enumeration {
  type County = Value
  val Carlow, Cavan, Clare, Cork, Donegal, Dublin, Galway, Kerry, Kildare, Kilkenny, Laois, Leitrim, Limerick, Longford, Louth, Mayo, Meath, Monaghan, Offaly, Roscommon, Sligo, Tipperary, Waterford, Westmeath, Wexford, Wicklow = Value
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
    fullMarketPrice: Boolean,
    vatExclusive: Boolean,
    propertyType: PropertyType,
    sizeDescription: Option[String]
)
