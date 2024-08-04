package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberToWordsConverterTest {

    @Test
    void basicTest() {
            NumberToWordsConverter converter = new NumberToWordsConverter();
            assertEquals(converter.convert(250), "doua sute cincizeci");
            assertEquals(converter.convert(18), "optsprezece");
            assertEquals(converter.convert(320), "trei sute douazeci");
            assertEquals(converter.convert(4500), "patru mii cinci sute");
            assertEquals(converter.convert(11243), "unsprezece mii doua sute patruzeci si trei");
            assertEquals(converter.convert(10000), "zece mii");
            assertEquals(converter.convert(22222), "douazeci si doua de mii doua sute douazeci si doi");
    }
}