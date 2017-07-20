package com.ohmuk.folitics.enums;

public enum Color {
    ANTI("#449d44"), PRO("#f95900");

   private String value;

   private Color(String value) {
       this.setValue(value);
   }

   public String getValue() {
       return value;
   }

   public void setValue(String value) {
       this.value = value;
   }

   public static final Color getCategoryState(String value) {
       if (ANTI.getValue().equals(value)) {
           return ANTI;
       }
       if (PRO.getValue().equals(value)) {
           return PRO;
       }
       return null;
   }
}
