class Converter {
    private static int dollars = 0;
    private static int cents = 0;
    private static final int ZERO = 0 ;
    private static final int ONE = 1;
    private static final int TEN = 10;
    private static final int HUNDRED = 100;
    private static final int THOUSAND = 1000;
    private static final int MAX_LIMIT = 9999;
    private static StringBuilder sbNumToText;

    private static final String[] tensNames = {
            "ten",
            "twenty",
            "thirty",
            "forty",
            "fifty",
            "sixty",
            "seventy",
            "eighty",
            "ninety"
    };
    private static final String[] numNames = {
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen"
    };

    /**
     * Split with whitespace
     * @param value value of amount
     * @return dollars part of amount as String
     */
    static String splitForWhiteSpace(String value){
        String[] parts = value.split(" ");
        return parts[0];
    }

    /**
     * Getter for dollars
     * @return returns amount of dollar part
     */
    public static int getDollars() {
        return dollars;
    }

    /**
     * Setter for dollars
     * @param dollars value of dollars
     */
    public static void setDollars(int dollars) {
        Converter.dollars = dollars;
    }

    /**
     * Getter for cents
     * @return returns the cents part of amount
     */
    public static int getCents() {
        return cents;
    }

    /**
     * Setter for cents
     * @param cents value of cents
     */
    public static void setCents(int cents) {
        Converter.cents = cents;
    }

    /**
     * Takes the value and if value has dot then split it and sets the dollars and cents parts
     * if not sets the value as a dollar part
     * @param value amount of dollars
     * @return the dollar parts
     */
    static String splitForDot(String value){
        System.out.println(value);
        if(!value.contains(".")){
            setDollars(Integer.parseInt(value));
            return value;
        }
        String[] parts = value.split("\\.");
        setDollars(Integer.parseInt(parts[0]));
        setCents(Integer.parseInt(parts[1]));
        return parts[0];
    }

    /**
     * Checks for the base condition which is zero
     * @return if the error occurs returns -1 if not returns 0
     */
    private static int baseConditions(){
        if(getDollars() == ZERO){
            sbNumToText.append("There is no contract for zero dollar !");
            return -1;
        }
        return 0;
    }

    /**
     * If the amount smaller than ten dollars,
     * adds the dollar part and cent part to the stringBuilder.
     */
    private static void smallerThanTenDollars(){
        if(getDollars() < TEN){
            sbNumToText.append(getDollars() + " dollars ");
            if(getCents() > ZERO)
                sbNumToText.append(getCents() + " cents ");
        }
    }

    /**
     * If the amount is nearly thousands,
     * this function controls the amount with matematical operations and then adds the result to the stringBuilder.
     */
    private static void controlForThousands(){
        if(getDollars() / THOUSAND > ZERO){
            if(getDollars() / THOUSAND != ONE)
                sbNumToText.append(numNames[(getDollars()/THOUSAND)-1] + " thousands");
            else
                sbNumToText.append(numNames[(getDollars()/THOUSAND)-1] + " thousand");
            setDollars(getDollars() % THOUSAND);
        }
    }

    /**
     * If the amount has hundreds of dollars,
     * this method controls the amount with matematical operations and then adds the result to the stringBuilder.
     */
    private static void controlForHundreds(){
        if(getDollars() / HUNDRED > ZERO){
            if(getDollars() / HUNDRED != ONE)
                sbNumToText.append(" "+numNames[(getDollars() / HUNDRED)-1] + " hundreds");
            else
                sbNumToText.append(" "+numNames[(getDollars() / HUNDRED)-1] + "  hundred");
            setDollars(getDollars() % HUNDRED);
        }
    }

    /**
     * If the amount has tensNums (ten, twenty etc.) this method calculates the amount
     * and adds to the stringBuilder
     */
    private static void biggerThanTenDollars(){
        if(getDollars() / TEN > ZERO){
            if(getDollars() / TEN != ONE) {
                sbNumToText.append(" "+tensNames[(getDollars() / TEN) - 1]);
                setDollars(getDollars()%TEN);
            }
            if(getDollars() % TEN == ZERO)
                sbNumToText.append(" dollars");
        }
    }

    /**
     * If first digit of amount is zero then the sentence will change
     * this method controls this situation
     */
    private static void controlForFirstDigit(){
        if(getDollars() % TEN > ZERO && (getDollars() / TEN) != ONE){
            sbNumToText.append(" "+numNames[(getDollars() % TEN)-1] + " dollars ");
        }
    }

    /**
     * If the part of cents is not zero
     * then this method calculates the amount and adds the sentence to the stringBuilder
     */
    private static void controlForCents(){
        if(getCents() / TEN > ZERO){
            if(getCents() % TEN == ZERO)
                sbNumToText.append(" "+tensNames[(getCents()/TEN)-1] + " cents");
            else{
                if(getCents() / TEN > 0){
                    if(getCents() / TEN != 1) {
                        sbNumToText.append(tensNames[(getCents() / TEN) - 1]);
                        sbNumToText.append(" "+numNames[(getCents() % TEN) - 1] + " cents");
                    }
                    else
                        sbNumToText.append(numNames[getCents()-1]+ " cents");
                }
            }
        }
    }

    /**
     * This method converts the amount (like 745.00 \$) to the English sentence
     * for cheques and contracts . The aim is prevent the fraud
     * @param amount amount of dollars
     * @return the wanted sentence in English
     */
    public static String convertNumberToText(String amount){
        try {
            sbNumToText = new StringBuilder();
            String temp = splitForWhiteSpace(amount);
            splitForDot(temp);
            if(getDollars() > MAX_LIMIT || getDollars() < 0){
                throw new IllegalArgumentException();
            }
            if(baseConditions() == -1){
                return "There is no contract for zero dollar !";
            }
            smallerThanTenDollars();
            controlForThousands();
            controlForHundreds();
            biggerThanTenDollars();
            controlForFirstDigit();
            controlForCents();
            return String.valueOf(sbNumToText);
        }
        catch (IllegalArgumentException exception){
            return "Entered parameter is not in range of this method";
        }
    }

    /**
     * This method calculates amount of the total cents
     * @param tokens which is English sentence
     * @param index of starting cents part
     * @return the number of totalCents
     */
    private static int calculateCents(String [] tokens,int index){
        int totalCents = 0;
        int tempForNums;
        int tempForTens;
        for (int i = index; i <tokens.length ; i++) {
            tempForNums = searchForNums(tokens[i]);
            tempForTens = searchForTens(tokens[i]);
            if(tempForTens != -2){
                totalCents += tempForTens;
            }
            if(tempForNums != -1){
                    totalCents += tempForNums;
            }
        }
        return totalCents;
    }

    /**
     * This method converts the amount of dollars to the English sentence
     * @param text wants to convert to the number
     * @return the amount of dollars
     */
    public static String convertTextToNumber(String text){
        StringBuilder sbTextToNum = new StringBuilder();
        String [] parts = text.split(" ");
        int resultForTens;
        int resultForNums;
        int totalDollars = 0;
        int totalCents = 0 ;
        for (int i = 0; i < parts.length ; i++) {
            resultForNums = searchForNums(parts[i]);
            resultForTens = searchForTens(parts[i]);
            if( resultForTens!= -2){
                totalDollars += resultForTens;
            }
            if(resultForNums != -1) {
                if (parts[i + 1].equals("thousands") || parts[i + 1].equals("thousand")) {
                    totalDollars += resultForNums * THOUSAND;
                }
                if (parts[i + 1].equals("hundreds") || parts[i + 1].equals("hundred")) {
                    totalDollars += resultForNums * HUNDRED;
                }
                else if (parts[i+1].equals("dollars")){
                    totalDollars += resultForNums;
                    totalCents = calculateCents(parts,i+1);
                    break;
                }
            }
        }
        sbTextToNum.append(totalDollars+"." + totalCents);
        return String.valueOf(sbTextToNum);
    }

    /**
     * Searchs the word on numNames
     * @param searched searches for the parameter
     * @return if finds the searched string then returns the index of this word
     */
    private static int searchForNums(String searched){
        for (int i = 0; i < numNames.length; i++) {
            if(numNames[i].equals(searched)) {
                return i+1;
            }
        }
        return -1;
    }

    /**
     * Searchs the word on tensNames
     * @param searched searches for the parameter
     * @return if finds the searched string then returns the index of this word multiple by 10
     */
    private static int searchForTens(String searched){
        for (int i = 0; i < tensNames.length; i++) {
            if(tensNames[i].equals(searched)) {
                return (i+1)*TEN;
            }
        }
        return -2;
    }
}
