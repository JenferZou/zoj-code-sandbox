package com.jenfer.utils;

import cn.hutool.core.util.StrUtil;
import com.jenfer.sandbox.model.ExecuteMessage;

import java.io.*;

public class ProcessUtils {

    /**
     * 执行进程并获取信息
     * @param runProcess
     * @param optName
     * @return
     */
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess,String optName) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            int exitValue = 0;
            exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
        if(exitValue==0){
            //分批获取用户代码执行后控制台输出
            System.out.println(optName+"成功");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            StringBuilder compileOutputStringBuilder = new StringBuilder();
            String compileOutputLine;
            while ((compileOutputLine = bufferedReader.readLine())!=null){
                compileOutputStringBuilder.append(compileOutputLine);
            }
            executeMessage.setMessage(compileOutputStringBuilder.toString());
            System.out.println(compileOutputStringBuilder);

        }else {
            //程序异常退出
            //分批获取用户代码执行后进程的正常输出流
            System.out.println(optName+"失败，错误码："+exitValue);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            String compileOutputLine;
            StringBuilder compileOutputStringBuilder = new StringBuilder();
            while ((compileOutputLine = bufferedReader.readLine())!=null){
                compileOutputStringBuilder.append(compileOutputLine);
            }
            executeMessage.setMessage(compileOutputStringBuilder.toString());
            System.out.println(compileOutputStringBuilder);

            //分批获取用户代码执行后控制台输出
            BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));

            String errorCompileOutputLine;
            StringBuilder errorCompileOutputStringBuilder = new StringBuilder();

            while ((errorCompileOutputLine = errorBufferedReader.readLine())!=null){
                errorCompileOutputStringBuilder.append(errorCompileOutputLine);
            }
            executeMessage.setErrorMessage(errorCompileOutputStringBuilder.toString());
            System.out.println(errorCompileOutputStringBuilder);

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }






    public static ExecuteMessage runInterProcessAndGetMessage(Process runProcess,String optName,String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();

        try {
            //向控制台输入程序案例
            OutputStream outputStream = runProcess.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] split = args.split(" ");
            String join = StrUtil.join("\n", split) + "\n";
            outputStreamWriter.write(join);
            //执行输入
            outputStreamWriter.flush();
            int exitValue = 0;
            exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);

            InputStream inputStream = runProcess.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder compileOutputStringBuilder = new StringBuilder();
            String compileOutputLine;
            while ((compileOutputLine = bufferedReader.readLine()) != null) {
                compileOutputStringBuilder.append(compileOutputLine);
            }
            executeMessage.setMessage(compileOutputStringBuilder.toString());
            outputStream.close();
            inputStream.close();
            outputStreamWriter.close();
            runProcess.destroy();
        }catch (Exception e){
            e.printStackTrace();
        }
        return executeMessage;
    }


}
