/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortationbutifailedtwice;

import interfaces.OutputInterface;

/**
 *
 * @author delucaa.2022
 */
public class Pusher {
    int id;
    OutputInterface outputs;
    Pusher(int id, OutputInterface outputs){
        this.id = id;
        this.outputs = outputs;
    }
    
    
    int pushTimer = 0;
    ////////////////////////////////////
    //Speed controls////////////////////
    int pushDelay = 9;
    int holdDelay = 5;//
    ////////////////////////////////////
    ////////////////////////////////////
    public void push() {
        if (pushTimer != 0 && pushTimer <= pushDelay) {
//            System.out.println("///////PUSH///////");
            pushTimer++;
//            System.out.println(id + ": -|");
//            System.out.println(id + ": >>>");
//            System.out.println("timer: "+pushTimer);
        }

        if (pushTimer >= pushDelay && pushTimer <= pushDelay + holdDelay) {
//            System.out.println(id + ": ---|");
//            System.out.println(id + ": ===");
//            System.out.println("timer: "+pushTimer);
            switch (id) {
                case 1:
                    outputs.setPusher1State(true);
                    break;
                case 2:
                    outputs.setPusher2State(true);
                    break;
                case 3:
                    outputs.setPusher3State(true);
                    break;
                case 4:
                    outputs.setPusher4State(true);
                    break;
                case 5:
                    outputs.setPusher5State(true);
                    break;
            }
            pushTimer += 1;
        }
        if (pushTimer == pushDelay + holdDelay) {
//            System.out.println(id + ": |<-");
//            System.out.println(id + ": <<<");
//            System.out.println("timer: "+pushTimer);
            switch (id) {
                case 1:
                    outputs.setPusher1State(false);
                    break;
                case 2:
                    outputs.setPusher2State(false);
                    break;
                case 3:
                    outputs.setPusher3State(false);
                    break;
                case 4:
                    outputs.setPusher4State(false);
                    break;
                case 5:
                    outputs.setPusher5State(false);
                    break;
            }
            pushTimer = 0;
//            System.out.println("//////////////////");

        }
    }
}
