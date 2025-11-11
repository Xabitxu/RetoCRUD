/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ConnectionPool;

public class HiloConnection extends Thread {

    private int delay = 30;
    private boolean end = false;
    private boolean ready = false;
    private Connection con;

    public HiloConnection(int delay) {
        this.delay = delay;
    }

    public Connection getConnection() {
        return con;
    }

    public boolean isReady() {
        return ready;
    }

    public void releaseConnection() {
        this.end = true;
        this.interrupt();
    }

    @Override
    public void run() {
        try {
            try {
                con = ConnectionPool.getConnection();
            } catch (Exception ex) {
                Logger.getLogger(HiloConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            ready = true;

            while (!end) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    if (end) {
                        break;
                    }

                    Thread.currentThread().interrupt();
                }
            }

            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } finally {

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HiloConnection.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
}
