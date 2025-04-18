package seedu.address.ui;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import one.jpro.platform.mdfx.MarkdownView;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s2-cs2103-f10-1.github.io/tp/UserGuide.html";
    private static final String HELP_CONTENT_PATH = "/view/HelpContent.md";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private StackPane helpContentPane;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        loadHelpContent();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Loads the Markdown help content into the window.
     */
    private void loadHelpContent() {
        try {
            InputStream is = getClass().getResourceAsStream(HELP_CONTENT_PATH);
            byte[] bytes = Objects.requireNonNull(is).readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            is.close();

            MarkdownView markdownView = new MarkdownView(content) {
                @Override
                protected List<String> getDefaultStylesheets() {
                    return List.of(
                        Objects.requireNonNull(getClass().getResource("/view/DarkTheme.css")).toExternalForm(),
                        Objects.requireNonNull(getClass().getResource("/view/HelpWindow.css")).toExternalForm()
                    );
                }
            };
            // Create a ScrollPane with proper configuration
            ScrollPane scrollPane = new ScrollPane(markdownView);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            scrollPane.getStyleClass().add("help-scroll-pane");

            helpContentPane.getChildren().add(scrollPane);

        } catch (IOException e) {
            logger.warning("Error loading help content: " + e.getMessage());
        }
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");


        getRoot().setOnShown(e -> {
            // Get screen dimensions
            javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();

            // Set window size to fit screen if necessary
            double maxScreenWidth = screenBounds.getWidth();
            double maxScreenHeight = screenBounds.getHeight();

            double dialogWidth = getRoot().getWidth();
            double dialogHeight = getRoot().getHeight();

            double finalDialogWidth = Math.min(maxScreenWidth, dialogWidth);
            double finalDialogHeight = Math.min(maxScreenHeight, dialogHeight);

            getRoot().setWidth(finalDialogWidth);
            getRoot().setHeight(finalDialogHeight);
            getRoot().centerOnScreen();
        });

        getRoot().show();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
