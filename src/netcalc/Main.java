/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package netcalc;

import javax.swing.UIManager;

/**
 *
 * @author fabioboris
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                }
                new FormNetCalc().setVisible(true);
            }
        });
    }
}
