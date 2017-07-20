package com.ohmuk.folitics.model;

import com.ohmuk.folitics.util.EncryptionUtil;

public class UserModel {
    private boolean isEncrypted;
    private String userName;
    private String userId;

    
    public boolean isEncrypted() {
        return isEncrypted;
    }

    public void setEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    public Long getUserIdLong() {
        if(isEncrypted){
            return  EncryptionUtil.getDecryptedUserId(userId);
        }
        else
            return Long.getLong(userId);
        
    }

    public String getUserId() throws Exception{
        if(isEncrypted){
            return EncryptionUtil.decrypt(userId);
        }
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() throws Exception {
        if(isEncrypted){
            return EncryptionUtil.decrypt(userName);
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
