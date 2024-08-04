package commons;

public class NumberToWordsConverter {
    public NumberToWordsConverter() {
    }

    private static final String[] units = {
        "", "unu", "doi", "trei", "patru",
        "cinci", "sase", "sapte", "opt", "noua"
    };

    private static final String[] tens = {
        "", "zece", "douazeci", "treizeci", "patruzeci",
        "cincizeci", "saizeci", "șaptezeci", "optzeci", "nouazeci"
    };

    private static final String[] teens = {
        "zece", "unsprezece", "doisprezece", "treisprezece", "paisprezece",
        "cincisprezece", "saisprezece", "șaptesprezece", "optsprezece", "nouasprezece"
    };

    public String convert(final int n) {
        if (n < 0) {
            return "minus " + convert(-n);
        }

        if (n < 10) {
            return units[n];
        }

        if (n < 20) {
            return teens[n - 10];
        }

        if (n < 100) {
            return tens[n / 10] + ((n % 10 != 0) ? " si " + units[n % 10] : "");
        }

        if (n < 1000) {
            return (n / 100 == 1 ? "o suta" : (n / 100 == 2 ? "doua" : units[n / 100]) + " sute")
                + (n % 100 != 0 ? " " + convert(n % 100) : "");
        }

        if (n < 10000) {
            return (n / 1000 == 1 ? "o mie" : (n / 1000 == 2 ? "doua" : units[n / 1000]) + " mii")
                + (n % 1000 != 0 ? " " + convert(n % 1000) : "");
        }

        if (n < 100000) {
            String res = "";
            if(n / 1000 < 20) res += teens[n / 1000 - 10] + " mii";
            else res += tens[n / 10000] + ((n % 10 != 0) ? " si "
                + (n / 10000 % 10 == 2 ? "doua" : units[n / 10000 % 10]) : "") + " de mii";
            return res + (n % 1000 != 0 ? " " + convert(n % 1000) : "");
        }

        return "Numarul este prea mare";
    }


}
