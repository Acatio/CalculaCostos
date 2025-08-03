/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuracionLog;

import java.io.IOException;
import java.util.logging.*;

/**
 *
 * @author jose
 */
public class LoggConfiguration
{

    private static final Logger LOG = Logger.getLogger(LoggConfiguration.class.getName());
    static
    {
        try
        {
            FileHandler fileHandler = new FileHandler("app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger rootLogger = Logger.getLogger("");
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.ALL);
        } catch (IOException e)
        {
            Logger.getGlobal().log(Level.SEVERE, "No se pudo configurar el logging", e);
        }
    }
}
