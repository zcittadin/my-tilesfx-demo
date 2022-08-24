package zct.sistemas.tilesfx.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class MyTilesController implements Initializable {

	@FXML
	private ScrollPane scrollPane;

	private GridPane gridPane;

	private static final double TILE_WIDTH = 200;
	private static final double TILE_HEIGHT = 200;
	private static final Color color = Color.web("#35447e");
	
	Map<Mock, Tile> mocks = new HashMap<>();

	int row = 0, col = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setCenterShape(true);
		gridPane.setPadding(new Insets(5));
		scrollPane.setContent(gridPane);
		scrollPane.setPannable(true);
		scrollPane.fitToWidthProperty().set(true);
		scrollPane.fitToHeightProperty().set(true);
		scrollPane.setCenterShape(true);
	}

	@FXML
	private void addTile() {
		Mock mock = new Mock("ROW: " + row, "COL: " + col);
		Tile tile = createTile(mock);
		mocks.put(mock, tile);
		gridPane.getChildren().add(tile);
		gridPane.heightProperty()
				.addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> scrollPane.setVvalue(1D));
	}

	@FXML
	private void clearAll() {
		gridPane.getChildren().clear();
		col = 0;
		row = 0;
	}

	private Tile createTile(Mock mock) {
		ImageView iv = new ImageView(new Image("/img/bandeira.png"));
		iv.setCursor(Cursor.HAND);
		iv.setOnMouseClicked(event -> System.out.println(mock.getName()));
		Tooltip.install(iv, new Tooltip("CLICK!"));
		Tile tile = TileBuilder.create()//
				.skinType(SkinType.CUSTOM)//
				.prefSize(TILE_WIDTH, TILE_HEIGHT)//
//				.maxSize(TILE_WIDTH, TILE_HEIGHT)//
				.minSize(0, TILE_HEIGHT)//
				.title(mock.getTitle())//
				.text(mock.getName())//
				.graphic(iv)//
				.backgroundColor(color)//
				.roundedCorners(true)//
				.build();//
		GridPane.setColumnIndex(tile, col);
		GridPane.setRowIndex(tile, row);
		col++;
		if (col == 4) {
			col = 0;
			row++;
		}
		return tile;
	}
	
	private class Mock {
		private String title;
		private String name;

		public Mock(String title, String name) {
			this.title = title;
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public String getName() {
			return name;
		}

	}

}
