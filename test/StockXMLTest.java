

/**
 * @author varshac
 * Junit test cases for StockkXML
 */
import static org.junit.Assert.assertEquals;
import implementation.Engine;

import org.junit.Assert;
import org.junit.Before;
//import org.junit.FixMethodOrder; //KB: comment for now

import org.junit.Test;

//import org.junit.runners.MethodSorters; //KB: comment for now



//@FixMethodOrder(MethodSorters.NAME_ASCENDING) //KB: comment for now
public class StockXMLTest {

		String XMLMessage = "<XML_MESSAGE length=\"00003217\" symbol=\"MSFT\">"
				+ "<Ask>46.76</Ask><AverageDailyVolume>33146700</AverageDailyVolume><Bid>46.75</Bid>"
						+ "<AskRealtime>46.76</AskRealtime><BidRealtime>46.75</BidRealtime><BookValue>10.897</BookValue>"
						+ "<Change_PercentChange>-0.309 - -0.66%</Change_PercentChange><Change>-0.309</Change><Commission/>"
						+ "<Currency>USD</Currency><ChangeRealtime>-0.309</ChangeRealtime>"
						+ "<AfterHoursChangeRealtime>N/A - N/A</AfterHoursChangeRealtime><DividendShare>1.12</DividendShare>"
						+ "<LastTradeDate>9/23/2014</LastTradeDate><TradeDate/><EarningsShare>2.63</EarningsShare>"
						+ "<ErrorIndicationreturnedforsymbolchangedinvalid/><EPSEstimateCurrentYear>2.72</EPSEstimateCurrentYear>"
						+ "<EPSEstimateNextYear>3.20</EPSEstimateNextYear><EPSEstimateNextQuarter>0.75</EPSEstimateNextQuarter>"
						+ "<DaysLow>46.66</DaysLow><DaysHigh>46.98</DaysHigh><YearLow>32.15</YearLow><YearHigh>47.57</YearHigh>"
						+ "<HoldingsGainPercent>- - -</HoldingsGainPercent><AnnualizedGain/><HoldingsGain/>"
						+ "<HoldingsGainPercentRealtime>N/A - N/A</HoldingsGainPercentRealtime><HoldingsGainRealtime/>"
						+ "<MoreInfo>cn</MoreInfo><OrderBookRealtime/><MarketCapitalization>385.2B</MarketCapitalization>"
						+ "<MarketCapRealtime/><EBITDA>32.131B</EBITDA><ChangeFromYearLow>+14.601</ChangeFromYearLow>"
						+ "<PercentChangeFromYearLow>+45.42%</PercentChangeFromYearLow>"
						+ "<LastTradeRealtimeWithTime>N/A - &lt;b&gt;46.751&lt;/b&gt;</LastTradeRealtimeWithTime>"
						+ "<ChangePercentRealtime>N/A - -0.66%</ChangePercentRealtime>"
						+ "<ChangeFromYearHigh>-0.819</ChangeFromYearHigh>"
						+ "<PercebtChangeFromYearHigh>-1.72%</PercebtChangeFromYearHigh>"
						+ "<LastTradeWithTime>2:03 pm</LastTradeWithTime>"
						+ "<LastTradePriceOnly>46.751</LastTradePriceOnly><HighLimit/><LowLimit/>"
						+ "<DaysRange>46.66 - 46.98</DaysRange><DaysRangeRealtime>N/A - N/A</DaysRangeRealtime>"
						+ "<FiftydayMovingAverage>45.2106</FiftydayMovingAverage>"
						+ "<TwoHundreddayMovingAverage>41.9705</TwoHundreddayMovingAverage>"
						+ "<ChangeFromTwoHundreddayMovingAverage>+4.7805</ChangeFromTwoHundreddayMovingAverage>"
						+ "<PercentChangeFromTwoHundreddayMovingAverage>+11.39%</PercentChangeFromTwoHundreddayMovingAverage>"
						+ "<ChangeFromFiftydayMovingAverage>+1.5404</ChangeFromFiftydayMovingAverage>"
						+ "<PercentChangeFromFiftydayMovingAverage>+3.41%</PercentChangeFromFiftydayMovingAverage>"
						+ "<Name>Microsoft Corpora</Name><Notes/><Open>46.93</Open><PreviousClose>47.06</PreviousClose>"
						+ "<PricePaid/><ChangeinPercent>-0.66%</ChangeinPercent><PriceSales>4.47</PriceSales>"
						+ "<PriceBook>4.32</PriceBook><ExDividendDate>Aug 19</ExDividendDate><PERatio>17.89</PERatio>"
						+ "<DividendPayDate>Dec 11</DividendPayDate><PERatioRealtime/><PEGRatio>2.31</PEGRatio>"
						+ "<PriceEPSEstimateCurrentYear>17.30</PriceEPSEstimateCurrentYear>"
						+ "<PriceEPSEstimateNextYear>14.71</PriceEPSEstimateNextYear><Symbol>MSFT</Symbol><SharesOwned/>"
						+ "<ShortRatio>2.90</ShortRatio><LastTradeTime>2:03pm</LastTradeTime>"
						+ "<TickerTrend>&amp;nbsp;=====-&amp;nbsp;</TickerTrend><OneyrTargetPrice>47.93</OneyrTargetPrice>"
						+ "<Volume>20205790</Volume><HoldingsValue/><HoldingsValueRealtime/><YearRange>32.15 - 47.57</YearRange>"
						+ "<DaysValueChange>- - -0.66%</DaysValueChange><DaysValueChangeRealtime>N/A - N/A</DaysValueChangeRealtime>"
						+ "<StockExchange>NasdaqNM</StockExchange><DividendYield>2.38</DividendYield>"
						+ "<PercentChange>-0.66%</PercentChange></XML_MESSAGE>";
		
		Engine engine;
		String XMLMessage2;
		@Before
		public void setup() {
			engine = new Engine();
		  XMLMessage2="<XML_MESSAGE length=\"00003082\" symbol=\"ALLE\"><Ask>52.72</Ask><AverageDailyVolume>622884</AverageDailyVolume><Bid/><AskRealtime>52.72</AskRealtime><BidRealtime>40.00</BidRealtime><BookValue>0.00</BookValue><Change_PercentChange>+1.27 - +2.65%</Change_PercentChange><Change>+1.27</Change><Commission/><Currency>USD</Currency><ChangeRealtime>+1.27</ChangeRealtime><AfterHoursChangeRealtime>N/A - N/A</AfterHoursChangeRealtime><DividendShare>0.24</DividendShare><LastTradeDate>10/3/2014</LastTradeDate><TradeDate/><EarningsShare>0.00</EarningsShare><ErrorIndicationreturnedforsymbolchangedinvalid/><EPSEstimateCurrentYear>2.37</EPSEstimateCurrentYear><EPSEstimateNextYear>2.89</EPSEstimateNextYear><EPSEstimateNextQuarter>0.68</EPSEstimateNextQuarter><DaysLow>48.58</DaysLow><DaysHigh>49.21</DaysHigh><YearLow>40.24</YearLow><YearHigh>58.29</YearHigh><HoldingsGainPercent>- - -</HoldingsGainPercent><AnnualizedGain/><HoldingsGain/><HoldingsGainPercentRealtime>N/A - N/A</HoldingsGainPercentRealtime><HoldingsGainRealtime/><MoreInfo>cnm</MoreInfo><OrderBookRealtime/><MarketCapitalization/><MarketCapRealtime/><EBITDA>0</EBITDA><ChangeFromYearLow>+8.90</ChangeFromYearLow><PercentChangeFromYearLow>+22.12%</PercentChangeFromYearLow><LastTradeRealtimeWithTime>N/A - &lt;b&gt;49.14&lt;/b&gt;</LastTradeRealtimeWithTime><ChangePercentRealtime>N/A - +2.65%</ChangePercentRealtime><ChangeFromYearHigh>-9.15</ChangeFromYearHigh><PercebtChangeFromYearHigh>-15.70%</PercebtChangeFromYearHigh><LastTradeWithTime>Oct  3 - &lt;b&gt;49.14&lt;/b&gt;</LastTradeWithTime><LastTradePriceOnly>49.14</LastTradePriceOnly><HighLimit/><LowLimit/><DaysRange>48.58 - 49.21</DaysRange><DaysRangeRealtime>N/A - N/A</DaysRangeRealtime><FiftydayMovingAverage>50.848</FiftydayMovingAverage><TwoHundreddayMovingAverage>52.4345</TwoHundreddayMovingAverage><ChangeFromTwoHundreddayMovingAverage>-3.2945</ChangeFromTwoHundreddayMovingAverage><PercentChangeFromTwoHundreddayMovingAverage>-6.28%</PercentChangeFromTwoHundreddayMovingAverage><ChangeFromFiftydayMovingAverage>-1.708</ChangeFromFiftydayMovingAverage><PercentChangeFromFiftydayMovingAverage>-3.36%</PercentChangeFromFiftydayMovingAverage><Name>Allegion plc Ordi</Name><Notes/><Open>48.77</Open><PreviousClose>47.87</PreviousClose><PricePaid/><ChangeinPercent>+2.65%</ChangeinPercent><PriceSales/><PriceBook/><ExDividendDate>Sep 12</ExDividendDate><PERatio/><DividendPayDate>Sep 30</DividendPayDate><PERatioRealtime/><PEGRatio>1.24</PEGRatio><PriceEPSEstimateCurrentYear>20.20</PriceEPSEstimateCurrentYear><PriceEPSEstimateNextYear>16.56</PriceEPSEstimateNextYear><Symbol>ALLE</Symbol><SharesOwned/><ShortRatio/><LastTradeTime>4:07pm</LastTradeTime><TickerTrend>&amp;nbsp;=+-=+=&amp;nbsp;</TickerTrend><OneyrTargetPrice>59.50</OneyrTargetPrice><Volume>968569</Volume><HoldingsValue/><HoldingsValueRealtime/><YearRange>40.24 - 58.29</YearRange><DaysValueChange>- - +2.65%</DaysValueChange><DaysValueChangeRealtime>N/A - N/A</DaysValueChangeRealtime><StockExchange>NYSE</StockExchange><DividendYield>0.50</DividendYield><PercentChange>+2.65%</PercentChange></XML_MESSAGE>";
		}

		
		@Test
		public void a_atomicIntegerTest() {
			String Query1 = "AverageDailyVolume = 33146700";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}
		
		@Test
		public void b_atomicDecimalTest() {
			String Query1 = "DaysHigh = 46.98";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}

		@Test
		public void c_atomicDateTest() {
			String Query1 = "LastTradeDate = 9/23/2014";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}

		@Test
		public void d_atomicStringTest() {
			String Query1 = "StockExchange = \"NasdaqNM\"";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}

		@Test
		public void e_atomicStringWithSpacesTest() {
			String Query1 = "Change_PercentChange = \"-0.309 - -0.66%\"";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}

		@Test
		public void f_compositeIntegerWithDecimalATest() {
			String Query1 = "AverageDailyVolume = 33146700 & DaysHigh = 46.98";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}

		@Test
		public void g_compositeIntegerWithDecimalOTest() {
			String Query1 = "AverageDailyVolume = 33146700 | ChangeFromTwoHundreddayMovingAverage = +4.7805";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}

		@Test
		public void h_compositeDecimalWithDateTest() {
			String Query1 = "DaysHigh = 46.98 & LastTradeWithTime = \"2:03 pm\"";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}

		@Test
		public void i_compositeDatewithStringTest() {
			String Query1 = "LastTradeWithTime = \"2:03 pm\" | StockExchange = \"NasdaqNM\"";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}
		
		@Test
		public void j_compositeStringWithStringTest(){
			String Query1 = "Change_PercentChange = \"-0.309 - -0.66%\" & StockExchange = \"NasdaqNM\"";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}
		

		@Test
		public void k_compositeTest(){
			String Query1 = "AverageDailyVolume = 33146700 "
					+ "& ChangeFromTwoHundreddayMovingAverage = +4.7805 "
					+ "& Change_PercentChange = \"-0.309 - -0.66%\" "
					+ "& StockExchange = \"NasdaqNM\" "
					+ "& LastTradeDate = 9/23/2014";
			//Engine engine = new Engine();

			engine.addQuery(Query1);
			boolean result = engine.runQueries(XMLMessage);
			assertEquals(true,result);
			engine.deleteQueries();
		}
		
		@Test
	    public void substitutionTest1()
	    {
	    String q="Ask = $AskRealtime";
	    engine.addQuery(q);
	    boolean success=engine.runQueries(XMLMessage2);
         engine.deleteQueries();
         assertEquals(true, success);
	    }

	    @Test
	    public void substitutionTest2()
	    {
	    	String q="Ask = $AskRealtime & Currency = USD";
	        engine.addQuery(q);
	        boolean success=engine.runQueries(XMLMessage2);
	        engine.deleteQueries();	        
	        assertEquals(true, success);
	    }

	   @Test
	    public void substitutionTest3()
	    {
	    	String q="Ask = $Bid | Currency = USD";
	        engine.addQuery(q);
	        boolean success=engine.runQueries(XMLMessage2);
	        engine.deleteQueries();
	        
	        assertEquals(true, success);
	    }

	    @Test
	    public void substitutionTest4()
	    {
	    	String q="Ask = $AskRealtime & AverageDailyVolume > 316700 & Currency = INR";
	        engine.addQuery(q);
	        boolean success=engine.runQueries(XMLMessage2);
	        //System.out.println(success);
	        engine.deleteQueries();
	       Assert.assertFalse(success);
	    }

		
}
