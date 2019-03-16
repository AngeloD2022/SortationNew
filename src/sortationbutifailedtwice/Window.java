/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortationbutifailedtwice;

import gui.SortFrame;
import interfaces.InputInterface;
import interfaces.OutputInterface;
import java.util.ArrayList;

/**
 *
 * @author delucaa.2022
 */
public class Window extends SortFrame {

    Scanner scanner;
    InputInterface inputs;
    OutputInterface outputs;
    ArrayList<Box> belt;
    Pusher[] pushers = new Pusher[5];
    // "Thought LinkedLists was hard? You ain't seen nothin' yet..."
    //probable failure inbound
    Window() {
        scanner = new Scanner();
        connectScanner(scanner);
        inputs = getInputInterface();
        outputs = getOutputInterface();
        belt = new ArrayList<Box>();
        for(int i = 0; i < 5; i++){
            pushers[i] = new Pusher(i+1, outputs);
        }
        start();
    }
    int scanned;
    int inducted;
    boolean inductIsTriggered;
    boolean firstTriggered;
    int[] timers = {0, 0, 0, 0, 0};
    boolean[] pushArmed = {false, false, false, false, false};
    boolean[] leftEye = {false, false, false, false, false};
    
    //Dont push until 5 ticks after eye gave false

    @Override
    public void onUpdate() {
        for(int i = 0; i < 5; i++){
            pushers[i].updatePusher();
        }
        //KEEP CHANGING SPEED, MISSED BOXES, BAD READS, and MISSED INDUCTS ALL IN MIND
        //Add boxes to ArrayList
        if (scanner.read != null) {
            scanned++;
            System.out.println(scanner.read);
            if (scanner.read.equals("BAD READ") || scanner.read.charAt(0) == '0') {
                belt.add(new Box(5, scanner.read));
            } else if (scanner.read.charAt(1) >= '1' && scanner.read.charAt(1) <= '3') {
                belt.add(new Box(1, scanner.read));
            } else if (scanner.read.charAt(1) >= '4' && scanner.read.charAt(1) <= '6') {
                belt.add(new Box(2, scanner.read));
            } else if (scanner.read.charAt(1) >= '7' && scanner.read.charAt(1) <= '9') {
                belt.add(new Box(3, scanner.read));
            } else if (scanner.read.charAt(1) == '0') {
                belt.add(new Box(6, scanner.read));
            }
            scanner.read = null;
        }
        
        
        //Increment Integer for each induct eye pass
        if (inputs.getInductPhotoEyeState() && !inductIsTriggered) {
            inductIsTriggered = true;
            inducted++;
        } else if (inductIsTriggered && !inputs.getInductPhotoEyeState()) {
            inductIsTriggered = false;
        }

        //Handle boxes that were not scanned
        if (inducted > scanned) {
            Box b = new Box(5, "MISSED");
            b.position = 8;
            belt.add(b);
            scanned = inducted;
        }

        //handle boxes that missed entry
        //if a box has just hit lane1
        if (!firstTriggered && inputs.getLane1PhotoEyeState()) {
            firstTriggered = true;
            boolean foundBox = false;
            for (int i = 0; i < belt.size(); i++) {
                if (belt.get(i).position >= 90 && belt.get(i).position <= 115) {
                    foundBox = true;
                    break;
                }
            }
            if (!foundBox) {
                Box b = new Box(4, "NOINDUCT");
                b.position = 100;
                belt.add(b);
            }
        } else if (!inputs.getLane1PhotoEyeState()) {
            firstTriggered = false;
        }

        //Push Boxes in their respective lanes
        for (int i = 0; i < belt.size(); i++) {
            belt.get(i).position++;
            switch (belt.get(i).destination) {
                case 1:
                    if (belt.get(i).position >= 100 && belt.get(i).position <= 110 && inputs.getLane1PhotoEyeState()) {
                        belt.remove(i);
                        pushArmed[0] = true;
                    }
                    break;
                case 2:// s->1 (100tks); 1->2 (total: 124, d:24); 2->3 (t: 159, d: 34); 3->4 (t: 183, d:24); 4->5 (t:218, d: 35);
                    if (belt.get(i).position >= 124 && belt.get(i).position <= 134 && inputs.getLane2PhotoEyeState()) {
                        belt.remove(i);
                        pushArmed[1] = true;
                    }
                    break;
                case 3:
                    if (belt.get(i).position >= 159 && belt.get(i).position <= 169 && inputs.getLane3PhotoEyeState()) {
                        belt.remove(i);
                        pushArmed[2] = true;
                    }
                    break;
                case 4:
                    if (belt.get(i).position >= 183 && belt.get(i).position <= 194 && inputs.getLane4PhotoEyeState()) {
                        belt.remove(i);
                        pushArmed[3] = true;
                    }
                    break;
                case 5:
                    if (belt.get(i).position >= 218 && belt.get(i).position <= 230 && inputs.getLane5PhotoEyeState()) {
                        belt.remove(i);
                        pushArmed[4] = true;
                    }
                break;
            }
            int l1centertime = 4;
            int l2centertime = 4;
            int l3centertime = 4;
            int l4centertime = 4;
            int l5centertime = 4;
            
            
            if(pushArmed[0] && !inputs.getLane1PhotoEyeState()){
                if(timers[0] == l1centertime){
                    timers[0] = 0;
                    pushers[0].push();
                    pushArmed[0] = false;
                }else{
                    timers[0]++;
                }
            }
            if(pushArmed[1] && !inputs.getLane2PhotoEyeState()){
                if(timers[1] == l2centertime){
                    timers[1] = 0;
                    pushers[1].push();
                    pushArmed[1] = false;
                }else{
                    timers[1]++;
                }
            }
            if(pushArmed[2] && !inputs.getLane3PhotoEyeState()){
                if(timers[2] == l3centertime){
                    timers[2] = 0;
                    pushers[2].push();
                    pushArmed[2] = false;
                }else{
                    timers[2]++;
                }
            }
            if(pushArmed[3] && !inputs.getLane4PhotoEyeState()){
                if(timers[3] == l4centertime){
                    timers[3] = 0;
                    pushers[3].push();
                    pushArmed[3] = false;
                }else{
                    timers[3]++;
                }
            }
            if(pushArmed[4] && !inputs.getLane5PhotoEyeState()){
                if(timers[4] == l5centertime){
                    timers[4] = 0;
                    pushers[4].push();
                    pushArmed[4] = false;
                }else{
                    timers[4]++;
                }
            }
            
        }
        
        

        //see if BADREAD exists, send to five
        //see if there is a box entry without induct hit
        //see if there is a noread box
        //see if there is a jam
    }
    //int[] pushTimer = 0;
    

}