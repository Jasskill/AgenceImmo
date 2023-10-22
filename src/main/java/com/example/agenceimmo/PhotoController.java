package com.example.agenceimmo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PhotoController {
    @FXML
    private ImageView laPhotoAffiche;
    private Logement leLogement;

    private int nbPhotos;

    private int pointeurlaPhoto = 0;

    public void setAll(Logement unLogement){
        leLogement = unLogement;
        nbPhotos = unLogement.getLesPhotos().size();
        if(nbPhotos > 0){
            if(leLogement.getLesPhotos().get(pointeurlaPhoto).getLien() != ""){
                try{
                    String chemin = "http://192.168.1.27/uploads/" + leLogement.getLesPhotos().get(pointeurlaPhoto).getLien();
                    System.out.println(chemin);

                    Image image = new Image(chemin);
                    laPhotoAffiche.setImage(image);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            Alert uneAlerte = new Alert(Alert.AlertType.ERROR);
            uneAlerte.setContentText("pas de photo pour ce logement");
            uneAlerte.show();
        }
    }

    public void gauche(){
        pointeurlaPhoto--;
        if(pointeurlaPhoto == -1){
            pointeurlaPhoto = nbPhotos-1;
        }
        if(nbPhotos > 0){
            if(leLogement.getLesPhotos().get(pointeurlaPhoto).getLien() != ""){
                try{
                    String chemin = "http://192.168.1.27/uploads/" + leLogement.getLesPhotos().get(pointeurlaPhoto).getLien();
                    System.out.println(chemin);

                    Image image = new Image(chemin);
                    laPhotoAffiche.setImage(image);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            Alert uneAlerte = new Alert(Alert.AlertType.ERROR);
            uneAlerte.setContentText("pas de photo pour ce logement");
            uneAlerte.show();
        }
    }

    public void droite(){
        pointeurlaPhoto = (pointeurlaPhoto + 1)%nbPhotos;
        if(nbPhotos > 0){
            if(leLogement.getLesPhotos().get(pointeurlaPhoto).getLien() != ""){
                try{
                    String chemin = "http://192.168.1.27/uploads/" + leLogement.getLesPhotos().get(pointeurlaPhoto).getLien();
                    System.out.println(chemin);

                    Image image = new Image(chemin);
                    laPhotoAffiche.setImage(image);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            Alert uneAlerte = new Alert(Alert.AlertType.ERROR);
            uneAlerte.setContentText("pas de photo pour ce logement");
            uneAlerte.show();
        }
    }
}
