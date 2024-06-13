package com.example;

public class Temperature {
    private int termometerId;
    private Double measureTime;
    private Double temperature;

    public Temperature(){}

    public Temperature(int termometerId, Double measureTime, Double temperature){
        this.termometerId = termometerId;
        this.measureTime = measureTime;
        this.temperature = temperature;
    }

    public int getTermometerId(){
        return termometerId;
    }

    public void setTermometerId(int termometerId){
        this.termometerId = termometerId;
    }

    public Double getMeasureTime(){
        return measureTime;
    }

    public void setTermometerId(Double measureTime){
        this.measureTime = measureTime;
    }

    public Double getTemperature(){
        return temperature;
    }

    public void setTemperature(Double temperature){
        this.temperature = temperature;
    }

    public String toString(){
        return "{Temperature="+
                "termometerId="+
                termometerId+
                ",measureTime="+
                measureTime+
                ",temperature="+
                temperature+
                "}";

    }
}

