package com.example.garci.positionsystemapp.model;

public interface ITask {
    final int EXIT_SUCCESS = 0;


    int weight();
    int run();
    String description();
}


//<inputType,progressType,resultType>
