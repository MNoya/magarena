package magic.ui.screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import magic.MagicUtility;
import magic.data.CardDefinitions;
import magic.data.IconImages;
import magic.data.URLUtils;
import magic.exceptions.DesktopNotSupportedException;
import magic.model.MagicCardDefinition;
import magic.ui.ScreenController;
import magic.ui.screen.interfaces.IActionBar;
import magic.ui.screen.interfaces.IStatusBar;
import magic.ui.screen.interfaces.IWikiPage;
import magic.ui.screen.widget.ActionBarButton;
import magic.ui.screen.widget.MenuButton;
import magic.ui.widget.TextFileReaderPanel;
import magic.utility.MagicFileSystem;
import magic.utility.MagicFileSystem.DataPath;
import magic.utility.MagicStyle;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class CardScriptScreen
    extends AbstractScreen
    implements IStatusBar, IActionBar, IWikiPage {

    private final ScreenContent content;

    public CardScriptScreen(final MagicCardDefinition card) {
        content = new ScreenContent(card);
        setContent(content);
    }

    @Override
    public String getScreenCaption() {
        return "Card Script";
    }

    @Override
    public JPanel getStatusPanel() {
        return null;
    }

    /* (non-Javadoc)
     * @see magic.ui.screen.AbstractScreen#isScreenReadyToClose(magic.ui.screen.AbstractScreen)
     */
    @Override
    public boolean isScreenReadyToClose(AbstractScreen nextScreen) {
        return true;
    }

    /* (non-Javadoc)
     * @see magic.ui.screen.interfaces.IActionBar#getLeftAction()
     */
    @Override
    public MenuButton getLeftAction() {
        return MenuButton.getCloseScreenButton("Close");
    }

    /* (non-Javadoc)
     * @see magic.ui.screen.interfaces.IActionBar#getRightAction()
     */
    @Override
    public MenuButton getRightAction() {
        return null;
    }

    /* (non-Javadoc)
     * @see magic.ui.screen.interfaces.IActionBar#getMiddleActions()
     */
    @Override
    public List<MenuButton> getMiddleActions() {
        final List<MenuButton> buttons = new ArrayList<>();
        if (MagicUtility.isDevMode()) {
            buttons.add(
                    new ActionBarButton(
                            IconImages.REFRESH_ICON,
                            "Reload", "Reload script/groovy files.",
                            new AbstractAction() {
                                @Override
                                public void actionPerformed(final ActionEvent e) {
                                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                    content.refreshContent();
                                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                }
                            })
            );
        }
        buttons.add(
                new ActionBarButton(
                        "Firemind", "Opens the Project Firemind scrips submission page in your browser.",
                        new AbstractAction() {
                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                openFiremindScriptsSubmissionWebpage();
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            }
                        })
        );
        return buttons;
    }

    private void openFiremindScriptsSubmissionWebpage() {
        URLUtils.openURL(URLUtils.URL_FIREMIND_SCRIPTS);
    }

    @Override
    public String getWikiPageName() {
        return "ImplementingCards";
    }

    private class ScreenContent extends JPanel {

        // UI components
        private final MigLayout migLayout = new MigLayout();
        private final ScriptFileViewer scriptViewer = new ScriptFileViewer();
        private final ScriptFileViewer groovyViewer = new ScriptFileViewer();
        private final JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        private final File scriptFile;
        private final File groovyFile;
        private final boolean isGroovy;

        public ScreenContent(final MagicCardDefinition card) {

            final Path scriptsPath = card.isMissing() ?
                    MagicFileSystem.getDataPath(DataPath.SCRIPTS_MISSING) :
                    MagicFileSystem.getDataPath(DataPath.SCRIPTS);

            scriptFile = scriptsPath.resolve(CardDefinitions.getScriptFilename(card)).toFile();
            groovyFile = scriptsPath.resolve(CardDefinitions.getGroovyFilename(card)).toFile();
            isGroovy = groovyFile.exists();

            setLookAndFeel();
            setLayout();
            refreshContent();

        }

        public void refreshContent() {
            scriptViewer.setContent(scriptFile);
            if (isGroovy) { groovyViewer.setContent(groovyFile); }
        }

        private void setLayout() {
            removeAll();
            migLayout.setLayoutConstraints("flowy, insets 0");
            add(isGroovy ? splitter : scriptViewer, "w 100%, h 100%");
        }

        private void setLookAndFeel() {
            setLayout(migLayout);
            setOpaque(false);
            if (isGroovy) {
                splitter.setOneTouchExpandable(true);
                splitter.setLeftComponent(scriptViewer);
                splitter.setRightComponent(groovyViewer);
                splitter.setResizeWeight(0.5);
                splitter.setDividerSize(14);
                splitter.setBorder(null);
                splitter.setOpaque(false);
            }
        }
    }

    private class ScriptFileViewer extends JPanel {

        private final MigLayout migLayout = new MigLayout();
        private final ScriptFileViewerHeader headerPanel = new ScriptFileViewerHeader();
        private final TextFileReaderPanel contentsPanel = new TextFileReaderPanel();

        public ScriptFileViewer() {
            setLookAndFeel();
            setLayout();
        }

        public void setContent(final File textFile) {
            headerPanel.setContent(textFile);
            contentsPanel.setContent(textFile.toPath());
        }

        private void setLookAndFeel() {
            setLayout(migLayout);
            setOpaque(false);
        }

        private void setLayout() {
            removeAll();
            migLayout.setLayoutConstraints("flowy, insets 0, gap 0");
            add(headerPanel, "w 100%, h 34!");
            add(contentsPanel, "w 100%, h 100%");
        }

    }

    private class ScriptFileViewerHeader extends JPanel {

        private final Color DEFAULT_FORECOLOR = Color.WHITE;

        private final MigLayout migLayout = new MigLayout();
        private final JLabel headerLabel = new JLabel();
        private File textFile;

        public ScriptFileViewerHeader() {
            setLookAndFeel();
            setLayout();
        }

        private void setLookAndFeel() {
            setLayout(migLayout);
            setOpaque(true);
            setBackground(Color.DARK_GRAY);
            // header label
            headerLabel.setForeground(DEFAULT_FORECOLOR);
            headerLabel.setFont(new Font("dialog", Font.PLAIN, 14));
            headerLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        MagicFileSystem.openFileInDefaultOsEditor(textFile);
                    } catch (IOException | DesktopNotSupportedException ex) {
                        ScreenController.showWarningMessage("Unable to open file :\n" + textFile + "\n\n" + ex.getMessage());
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    headerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    headerLabel.setForeground(MagicStyle.HIGHLIGHT_COLOR);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    headerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    headerLabel.setForeground(DEFAULT_FORECOLOR);
                }
            });
        }

        private void setLayout() {
            removeAll();
            migLayout.setLayoutConstraints("insets 0 4 0 4, aligny center");
            add(headerLabel, "w 100%, h 100%");
        }

        public void setContent(final File textFile) {
            this.textFile = textFile;
            headerLabel.setText(textFile.getAbsolutePath());
        }

    }

}
