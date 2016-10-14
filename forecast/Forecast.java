package forecast;

/*
*****************************************************************************************************************
                                 Name:            Forecast Class
                                 Version:         1.4
                                 Chapter:         7, Class 1
                                 Author:          Casey J. Murphy
                                 Date Created:    23 Feb 16
                                 Last Modified:   9 Mar 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

   Write a class encapsulating the concept of the weather forecast, assuming that it has the following attributes:
   	The temperature
   	The sky conditions (sunny, snowy, cloudy, ran)
   	Include a constructor, the accessors and mutators, and methods toString and equals.
   	Temperature in Fahrenheit should be between -50 and +150; the default value is 70, if needed.
   	The default sky condition is sunny.  Include a method that converts Fahrenheit to Celsius.
   	Also include a method that checks whether the weather attributes are consistent
   	(There are two conditions where the weather is not consistent:
   	If the temp is below 32 F and it is not snowy, and when the temp is above 100 and it is not sunny).
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
* 				1.0 - Working with correct output
* 				1.1 - Added createNew, loadRandom, and loadTestData methods from client
* 				1.2 - Added forecast group array creation functionality, removed from client
* 				1.3 - Changed convertToC to return only a double, was returning a formatted string
* 				1.4 - Added minTemp and maxTemp to handle limit thresholds
* 					  Added two additional temperature values to the loadTestData method
* 					  Added 'invalid' message suppression when loading test data
* 					  Updated documentation
* 					  Fixed indentation
* ***************************************************************************************************************
*/


/**
 * Creates a simple forecast in an array of forecasts.
 * @author Casey Murphy
 * @version 1.4
 *
 */
public class Forecast
{
    //class constants
    private static final int maxTemp = 150;
    private static final int minTemp = -50;
    private static final int maxRecords = 50;
    private static final Forecast[] fg = new Forecast[maxRecords];
    private static final String POSSIBLE_SKY_CONDITIONS [] =
            {"Sunny", "Cloudy", "Rain", "Snow"};

    // class variables
    private static int count= 0;					// counts forecasts, used for assigning IDs
    private static boolean suppressed = false;		// used to suppress printing invalid message

    //instance variables
    private int id = 1;								// IDs start at 1, maps to 0 in array
    private double tempF;
    private String skyCondition;

//----------------------------------------------------------------------------------------------------------------------
// Constructors

    /**
     * Default temperature set to 70.0 F; sky condition set to 'Sunny'.
     */
    public Forecast()
    {
        count++;
        id = count;
        tempF = 70.0;
        skyCondition = "Sunny";
    }

    /**
     * Constructs a forecast with an id, temperature, and sky condition.
     * ID is automatically generated based on how many objects have been created.
     * @param startTempF temperature in degrees fahrenheit
     * @param startSkyCondition sky condition
     */
    public Forecast(double startTempF, String startSkyCondition)
    {
        count++;
        id = count;
        setTempF(startTempF);
        setSkyCondition(startSkyCondition);
    }
//----------------------------------------------------------------------------------------------------------------------
// Accessors

    /**
     *
     * @return ID number to identify individual forecasts within array
     */
    public int getId()
    {
        return id;
    }

    /**
     *
     * @return forecast temperature in degrees fahrenheit
     */
    public double getTempF()
    {
        return tempF;
    }

    /**
     *
     * @return forecast sky condition
     */
    public String getSkyCondition()
    {
        return skyCondition;
    }

    /**
     *
     * @return current number of generated forecasts
     */
    public static int getCount()
    {
        return count;
    }

    /**
     *
     * @return array of possible sky conditions
     */
    public static String[] getConditionList()
    {
        return POSSIBLE_SKY_CONDITIONS;
    }

    /**
     *
     * @return maximum number of forecasts that can fit in the forecast array
     */
    public static int getMaxRecords()
    {
        return maxRecords;
    }

    /**
     *
     * @return forecast array
     */
    public static Forecast[] getForecastGroup()
    {
        return fg;
    }
//----------------------------------------------------------------------------------------------------------------------
// Mutators

    /**
     * Sets the temperature in degrees fahrenheit within the limits minTemp to maxTemp.
     * If the inputed value is out of range, sets temperature to 70.0.
     * Does not print invalid message to console if class variable suppressed is true.
     * @param newTempF forecast temperature
     */
    public void setTempF(double newTempF) {
        if (minTemp <= newTempF && newTempF <= maxTemp) {
            tempF = newTempF;
        }
        else {
            if (!suppressed)
            {
                System.out.println("*Invalid temperature.  Default Assigned.*");
            }
            tempF = 70.0;
        }
    }

    /**
     * Sets the sky condition for this forecast.  Option must match a value in the
     * possible sky conditions array (regardless of casing).  If it does not, the default
     * setting of 'Sunny' is used.
     * Does not print invalid message to console if class variable suppressed is true.
     * @param newSkyCondition sky condition for forecast
     */
    public void setSkyCondition(String newSkyCondition)
    {
        boolean isDefault = false;
        for (int i = 0; i < POSSIBLE_SKY_CONDITIONS.length; i++)
        {
            String condHolder = POSSIBLE_SKY_CONDITIONS[i];
            if (newSkyCondition.equalsIgnoreCase(condHolder))
            {
                skyCondition = condHolder;
                isDefault = false;
                break;
            }
            else
            {
                isDefault = true;
            }
        }

        if (isDefault)
        {
            skyCondition = "Sunny";
            if (!suppressed)
            {
                System.out.println("*Invalid condition.  Default assigned.*");
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------
//Class Methods
    /**
     * Generates a random temperature in degrees fahrenheit.
     * @return random fahrenheit temperature between minTemp and maxTemp
     */
    public static double genTemp()
    {
        double temp = Math.random() * maxTemp + (minTemp);
        return Math.round(temp);
    }

    /**
     * Generates a random sky condition based on possible sky conditions array.
     * @return random sky condition
     */
    public static String genSky()
    {
        int random = (int) (Math.random() * (POSSIBLE_SKY_CONDITIONS.length));
        return POSSIBLE_SKY_CONDITIONS[random];
    }

    /**
     * Loads forecast array with random forecasts.
     * @param input amount of forecasts to be generated
     */
    public static void loadRandom(int input)
    {
        int cnt = getCount();
        for (int i = cnt; i < (cnt + input); i++)
        {
            double temp = genTemp();
            String sky = genSky();
            fg[i] = new Forecast(temp, sky);
        }
    }

    /**
     * Creates a new forecast and loads it into the forecast array
     * @param temp temperature in degrees fahrenheit
     * @param sky sky condition
     */
    public static void createNew(double temp, String sky)
    {
        int i = getCount();
        fg[i] = new Forecast(temp, sky);
    }

    /**
     * Loads forecast array with easily testable data.  42 elements are loaded
     * into the array with data for every sky condition at different temperature
     * thresholds.  Messages for invalid inputs are suppressed for this method.
     */
    public static void loadTestData()
    {
        suppressed = true;		// toggle message suppression in setTempF
        String [] sky = getConditionList();
        int [] temps = {minTemp - 1, minTemp, 31, 32, 33, 99, 100, 101, maxTemp, maxTemp + 1};

        for (int i = 0; i < temps.length; i++)
        {
            for (int j = 0; j < sky.length; j++)
            {
                fg[Forecast.getCount()] = new Forecast(temps[i], sky[j]);
            }
        }
        // two defaults created for easy comparing
        fg[Forecast.getCount()] = new Forecast();
        fg[Forecast.getCount()] = new Forecast();
        suppressed = false;		// toggle message printing in setTempF
    }

//----------------------------------------------------------------------------------------------------------------------
// Instance Methods

    /**
     * Converts individual forecasts into a string.
     * @return converted string
     */
    public String toString()
    {
        return	"Temperature: "    + tempF + " °F, " +
                "Sky Condition: "  + skyCondition;
    }

    /**
     * Compares two forecasts.
     * @return true is the objects' data is equal, false if not
     */
    public boolean equals(Object o)
    {
        if (!(o instanceof Forecast)) {
            return false;
        }
        else
        {
            Forecast objForecast = (Forecast) o;
            if (skyCondition.equals(objForecast.skyCondition) && tempF == objForecast.tempF)
            {
                return true;
            }
            return false;
        }
    }
    /**
     * Converts a forecast temperature into degrees celsius.
     * @param tempIn fahrenheit temperature to be converted
     * @return converted temperature in celsius
     */
    public double convertToC(double tempIn)
    {
        return (tempIn - 32) * (5.0 / 9.0);
    }

    /**
     * Checks for consistency within the forecast object.  Forecast is consistent if
     * the temperature is below 32 and the condition is Snow, or if the temperature
     * is above 100 and the condition is Sunny.
     * @return true is consistent, false if not
     */
    public boolean forecastConsistent()
    {
        if (!skyCondition.equals("Snow") && tempF < 32.0)
        {
            return false;
        }
        if (tempF > 100.0 && !(skyCondition.equals("Sunny")))
        {
            return false;
        }
        return true;
    }
}// end class Forecast