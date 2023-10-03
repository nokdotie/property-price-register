package ie.nok.ppr.common

import com.github.tototoshi.csv.CSVReader
import com.github.tototoshi.csv.*

import java.io.File
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object PPRParser {

  def recordsFromCsv(
                      file: File,
                      encoding: String = "Windows-1252"
                    ): List[PropertyPriceRegisterRecord] = {
    val reader = CSVReader.open(file, encoding)
    reader
      .allWithHeaders()
      .flatMap(toPropertyPriceRegisterRaw)
      .map(toPropertyPriceRegisterRecord)
  }

  private def yesNoToBoolean(input: String): Boolean = input match {
    case "Yes" => true
    case "No"  => false
    case _     => false
  }

  private def toPropertyPriceRegisterRaw(
      map: Map[String, String]
  ): Option[PropertyPriceRegisterRaw] = Option(
    PropertyPriceRegisterRaw(
      dateOfSale = map("Date of Sale (dd/mm/yyyy)"),
      address = map("Address"),
      county = map("County"),
      eirCode = {
        val str = map("Eircode")
        Option.when(str.nonEmpty)(str)
      },
      priceInEuro = map("Price (€)").replace("€", "").replace(",", "").toDouble,
      notFullMarketPrice = yesNoToBoolean(map("Not Full Market Price")),
      vatExclusive = yesNoToBoolean(map("VAT Exclusive")),
      description = map("Description of Property"),
      sizeDescription = {
        val str = map("Property Size Description")
        Option.when(str.nonEmpty)(str)
      }
    )
  )

  private def toPropertyPriceRegisterRecord(
      input: PropertyPriceRegisterRaw
  ): PropertyPriceRegisterRecord = {
    PropertyPriceRegisterRecord(
      dateOfSale = LocalDate.parse(input.dateOfSale, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
      address = toAddress(input),
      fullMarketPrice = !input.notFullMarketPrice,
      vatExclusive = input.vatExclusive,
      propertyType = toPropertyType(input.description),
      sizeDescription = input.sizeDescription
    )
  }

  private def toAddress(input: PropertyPriceRegisterRaw): Address = {
    val addressWithoutCounty1 = input.address
      .split(", COUNTY ")(0)
      .split(", CO. ")(0)
      .split(", CO ")(0)
    val addressWithoutCounty2 = County.values
      .foldLeft(addressWithoutCounty1)((acc, county) => acc.replace(s", ${county.toString.toUpperCase}", ""))

    val rawSplit = addressWithoutCounty2.split(",").map(_.trim)
    val line2 = rawSplit.lift(1)
    val line3 = rawSplit.lift(2)
    val line4 = line3.map(l => l.splitAt(l.indexOf("DUBLIN"))._2)
    Address(
      raw = input.address,
      line1 = rawSplit(0),
      line2 = line2,
      line3 = line3,
      town = line4.orElse(line3).orElse(line2).getOrElse("UNKNOWN"),
      county = County.withName(input.county),
      eirCode = input.eirCode
    )
  }

  private def toPropertyType(str: String): PropertyType = str match {
    case PropertyType.New.description => PropertyType.New
    case PropertyType.SecondHand.description => PropertyType.SecondHand
    case _ => PropertyType.Unknown
  }

}
