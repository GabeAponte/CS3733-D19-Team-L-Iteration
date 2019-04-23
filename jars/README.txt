---- Team L Gift Request ----
Steps to include this API into your project:

1) Add the JAR file as a library in your project
	-You can do this by adding a folder called jars with the same scope as your source directory and adding the jar to it
	-You can then load the dependency by writing the line compile files("jars/ServiceRequest-1.0-SNAPSHOT.jar") to your
		build.gradle
2) Import the jar: import should look like import giftRequest.GiftRequest
2) Add a function that is linked to a button in your UI
3) In that function, enter the following code:

GiftRequest gr = new GiftRequest();
try{
    gr.run(0,0,1900,1000,null,null,null);
}catch (Exception e){
    System.out.println("Failed to run API");
    e.printStackTrace();
}
4) Lastly you will want to set up the database by placing it in a folder called myDB with the same scope 
	as your application source folder

----------------------------------------------------------------------------------------------

About the run function:
1) the first 2 parameters are the x and y coordinates of the top left corner of the window
2) the next 2 parameters are the height and the width of the window created
	HINT: The suggested minimum height is 1000 and the suggested minimum width is 1900
3) the next parameter is the path to your CSV file, if this is NULL, we have provided a default
	HINT: The CSV file path should look like "/pathtofile"
4) the next parameter can be a string for a specified location that the request will use
5) the last parameter is not used
