# Newspaper-Headline-Searcher

The Newspaper Headline Search application uses NewsAPI to search headlines from the past two days for keywords and phrases.

### Before Using

In order to use the application, first acquire a free or paid API key from [NewsAPI](newsapi.org). Place the key at the location designated by a comment in the HeadlineModel.java file.
Then add a list of desired sources in the sources parameter of the api call or remove the parameter completely to search the entire base. The application can then be compiled to a .jar or .exe and run directly from the HeadlineApplication.java main.

### Development Notes

The project uses the Java Swing framework as the base for the GUI. Note that the methods for updating the JLists on the results and history panels are **hard coded** so should be changed with care.
