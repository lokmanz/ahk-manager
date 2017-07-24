package ahk.manager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsProcess
{
    private String processName;
    private String processPath;

    public WindowsProcess(String processName, String processPath)
    {
        this.processName = processName;
        this.processPath = processPath;
    }

    public void kill() throws Exception
    {
        if (isRunning())
        {
            getRuntime().exec("taskkill /F /IM " + processName);
        }
    }
    
    public void run() throws IOException
    {
        String pathExe=this.processPath;
        pathExe=pathExe.replaceAll("/", "\\\\\\\\");
        pathExe=pathExe.replace("ahk", "exe");
        
        String pathExeDir=pathExe.substring(0, pathExe.length() - 14);
        pathExeDir=pathExeDir.replaceAll("/", "\\\\\\\\");
        
        Runtime.getRuntime().exec(pathExe, null, new File(pathExeDir));
//        Runtime.getRuntime().exec("C:\\Users\\User\\Documents\\AutoHotkey.exe", null, new File("C:\\Users\\User\\Documents\\"));
    }

    public boolean isRunning() throws Exception
    {
        Process listTasksProcess = getRuntime().exec("tasklist");
        BufferedReader tasksListReader = new BufferedReader(
                new InputStreamReader(listTasksProcess.getInputStream()));

        String tasksLine;

        while ((tasksLine = tasksListReader.readLine()) != null)
        {
            if (tasksLine.contains(processName))
            {
                return true;
            }
        }

        return false;
    }

    private Runtime getRuntime()
    {
        return Runtime.getRuntime();
    }
    
}