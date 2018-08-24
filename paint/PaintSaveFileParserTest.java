package ca.utoronto.utm.paint;
import static org.junit.Assert.*;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.utoronto.utm.paint.PaintSaveFileParser;

public class PaintSaveFileParserTest {

	private Pattern pErrorMessage = Pattern.compile("^Error in line\\s+(\\d+)\\s+");
	private void doParserTestCase(String fileName, String message, String expectedErrorMessage) throws IOException {
		BufferedReader lineInput = new BufferedReader(new FileReader(fileName));
		PaintSaveFileParser parser = new PaintSaveFileParser();
		
		boolean retVal = parser.parse(lineInput);
		String errorMessage = parser.getErrorMessage();
		// System.out.println(fileName+" "+ errorMessage);

		if (expectedErrorMessage.equals("")) {
			assertTrue(fileName + ": Returns true for basic file with no spaces", retVal);
			assertEquals(fileName + ": No error message", "", errorMessage);
		} else {
			String reportedLine = "", expectedLine = "";

			assertFalse(fileName + ": Returns false for basic incorrect file format", retVal);
			// System.out.println(errorMessage);
			Matcher m;
			m = pErrorMessage.matcher(expectedErrorMessage);
			if (m.find())
				expectedLine = m.group(1);

			m = pErrorMessage.matcher(errorMessage);
			if (m.find())
				reportedLine = m.group(1);
			// System.out.println(expectedLine+":"+reportedLine);
			assertEquals(fileName + ": Error Message", expectedLine, reportedLine);
		}
		lineInput.close();
	}

	@Test
	public void parserTest1() throws IOException {
		this.doParserTestCase("samplefiles/basic_nospace.txt", "Returns true for basic file with no spaces", "");
	}

	@Test
	public void parserTest2() throws IOException {
		this.doParserTestCase("samplefiles/basic_spaces.txt", "Returns true for basic file with no spaces", "");
	}

	@Test
	public void parserTest3() throws IOException {
		this.doParserTestCase("samplefiles/basic_multispaces.txt", "Returns true for basic file with multiple spaces",
				"");
	}

	@Test
	public void parserTest4() throws IOException {
		this.doParserTestCase("samplefiles/basic_fail.txt", 
				"Returns false for basic incorrect file format", 
				"Error in line 1 ");
	}

	@Test
	public void parserTest5() throws IOException {
		this.doParserTestCase("samplefiles/circle_single.txt", "Returns true for file with one circle", "");
	}

	@Test
	public void parserTest6() throws IOException {
		this.doParserTestCase("samplefiles/circle_multi.txt", "Returns true for file with multiple circles", "");
	}

	@Test
	public void parserTest7() throws IOException {
		this.doParserTestCase("samplefiles/circle_fail_values.txt", "Returns false for circle with incorrect values",
				"Error in line 3 ");
	}

	@Test
	public void parserTest8() throws IOException {
		this.doParserTestCase("samplefiles/circle_fail_wrongend.txt", "Returns false for circle with wrong end", "Error in line 7 ");
	}

	@Test
	public void parserTest9() throws IOException {
		this.doParserTestCase("samplefiles/circle_fail_wrongorder.txt",
				"Returns false for circle with properties in wrong order", "Error in line 3 ");
	}

	@Test
	public void parserTest10() throws IOException {
		this.doParserTestCase("samplefiles/rectangle_single.txt", "Returns true for file with one rect", "");
	}

	@Test
	public void parserTest11() throws IOException {
		this.doParserTestCase("samplefiles/rectangle_multi.txt", "Returns true for file with multiple rect", "");
	}

	@Test
	public void parserTest12() throws IOException {
		this.doParserTestCase("samplefiles/rectangle_wrongorder.txt",
				"Returns false for rect with properties in wrong order", "Error in line 5 ");
	}

	@Test
	public void parserTest13() throws IOException {
		this.doParserTestCase("samplefiles/squiggle_single.txt", "Returns true for file with one squiggle", "");
	}

	@Test
	public void parserTest14() throws IOException {
		this.doParserTestCase("samplefiles/multishapes.txt", "Returns true for file with multiple shapes.", "");
	}

	@Test
	public void parserTest15() throws IOException {
		this.doParserTestCase("samplefiles/multishapes_fail_missingend.txt",
				"Returns false for multiple shapes file with incorrect end file line", "Error in line 48 ");
	}

	@Test
	public void parserTest16() throws IOException {
		this.doParserTestCase("samplefiles/multishapes_fail_missingendshape.txt",
				"Returns false for multiple shapes file with incorrect end shape", "Error in line 13 ");
	}
	
	@Test
	public void parserTest17() throws IOException {
		this.doParserTestCase("samplefiles/rectangle_fail_wrongend.txt",
				"Returns false for rectangle with wrong end", "Error in line 7 ");
	}
	
	@Test
	public void parserTest18() throws IOException {
		this.doParserTestCase("samplefiles/squiggle_wrongend.txt",
				"Returns false for squiggle with wrong end", "Error in line 9 ");
	}
	
	@Test
	public void parserTest19() throws IOException {
		this.doParserTestCase("samplefiles/squiggle_multi.txt",
				"Returns true for file with multiple squiggles.", "");
	}
	
	@Test
	public void parserTest20() throws IOException {
		this.doParserTestCase("samplefiles/multi_fails.txt",
				"Returns false as soon as first fail happens", "Error in line 19 expected squiggle end.");
	}
	
	@Test
	public void parserTest21() throws IOException {
		this.doParserTestCase("samplefiles/circle_fill_fail.txt",
				"Returns false as no fill found", "Error in line 5 expected fill property of the shapes.");
	}
	
	@Test
	public void parserTest22() throws IOException {
		this.doParserTestCase("samplefiles/circle_center_fail.txt",
				"Returns false as center is wrong", "Error in line 5 expected circle center.");
	}
	
	@Test
	public void parserTest23() throws IOException {
		this.doParserTestCase("samplefiles/circle_fail_radius.txt",
				"Returns false as radius is wrong", "Error in line 6 expected circle radius.");
	}
	
	@Test
	public void parserTest24() throws IOException {
		this.doParserTestCase("samplefiles/rectangle_points_fail.txt",
				"Returns false as rectangle point is wrong.", "Error in line 5 expected one of the rectangle shapes point.");
	}
	
	@Test
	public void parserTest25() throws IOException {
		this.doParserTestCase("samplefiles/squiggle_no_points.txt",
				"Returns false no points.", "Error in line 5 expected points of squiggle.");
	}
	
	@Test
	public void parserTest26() throws IOException {
		this.doParserTestCase("samplefiles/squiggle_endpoints_notfound.txt",
				"Returns false no points end.", "Error in line 23 expected squiggle shape.");
	}
	
	@Test
	public void parserTest27() throws IOException {
		this.doParserTestCase("samplefiles/color_notfound.txt",
				"Returns false no shape color", "Error in line 3 expected color of shape.");
	}
}

