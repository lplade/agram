package name.lplade;

import java.util.LinkedList;

class Table {
    //special circular linked list implementation

    LinkedList table;

    Table(){
        this.table = new LinkedList();
    }

    void addPlayer(Player p){

    }

    void removePlayer(Player p){
        //Don't think we ever need this
    }

    class Node{
        Player player, right, left;

        Node(Player p, Player onTheRight, Player onTheLeft){
            this.player = p;
            this.right = onTheRight;
            this.left = onTheLeft;
        }

    }


}
