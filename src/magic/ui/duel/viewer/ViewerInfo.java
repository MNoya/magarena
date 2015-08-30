package magic.ui.duel.viewer;

import magic.model.MagicGame;
import magic.model.MagicPlayer;
import magic.model.MagicMessage;
import magic.model.stack.MagicItemOnStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ViewerInfo {

    private final PlayerViewerInfo playerInfo;
    private final PlayerViewerInfo opponentInfo;
    private final List<StackViewerInfo> stack = new ArrayList<>();
    private final List<MagicMessage> log = new ArrayList<>(MAX_LOG);
        
    private static final int MAX_LOG = 50;

    public ViewerInfo(final MagicGame game) {
        final MagicPlayer player=game.getVisiblePlayer();
        playerInfo=new PlayerViewerInfo(game,player);
        opponentInfo=new PlayerViewerInfo(game,player.getOpponent());

        for (final MagicItemOnStack itemOnStack : game.getStack()) {
            stack.add(new StackViewerInfo(game,itemOnStack));
        }
        
        // get the last maxMsg messages
        int n = game.getLogBook().size();
        final Iterator<MagicMessage> iter = game.getLogBook().listIterator(Math.max(0, n - MAX_LOG));
        while (iter.hasNext()) {
            log.add(iter.next());
        }
    }

    public PlayerViewerInfo getPlayerInfo(final boolean opponent) {
        return opponent?opponentInfo:playerInfo;
    }

    public PlayerViewerInfo getAttackingPlayerInfo() {
        return playerInfo.turn?playerInfo:opponentInfo;
    }

    public PlayerViewerInfo getDefendingPlayerInfo() {
        return playerInfo.turn?opponentInfo:playerInfo;
    }

    public boolean isVisiblePlayer(final MagicPlayer player) {
        return playerInfo.player==player;
    }

    public List<StackViewerInfo> getStack() {
        return stack;
    }
    
    public List<MagicMessage> getLog() {
        return log;
    }
}
