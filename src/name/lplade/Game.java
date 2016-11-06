package name.lplade;

// based on
// http://stackoverflow.com/questions/4870165/player-order-in-card-game


class Game {
    private String[] playerNames;
    private Player[] players;
    private int playerTurnIndex = 0;
    private int winnerIndex = 0;

    Game(String[] playerNames) {
        this.playerNames = playerNames;
        players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++) {
            players[i] = new Player(playerNames[i]);
        }
    }

    public int getPlayerTurnIndex(){
        return playerTurnIndex;
    }

    Player getCurrentPlayer(){
        return players[playerTurnIndex];
    }

    public String[] getPlayerNames(){
        return playerNames;
    }

    void advancePlay() {
        playerTurnIndex++;
        playerTurnIndex %= players.length; //I THINK this handles go-rounds past the first
        for (Player player: players) {
            if (player != getCurrentPlayer()) {
                player.setMyTurn(false);
            } else {
                player.setMyTurn(true);
            }
        }
    }

    //re-initializes the turn order. Argument is the player array index.
    //remember to getPlayerTurnIndex() before calling this.
    void startNewRound(int playerIndex){
        //reset all the players except the new starting player
        for (int i = 0; i < players.length; i++) {
            if (i == playerIndex) {
                players[i].setMyTurn(true);
            } else {
                players[i].setMyTurn(false);
            }
        }
        // and initialize this index in the object's variable
        playerTurnIndex = playerIndex;
    }

    int getPlayerCount(){
        return players.length;
    }

    Player getPlayer(int i){
        return players[i];
    }

    int getWinnerIndex(){
        return this.winnerIndex;
    }

    Player getWinningPlayer(){
        return getPlayer(getWinnerIndex());
    }

    boolean isCurrentPlayerWinning(){
        return this.playerTurnIndex == this.winnerIndex;
    }

    void setWinning(){
        this.winnerIndex = this.playerTurnIndex;
    }



}
