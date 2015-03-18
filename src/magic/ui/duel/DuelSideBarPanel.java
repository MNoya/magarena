package magic.ui.duel;

import javax.swing.JPanel;
import magic.data.GeneralConfig;
import magic.ui.SwingGameController;
import magic.ui.duel.player.GamePlayerPanel;
import magic.ui.duel.resolution.DefaultResolutionProfile;
import magic.ui.duel.viewer.GameStatusPanel;
import magic.ui.duel.viewer.LogBookViewer;
import magic.ui.duel.viewer.LogStackViewer;
import magic.ui.duel.viewer.StackViewer;
import magic.ui.widget.FontsAndBorders;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DuelSideBarPanel extends JPanel {

    private static final GeneralConfig CONFIG = GeneralConfig.getInstance();

    private final GamePlayerPanel opponentViewer;
    private final GamePlayerPanel playerViewer;
    private final LogStackViewer logStackViewer;
    private final LogBookViewer logBookViewer;
    private final GameStatusPanel gameStatusPanel;
    private final SwingGameController controller;
    private boolean isSwitchedPlayers = false;

    DuelSideBarPanel(final SwingGameController controller, final StackViewer imageStackViewer) {
        this.controller = controller;
        setOpaque(false);
        //
        opponentViewer = new GamePlayerPanel(controller, controller.getViewerInfo().getPlayerInfo(true));
        playerViewer = new GamePlayerPanel(controller, controller.getViewerInfo().getPlayerInfo(false));
        logBookViewer = new LogBookViewer(controller.getGame().getLogBook());
        logBookViewer.setVisible(!CONFIG.isLogViewerDisabled());
        logStackViewer = new LogStackViewer(logBookViewer, imageStackViewer);
        logStackViewer.setBackground(FontsAndBorders.TRANSLUCENT_WHITE_STRONG);
        gameStatusPanel= new GameStatusPanel(controller);
        gameStatusPanel.setBackground(FontsAndBorders.TRANSLUCENT_WHITE_STRONG);
    }

    GameStatusPanel getGameStatusPanel() {
        return gameStatusPanel;
    }

    LogBookViewer getLogBookViewer() {
        return logBookViewer;
    }

    void doSetLayout() {

        final int insets = 6;
        final int maxWidth = DefaultResolutionProfile.getPanelWidthLHS() - (insets * 2);

        removeAll();
        setLayout(new MigLayout("insets " + insets + ", gap 0 10, flowy"));

        add(isSwitchedPlayers ? playerViewer : opponentViewer, "w " + maxWidth + "!, h " + DefaultResolutionProfile.PLAYER_VIEWER_HEIGHT_SMALL + "!");
        add(logStackViewer, "w " + maxWidth + "!, h 100%");
        add(gameStatusPanel, "w " + maxWidth + "!, h " + DefaultResolutionProfile.GAME_VIEWER_HEIGHT + "!");
        add(isSwitchedPlayers ? opponentViewer : playerViewer,   "w " + maxWidth + "!, h " + DefaultResolutionProfile.PLAYER_VIEWER_HEIGHT_SMALL + "!");

        logStackViewer.setLogStackLayout();

    }

    void doUpdate() {
        opponentViewer.updateDisplay(controller.getViewerInfo().getPlayerInfo(!isSwitchedPlayers));
        playerViewer.updateDisplay(controller.getViewerInfo().getPlayerInfo(isSwitchedPlayers));
        gameStatusPanel.update();
    }

    void switchPlayers() {
        isSwitchedPlayers = !isSwitchedPlayers;
        doSetLayout();
        revalidate();
    }

}
