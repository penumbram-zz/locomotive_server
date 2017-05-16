package app.rest.socket;

import app.rest.game.Prize;

import java.util.List;

/**
 * Created by tolgacaner on 07/05/2017.
 */
public interface GameServerDelegate {
    void serverFreed(GameServer gameServer);
    void updatePrizes(List<Prize> prizes,long gameId);
}
