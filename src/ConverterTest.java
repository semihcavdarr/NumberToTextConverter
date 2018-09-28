

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {

    @Test
    void splitForWhiteSpace() {
        String amount = "9999.99 /$";
        java.lang.String retValue = Converter.splitForWhiteSpace(amount);
        assertEquals(retValue, "9999.99");

    }
    @Test
    void splitForDot() {
        String amount = "745.11";
        String retValue = Converter.splitForDot(amount);
        assertEquals(retValue,"745");
    }

    @Test
    void convertNumberToText() {
        assertEquals("nine thousands nine hundreds ninety nine dollars ninety nine cents",Converter.convertNumberToText("9999.99 /$"));
        assertEquals("Entered parameter is not in range of this method",Converter.convertNumberToText("10000 /$"));
        assertEquals("There is no contract for zero dollar !",Converter.convertNumberToText("0 /$"));
        assertEquals("Entered parameter is not in range of this method",Converter.convertNumberToText("-100 /$"));
        assertEquals("one thousand three hundreds three dollars seventeen cents",Converter.convertNumberToText("1303.17"));
    }
    @Test
    void convertTextToNumber() {
        assertEquals("9999.99",Converter.convertTextToNumber("nine thousands nine hundreds ninety nine dollars ninety nine cents"));
        assertEquals("1303.17",Converter.convertTextToNumber("one thousand three hundreds three dollars seventeen cents"));
    }

}