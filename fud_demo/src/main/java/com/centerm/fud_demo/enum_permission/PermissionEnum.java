package com.centerm.fud_demo.enum_permission;

public enum PermissionEnum {
    USER(0),
    ADMIN(1),
    SUPERVIP(2);
    private int i;
   private PermissionEnum(int i) {
        this.i=i;
    }
    public int getNum()
    {
        return i;
    }
}
