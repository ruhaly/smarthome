package com.changhong.smarthome.phone.sns.bean;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class TSnsThemeCode implements java.io.Serializable
{
    private BigDecimal id;
    
    private String code;
    
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    private String codeName;
    
    public BigDecimal getId()
    {
        return id;
    }
    
    public void setId(BigDecimal id)
    {
        this.id = id;
    }
    
    public String getCodeName()
    {
        return codeName;
    }
    
    public void setCodeName(String codeName)
    {
        this.codeName = codeName;
    }
}