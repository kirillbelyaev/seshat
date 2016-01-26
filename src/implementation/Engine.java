/*
Copyright (c) 2010-2016  Kirill Belyaev
 * kirillbelyaev@yahoo.com
 * kirill@cs.colostate.edu
 * Seshat-CFE-Lib - XML Content Filtering Engine Library
 * This work is licensed under the Creative Commons Attribution-NonCommercial 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/ or send 
 * a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
 */

/*
 "DION
 The violent carriage of it
 Will clear or end the business: when the oracle,
 Thus by Apollo's great divine seal'd up,
 Shall the contents discover, something rare
 Even then will rush to knowledge. Go: fresh horses!
 And gracious be the issue!"

 Winter's Tale, Act 3, Scene 1. William Shakespeare
 */

package implementation;

/*
import static interfaces.Constants.DefaultArraySize;
import static interfaces.Constants.LogicSet;
import static interfaces.Constants.OperatorSet;
*/

import static interfaces.Constants.*;
import interfaces.CEPLib;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.NodeList;

import utilities.DateUtils;

/**
 *
 * @author I829920
 */

public class Engine implements CEPLib {
	// parsing engine globals
	private int[] truth;
	private char[] plogic;
	private char[][] expg;

	// arrays for holding parsed cmd search expressions
	private char tuple[][];
	private char logic[];
	private char[][] elements;
	private char[] operators;
	private char[][] values;

	private int MatchingMessagesCount = -1;
	private String Query = null;
	private ArrayList<String> Queries = null;

	// parsing engine globals
	private boolean g_and = true;
	private boolean g_or;
	private boolean complex = false;
	private int exp_gn;

	private XMLTree tree = new XMLTree();
	private ByteArrayInputStream bais = null;

	private static ArrayList<String> replace = new ArrayList<String>();
	private static ArrayList<String> removeList = new ArrayList<String>();

	public Engine() {
		if (this.setUpEngineArrays() != 0)
			return;
		if (tree.setUpDOM() != 0)
			return;
		// System.out.println("EngineState initialized.");
	}

	private int setUpEngineArrays() {
		truth = new int[DefaultArraySize];
		plogic = new char[DefaultArraySize];
		expg = new char[DefaultArraySize][DefaultArraySize];

		tuple = new char[DefaultArraySize][DefaultArraySize];
		logic = new char[DefaultArraySize];
		elements = new char[DefaultArraySize][DefaultArraySize];
		operators = new char[DefaultArraySize];
		values = new char[DefaultArraySize][DefaultArraySize];

		Queries = new ArrayList<String>();

		return 0;
	}

	private void clearEngineArrays() {
		int i = 0;

		Arrays.fill(truth, 0);
		Arrays.fill(plogic, '\0');

		for (i = 0; i < expg.length; i++)
			Arrays.fill(expg[i], '\0');

		Arrays.fill(operators, '\0');
		Arrays.fill(logic, '\0');

		for (i = 0; i < elements.length; i++)
			Arrays.fill(elements[i], '\0');

		for (i = 0; i < values.length; i++)
			Arrays.fill(values[i], '\0');

		for (i = 0; i < tuple.length; i++)
			Arrays.fill(tuple[i], '\0');
	}

	private void resetMatch() {
		this.MatchingMessagesCount = 0;
	}

	private void incrementMatch() {
		this.MatchingMessagesCount++;
	}

	@Override
	public int addQuery(String q) {
		if (q == null || q.isEmpty())
			return -1;

		/*
		 * START : Changes for 1.5 - Variable Substitution.
		 */
		if (q.contains("$")) {
			this.replace.add(q);
		}
		/*
		 * END : Changes for 1.5 - Variable Substitution.
		 */
		else
			this.Queries.add(q);

		return 0;
	}

	@Override
	public void deleteQueries() {
		this.replace.clear();
		this.Queries.clear();
	}

	@Override
	public int initializeEngineWithQuery(String q) {
		this.resetMatch();// 02/17/13

		this.clearEngineArrays(); // 05/10/12 clear the exp globals arrays

		if (q == null || q.isEmpty())
			return -1;

		Query = q;
		Query = Query.trim();

		// case: if exp contains ()
		// fill the expg global arrays with data
		if (Query.indexOf("(") != -1 && Query.indexOf(")") != -1) {
			complex = true;
		} else {// 05/09/12 reset to false
			complex = false;
		}

		// terminate immediately if any of () parentheses are found - we now use
		// query decomposition
		// that eliminates the need for () construct
		if (Query.indexOf("(") != -1 || Query.indexOf(")") != -1)
			return -1;

		if (complex == false)
			if (this.fillEngineArrays(Query) != 0)
				return -1;

		return 0;
	}

	private int checkOperatorValidity(char o) {
		int v = -1;
		for (int i = 0; i < OperatorSet.length; i++)
			if (o == OperatorSet[i]) {
				v = 0;
				break;
			}

		return v;
	}

	private int checkLogicValidity(char o) {
		int v = -1;
		for (int i = 0; i < LogicSet.length; i++)
			if (o == LogicSet[i] || o == '\0') {
				v = 0;
				break;
			}

		return v;
	}

	private int fillEngineArrays(String q) {
		if (q == null || q.isEmpty())
			return -1;

		int l = 0;
		int ll = 1;
		int tn = 0;
		int i = 0;

		/*
		 * START : Changes for 1.1 - String Comparison. Replace all the spaces
		 * between 2 quotes with ##
		 */
		Matcher matcher = Pattern.compile("(\"[^\"]*?\")").matcher(q);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1)
					.replaceAll(" ", "##"));
		}
		matcher.appendTail(sb);

		q = sb.toString();

		StringTokenizer st = new StringTokenizer(q);
		/*
		 * END : Changes for 1.1 - String Comparison.
		 */
		while (st.hasMoreTokens())// let us get rid of any space or tab
									// character to correctly deliniate the
									// tokens
		{
			tn++;
			char[] token = st.nextToken().toCharArray();
			// System.out.println(token);
			System.arraycopy(token, 0, tuple[i], 0, token.length);
			token = null;
			i++;
		}

		i = 0;

		while (i < tn) {
			System.arraycopy(tuple[i], 0, elements[l], 0, tuple[i].length);
			i++;
			operators[l] = tuple[i][0];

			// terminate if operator is not a valid operator
			// System.out.println("operator: " + operators[l]);
			if (this.checkOperatorValidity(operators[l]) != 0)
				return -1;

			i++;

			System.arraycopy(tuple[i], 0, values[l], 0, tuple[i].length);
			i++;
			logic[ll] = tuple[i][0];

			// terminate if operator is not a valid operator
			// System.out.println("logic: " + logic[ll]);
			if (this.checkLogicValidity(logic[ll]) != 0)
				return -1;

			i++;

			l++;
			ll++;
		}
		logic[0] = logic[1];

		return 0;
	}

	@Override
	public boolean runQueries(String message) {
		/*
		 * START : Changes for 1.5 - Variable Substitution.
		 */
		if (message == null || message.isEmpty())
			return false;
		int situation;
		if (replace.size() > 0) {
			for (int i = 0; i < replace.size(); i++) {
				String temp_query = replace.get(i);

				if (temp_query.contains("|") || temp_query.contains("&"))
					situation = 1;
				else
					situation = 0;

				temp_query = replaceAndGetQuery(temp_query, message, situation);

				this.addQuery(temp_query);
				this.removeList.add(temp_query);
			}
		}
		/*
		 * END : Changes for 1.5 - Variable Substitution.
		 */

		for (int i = 0; i < this.Queries.size(); i++)
			if (this.initializeEngineWithQuery(this.Queries.get(i)) == 0) {
				if (this.runQuery(message) == true) {
					return true;
				}
			} else
				return false;

		for (int j = 0; j < removeList.size(); j++)
			this.removeQuery(removeList.get(j));

		return false;
	}

	/*
	 * START : Changes for 1.5 - Variable Substitution.
	 */
	private String replaceAndGetQuery(String temp_query, String message,
			int situation) {
		String temp[] = null;
		if (situation == 0) {
			String tag = temp_query.substring(temp_query.indexOf("$") + 1);
			temp_query = temp_query.substring(0, temp_query.indexOf("$"))
					+ substitute(tag, message);
		} else {
			temp = temp_query.split(" ");
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].contains("$"))
					temp[i] = substitute(temp[i], message);

			}

			StringBuilder s = new StringBuilder();
			for (int i = 0; i < temp.length; i++)
				s.append(temp[i] + " ");

			temp_query = s.toString();
		}

		return temp_query;

	}

	public String substitute(String tag, String message) {
		tag = tag.replace("$", "").trim();
		int startPos = message.indexOf(tag) + tag.length() + 1;
		int endPos = message.indexOf("</" + tag);

		return message.substring(startPos, endPos);

	}

	/*
	 * END : Changes for 1.5 - Variable Substitution.
	 */

	// generic preferred method to run cep query on xml message string
	@Override
	public boolean runQuery(String message) {
		if (this.Query == null || this.Query.isEmpty() || message == null
				|| message.isEmpty())
			return false;

		byte[] buffer = null;
		buffer = message.getBytes();
		bais = new ByteArrayInputStream(buffer);

		if (bais == null)
			return false;

		NodeList nl = tree.getNodeList(bais);

		String val = null;

		if (nl == null)
			return false;

		int numberOfExpressions = 0;
		int cycle_and[] = new int[DefaultArraySize];
		int cycle_or[] = new int[DefaultArraySize];

		boolean oracle_and = true;
		boolean oracle_or = false;
		boolean oracle = false;
		boolean and_found = false;

		int and_count = 0;
		int or_count = 0;
		int i = 0;

		// General case when complex = false
		// lets fill the logic arrays with zeroes initially
		Arrays.fill(cycle_and, 0);
		Arrays.fill(cycle_or, 0);

		// lets count the number of expressions
		while (this.logic[i] == '&' || this.logic[i] == '|') {
			i++;
			numberOfExpressions++;
		}

		if (numberOfExpressions == 0)
			if ((this.elements[0].length) != 0 && this.operators[0] != 0
					&& (this.values[0].length) != 0)
				numberOfExpressions = 1;

		// lets count the AND and OR
		for (i = 0; i < numberOfExpressions; i++) {
			if (this.logic[i] == '&')
				and_count++;
			if (this.logic[i] == '|')
				or_count++;
		}

		for (i = 0; i < numberOfExpressions; i++)// loop through defined
													// tags/attributes of the
													// expression
		{
			val = tree.getValueOfElementWithName(nl, this.elements[i]);

			if (val == null)
				break;

			if (processElement(val, this.elements[i], this.operators[i],
					this.values[i]) == 0) {// operate on elements
				if (this.logic[i] == '&') {
					cycle_and[i] = 1;
				}
				if (this.logic[i] == '|') {
					cycle_or[i] = 1;
				}
				if (numberOfExpressions == 1)
					oracle = true; // if there is just one exp
			}

		}// end of for loop

		// the deciding logic happens here
		for (i = 0; i < numberOfExpressions; i++) {
			if (cycle_and[i] != 1) {
				oracle_and = false;
				break;
			}
		}

		for (i = 0; i < numberOfExpressions; i++) {
			if (cycle_or[i] == 1) {
				oracle_or = true;
				break;
			}
		}

		if (oracle_and == true || oracle_or == true || oracle == true) {
			this.incrementMatch(); // increase the matching count
			return true;
		}// end of checking oracles
		return false;
	}

	// core processing function
	/*
	 * Modified : varshac START : Changes for 1.1 - String Comparison. Changes
	 * for 1.2 - Decimal number support. Changes for 1.3 - Different Data/Time
	 * support.
	 */
	int processElement(String nodeValue, char[] element, char operator,
			char[] value) {

		if (nodeValue == null || value == null)
			return -1;

		char[] nv = new char[DefaultArraySize];
		Arrays.fill(nv, '\0');
		System.arraycopy(nodeValue.toCharArray(), 0, nv, 0,
				nodeValue.toCharArray().length);

		value = replaceHash(value);

		// System.out.println(nodeValue);
		// System.out.println(String.valueOf(value).trim());

		if (isDoubleValue(nodeValue)
				&& isDoubleValue(String.valueOf(value).trim())) {
			// must be a double
			// System.out.println("Decimal\n");
			try {
				double doubleValue, doubleValueStated;
				doubleValue = Double.valueOf(nodeValue.trim());
				doubleValueStated = Double
						.valueOf(String.valueOf(value).trim());
				if (operator == '=') {
					if (doubleValue == doubleValueStated)
						return 0;
				} else if (operator == '<') {
					if (doubleValue < doubleValueStated)
						return 0;
				} else if (operator == '>') {
					if (doubleValue > doubleValueStated)
						return 0;
				} else if (operator == '!') {
					if (!(doubleValue == doubleValueStated))
						return 0;
				}
			} catch (NumberFormatException e) {
				return -1;
			}
		} else if (isDate(nodeValue) && isDate(String.valueOf(value).trim())) {

			// System.out.println("Date\n");
			// must be a date
			Date dateValue, dateValueStated;
			try {
				dateValue = convertToDate(nodeValue.trim());
				dateValueStated = convertToDate(String.valueOf(value).trim());

				if (operator == '=') {
					if (isCheckDateEqual(dateValue, dateValueStated))
						return (0);
				} else if (operator == '>') {
					if (isCheckDateGreater(dateValue, dateValueStated))
						return (0);
				} else if (operator == '<') {
					if (isCheckDateLesser(dateValue, dateValueStated))
						return (0);
				} else if (operator == '!') {
					if (!isCheckDateEqual(dateValue, dateValueStated))
						return (0);
				}
			} catch (ParseException e) {
				return -1;
			}

		} else if (isString(nodeValue)
				&& isString(String.valueOf(value).trim())) {
			// System.out.println("String\n");
			// must be a string
			if (operator == '=') {
				String s1 = new String(nv).trim();
				String s2 = new String(value).trim();
				if (s1.compareTo(s2) == 0)
					return (0);
			} else if (operator == '!') {
				String s1 = new String(nv).trim();
				String s2 = new String(value).trim();
				if (s1.compareTo(s2) != 0)
					return (0);
			} else if (operator == '%') {
				String s = new String(value);
				if (nodeValue.trim().indexOf(s.trim()) != -1)
					return (0);
			}
		} else {
			try {
				// System.out.println("Integer\n");
				// must be a number
				int numericValue, numericValueStated;
				numericValue = Integer.valueOf(nodeValue.trim());
				numericValueStated = Integer.valueOf(String.valueOf(value)
						.trim());

				if (operator == '=') {
					if (numericValue == numericValueStated)
						return 0;
				} else if (operator == '<') {
					if (numericValue < numericValueStated)
						return 0;
				} else if (operator == '>') {
					if (numericValue > numericValueStated)
						return 0;
				} else if (operator == '!') {
					if (!(numericValue == numericValueStated))
						return 0;
				}
			} catch (NumberFormatException e) {
				return -1;
			}
		}

		return -1;
	}

	/*
	 * END : Changes for 1.1 - String Comparison. Changes for 1.2 - Decimal
	 * number support. Changes for 1.3 - Different Data/Time support.
	 */
	// Diagnostic routines

	/*
	 * START : Changes for 1.1 - String Comparison. Changes for 1.2 - Decimal
	 * number support. Changes for 1.3 - Different Data/Time support.
	 */
	private char[] replaceHash(char[] value) {

		String ret = new String(value);
		ret = ret.replaceAll("##", " ");
		ret = ret.replaceAll("\"", "");
		return ret.toCharArray();
	}

	private boolean isString(String s) {
		if (s.contains(" ")) {
			return true;
		}
		boolean atleastOneAlpha = s.matches(".*[a-zA-Z]+.*");
		return atleastOneAlpha;
	}

	private boolean isDate(String str) {
		try {
			Date date = convertToDate(str);
			if (date == null)
				return false;
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	private boolean isDoubleValue(String nodeValue) {

		if (isString(nodeValue))
			return false;

		int dotCount = 0;
		for (int i = 0; i < nodeValue.length(); i++)
			if (nodeValue.charAt(i) == '.') {
				dotCount++;
			}
		if (dotCount == 1)
			return true;
		return false;
	}

	private boolean isCheckDateLesser(Date dateValue, Date dateValueStated) {
		if (dateValue == null || dateValueStated == null)
			return false;
		if (dateValue.compareTo(dateValueStated) < 0) {
			return true;
		}
		return false;
	}

	private boolean isCheckDateGreater(Date dateValue, Date dateValueStated) {
		if (dateValue == null || dateValueStated == null)
			return false;
		if (dateValue.compareTo(dateValueStated) > 0) {
			return true;
		}
		return false;
	}

	private boolean isCheckDateEqual(Date dateValue, Date dateValueStated) {
		if (dateValue == null || dateValueStated == null)
			return false;
		if (dateValue.compareTo(dateValueStated) == 0)
			return true;
		return false;

	}

	private Date convertToDate(String input) throws ParseException {
		Date date = DateUtils.parse(input);
		return date;
	}

	/*
	 * END : Changes for 1.1 - String Comparison. Changes for 1.2 - Decimal
	 * number support. Changes for 1.3 - Different Data/Time support.
	 */

	public void printTupleArray() {
		System.out.println("Printing tuple array:");
		for (int i = 0; i < this.tuple.length; i++) {
			System.out.println(tuple[i]);
		}
		System.out.println("End of Printing tuple array:");
	}

	public void printOperatorArray() {
		System.out.println("Printing operator array:");
		for (int i = 0; i < this.operators.length; i++) {
			System.out.println(operators[i]);
		}
		System.out.println("End of Printing operator array:");
	}

	public void printLogicArray() {
		System.out.println("Printing logic array:");
		for (int i = 0; i < this.logic.length; i++) {
			System.out.println(logic[i]);
		}
		System.out.println("End of Printing logic array:");
	}

	public void printElementArray() {
		System.out.println("Printing element array:");
		for (int i = 0; i < this.elements.length; i++) {
			System.out.println(this.elements[i]);
		}
		System.out.println("End of Printing element array:");
	}

	public void printValueArray() {
		System.out.println("Printing value array:");
		for (int i = 0; i < this.values.length; i++) {
			System.out.println(values[i]);
		}
		System.out.println("End of Printing value array:");
	}

	@Override
	public int getMatchingMessagesCount() {
		return this.MatchingMessagesCount;
	}

	public void removeQuery(String query) {
		Queries.remove(query);
	}
}// end of class