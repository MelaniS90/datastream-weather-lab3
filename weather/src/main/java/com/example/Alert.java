/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 package com.example;

 import java.util.Objects;
 
 /** A simple alert event. */
 @SuppressWarnings("unused")
 public final class Alert {
 
     private Double temperature;
     private String date;

     public Alert(){}

     public Alert(Double temperature, String date){
        this.temperature = temperature;
        this.date = date;
     }
 
     public Double getTemperature() {
         return temperature;
     }
 
     public void setTemperature(Double temperature) {
         this.temperature = temperature;
     }

     public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
 
     @Override
     public boolean equals(Object o) {
         if (this == o) {
             return true;
         } else if (o == null || getClass() != o.getClass()) {
             return false;
         }
         Alert alert = (Alert) o;
         return temperature == alert.temperature;
     }
 
     @Override
     public int hashCode() {
         return Objects.hash(temperature);
     }
 
     @Override
     public String toString() {
         return "Alert{" + "temperature=" + temperature + ",date="+date+'}';
     }
 }