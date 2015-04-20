----*-*-*-*-*-*-*-*-Purpose: 2015SpringUMKCHackathon-*-*-*-*-*-*-*-*-*----
----*-*-*-*-*-*-*-*-Statement: DSTUseCase# 1 - Statistics Is Fun-*-*-*-*-*-*-*-*-*----

In this program, we have three classes
Main Class (name of the class which having driver method i.e main method)
Mapper class to Implement the Map side Business Logic
Reducer class to implement the Reducer side business Logic.

Approach for Programming the Map Reduce logic using Java:

Main Class
{
	Driver Method
	{
		Invoke Mapper and Reducer
		Set No of Reducers = 2
	}
}
Mapper Class
{
	Map Method
	{
		Read the Input data from the file and generate key value pairs
	}
}
Reducer Class
{
	Reduce Method
	{
		Read key value pairs and perform the business computations.
	}
}

Input Data set is text document with the Integer dataset.

Output File Format is plain text document generated in Hadoop by Reducer. It contains the Mathematical Computations present in problem statement.


Data Laoding into HDFS:

create the directory in HDFS:
cmd> hadoop fs -mkdir /hackathon
cmd> hadoop fs -copyFromLocal /home/biadmin/Desktop/HackathonSP2015/input.txt /user/biadmin/hackathon/
cmd> hadoop fs -ls /user/biadmin/hackathon/

Export the Eclipse Project to Jar File:

Using Eclipse:

Right Click on class Name.
Select export -> java -> jarfile
Choose jar file name and finish.

To Run the Map Reduce Program Jar file in Hadoop:

cmd> hadoop jar StatisticalAnalysis.jar com.hackathon.mapreduce.AnalyzeData 
=========================================================================================================================




 