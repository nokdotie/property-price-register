package ie.nok.ppr.common

import munit.FunSuite

import java.io.File
import scala.io.{BufferedSource, Source}

class PPRParserTest extends FunSuite {

  test("Parse PPR from February 2023") {
    val path = getClass.getResource("/PPR-2023-02.csv")
    val file: File = File(path.getPath)
    val records = PPRParser.recordsFromCsv(file)
    assertResult(records, 3838)
  }

  private def assertResult(records: List[PropertyPriceRegisterRecord], size: Int): Unit = {
    assert(records.nonEmpty)
    assertEquals(records.size, size)
    assert(!records.exists(_.address.town.contains(", COUNTY")))
    assert(!records.exists(_.address.town.contains(", CO. ")))
    assert(!records.exists(_.address.town.contains(", CO ")))
//    assert(!records.exists(r => r.address.town == r.address.county.toString.toUpperCase()))

    records.foreach(println)
    println()
    println("UNKNOWN:")
    records.filter(_.address.town == "UNKNOWN").foreach(println)

    assert(!records.exists(r => r.address.town == "UNKNOWN"))
    assert(records.exists(_.address.line2.nonEmpty))
    assert(records.exists(_.address.line3.nonEmpty))
  }
}
