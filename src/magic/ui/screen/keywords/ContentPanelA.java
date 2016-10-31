package magic.ui.screen.keywords;

import magic.ui.FontsAndBorders;
import magic.ui.widget.TexturedPanel;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
class ContentPanelA extends TexturedPanel {
    
    ContentPanelA() {   
        setLayout(new MigLayout("insets 0, gap 0, fill"));
        add(new KeywordsScrollPane(), "grow");
        setBackground(FontsAndBorders.TRANSLUCENT_WHITE_STRONG);
    }    
}
