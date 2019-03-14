/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortationbutifailedtwice;

/**
 *
 * @author delucaa.2022
 */
public class Box {
    
    String barcode;
    int position = 0;
    int destination;
    Box(int dest, String barcode){
        destination = dest;
        this.barcode = barcode;
    }
    public String toString() {
        return "LANE #" + destination +" || LOCATION: "+ position+" || "+barcode;
    }
}
