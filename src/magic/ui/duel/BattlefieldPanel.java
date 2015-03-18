package magic.ui.duel;

import javax.swing.JPanel;
import magic.model.MagicCardList;
import magic.model.MagicPlayerZone;
import magic.model.event.MagicEvent;
import magic.ui.duel.animation.PlayCardAnimation;
import magic.ui.duel.resolution.ResolutionProfileResult;
import magic.ui.duel.viewer.PlayerViewerInfo;
import magic.ui.duel.viewer.StackViewer;

@SuppressWarnings("serial")
public abstract class BattlefieldPanel extends JPanel {

    public abstract void doUpdate();

    public abstract void showCards(final MagicCardList cards);

    public abstract void focusViewers(final int handGraveyard);

    public abstract void resizeComponents(final ResolutionProfileResult result);

    public abstract void setAnimationEvent(final MagicEvent event, final DuelPanel gamePanel);

    public abstract PlayCardAnimation getPlayCardFromHandAnimation();

    public abstract void setPlayCardFromHandAnimation(final PlayCardAnimation event);
    
    public abstract StackViewer getStackViewer();

    public abstract void setActivePlayerZone(PlayerViewerInfo playerInfo, MagicPlayerZone zone);

    public abstract void setFullScreenActivePlayerZone(PlayerViewerInfo playerInfo, MagicPlayerZone zone);
}
