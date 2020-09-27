package principal;

import java.io.File;
import java.text.DecimalFormat;

import classificacao_caracteristicas.Classificacao_caracteristicas;
import extrator_caracteristicas.ExtractCaracteristicas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class PrincipalController {

	@FXML
	private ImageView imageOri;
	@FXML
	private ImageView imagePro;

	@FXML
	private Label resultado_PretoFardaChief;
	@FXML
	private Label resultado_AzulFardaChief;
	@FXML
	private Label resultado_Camisa_Grampa;
	@FXML
	private Label resultado_Barba_Grampa;
	@FXML
	private Label classificaChief;
	@FXML
	private Label classificaKrusty;

	File f;
	double[] caracteristicasExtraidas;
	DecimalFormat formatador = new DecimalFormat("0.00");

	@FXML
	public void extrairCaracteristicas() {
		ExtractCaracteristicas.extrair(imageOri, imagePro);
	}

	@FXML
	public void selecionaImagem() {
		f = buscaImg();
		if (f != null) {
			Image img = new Image(f.toURI().toString());
			getImageOri().setImage(img);
			getImageOri().setFitWidth(img.getWidth());
			getImageOri().setFitHeight(img.getHeight());
			caracteristicasExtraidas = ExtractCaracteristicas.extraiCaracteristicas(f, imageOri, imagePro);
			resultado_PretoFardaChief.setText(formatador.format(caracteristicasExtraidas[0]));
			resultado_AzulFardaChief.setText(formatador.format(caracteristicasExtraidas[1]));
			resultado_Barba_Grampa.setText(formatador.format(caracteristicasExtraidas[2]));
			resultado_Camisa_Grampa.setText(formatador.format(caracteristicasExtraidas[3]));
		}
	}

	@FXML
	public void classificarImagem() {
		if (f != null) {
			double[] caracteristicasClassificada = Classificacao_caracteristicas.naiveBayes(caracteristicasExtraidas);
			double[] caracPor = new double[caracteristicasClassificada.length];
			int cont = 0;
			for (double d : caracteristicasClassificada) {
				caracPor[cont] = d * 100;
				cont++;
			}
			classificaChief.setText(formatador.format(caracPor[0]) + "%");
			classificaKrusty.setText(formatador.format(caracPor[1]) + "%");
		}
	}

	private File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.JPG", "*.png",
				"*.PNG", "*.gif", "*.GIF", "*.bmp", "*.BMP"));
		fileChooser.setInitialDirectory(new File("src/imagens"));
		File imgSelec = fileChooser.showOpenDialog(null);
		try {
			if (imgSelec != null) {
				return imgSelec;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ImageView getImageOri() {
		return imageOri;
	}

	public ImageView getImagePro() {
		return imagePro;
	}

	public void setImagePro(ImageView imagePro) {
		this.imagePro = imagePro;
	}
}
