/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortationbutifailedtwice;

import interfaces.ScannerInterface;

/**
 *
 * @author delucaa.2022
 */
public class Scanner implements ScannerInterface{
    String read = null;
    @Override
    public void OnRead(String string) {
        read = string;
    }
    
}
