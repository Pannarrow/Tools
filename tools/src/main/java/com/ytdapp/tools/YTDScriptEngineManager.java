package com.ytdapp.tools;


import com.ytdapp.tools.log.YTDLog;

import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class YTDScriptEngineManager {
    /*单例*/
    private static volatile YTDScriptEngineManager engineManager;
    private ScriptEngine scriptEngine;

    private YTDScriptEngineManager(){
        ScriptEngineManager manager = new ScriptEngineManager();
        scriptEngine = manager.getEngineByName("javascript");
        String text = YTDUtils.getTextFileContent(ContextUtil.getContext().getResources().openRawResource(R.raw.ytd_jscontext));
        try {
            scriptEngine.eval(text);
        } catch (ScriptException e) {
            YTDLog.log(e);
        }
    }


    public  static YTDScriptEngineManager getInstance(){
        if (engineManager == null){
            synchronized (YTDScriptEngineManager.class){
                if (engineManager == null){
                    engineManager = new YTDScriptEngineManager();
                }
            }
        }
        return engineManager;
    }

    /**
     * 添加变量
     * @param variables 变量
     */
    public void addVariables(Map <String,String> variables){
        for (String key:variables.keySet()){
            Object objct = scriptEngine.get(key);
            if (objct == null){
                try {
                    scriptEngine.eval("var "+key+"="+(String) variables.get(key)+";");
                } catch (ScriptException e) {
                    YTDLog.log(e);
                }
            }else {
                scriptEngine.put(key,YTDParseUtil.parseDouble(variables.get(key)));
            }
        }
    }

    /**
     * 公式判断
     * @param formula 公式
     * @return 判断结果
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    public boolean evalJavaScriptWithFormula(String formula) {
        Invocable jsInvoke = (Invocable) scriptEngine;
        boolean res = false;
        try {
            res = (Boolean)jsInvoke.invokeFunction("caculateFormula", new Object[] { formula });
        } catch (Exception e) {
            YTDLog.log(e);
        }
        return res;
    }

    /**
     * 是否数字
     * @param str
     * @return
     */
    public boolean isNumberJavaScriptWithFormula(String str) {
        Invocable jsInvoke = (Invocable) scriptEngine;
        boolean res = false;
        try {
            res = (Boolean)jsInvoke.invokeFunction("isNumber", new Object[] { str });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
