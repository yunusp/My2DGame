package entity;

import main.GamePanel;


public class NPC_OldMan extends Entity {
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
    }

    public void getImage() {
        up1     =    setupPackageAgnosticEntity("/npc/oldman_up_1");
        up2     =    setupPackageAgnosticEntity("/npc/oldman_up_2");
        down1   =    setupPackageAgnosticEntity("/npc/oldman_down_1");
        down2   =    setupPackageAgnosticEntity("/npc/oldman_down_2");
        right1  =    setupPackageAgnosticEntity("/npc/oldman_right_1");
        right2  =    setupPackageAgnosticEntity("/npc/oldman_right_2");
        left1   =    setupPackageAgnosticEntity("/npc/oldman_left_1");
        left2   =    setupPackageAgnosticEntity("/npc/oldman_left_2");
    }

    @Override
    public void setAction(){

    }

}
